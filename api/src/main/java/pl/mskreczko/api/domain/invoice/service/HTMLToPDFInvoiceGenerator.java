package pl.mskreczko.api.domain.invoice.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;
import pl.mskreczko.api.domain.invoice.Invoice;

import java.io.*;
import java.nio.file.Files;

@Service
public class HTMLToPDFInvoiceGenerator implements InvoiceGenerator {

    private final String TEMPLATE_PATH = "templates/html_invoice.html";

    String fillInvoiceTemplate(Invoice invoice) throws IOException {
        File resource = new ClassPathResource(TEMPLATE_PATH).getFile();
        String content = new String(Files.readAllBytes(resource.toPath()));

        int idx = content.indexOf("<tbody>");
        StringBuilder items = new StringBuilder();
        for (final var t : invoice.getTickets()) {
            items.append("<tr>");
            items.append("<td>").append(t.getFlight().getDepartureAirport().getIcao()).append(" to ")
                    .append(t.getFlight().getDestinationAirport().getIcao()).append(" | ").append(t.getTravelClass()).append("</td>");
            items.append("<td>1</td>");
            items.append("<td>").append(t.getPrice()).append("$</td>");
            items.append("</tr>");
        }
        items.append("<tr><td></td><td>Grand Total</td><td>").append(invoice.getTotalValue()).append("$</td></tr>");
        return new StringBuilder(content).insert(idx, items).toString();
    }

    @Override
    public void generateInvoice(String outputFilename, Invoice invoice) {

        String content = null;
        try {
            content = fillInvoiceTemplate(invoice);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document document = Jsoup.parse(content, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

        try (OutputStream outputStream = new FileOutputStream(outputFilename)) {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            renderer.setDocumentFromString(document.html());
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
