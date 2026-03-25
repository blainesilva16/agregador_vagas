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
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FavoriteVacancyPdfGenerator implements PdfGenerator<FavoriteVacancyReportDTO> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final Font TITLE_FONT =
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);

    private static final Font SUBTITLE_FONT =
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);

    private static final Font TEXT_FONT =
            FontFactory.getFont(FontFactory.HELVETICA, 10);

    private static final Font SMALL_FONT =
            FontFactory.getFont(FontFactory.HELVETICA, 9);

    private static final Font HEADER_FONT =
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);

    private static final Font BODY_FONT =
            FontFactory.getFont(FontFactory.HELVETICA, 9);

    @Override
    public byte[] generate(FavoriteVacancyReportDTO data) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4, 30, 30, 30, 30);
            PdfWriter.getInstance(document, outputStream);
            document.open();

            addDocumentHeader(document, data);

            if (data.favorites() == null || data.favorites().isEmpty()) {
                Paragraph empty = new Paragraph(
                        "Nenhuma vaga favoritada foi encontrada para este cliente.",
                        TEXT_FONT
                );
                empty.setSpacingBefore(10f);
                document.add(empty);
            } else {
                PdfPTable table = createFavoritesTable();
                addTableHeader(table);
                addTableRows(table, data);

                document.add(table);
            }

            document.add(Chunk.NEWLINE);
            addDocumentFooter(document);

            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF de vagas favoritadas.", e);
        }
    }

    private void addDocumentHeader(Document document, FavoriteVacancyReportDTO data) throws Exception {
        Paragraph title = new Paragraph("Relatório de Vagas Favoritadas", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(12f);
        document.add(title);

        document.add(new Paragraph("Cliente: " + safe(data.customerName()), SUBTITLE_FONT));
        document.add(new Paragraph("E-mail: " + safe(data.customerEmail()), TEXT_FONT));
        document.add(new Paragraph("Gerado em: " + formatDateTime(data.generatedAt()), TEXT_FONT));
        document.add(new Paragraph("Total de vagas favoritadas: " + safeNumber(data.totalFavorites()), TEXT_FONT));
        document.add(Chunk.NEWLINE);
    }

    private PdfPTable createFavoritesTable() throws Exception {
        PdfPTable table = new PdfPTable(new float[]{0.6f, 3.4f, 3.4f, 2f, 1.6f, 1f});
        table.setWidthPercentage(100);
        table.setSpacingBefore(0f);
        table.setHeaderRows(1);
        table.setSplitLate(false);
        table.setKeepTogether(false);
        return table;
    }

    private void addTableHeader(PdfPTable table) {
        addHeaderCell(table, "UF", Element.ALIGN_CENTER);
        addHeaderCell(table, "Título", Element.ALIGN_LEFT);
        addHeaderCell(table, "Empresa", Element.ALIGN_LEFT);
        addHeaderCell(table, "Tipo de contrato", Element.ALIGN_CENTER);
        addHeaderCell(table, "Tipo de vaga", Element.ALIGN_CENTER);
        addHeaderCell(table, "Salário", Element.ALIGN_CENTER);
    }

    private void addTableRows(PdfPTable table, FavoriteVacancyReportDTO data) {
        for (FavoriteVacancyReportItemDTO item : data.favorites()) {
            addBodyCell(table, safe(item.ufAbrev()), Element.ALIGN_CENTER);
            addBodyCell(table, safe(item.title()), Element.ALIGN_LEFT);
            addBodyCell(table, safe(item.companyName()), Element.ALIGN_LEFT);
            addBodyCell(table, safe(item.hiringType()), Element.ALIGN_CENTER);
            addBodyCell(table, safe(item.workMode()), Element.ALIGN_CENTER);
            addBodyCell(table, safe(item.salaryRange()), Element.ALIGN_CENTER);
        }
    }

    private void addDocumentFooter(Document document) throws Exception {
        Paragraph footer = new Paragraph(
                "Documento gerado automaticamente pelo sistema TalentHub.",
                SMALL_FONT
        );
        footer.setAlignment(Element.ALIGN_RIGHT);
        document.add(footer);
    }

    private void addHeaderCell(PdfPTable table, String text, int horizontalAlignment) {
        addCell(
                table,
                text,
                HEADER_FONT,
                horizontalAlignment,
                7f,
                7f,
                4f,
                4f,
                15f
        );
    }

    private void addBodyCell(PdfPTable table, String text, int horizontalAlignment) {
        addCell(
                table,
                text,
                BODY_FONT,
                horizontalAlignment,
                5f,
                5f,
                5f,
                5f,
                20f
        );
    }

    private void addCell(
            PdfPTable table,
            String text,
            Font font,
            int horizontalAlignment,
            float paddingTop,
            float paddingBottom,
            float paddingLeft,
            float paddingRight,
            float minimumHeight
    ) {
        PdfPCell cell = new PdfPCell(new Phrase(safe(text), font));
        cell.setHorizontalAlignment(horizontalAlignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingTop(paddingTop);
        cell.setPaddingBottom(paddingBottom);
        cell.setPaddingLeft(paddingLeft);
        cell.setPaddingRight(paddingRight);
        cell.setMinimumHeight(minimumHeight);

        table.addCell(cell);
    }

    private String formatDateTime(LocalDateTime value) {
        return value == null ? "-" : value.format(DATE_TIME_FORMATTER);
    }

    private String safe(Object value) {
        return value != null ? value.toString() : "-";
    }

    private String safeNumber(Integer value) {
        return value == null ? "-" : String.valueOf(value);
    }
}