package com.gestion.inventory.useCase.sevice;

import com.gestion.inventory.domain.Alert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailNotificationService {
    private final JavaMailSender mailSender;
    @Value("${app.email.compras}")
    private String emailCompras;
    @Value("${app.email.gerencia}")
    private String emailGerencia;
    @Value("${app.email.from}")
    private String emailFrom;

    public EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarAlertaStockCritico(Alert alerta) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom(emailFrom);
        mensaje.setTo(emailCompras, emailGerencia);
        mensaje.setSubject("⚠️ ALERTA CRÍTICA: Stock Bajo - "
                + alerta.getProduct().getArticulo());

        mensaje.setText(String.format(
                "ALERTA DE INVENTARIO - ACCIÓN REQUERIDA EN 24 HORAS\n\n"
                        + "Producto: %s\n"
                        + "Código: %s\n"
                        + "Stock Actual: %d unidades\n"
                        + "Stock Mínimo: %d unidades\n"
                        + "Nivel Crítico (90%% mínimo): %d unidades\n\n"
                        + "DESCRIPCIÓN:\n%s\n\n"
                        + "ACCIÓN REQUERIDA:\n"
                        + "Gestionar orden de compra en las próximas 24 horas.\n\n"
                        + "Fecha de Detección: %s\n"
                        + "ID de Alerta: %d\n\n"
                        + "Este mensaje es automático. No responder.",
                alerta.getProduct().getArticulo(),
                alerta.getProduct().getCodigoBarras() != null
                        ? alerta.getProduct().getCodigoBarras() : "N/A",
                alerta.getStockActual(),
                alerta.getStockMinimo(),
                (int) (alerta.getStockMinimo() * 0.9),
                alerta.getDescripcion(),
                alerta.getFecha(),
                alerta.getIdAlert()
        ));

        try {
            mailSender.send(mensaje);
            System.out.println("✅ Email de alerta enviado para: "
                    + alerta.getProduct().getArticulo());
        } catch (Exception e) {
            System.err.println("❌ Error enviando email de alerta: " + e.getMessage());
        }
    }
}