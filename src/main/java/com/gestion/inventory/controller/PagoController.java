package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Pago;
import com.gestion.inventory.domain.dtorecust.PagoRequest;
import com.gestion.inventory.useCase.pago.CreatePago;
import com.gestion.inventory.useCase.pago.GetAllPagos;
import com.gestion.inventory.useCase.pago.GetPago;
import com.gestion.inventory.useCase.pago.GetPagosByCodigoCompra;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.Map;

        @RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

            CreatePago createPago;
    GetAllPagos getAllPagos;
    GetPago getPagoById;
    GetPagosByCodigoCompra getPagosByCodigoCompra;
    AnularPago anularPago;

            public PagoController(
            CreatePago createPago,
            GetAllPagos getAllPagos,
            GetPago getPagoById,
            GetPagosByCodigoCompra getPagosByCodigoCompra,
            AnularPago anularPago) {

                        this.createPago = createPago;
                this.getAllPagos = getAllPagos;
                this.getPagoById = getPagoById;
                this.getPagosByCodigoCompra = getPagosByCodigoCompra;
                this.anularPago = anularPago;
            }

            @GetMapping
    public ResponseEntity<List<Pago>> getAll() {
                return ResponseEntity.ok(getAllPagos.execute());
            }

            @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
                try {
                        return ResponseEntity.ok(getPagoById.execute(id));
                    } catch (RuntimeException e) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(e.getMessage());
                    }
            }

            @GetMapping("/compra/{codigoCompra}")
    public ResponseEntity<List<Pago>> getByCodigoCompra(
            @PathVariable String codigoCompra) {

                        return ResponseEntity.ok(
                                getPagosByCodigoCompra.execute(codigoCompra));
            }

            @PostMapping
    public ResponseEntity<?> create(@RequestBody PagoRequest request) {
                try {
                        Pago pago = createPago.execute(request);
                        return ResponseEntity.status(HttpStatus.CREATED).body(pago);
                    } catch (RuntimeException e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                    }
            }

            @PatchMapping("/{id}/anular")
    public ResponseEntity<?> anular(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body) {
                try {
                        String motivo = body == null ? null : body.get("motivo");
                        return ResponseEntity.ok(anularPago.execute(id, motivo));
                    } catch (RuntimeException e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                    }
            }
}