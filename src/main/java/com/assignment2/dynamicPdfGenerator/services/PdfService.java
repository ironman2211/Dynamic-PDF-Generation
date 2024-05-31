package com.assignment2.dynamicPdfGenerator.services;

import com.assignment2.dynamicPdfGenerator.models.InvoiceRequest;

import java.io.IOException;

public interface PdfService {
    public String generatePdf(InvoiceRequest invoiceRequest) throws IOException;

    public byte[] getPdf(String pdfPath) throws IOException;
}
