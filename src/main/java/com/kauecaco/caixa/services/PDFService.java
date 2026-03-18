package com.kauecaco.caixa.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.kauecaco.caixa.models.Movimento;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
@Service
public class PDFService {

    public static ByteArrayOutputStream extrato(List<Movimento> todosMovimentos)
            throws DocumentException, IOException {

        Document document = new Document();

        Rectangle retangulo = new Rectangle(595.33f, 841.97f);
        document.setPageSize(retangulo);
        document.setMargins(20, 20, 20, 20);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);

        document.open();

        Font fonte10 = new Font(Font.FontFamily.HELVETICA, 10);
        Font fonte10Bold = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Font fonte18Bold = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);

        PdfPTable tabelaTitulo = new PdfPTable(1);
        tabelaTitulo.getDefaultCell().setBorder(0);

        PdfPCell celula = new PdfPCell(new Phrase("Extrato de Movimentações", fonte18Bold));
        celula.setBorder(0);
        celula.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaTitulo.addCell(celula);

        document.add(tabelaTitulo);

        document.add(new Phrase(" "));

        PdfPTable tabelaMovimentos = new PdfPTable(4);
        tabelaMovimentos.setWidthPercentage(100);
        tabelaMovimentos.getDefaultCell().setBorder(0);

        tabelaMovimentos.addCell(new Phrase("Tipo", fonte10Bold));
        tabelaMovimentos.addCell(new Phrase("Descrição", fonte10Bold));
        tabelaMovimentos.addCell(new Phrase("Data", fonte10Bold));

        celula = new PdfPCell(new Phrase("Valor", fonte10Bold));
        celula.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celula.setBorder(0);
        tabelaMovimentos.addCell(celula);
        double saldo = 0;
        for (Movimento mov : todosMovimentos) {
            saldo += mov.getTipo() == 1 ? mov.getValor() : -mov.getValor();
            String tipo = mov.getTipo() == 1 ? "Entrada" : "Saída";

            tabelaMovimentos.addCell(new Phrase(tipo, fonte10));
            tabelaMovimentos.addCell(new Phrase(mov.getDescricao(), fonte10));
            tabelaMovimentos.addCell(new Phrase(mov.getData().toString(), fonte10));

            celula = new PdfPCell(new Phrase(
                    String.format("R$ %.2f", mov.getValor()), fonte10));
            celula.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celula.setBorder(0);
            tabelaMovimentos.addCell(celula);
        }
        PdfPCell celulaSaldo = new PdfPCell(new Phrase("Saldo final", fonte10Bold));
        celulaSaldo.setColspan(3);
        celulaSaldo.setBorder(0);

        PdfPCell valorSaldo = new PdfPCell(new Phrase(
                String.format("R$ %.2f", saldo), fonte10Bold));
        valorSaldo.setHorizontalAlignment(Element.ALIGN_RIGHT);
        valorSaldo.setBorder(0);

        celulaSaldo.setBorderWidthTop(1);
        valorSaldo.setBorderWidthTop(1);

        tabelaMovimentos.addCell(celulaSaldo);
        tabelaMovimentos.addCell(valorSaldo);
        document.add(tabelaMovimentos);

        document.close();

        return outputStream;
    }
}