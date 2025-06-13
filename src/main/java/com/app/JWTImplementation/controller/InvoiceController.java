package com.app.JWTImplementation.controller;

import com.app.JWTImplementation.dto.InvoiceDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.model.Reserve;
import com.app.JWTImplementation.service.InvoiceService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<ApiResponse<InvoiceDTO>> createInvoiceForReservation(
            @RequestParam("reserveId") Integer reserveId,
            @RequestParam("method") String paymentMethod
    ) {

        ApiResponse<InvoiceDTO> response = new ApiResponse<>(
                "Success",
                "Invoice generated sucessfully",
                invoiceService.generateInvoiceForReserve(reserveId, paymentMethod)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<String>> generatedInvoiceForReserveId(@PathVariable("id") Integer id) throws JRException {

        // prueba local
        //invoiceService.generateInvoicePDF(id);

        invoiceService.generateAndSendInvoice(id);

        ApiResponse<String> response = new ApiResponse<>(
                "Success",
                "Invoice generated successfully",
                null
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
