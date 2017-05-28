package application.report;

import application.entity.Sale;
import application.entity.User;
import application.repository.OnlineOrderRepository;
import application.repository.SaleRepository;
import application.repository.UserRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mihnea on 22/05/2017.
 */
public class CSVReport implements Report {

    private static final char DEFAULT_SEPARATOR = ',';
    private final String file = "src/main/resources/report/CSVReport.csv";

    @Override
    public void generate(String username, UserRepository userRepository, OnlineOrderRepository onlineOrderRepository, SaleRepository saleRepository) {

        try {
            FileWriter writer = new FileWriter(file);
            User user = userRepository.findByUsername(username);
            List<Sale> sales = saleRepository.findBySaledBy(user);
            for(Sale sale:sales){
                List<String> values = new ArrayList<>();
                values.add(sale.getDate().toString());
                values.add(sale.getSaledBy().getUsername());
                values.add(sale.getSaledTo().getUsername());
                writeLine(writer, values);
            }
            writer.flush();
            writer.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void writeLine(Writer writer, List<String> values) throws IOException {
        writeLine(writer,values,DEFAULT_SEPARATOR,' ');
    }

    public static void writeLine(Writer writer, List<String> values, char separators) throws IOException{
        writeLine(writer,values,separators,' ');
    }

    public static void writeLine(Writer writer, List<String> values, char separators, char quote) throws IOException{
        boolean first = true;
        if(separators == ' '){
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for(String value:values){
            if(!first){
                stringBuilder.append(separators);
            }
            if(quote == ' '){
                stringBuilder.append(csvFormat(value));
            } else{
                stringBuilder.append(quote).append(csvFormat(value)).append(quote);
            }
            first = false;
        }
        stringBuilder.append("\n");
        writer.append(stringBuilder.toString());
    }
    private static String csvFormat(String value){
        String result = value;
        if(result.contains("\"")){
            result = result.replace("\"","\"\"");
        }
        return result;
    }
}
