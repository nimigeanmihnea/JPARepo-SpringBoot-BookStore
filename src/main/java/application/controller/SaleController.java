package application.controller;

import application.entity.*;
import application.mail.Mail;
import application.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.websocket.server.PathParam;
import java.util.Date;

/**
 * Created by Mihnea on 28/05/2017.
 */

@Controller
public class SaleController {

    @Autowired
    private OnlineOrderRepository onlineOrderRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DetailsRepository detailsRepository;

    @RequestMapping(value = "/employee/sell", method = RequestMethod.GET)
    public String sell(@PathParam("param") String param){
        long id = Long.parseLong(param);
        OnlineOrder order = onlineOrderRepository.findOne(id);
        if(order != null){
            Book book = order.getBook();
            Stationery stationery = order.getStationery();
            Tea tea = order.getTea();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            User user = userRepository.findByUsername(name);

            if(book != null){
                Sale sale = new Sale(new Date(), user, order.getUser(), book, null, null);
                saleRepository.save(sale);
                onlineOrderRepository.delete(order);
                Details details = detailsRepository.findByUser(order.getUser());
                Mail mail = new Mail();
                mail.send("bookstoreapplication@gmail.com",details.getEmail(), "Order processed","Your order with the ID "+order.getId()+" has been processed.\nThank you!");
                return "redirect:/home";
            }

            if(stationery != null){
                Sale sale = new Sale(new Date(), user, order.getUser(), null, stationery, null);
                saleRepository.save(sale);
                onlineOrderRepository.delete(order);
                Details details = detailsRepository.findByUser(order.getUser());
                Mail mail = new Mail();
                mail.send("bookstoreapplication@gmail.com", details.getEmail(), "Order processed","Your order with the ID "+order.getId()+" has been processed.\nThank you!");
                return "redirect:/home";
            }

            if(tea != null){
                Sale sale = new Sale(new Date(), user, order.getUser(), null, null, tea);
                saleRepository.save(sale);
                onlineOrderRepository.delete(order);
                Details details = detailsRepository.findByUser(order.getUser());
                Mail mail = new Mail();
                mail.send("bookstoreapplication@gmail.com",details.getEmail(), "Order processed","Your order with the ID "+order.getId()+" has been processed.\nThank you!");
                return "redirect:/home";
            }
            return "redirect:/sale_error";
        }else return "redirect:/sale_error";
    }
}
