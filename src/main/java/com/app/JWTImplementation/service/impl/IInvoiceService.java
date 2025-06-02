package com.app.JWTImplementation.service.impl;

import com.app.JWTImplementation.dto.InvoiceDTO;
import com.app.JWTImplementation.model.Invoice;

import java.util.List;

public interface IInvoiceService {
    List<Invoice> findAllInvoice();
    Invoice saveInvoice(InvoiceDTO invoiceDTO);
    Invoice findInvoiceById(Integer id);
    Invoice updateInvoiceById(Integer id, InvoiceDTO invoiceDetails);
    void deleteInvoiceById(Integer id);
}
