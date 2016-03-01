package kz.epam.spadv.web;

import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import kz.epam.spadv.domain.Ticket;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Oleg_Motorin on 2/24/2016.
 */
public class TicketsPdfView extends AbstractPdfView {
    @Override protected void buildPdfDocument(Map<String, Object> map, Document document,
            PdfWriter pdfWriter, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) throws Exception {
        Table table = new Table(4);
        table.addCell("Event");
        table.addCell("Auditorium");
        table.addCell("Seat");
        table.addCell("Price");

        Collection<Ticket> tickets = (Collection<Ticket>) map.get("tickets");

        for (Ticket ticket : tickets) {
            table.addCell(ticket.getEvent().getName());
            table.addCell(ticket.getEvent().getAuditorium().getName());
            table.addCell(String.valueOf(ticket.getSeat().getNumber()));
            table.addCell(String.valueOf(ticket.getPrice()));
        }
        document.add(table);
    }
}
