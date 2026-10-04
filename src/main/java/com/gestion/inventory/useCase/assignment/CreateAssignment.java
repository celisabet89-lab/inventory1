package com.gestion.inventory.useCase.assignment;

import com.gestion.inventory.domain.Assignment;
import com.gestion.inventory.domain.Cliente;
import com.gestion.inventory.domain.Onu;
import com.gestion.inventory.domain.User;
import com.gestion.inventory.domain.dtorecust.AssignmentRequets;
import com.gestion.inventory.sevice.IAssignmentService;
import com.gestion.inventory.sevice.IClienteService;
import com.gestion.inventory.sevice.IOnuservice;
import com.gestion.inventory.sevice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
public class CreateAssignment {

    @Autowired
    IAssignmentService assignmentService;
    @Autowired
    IOnuservice onuService;
    @Autowired
    IClienteService clienteService;

    @Autowired
    IUserService userService;

    @Transactional
    public Assignment execute(AssignmentRequets request) {
        validarDatosObligatorios(request);

        validarProtocoloDocumentacion(request);

        Onu onu = validarYObtenerOnu(request.getIdOnu());
        Cliente cliente = validarYObtenerCliente(request.getIdCliente());
        User tecnico = validarYObtenerTecnico(request.getIdUser());

        // VALIDACIÓN 4: ONU no debe estar ya asignada
        if (onu.getEstado() != null && onu.getEstado().equals("ASIGNADA")) {
            throw new IllegalStateException(
                    "La ONU ya está asignada. Serial: " + onu.getSerial()
            );
        }

        // Crear la asignación
        Assignment assignment = new Assignment();
        assignment.setFecha(request.getFecha());
        assignment.setDatos(request.getDatos());
        assignment.setFotosUrls(request.getFotosUrls());
        assignment.setItemsChecklist(request.getItemsChecklist());
        assignment.setChecklistCompletado(request.getChecklistCompletado());
        assignment.setOnu(onu);
        assignment.setCliente(cliente);
        assignment.setUser(tecnico);

        // Actualizar estado de la ONU
        onu.setEstado("ASIGNADA");
        onu.setCliente(cliente);
        onuService.save(onu);

        return assignmentService.save(assignment);
    }

    private void validarDatosObligatorios(AssignmentRequets request) {
        if (request.getIdOnu() == null) {
            throw new IllegalArgumentException("El ID de la ONU es obligatorio");
        }
        if (request.getIdCliente() == null) {
            throw new IllegalArgumentException("El ID del cliente es obligatorio");
        }
        if (request.getIdUser() == null) {
            throw new IllegalArgumentException("El técnico responsable es obligatorio");
        }
        if (!StringUtils.hasText(request.getDatos())) {
            throw new IllegalArgumentException("Los datos de la asignación son obligatorios");
        }
    }

    /**
     * CUMPLE OBJETIVO 4: Protocolo obligatorio de documentación
     * - Fotos obligatorias
     * - Checklist obligatorio
     * - Items del checklist mínimos
     */
    private void validarProtocoloDocumentacion(AssignmentRequets request) {
        // Validar fotos (mínimo 2: antes y después)
        if (!StringUtils.hasText(request.getFotosUrls())) {
            throw new IllegalArgumentException(
                    "DOCUMENTACIÓN OBLIGATORIA: Se requieren al menos 2 fotos " +
                            "(estado de ONU y ubicación de instalación)"
            );
        }

        String[] fotos = request.getFotosUrls().split(",");
        if (fotos.length < 2) {
            throw new IllegalArgumentException(
                    "DOCUMENTACIÓN OBLIGATORIA: Se requieren mínimo 2 fotos. " +
                            "Subidas: " + fotos.length
            );
        }

        // Validar checklist
        if (!StringUtils.hasText(request.getItemsChecklist())) {
            throw new IllegalArgumentException(
                    "DOCUMENTACIÓN OBLIGATORIA: El checklist es requerido"
            );
        }

        String[] items = request.getItemsChecklist().split(",");
        if (items.length < 3) {
            throw new IllegalArgumentException(
                    "DOCUMENTACIÓN OBLIGATORIA: El checklist debe tener al menos 3 items " +
                            "(ej: verificación de señal, cable instalado, ONU funcional). " +
                            "Items: " + items.length
            );
        }

        // Validar que el checklist esté marcado como completado
        if (request.getChecklistCompletado() == null || !request.getChecklistCompletado()) {
            throw new IllegalArgumentException(
                    "DOCUMENTACIÓN OBLIGATORIA: El checklist debe estar completado " +
                            "antes de registrar la asignación"
            );
        }
    }

    private Onu validarYObtenerOnu(Long onuId) {
        Onu onu = onuService.getById(onuId);
        if (onu == null) {
            throw new IllegalArgumentException("ONU no encontrada con ID: " + onuId);
        }
        return onu;
    }

    private Cliente validarYObtenerCliente(Long clienteId) {
        Cliente cliente = clienteService.getById(clienteId);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado con ID: " + clienteId);
        }
        return cliente;
    }

    private User validarYObtenerTecnico(Long userId) {
        User user = userService.getById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Usuario/Técnico no encontrado con ID: " + userId);
        }
        return user;
    }
}