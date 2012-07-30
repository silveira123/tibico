/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.util.funcoes;

import academico.controlepauta.cdp.MatriculaTurma;
import java.util.List;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import org.zkoss.zul.Filedownload;

/**
 *
 * @author jmiranda
 */
public class ResultadosToPdf {

    public static void gerarPdf(List<MatriculaTurma> matTurma, Double media) throws BadElementException, MalformedURLException, IOException, DocumentException {
        Document document = new Document();
        com.lowagie.text.Image figura = com.lowagie.text.Image.getInstance("C:\\Users\\jmiranda\\Desktop\\Tibico\\web\\images\\tibico6.png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);



        figura.scaleAbsolute(200, 75);
        figura.setAlignment(Element.ALIGN_CENTER);
        document.open();
        document.add(figura);

        Font f = new Font();
        f.setSize(18);

        Font f2 = new Font();
        f2.setSize(10);

        document.add(new Paragraph("\r\n"));
        Paragraph p;
        p = new Paragraph("Relatório Resultados", f);


        p.setAlignment(Element.ALIGN_CENTER);

        document.add(p);

        document.add(new Paragraph("\r\n"));
        document.add(new Paragraph("Curso:  " + matTurma.get(0).getAluno().getCurso().toString(), f2));

        document.add(new Paragraph("Calendario:  " + matTurma.get(0).getTurma().getCalendario().getIdentificador(), f2));
        
        document.add(new Paragraph("Turma:  " + matTurma.get(0).getTurma().toString() , f2));
        
        document.add(new Paragraph("Media Turma:  " + media.toString() , f2));
        document.add(new Paragraph("\r\n"));
        
        p = new Paragraph("Matrículas da Turma", f);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\r\n"));

        PdfPTable table = new PdfPTable(new float[]{0.20f,0.32f, 0.13f, 0.10f, 0.25f});
        table.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell cell = new PdfPCell(new Paragraph("Matrícula"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Nome"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Freq.(%)"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Nota Final"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Situação"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        MatriculaTurma mt;
        for (int i = 0; i < matTurma.size(); i++) {
            mt = matTurma.get(i);

            cell = new PdfPCell(new Paragraph(mt.getAluno().getMatricula().toString(), f2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(cell);

            cell = new PdfPCell(new Paragraph(mt.getAluno().getNome().toString(), f2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph(mt.getPercentualPresenca().toString(), f2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            
            cell = new PdfPCell(new Paragraph(mt.getResultadoFinal().toString(), f2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            
            
            cell = new PdfPCell(new Paragraph(mt.getSituacaoAluno().toString(), f2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

        }
        document.add(table);

        document.close();
        Filedownload fd = null;
        fd.save(baos.toByteArray(), null, "resultados" + "-" + matTurma.get(0).getTurma() );
    }
}
