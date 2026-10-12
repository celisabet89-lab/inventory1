package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.dtorecust.SupplierOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SupplierEmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String fromEmail;

    public void sendOrderEmail(
            SupplierOrderRequest req
    ) {

        if (
                mailSender == null ||
                        fromEmail == null ||
                        fromEmail.isBlank()
        ) {

            throw new RuntimeException(
                    "El servicio de correo no está configurado en el backend."
            );
        }

        try {

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            message,
                            true,
                            "UTF-8"
                    );

            helper.setFrom(fromEmail);

            helper.setTo(
                    req.getSupplierEmail()
            );

            helper.setSubject(
                    "Pedido de reposición - SIN LIMITE"
            );

            helper.setText(
                    buildHtml(req),
                    true
            );

            mailSender.send(message);

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "CORREO ENVIADO CORRECTAMENTE"
            );

            System.out.println(
                    "Proveedor: " +
                            req.getSupplierEmail()
            );

            System.out.println(
                    "Producto: " +
                            req.getProductoNombre()
            );

            System.out.println(
                    "Cantidad: " +
                            req.getCantidadPedida()
            );

            System.out.println(
                    "========================================"
            );

        } catch (Exception e) {

            System.err.println(
                    "========================================"
            );

            System.err.println(
                    "ERROR ENVIANDO CORREO"
            );

            System.err.println(
                    e.getMessage()
            );

            System.err.println(
                    "========================================"
            );

            throw new RuntimeException(
                    "No se pudo enviar el pedido por correo: " +
                            e.getMessage(),
                    e
            );
        }
    }

    private String buildHtml(
            SupplierOrderRequest req
    ) {

        String fecha =
                LocalDateTime.now()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "dd/MM/yyyy HH:mm"
                                )
                        );

        String proveedor =
                req.getSupplierName() != null &&
                        !req.getSupplierName().isBlank()
                        ? req.getSupplierName()
                        : req.getSupplierEmail();

        String producto =
                req.getProductoNombre() != null &&
                        !req.getProductoNombre().isBlank()
                        ? req.getProductoNombre()
                        : "Producto";

        String observaciones =
                req.getObservaciones() != null &&
                        !req.getObservaciones().isBlank()
                        ? req.getObservaciones()
                        : "Sin observaciones";

        String solicitadoPor =
                req.getSolicitadoPor() != null &&
                        !req.getSolicitadoPor().isBlank()
                        ? req.getSolicitadoPor()
                        : "Sistema";

        return """
            <!DOCTYPE html>
            <html lang="es">

            <head>

                <meta charset="UTF-8">

                <meta
                    name="viewport"
                    content="width=device-width, initial-scale=1.0"
                >

                <title>
                    Pedido de reposición
                </title>

            </head>

            <body
                style="
                    margin:0;
                    padding:0;
                    background:#f3f4f6;
                    font-family:Arial,Helvetica,sans-serif;
                "
            >

                <table
                    width="100%%"
                    cellpadding="0"
                    cellspacing="0"
                    border="0"
                >

                    <tr>

                        <td
                            align="center"
                            style="padding:30px 15px;"
                        >

                            <table
                                width="600"
                                cellpadding="0"
                                cellspacing="0"
                                border="0"
                                style="
                                    max-width:600px;
                                    width:100%%;
                                    background:#ffffff;
                                    border-radius:16px;
                                    overflow:hidden;
                                    box-shadow:0 8px 30px rgba(0,0,0,.10);
                                "
                            >

                                <tr>

                                    <td
                                        style="
                                            background:#0f766e;
                                            padding:28px 30px;
                                            text-align:center;
                                        "
                                    >

                                        <div
                                            style="
                                                font-size:42px;
                                                margin-bottom:8px;
                                            "
                                        >
                                            📦
                                        </div>

                                        <h1
                                            style="
                                                margin:0;
                                                color:#ffffff;
                                                font-size:24px;
                                                font-weight:700;
                                            "
                                        >
                                            PEDIDO DE REPOSICIÓN
                                        </h1>

                                        <p
                                            style="
                                                margin:8px 0 0;
                                                color:#ccfbf1;
                                                font-size:13px;
                                            "
                                        >
                                            SIN LIMITE
                                        </p>

                                    </td>

                                </tr>


                                <tr>

                                    <td
                                        style="
                                            padding:30px;
                                        "
                                    >

                                        <p
                                            style="
                                                margin:0 0 22px;
                                                color:#374151;
                                                font-size:15px;
                                                line-height:1.6;
                                            "
                                        >

                                            Estimado/a
                                            <strong>
                                                %s
                                            </strong>,

                                            <br>

                                            Solicitamos la disponibilidad
                                            del siguiente producto:

                                        </p>


                                        <table
                                            width="100%%"
                                            cellpadding="0"
                                            cellspacing="0"
                                            border="0"
                                            style="
                                                border:1px solid #dbe5e3;
                                                border-radius:10px;
                                                overflow:hidden;
                                            "
                                        >

                                            <tr>

                                                <td
                                                    colspan="2"
                                                    style="
                                                        padding:13px 18px;
                                                        background:#ecfdf9;
                                                        color:#0f766e;
                                                        font-size:14px;
                                                        font-weight:700;
                                                    "
                                                >

                                                    📋 Detalle del pedido

                                                </td>

                                            </tr>


                                            <tr>

                                                <td
                                                    style="
                                                        width:45%%;
                                                        padding:13px 18px;
                                                        color:#64748b;
                                                        font-size:13px;
                                                        border-top:1px solid #e5e7eb;
                                                    "
                                                >

                                                    Producto

                                                </td>

                                                <td
                                                    style="
                                                        padding:13px 18px;
                                                        color:#134e4a;
                                                        font-size:14px;
                                                        font-weight:700;
                                                        text-align:right;
                                                        border-top:1px solid #e5e7eb;
                                                    "
                                                >

                                                    %s

                                                </td>

                                            </tr>


                                            <tr>

                                                <td
                                                    style="
                                                        padding:13px 18px;
                                                        color:#64748b;
                                                        font-size:13px;
                                                        border-top:1px solid #e5e7eb;
                                                    "
                                                >

                                                    Cantidad solicitada

                                                </td>

                                                <td
                                                    style="
                                                        padding:13px 18px;
                                                        color:#0f766e;
                                                        font-size:20px;
                                                        font-weight:800;
                                                        text-align:right;
                                                        border-top:1px solid #e5e7eb;
                                                    "
                                                >

                                                    %d unidades

                                                </td>

                                            </tr>


                                            <tr>

                                                <td
                                                    style="
                                                        padding:13px 18px;
                                                        color:#64748b;
                                                        font-size:13px;
                                                        border-top:1px solid #e5e7eb;
                                                    "
                                                >

                                                    Fecha

                                                </td>

                                                <td
                                                    style="
                                                        padding:13px 18px;
                                                        color:#374151;
                                                        font-size:13px;
                                                        text-align:right;
                                                        border-top:1px solid #e5e7eb;
                                                    "
                                                >

                                                    %s

                                                </td>

                                            </tr>


                                            <tr>

                                                <td
                                                    style="
                                                        padding:13px 18px;
                                                        color:#64748b;
                                                        font-size:13px;
                                                        border-top:1px solid #e5e7eb;
                                                    "
                                                >

                                                    Solicitado por

                                                </td>

                                                <td
                                                    style="
                                                        padding:13px 18px;
                                                        color:#374151;
                                                        font-size:13px;
                                                        text-align:right;
                                                        border-top:1px solid #e5e7eb;
                                                    "
                                                >

                                                    %s

                                                </td>

                                            </tr>

                                        </table>


                                        <div
                                            style="
                                                margin-top:22px;
                                                padding:16px;
                                                background:#f8fafc;
                                                border-radius:10px;
                                                border-left:4px solid #0f766e;
                                            "
                                        >

                                            <p
                                                style="
                                                    margin:0 0 5px;
                                                    color:#134e4a;
                                                    font-size:13px;
                                                    font-weight:700;
                                                "
                                            >

                                                📝 Observaciones

                                            </p>

                                            <p
                                                style="
                                                    margin:0;
                                                    color:#475569;
                                                    font-size:13px;
                                                    line-height:1.5;
                                                "
                                            >

                                                %s

                                            </p>

                                        </div>


                                        <div
                                            style="
                                                margin-top:24px;
                                                padding:16px;
                                                background:#f0fdfa;
                                                border-radius:10px;
                                                text-align:center;
                                            "
                                        >

                                            <p
                                                style="
                                                    margin:0;
                                                    color:#0f766e;
                                                    font-size:14px;
                                                    font-weight:700;
                                                "
                                            >

                                                Por favor, confirme

                                                disponibilidad

                                                y tiempo de entrega.

                                            </p>

                                        </div>

                                    </td>

                                </tr>


                                <tr>

                                    <td
                                        style="
                                            padding:18px 30px;
                                            background:#f8fafc;
                                            border-top:1px solid #e5e7eb;
                                            text-align:center;
                                        "
                                    >

                                        <p
                                            style="
                                                margin:0;
                                                color:#94a3b8;
                                                font-size:11px;
                                            "
                                        >

                                            SIN LIMITE ·
                                            Sistema de Gestión de Inventario

                                        </p>

                                        <p
                                            style="
                                                margin:5px 0 0;
                                                color:#cbd5e1;
                                                font-size:10px;
                                            "
                                        >

                                            Pedido generado automáticamente

                                        </p>

                                    </td>

                                </tr>

                            </table>

                        </td>

                    </tr>

                </table>

            </body>

            </html>
            """.formatted(
                escaparHtml(proveedor),
                escaparHtml(producto),
                req.getCantidadPedida(),
                fecha,
                escaparHtml(solicitadoPor),
                escaparHtml(observaciones)
        );
    }

    private String escaparHtml(
            String valor
    ) {

        if (valor == null) {
            return "";
        }

        return valor
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#039;");
    }
}