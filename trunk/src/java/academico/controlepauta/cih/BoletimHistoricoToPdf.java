/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controlepauta.cih;

import academico.controlepauta.cdp.MatriculaTurma;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;

/**
 *
 * @author jmiranda
 */
public class BoletimHistoricoToPdf {

    static String tipo;

    public static void gerarPdf(List<MatriculaTurma> matTurma, boolean b) throws BadElementException, MalformedURLException, IOException, DocumentException {
        if (b) {
            tipo = "boletim";
        } else {
            tipo = "historico";
        }

        Document document = new Document();
//        com.lowagie.text.Image figura = com.lowagie.text.Image.getInstance(Executions.getCurrent().getDesktop().getWebApp().getRealPath("\\images\\tibico6.png"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);



//        figura.scaleAbsolute(200, 75);
//        figura.setAlignment(Element.ALIGN_CENTER);
        document.open();
//        document.add(figura);

        Font f = new Font();
        f.setSize(18);

        Font f2 = new Font();
        f2.setSize(10);

        document.add(new Paragraph("\r\n"));
        Paragraph p;
        if (b) {
            p = new Paragraph("Boletim Acadêmico", f);
        } else {
            p = new Paragraph("Histórico Acadêmico", f);
        }

        p.setAlignment(Element.ALIGN_CENTER);

        document.add(p);

        document.add(new Paragraph("\r\n"));
        document.add(new Paragraph("Aluno:  " + matTurma.get(0).getAluno().getMatricula() + "   " + matTurma.get(0).getAluno().getNome(), f2));
        if (b) {
            document.add(new Paragraph("Calendario:  " + matTurma.get(0).getTurma().getCalendario().getIdentificador(), f2));
            document.add(new Paragraph("\r\n"));
        }
        document.add(new Paragraph("Curso:  " + matTurma.get(0).getAluno().getCurso().getNome() + "      Coeficiente:   " + matTurma.get(0).getAluno().getCoeficiente(), f2));
        document.add(new Paragraph("\r\n"));

        p = new Paragraph("Disciplinas Cursadas", f);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\r\n"));

        PdfPTable table = new PdfPTable(new float[]{0.4f, 0.15f, 0.15f, 0.3f});
        table.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell cell = new PdfPCell(new Paragraph("Disciplina"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Faltas"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Nota"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);


        cell = new PdfPCell(new Paragraph("Situação"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        MatriculaTurma mt;
        for (int i = 0; i < matTurma.size(); i++) {
            mt = matTurma.get(i);

            cell = new PdfPCell(new Paragraph(mt.getTurma().getDisciplina().toString(), f2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(cell);

            cell = new PdfPCell(new Paragraph(mt.toDecimalFormat(), f2));
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
        fd.save(baos.toByteArray(), null, tipo + "-" + matTurma.get(0).getAluno().getMatricula() + "-" + matTurma.get(0).getTurma().getCalendario().getIdentificador().toString()+".pdf");
    }
}
