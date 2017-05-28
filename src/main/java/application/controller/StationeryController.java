package application.controller;

import application.entity.*;
import application.repository.OnlineOrderRepository;
import application.repository.SaleRepository;
import application.repository.StationeryRepository;
import application.repository.UserRepository;
import application.validator.BookValidator;
import application.validator.StationeryValidator;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

/**
 * Created by Mihnea on 27/05/2017.
 */

@Controller
public class StationeryController {

    @Autowired
    private StationeryRepository stationeryRepository;

    @Autowired
    private OnlineOrderRepository onlineOrderRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/stationery", method = RequestMethod.GET)
    public String show(@PathParam("search") String search, Model model){
        if(search != null){
            List<Stationery> sts = stationeryRepository.findByBarcodeOrNameOrType(
                    search.replace("_", " "),
                    search.replace("_", " "),
                    search.replace("_", " ")
            );
            model.addAttribute("sts", sts);
            return "/stationery";
        }
        return "/stationery";
    }

    @RequestMapping(value = "/stationery", method = RequestMethod.POST)
    public String search(HttpServletRequest request){
        String search = request.getParameter("search");
        return "redirect:/stationery?search="+search.replaceAll(" ","_");
    }

    @RequestMapping(value = "/employee/add_st", method = RequestMethod.GET)
    public String showAddStationery(){
        return "/employee/add_st";
    }

    @RequestMapping(value = "/employee/add_st", method = RequestMethod.POST )
    public String addStationery(HttpServletRequest request){
        Stationery stationery = new Stationery();
        stationery.setBarcode(request.getParameter("barcode"));
        stationery.setName(request.getParameter("name"));
        stationery.setPrice(Float.parseFloat(request.getParameter("price")));
        stationery.setStock(Integer.parseInt(request.getParameter("stock")));
        stationery.setType(request.getParameter("type"));
        stationery.setImage("images/"+request.getParameter("image"));

        try{
            stationeryRepository.save(stationery);
            return "redirect:/home";
        }catch (ConstraintViolationException ex){
            return "redirect:/stationery_error";
        }
    }

    @RequestMapping(value = "/employee/update_st", method = RequestMethod.GET)
    public String showUpdate(@PathParam("param") String param, Model model){
        long id = Long.parseLong(param);
        Stationery stationery = stationeryRepository.findOne(id);

        if(stationery != null){
            model.addAttribute("st", stationery);
            return "/employee/update_st";
        }else return "redirect:/stationery_error";
    }

    @RequestMapping(value = "/employee/update_st", method = RequestMethod.POST)
    public String update(HttpServletRequest request){
        Stationery stationery = stationeryRepository.findOne(Long.parseLong(request.getParameter("id")));
        if(stationery != null){
            stationery.setStock(Integer.parseInt(request.getParameter("stock")));
            stationery.setPrice(Float.parseFloat(request.getParameter("price")));
            stationeryRepository.save(stationery);
            return "redirect:/home";
        }else return "redirect:/stationery_error";
    }

    @RequestMapping(value = "/employee/delete_st", method = RequestMethod.GET)
    public String delete(@PathParam("param") String param){
        long id = Long.parseLong(param);
        Stationery stationery = stationeryRepository.findOne(id);

        if(stationery != null){
            stationeryRepository.delete(stationery);
            return "redirect:/home";
        }else return "redirect:/stationery_error";
    }

    @RequestMapping(value = "/user/buy_st", method = RequestMethod.GET)
    public String showBuy(@PathParam("param") String param, Model model){
        long id = Long.parseLong(param);
        Stationery stationery = stationeryRepository.findOne(id);

        if(stationery != null) {
            model.addAttribute("st",stationery);
            return "/user/buy_st";
        }else return "redirect:/stationery_error";
    }

    @RequestMapping(value = "/user/buy_st", method = RequestMethod.POST)
    public String buy(HttpServletRequest request){
        Stationery stationery = stationeryRepository.findOne(Long.parseLong(request.getParameter("id")));
        int quantity = Integer.parseInt(request.getParameter("q"));

        if(stationery != null){
            StationeryValidator validator = new StationeryValidator();
            if(validator.validate(stationery, quantity)){
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String name = auth.getName();
                User user = userRepository.findByUsername(name);
                OnlineOrder order = new OnlineOrder(new Date(),user, null, stationery, null);
                stationery.setStock(stationery.getStock() - quantity);
                stationeryRepository.save(stationery);
                onlineOrderRepository.save(order);
                for (User aux:userRepository.findAll()){
                    if(user.getRole().equals("ROLE_EMPLOYEE")){
                        user.setShouldBeNotified(true);
                        userRepository.save(user);
                    }
                }
                return "redirect:/home";
            }else return "redirect:/stationery_error";
        }else return "redirect:/stationery_error";
    }
}
