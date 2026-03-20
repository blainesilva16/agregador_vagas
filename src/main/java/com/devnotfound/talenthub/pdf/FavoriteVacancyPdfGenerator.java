package com.devnotfound.talenthub.pdf;

import com.devnotfound.talenthub.dto.FavoriteVacancyReportDTO;
import com.devnotfound.talenthub.dto.FavoriteVacancyReportItemDTO;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class FavoriteVacancyPdfGenerator implements PdfGenerator<FavoriteVacancyReportDTO> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public byte[] generate(FavoriteVacancyReportDTO data) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

            Paragraph title = new Paragraph("Relatório de Vagas Favoritadas", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(15f);
            document.add(title);

            document.add(new Paragraph("Cliente: " + safe(data.customerName()), subtitleFont));
            document.add(new Paragraph("E-mail: " + safe(data.customerEmail()), textFont));
            document.add(new Paragraph("Gerado em: " + formatDateTime(data.generatedAt()), textFont));
            document.add(new Paragraph("Total de vagas favoritadas: " + safeNumber(data.totalFavorites()), textFont));
            document.add(Chunk.NEWLINE);

            if (data.favorites() == null || data.favorites().isEmpty()) {
                Paragraph empty = new Paragraph(
                        "Nenhuma vaga favoritada foi encontrada para este cliente.",
                        textFont
                );
                empty.setSpacingBefore(10f);
                document.add(empty);
            } else {
                PdfPTable table = new PdfPTable(new float[]{0.8f, 2.4f, 2.0f, 2.8f, 1.6f});
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);

                addHeaderCell(table, "ID");
                addHeaderCell(table, "Título");
                addHeaderCell(table, "Empresa");
                addHeaderCell(table, "Link");
                addHeaderCell(table, "Favoritada em");

                for (FavoriteVacancyReportItemDTO item : data.favorites()) {
                    addBodyCell(table, safeNumber(item.crawlerId()));
                    addBodyCell(table, safe(item.title()));
                    addBodyCell(table, safe(item.companyName()));
//                    addBodyCell(table, safe(item.cityName()));
                    addBodyCell(table, safe(item.postingLink()));
                    addBodyCell(table, formatDateTime(item.favoritedAt()));
                }

                document.add(table);
            }

            document.add(Chunk.NEWLINE);

            Paragraph footer = new Paragraph(
                    "Documento gerado automaticamente pelo sistema TalentHub.",
                    smallFont
            );
            footer.setAlignment(Element.ALIGN_RIGHT);
            document.add(footer);

            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF de vagas favoritadas.", e);
        }
    }

    private void addHeaderCell(PdfPTable table, String text) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        PdfPCell cell = new PdfPCell(new Phrase(text, headerFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6f);
        table.addCell(cell);
    }

    private void addBodyCell(PdfPTable table, String text) {
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
        PdfPCell cell = new PdfPCell(new Phrase(text, bodyFont));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5f);
        table.addCell(cell);
    }

    private String formatDateTime(java.time.LocalDateTime value) {
        return value == null ? "-" : value.format(DATE_TIME_FORMATTER);
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }

    private String safeNumber(Integer value) {
        return value == null ? "-" : String.valueOf(value);
    }
}