package com.app.JWTImplementation.service;

import com.app.JWTImplementation.dto.EmailRegisterDTO;
import com.app.JWTImplementation.dto.EmailInvoiceDTO;
import com.app.JWTImplementation.service.impl.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendEmail(EmailRegisterDTO email) throws MessagingException {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email.getAddressee());
            helper.setSubject(email.getSubjet());

            // si queremos enviar texto plano
            //helper.setText("aca iria el texto plano");
            //helper.setText(email.getMensaje());

            // procesar por plantilla html
            Context context = new Context();
            context.setVariable("message", email.getMessage());

            String contentHTML = templateEngine.process("register-email", context);

            helper.setText(contentHTML, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error sending email: " + e.getMessage(), e);
        }

    }

    @Override
    public void sendInvoiceEmail(EmailInvoiceDTO emailDTO) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(emailDTO.getAddressee());
        helper.setSubject("Factura de tu reserva - SPA Sentirse Bien");

        // Procesar plantilla específica para facturas
        Context context = new Context();
        context.setVariable("customerName", emailDTO.getCustomerName());
        String htmlContent = templateEngine.process("invoice-email", context);

        helper.setText(htmlContent, true);

        // Adjuntar PDF
        helper.addAttachment("Factura.pdf", new ByteArrayResource(emailDTO.getPdfAttachment()));

        javaMailSender.send(message);

    }

}
