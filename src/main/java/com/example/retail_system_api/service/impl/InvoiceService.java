package com.example.retail_system_api.service.impl;

import com.example.retail_system_api.entity.InvoiceEntity;
import com.example.retail_system_api.entity.SaleEntity;
import com.example.retail_system_api.repo.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    public InvoiceEntity createInvoice(SaleEntity sale) {

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setSale(sale);
        invoice.setTotalAmount(sale.getTotalAmount());

        // generate unique invoice number
        invoice.setInvoiceNumber("INV-" + UUID.randomUUID().toString().substring(0,8).toUpperCase());

        invoice.setNotes("Generated after sale");

        return invoiceRepository.save(invoice);
    }
}
