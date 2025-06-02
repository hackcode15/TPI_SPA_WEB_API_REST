package com.app.JWTImplementation.service;

import com.app.JWTImplementation.dto.InvoiceDTO;
import com.app.JWTImplementation.dto.responses.TotalIncomeHistory;
import com.app.JWTImplementation.exceptions.InvalidReservationException;
import com.app.JWTImplementation.exceptions.ReserveNotFoundException;
import com.app.JWTImplementation.model.Customer;
import com.app.JWTImplementation.model.Invoice;
import com.app.JWTImplementation.model.Reserve;
import com.app.JWTImplementation.repository.InvoiceRepository;
import com.app.JWTImplementation.repository.ReserveRepository;
import com.app.JWTImplementation.service.impl.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService implements IInvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ReserveRepository reserveRepository;

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

}
