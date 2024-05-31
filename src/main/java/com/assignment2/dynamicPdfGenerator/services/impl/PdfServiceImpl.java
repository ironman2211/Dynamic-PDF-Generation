package com.assignment2.dynamicPdfGenerator.services.impl;

import com.assignment2.dynamicPdfGenerator.models.InvoiceRequest;
import com.assignment2.dynamicPdfGenerator.services.PdfService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private TemplateEngine templateEngine;

    public String generatePdf(InvoiceRequest invoiceRequest) throws IOException {
        Context context = new Context();
        context.setVariable("invoice", invoiceRequest);

        String htmlContent = templateEngine.process("invoice", context);

        String pdfDirectory = "invoices";
        String pdfPath = pdfDirectory + "/" + invoiceRequest.hashCode() + ".pdf";
        Path path = Paths.get(pdfPath);

        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            try (OutputStream outputStream = new FileOutputStream(pdfPath)) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.useFastMode();
                builder.withHtmlContent(htmlContent, null);
                builder.toStream(outputStream);
                builder.run();
            }
        }

        return pdfPath;
    }

    public byte[] getPdf(String pdfPath) throws IOException {
        Path path = Paths.get(pdfPath);
        return Files.readAllBytes(path);
    }
}
