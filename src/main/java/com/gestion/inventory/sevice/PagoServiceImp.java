package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Movements;
import com.gestion.inventory.domain.Pago;
import com.gestion.inventory.domain.dtorecust.PagoRequest;
import com.gestion.inventory.repository.PagoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service

public class PagoServiceImp implements IPagoService {

    PagoRepository pagoRepository;
    IMovementsService movementsService;

           public PagoServiceImp(
            PagoRepository pagoRepository,
            IMovementsService movementsService) {
               this.pagoRepository = pagoRepository;
               this.movementsService = movementsService;
           }

           @Override
    public List<Pago> getAll() {
               return pagoRepository.findAllByOrderByFechaDesc();
           }

           @Override
    public Pago getById(Long id) {
               return pagoRepository.findById(id)
                               .orElseThrow(() -> new RuntimeException("No se encontró el pago con ID: "));
           }

           @Override
    public List<Pago> getByCodigoCompra(String codigoCompra) {
               return pagoRepository
                               .findByCodigoCompraOrderByFechaDesc(codigoCompra);
           }

           @Override
    @Transactional
    public Pago registrarPago(PagoRequest request) {
               if (request == null
                               || request.getCodigoCompra() == null
                               || request.getCodigoCompra().trim().isEmpty()) {
                       throw new RuntimeException("El código de compra es obligatorio");
                   }
               if (request.getMonto() == null
                               || request.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
                       throw new RuntimeException("El monto debe ser mayor que cero");
                   }
               if (request.getFormaPago() == null
                               || request.getFormaPago().trim().isEmpty()) {
                       throw new RuntimeException("La forma de pago es obligatoria");
                   }

                       String codigo = request.getCodigoCompra().trim();
               List<Movements> movimientos = comprasActivas(codigo);

                       if (movimientos.isEmpty()) {
                       throw new RuntimeException(
                                                   "No se encontró una compra activa con el código: " + codigo);
                   }

                       BigDecimal totalCompra = totalDe(movimientos);
               if (totalCompra.compareTo(BigDecimal.ZERO) <= 0) {
                       throw new RuntimeException("La compra no tiene un total válido para pagar");
                   }

                       BigDecimal totalPagado = totalPagadoActivo(codigo);
               BigDecimal saldoPendiente = totalCompra.subtract(totalPagado);

                       if (saldoPendiente.compareTo(BigDecimal.ZERO) <= 0) {
                       throw new RuntimeException("Esta compra ya está pagada");
                   }
               if (request.getMonto().compareTo(saldoPendiente) > 0) {
                       throw new RuntimeException("El monto supera el saldo pendiente de Bs ");
                   }

                       Pago pago = new Pago();
               pago.setCodigoCompra(codigo);
               pago.setMonto(request.getMonto());
               pago.setFormaPago(request.getFormaPago().trim());
               pago.setObservaciones(request.getObservaciones());
               pago.setEstado("ACTIVO");
               Pago guardado = pagoRepository.save(pago);

                       actualizarEstadoPago(movimientos, totalCompra,
                                       totalPagado.add(request.getMonto()));

                       return guardado;
           }

           @Override
    @Transactional
    public Pago anularPago(Long idPago, String motivo) {
               Pago pago = getById(idPago);

                       if (!esActivo(pago)) {
                       throw new RuntimeException("El pago ya está anulado");
                   }

                       pago.setEstado("ANULADO");
               pago.setMotivoAnulacion(
                               motivo == null || motivo.trim().isEmpty() ? null : motivo.trim());
               pago.setFechaAnulacion(LocalDateTime.now());
               Pago anulado = pagoRepository.save(pago);

                       String codigo = pago.getCodigoCompra() == null
                               ? "" : pago.getCodigoCompra().trim();
               List<Movements> movimientos = comprasActivas(codigo);

                       if (!movimientos.isEmpty()) {
                       actualizarEstadoPago(movimientos, totalDe(movimientos),
                                       totalPagadoActivo(codigo));
                   }

                       return anulado;
           }

           // ── Auxiliares ────────────────────────────────────────────────

           private static boolean esActivo(Pago pago) {
               return pago.getEstado() == null
                               || pago.getEstado().trim().isEmpty()
                               || "ACTIVO".equalsIgnoreCase(pago.getEstado().trim());
           }

           private List<Movements> comprasActivas(String codigo) {
               return movementsService.getAll()
                               .stream()
                               .filter(m -> m.getCodigo() != null
                                       && m.getCodigo().trim().equalsIgnoreCase(codigo))
                               .filter(m -> m.getTipo() != null
                                       && m.getTipo().trim().equalsIgnoreCase("ENTRADA"))
                               .filter(m -> m.getEstadoMovimiento() == null
                                       || m.getEstadoMovimiento().trim().isEmpty()
                                       || m.getEstadoMovimiento().equalsIgnoreCase("ACTIVO"))
                               .toList();
           }

           /**
     * Suma el total de la compra. Si una línea no trae total guardado se
     * calcula como cantidad × precio del producto.
     */
           private BigDecimal totalDe(List<Movements> movimientos) {
               return movimientos.stream()
                               .map(m -> {
                               if (m.getTotal() != null
                                               && m.getTotal().compareTo(BigDecimal.ZERO) > 0) {
                                       return m.getTotal();
                                   }
                               if (m.getCantidad() != null
                                               && m.getProduct() != null
                                               && m.getProduct().getPrecio() != null) {
                                       return m.getProduct().getPrecio()
                                                       .multiply(BigDecimal.valueOf(m.getCantidad()));
                                   }
                               return BigDecimal.ZERO;
                           })
                               .reduce(BigDecimal.ZERO, BigDecimal::add);
           }

           private BigDecimal totalPagadoActivo(String codigo) {
               return pagoRepository
                               .findByCodigoCompraOrderByFechaDesc(codigo)
                               .stream()
                               .filter(PagoServiceImp::esActivo)
                               .map(Pago::getMonto)
                               .filter(monto -> monto != null)
                               .reduce(BigDecimal.ZERO, BigDecimal::add);
           }

           private void actualizarEstadoPago(
            List<Movements> movimientos,
            BigDecimal totalCompra,
            BigDecimal totalPagado) {

                       String estadoPago;
               if (totalPagado.compareTo(BigDecimal.ZERO) <= 0) {
                       estadoPago = "PENDIENTE";
                   } else if (totalPagado.compareTo(totalCompra) >= 0) {
                       estadoPago = "PAGADO";
                   } else {
                       estadoPago = "PARCIAL";
                   }

                       for (Movements movimiento : movimientos) {
                       movimiento.setEstadoPago(estadoPago);
                       movementsService.save(movimiento);
                   }
           }
}