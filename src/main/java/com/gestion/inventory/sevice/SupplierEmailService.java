package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.dtorecust.SupplierOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class SupplierEmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String fromEmail;

    public void sendOrderEmail(SupplierOrderRequest req) {
        if (mailSender == null || fromEmail.isBlank()) {
            System.out.println("⚠️ Email no configurado — pedido no enviado");
            return;
        }
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(req.getSupplierEmail());
            helper.setSubject("📦 Pedido de Stock — " + req.getProductoNombre());
            helper.setText(buildHtml(req), true);

            mailSender.send(msg);
            System.out.println("✉️ Pedido enviado a proveedor: " + req.getSupplierEmail());

        } catch (Exception e) {
            System.err.println("❌ Error enviando pedido a proveedor: " + e.getMessage());
            throw new RuntimeException("No se pudo enviar el email al proveedor: " + e.getMessage());
        }
    }

    private String buildHtml(SupplierOrderRequest req) {
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String proveedor = req.getSupplierName() != null && !req.getSupplierName().isBlank()
                ? req.getSupplierName() : req.getSupplierEmail();

        return """
            <!DOCTYPE html>
            <html>
            <body style="margin:0;padding:0;background:#f3f4f6;font-family:Arial,sans-serif;">
              <table width="100%%" cellpadding="0" cellspacing="0">
                <tr><td align="center" style="padding:30px 10px;">
                  <table width="580" cellpadding="0" cellspacing="0"
                         style="background:#fff;border-radius:12px;overflow:hidden;box-shadow:0 4px 16px rgba(0,0,0,.08);">

                    <!-- BANNER -->
                    <tr><td style="background:linear-gradient(135deg,#1b6f6f,#0f4f4f);padding:30px 32px;">
                      <div style="font-size:42px;text-align:center;">📦</div>
                      <h1 style="color:#fff;text-align:center;margin:8px 0 0;font-size:22px;">
                        ORDEN DE PEDIDO
                      </h1>
                      <p style="color:#a7f3d0;text-align:center;margin:6px 0 0;font-size:13px;">
                        Sistema de Gestión de Inventario
                      </p>
                    </td></tr>

                    <!-- CUERPO -->
                    <tr><td style="padding:32px;">

                      <p style="color:#374151;font-size:15px;margin:0 0 24px;">
                        Estimado/a <b>%s</b>,<br/>
                        Le comunicamos que necesitamos realizar el siguiente pedido de reposición de stock:
                      </p>

                      <!-- TABLA DE PEDIDO -->
                      <table width="100%%" cellpadding="0" cellspacing="0"
                             style="background:#f9fafb;border-radius:8px;border:1px solid #e5e7eb;margin-bottom:24px;">
                        <tr style="background:#1b6f6f;">
                          <td colspan="2" style="padding:12px 20px;color:#fff;font-weight:700;font-size:14px;border-radius:8px 8px 0 0;">
                            📋 Detalle del Pedido
                          </td>
                        </tr>
                        <tr>
                          <td style="padding:12px 20px;color:#6b7280;font-size:13px;border-bottom:1px solid #e5e7eb;">Producto</td>
                          <td style="padding:12px 20px;color:#111827;font-weight:700;font-size:14px;text-align:right;border-bottom:1px solid #e5e7eb;">%s</td>
                        </tr>
                        <tr>
                          <td style="padding:12px 20px;color:#6b7280;font-size:13px;border-bottom:1px solid #e5e7eb;">Cantidad solicitada</td>
                          <td style="padding:12px 20px;color:#1b6f6f;font-weight:700;font-size:20px;text-align:right;border-bottom:1px solid #e5e7eb;">%d unidades</td>
                        </tr>
                        <tr>
                          <td style="padding:12px 20px;color:#6b7280;font-size:13px;border-bottom:1px solid #e5e7eb;">Fecha de solicitud</td>
                          <td style="padding:12px 20px;color:#374151;font-size:13px;text-align:right;border-bottom:1px solid #e5e7eb;">%s</td>
                        </tr>
                        <tr>
                          <td style="padding:12px 20px;color:#6b7280;font-size:13px;">Solicitado por</td>
                          <td style="padding:12px 20px;color:#374151;font-size:13px;text-align:right;">%s</td>
                        </tr>
                      </table>

                      %s

                      <p style="color:#374151;font-size:14px;margin:0;">
                        Por favor, confirme la disponibilidad y tiempo de entrega respondiendo a este correo.
                      </p>
                    </td></tr>

                    <!-- FOOTER -->
                    <tr><td style="background:#f9fafb;padding:16px 32px;border-top:1px solid #e5e7eb;text-align:center;">
                      <p style="margin:0;color:#9ca3af;font-size:12px;">
                        Sistema de Gestión de Inventario · Pedido generado automáticamente
                      </p>
                    </td></tr>

                  </table>
                </td></tr>
              </table>
            </body>
            </html>
            """.formatted(
                proveedor,
                req.getProductoNombre(),
                req.getCantidadPedida(),
                fecha,
                req.getSolicitadoPor() != null ? req.getSolicitadoPor() : "Sistema",
                req.getObservaciones() != null && !req.getObservaciones().isBlank()
                        ? "<div style='background:#fef3c7;border-left:4px solid #f59e0b;padding:12px 16px;border-radius:0 8px 8px 0;margin-bottom:20px;'>" +
                        "<b style='color:#92400e;font-size:13px;'>📝 Observaciones:</b>" +
                        "<p style='color:#78350f;font-size:13px;margin:4px 0 0;'>" + req.getObservaciones() + "</p></div>"
                        : ""
        );
    }
}