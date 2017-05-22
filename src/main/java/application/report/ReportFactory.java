package application.report;

/**
 * Created by Mihnea on 22/05/2017.
 */

public class ReportFactory {

    public Report getReport(String type){
        if(type == null){
            return null;
        }else {
            if (type.equalsIgnoreCase("csv")){
                return new CSVReport();
            }else{
                if(type.equalsIgnoreCase("pdf")){
                    return new PDFReport();
                }else return null;
            }
        }
    }
}
