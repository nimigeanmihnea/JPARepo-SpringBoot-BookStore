package application.report;

import application.entity.OnlineOrder;
import application.entity.User;
import application.repository.OnlineOrderRepository;
import application.repository.SaleRepository;
import application.repository.UserRepository;
import com.itextpdf.text.Document;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.List;


/**
 * Created by Mihnea on 22/05/2017.
 */

@Component
public class PDFReport implements Report {

    private static String file = "src/main/resources/report/PDFReport.pdf";

    @Override
    public void generate(String username, UserRepository userRepository, OnlineOrderRepository onlineOrderRepository, SaleRepository saleRepository) {
        try{
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            addMetaData(document);
            addContnent(document, username, userRepository, onlineOrderRepository);
            document.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("PDF Order Report");
        document.addAuthor("BookStore");
        document.addCreator("BookStore");
    }

    private static void addContnent(Document document, String username, UserRepository userRepository, OnlineOrderRepository onlineOrderRepository){
        User user = userRepository.findByUsername(username);
        List<OnlineOrder> orderList = onlineOrderRepository.findByUser(user);
        for (OnlineOrder aux:orderList){
            Paragraph paragraph = new Paragraph();
            if(aux.getBook() != null)
                paragraph = new Paragraph(aux.getBook().toString()+"\n");
            if(aux.getTea() != null)
                paragraph = new Paragraph(aux.getTea().toString()+"\n");
            if(aux.getStationery() != null)
                paragraph = new Paragraph(aux.getStationery().toString()+"\n");
            try {
                document.add(paragraph);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }
}
