package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailNotificationService {


    @Autowired(required = false)
    private JavaMailSender mailSender;

    public void sendStockAlert(String to, String producto, int stockActual, int stockMinimo) {
        if (mailSender == null) {
            System.out.println("⚠️ Email no configurado. Alerta para: " + to + " | Producto: " + producto);
            return;
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("⚠️ Alerta de Stock: " + producto);
            helper.setText(
                    "<h2>Alerta de Stock Bajo</h2>" +
                            "<p><b>Producto:</b> " + producto + "</p>" +
                            "<p><b>Stock actual:</b> " + stockActual + "</p>" +
                            "<p><b>Stock mínimo:</b> " + stockMinimo + "</p>" +
                            "<p>Por favor gestiona el pedido dentro de las próximas 24 horas.</p>",
                    true
            );
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error enviando email: " + e.getMessage());
        }
    }

    public void enviarAlertaStockCritico(Alert alertaGuardada) {
    }
}