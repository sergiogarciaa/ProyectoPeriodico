package proyecto.periodico.utils;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dto.NoticiaDTO;

@Component("administracionPdf")
public class listarNoticiasPdf extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        @SuppressWarnings("unchecked")
        List<NoticiaDTO> noticias = (List<NoticiaDTO>) model.get("noticias");

        PdfPTable table = new PdfPTable(5); // 5 columnas: Titulo,Categoría, Fecha, Usuario, Nº Comentarios
        PdfPCell header1 = new PdfPCell(new Phrase("Titulo"));
        PdfPCell header2 = new PdfPCell(new Phrase("Categoría"));
        PdfPCell header3 = new PdfPCell(new Phrase("Fecha de Publicacion"));
        PdfPCell header4 = new PdfPCell(new Phrase("Usuario"));
        PdfPCell header5 = new PdfPCell(new Phrase("Nº Comentarios"));

        table.addCell(header1);
        table.addCell(header2);
        table.addCell(header3);
        table.addCell(header4);
        table.addCell(header5);

        for (NoticiaDTO noticia : noticias) {
            table.addCell(new Phrase(noticia.getTituloNoticia()));
            table.addCell(new Phrase(noticia.getIdCategoria().getTipoCategoria()));
            table.addCell(new Phrase(noticia.getFchaPublicacionMostrarWeb().toString()));
            table.addCell(new Phrase(noticia.getIdUsuario().getNombreUsuario()));
            if (noticia.getComentarios() == null) {
                table.addCell(new Phrase("0"));
            }else {
            	 table.addCell(new Phrase(String.valueOf(noticia.getComentarios().size())));
            }
        }

        document.add(table);
    }
}
