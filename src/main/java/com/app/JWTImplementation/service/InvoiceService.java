package com.app.JWTImplementation.service;

import com.app.JWTImplementation.dto.EmailInvoiceDTO;
import com.app.JWTImplementation.dto.InvoiceDTO;
import com.app.JWTImplementation.dto.projection.InvoiceProjection;
import com.app.JWTImplementation.dto.responses.TotalIncomeHistory;
import com.app.JWTImplementation.exceptions.InvalidReservationException;
import com.app.JWTImplementation.exceptions.ReserveNotFoundException;
import com.app.JWTImplementation.model.Customer;
import com.app.JWTImplementation.model.Invoice;
import com.app.JWTImplementation.model.Reserve;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.repository.CustomerRepository;
import com.app.JWTImplementation.repository.InvoiceRepository;
import com.app.JWTImplementation.repository.ReserveRepository;
import com.app.JWTImplementation.service.impl.IInvoiceService;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class InvoiceService implements IInvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public InvoiceDTO generateInvoiceForReserve(Integer reserveId, String paymentMethod) {

        Reserve reserve = reserveRepository.findById(reserveId)
                .orElseThrow(() -> new ReserveNotFoundException(reserveId));

        // Verificar si ya tiene factura
        if (reserve.getInvoice() != null) {
            throw new InvalidReservationException("Esta reserva ya tiene una factura asociada");
        }

        Invoice.PaymentMethod method = Invoice.PaymentMethod.valueOf(
                paymentMethod.toUpperCase());

        // Calcular valores de facturación
        BigDecimal subtotal = reserve.getPricePaid();
        BigDecimal taxRate = new BigDecimal("0.21"); // 21% IVA
        BigDecimal taxAmount = subtotal.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(taxAmount);

        // Obtener datos del cliente
        //Customer customer = (Customer) reserve.getUser();
        Customer customer = reserve.getUser().asCustomer();

        // Crear factura
        Invoice invoice = Invoice.builder()
                .invoiceNumber(generateInvoiceNumber())
                .issueDate(LocalDateTime.now())
                .paymentMethod(method)
                .subtotal(subtotal)
                .taxAmount(taxAmount)
                .total(total)
                .customerName(customer.getFirstName() + " " + customer.getLastName())
                .customerEmail(customer.getEmail())
                .customerIdentification(customer.getPhone())
                .reserve(reserve)
                .isPaid(true)
                .build();

        // Guardar y asociar la factura a la reserva
        invoice = invoiceRepository.save(invoice);
        reserve.setInvoice(invoice);
        reserve.setStatus(Reserve.StatusReserve.CONFIRMED); // actualizo el estado de la reserva al completar el pago
        reserveRepository.save(reserve);

        return InvoiceDTO.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .issueDate(invoice.getIssueDate().toLocalDate())
                .paymentMethod(method.name())
                .subtotal(invoice.getSubtotal())
                .taxAmount(invoice.getTaxAmount())
                .total(invoice.getTotal())
                .serviceName(reserve.getService().getName())
                .customerName(invoice.getCustomerName())
                .customerEmail(invoice.getCustomerEmail())
                .customerIdentification(invoice.getCustomerIdentification())
                .reserveId(reserve.getId())
                .isPaid(invoice.getIsPaid() == true ? "SI" : "NO")
                .build();

    }

    private String generateInvoiceNumber() {
        return "INV-" + LocalDateTime.now().getYear() + "-"
                + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public List<Invoice> findAllInvoice() {
        return null;
    }

    @Override
    public Invoice saveInvoice(InvoiceDTO invoiceDTO) {
        Reserve reserve = reserveRepository.findById(invoiceDTO.getReserveId())
                .orElseThrow(() -> new ReserveNotFoundException(invoiceDTO.getReserveId()));

        // Verificar si ya tiene factura
        if (reserve.getInvoice() != null) {
            throw new IllegalStateException("La reserva ya tiene una factura asociada");
        }

        Invoice invoice = mapDtoToEntity(invoiceDTO, reserve);
        invoice = invoiceRepository.save(invoice);

        // Asociar la factura a la reserva
        reserve.setInvoice(invoice);
        reserveRepository.save(reserve);

        return invoice;
    }

    private Invoice mapDtoToEntity(InvoiceDTO dto, Reserve reserve) {
        Invoice.PaymentMethod paymentMethod = Invoice.PaymentMethod.valueOf(
                dto.getPaymentMethod().toUpperCase());

        return Invoice.builder()
                .invoiceNumber(dto.getInvoiceNumber())
                .issueDate(LocalDateTime.now())
                .paymentMethod(paymentMethod)
                .subtotal(dto.getSubtotal())
                .taxAmount(dto.getTaxAmount())
                .total(dto.getTotal())
                .customerName(dto.getCustomerName())
                .customerEmail(dto.getCustomerEmail())
                .customerIdentification(dto.getCustomerIdentification())
                .reserve(reserve)
                .isPaid("SI".equalsIgnoreCase(dto.getIsPaid()))
                .build();
    }

    @Override
    public Invoice findInvoiceById(Integer id) {
        return null;
    }

    @Override
    public Invoice updateInvoiceById(Integer id, InvoiceDTO invoiceDetails) {
        return null;
    }

    @Override
    public void deleteInvoiceById(Integer id) {

    }


    // Metodo para generar la factura - solo para pruebas locales
    /*@Transactional
    public void generateInvoicePDF(Integer idReserve) throws JRException {

        String destinationPath = "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "static" + File.separator +
                "ReportGenerated.pdf";

        String filePath = "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                "report" + File.separator +
                "Report.jrxml";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        InvoiceProjection details = invoiceRepository.findByReserveId(idReserve)
                .orElseThrow(() -> new ReserveNotFoundException(idReserve));

        // BigDecimal total = details.getSubtotal().add(details.getTaxAmount());

        Reserve reserve = reserveRepository.findById(details.getReserveId())
                .orElseThrow(() -> new ReserveNotFoundException(idReserve));

        String paymentMethod = switch (details.getPaymentMethod()) {
            case "CASH" -> "Efectivo";
            case "CREDIT_CARD" -> "Tarjeta de credito";
            case "DEBIT_CARD" -> "Tarjeta de debito";
            case "BANK_TRANSFER" -> "Transferencia bancaria";
            default -> "OTHER";
        };

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_fullname", details.getCustomerName());
        parameters.put("customer_email", details.getCustomerEmail());
        parameters.put("customer_phone", details.getCustomerIdentification());
        parameters.put("invoice_number", details.getInvoiceNumber());
        parameters.put("invoice_issue_date", formatter.format(LocalDateTime.now()));
        parameters.put("payment_method", paymentMethod);
        parameters.put("total_paid", details.getTotal());
        parameters.put("reserve_id", details.getReserveId());
        parameters.put("service_name", reserve.getService().getName());
        parameters.put("subtotal",  details.getSubtotal());
        parameters.put("tax_amount", details.getTaxAmount());
        parameters.put("total", details.getTotal());
        parameters.put("imageDir", "classpath:/static/images/");

        JasperReport report = JasperCompileManager.compileReport(filePath);
        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfFile(print, destinationPath);

        System.out.println("Report created successfully");

    }*/

    // metodo para generar y enviar la factura por correo - uso en produccion
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void generateAndSendInvoice(Integer idReserve) {
        taskExecutor.execute(() -> {
            try {
                // 1. Obtener los datos de la factura usando tu proyección
                InvoiceProjection invoiceProjection = invoiceRepository.findByReserveId(idReserve)
                        .orElseThrow(() -> new ReserveNotFoundException(idReserve));

                // 2. Generar el PDF directamente desde la proyección
                byte[] pdfBytes = generateInvoicePDF(invoiceProjection);

                // 3. Enviar el correo
                EmailInvoiceDTO emailDTO = EmailInvoiceDTO.builder()
                        .addressee(invoiceProjection.getCustomerEmail())
                        .customerName(invoiceProjection.getCustomerName())
                        .pdfAttachment(pdfBytes)
                        .build();

                emailService.sendInvoiceEmail(emailDTO);

            } catch (Exception e) {
                System.out.println("Error al enviar factura para reserva " + idReserve);
            }
        });
    }

    /*private byte[] generateInvoicePDF(InvoiceProjection details) throws JRException {

        String destinationPath = "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "static" + File.separator +
                "ReportGenerated.pdf";

        String filePath = "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                "report" + File.separator +
                "Report.jrxml";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String paymentMethod = switch (details.getPaymentMethod()) {
            case "CASH" -> "Efectivo";
            case "CREDIT_CARD" -> "Tarjeta de credito";
            case "DEBIT_CARD" -> "Tarjeta de debito";
            case "BANK_TRANSFER" -> "Transferencia bancaria";
            default -> "OTHER";
        };

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_fullname", details.getCustomerName());
        parameters.put("customer_email", details.getCustomerEmail());
        parameters.put("customer_phone", details.getCustomerIdentification());
        parameters.put("invoice_number", details.getInvoiceNumber());
        parameters.put("invoice_issue_date", formatter.format(details.getIssueDate()));
        parameters.put("payment_method", paymentMethod);
        parameters.put("total_paid", details.getTotal());
        parameters.put("reserve_id", details.getReserveId());
        parameters.put("service_name", details.getServiceName());
        parameters.put("subtotal",  details.getSubtotal());
        parameters.put("tax_amount", details.getTaxAmount());
        parameters.put("total", details.getTotal());
        parameters.put("imageDir", "classpath:/static/images/");

        JasperReport report = JasperCompileManager.compileReport(filePath);
        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

        return JasperExportManager.exportReportToPdf(print);

    }*/

    private byte[] generateInvoicePDF(InvoiceProjection details) throws JRException {
        // Usa ClassLoader para cargar el template desde recursos
        InputStream templateStream = getClass().getClassLoader()
                .getResourceAsStream("templates/report/Report.jrxml");

        if (templateStream == null) {
            throw new RuntimeException("No se pudo cargar el template de la factura");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String paymentMethod = switch (details.getPaymentMethod()) {
            case "CASH" -> "Efectivo";
            case "CREDIT_CARD" -> "Tarjeta de credito";
            case "DEBIT_CARD" -> "Tarjeta de debito";
            case "BANK_TRANSFER" -> "Transferencia bancaria";
            default -> "OTHER";
        };

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_fullname", details.getCustomerName());
        parameters.put("customer_email", details.getCustomerEmail());
        parameters.put("customer_phone", details.getCustomerIdentification());
        parameters.put("invoice_number", details.getInvoiceNumber());
        parameters.put("invoice_issue_date", formatter.format(details.getIssueDate()));
        parameters.put("payment_method", paymentMethod);
        parameters.put("total_paid", details.getTotal());
        parameters.put("reserve_id", details.getReserveId());
        parameters.put("service_name", details.getServiceName());
        parameters.put("subtotal", details.getSubtotal());
        parameters.put("tax_amount", details.getTaxAmount());
        parameters.put("total", details.getTotal());
        parameters.put("imageDir", "classpath:/static/images/");

        JasperReport report = JasperCompileManager.compileReport(templateStream);
        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

        return JasperExportManager.exportReportToPdf(print);
    }

}
