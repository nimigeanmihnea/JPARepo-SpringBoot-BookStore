package application.controller;

import application.report.Report;
import application.report.ReportFactory;
import application.repository.OnlineOrderRepository;
import application.repository.SaleRepository;
import application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Mihnea on 28/05/2017.
 */

@Controller
public class ReportController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OnlineOrderRepository onlineOrderRepository;

    @Autowired
    private SaleRepository saleRepository;

    @RequestMapping(value = "/user/generate", method = RequestMethod.GET)
    public String userReport(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        ReportFactory reportFactory = new ReportFactory();

        Report pdf = reportFactory.getReport("pdf");
        pdf.generate(username, userRepository, onlineOrderRepository, saleRepository);

        return "redirect:/home";
    }

    @RequestMapping(value = "/employee/generate", method = RequestMethod.GET)
    public String employeeReport(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        ReportFactory reportFactory = new ReportFactory();

        Report csv = reportFactory.getReport("csv");
        csv.generate(username, userRepository, onlineOrderRepository, saleRepository);

        return "redirect:/home";
    }
}
