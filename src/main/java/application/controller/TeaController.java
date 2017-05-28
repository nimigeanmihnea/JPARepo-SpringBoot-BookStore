package application.controller;

import application.entity.OnlineOrder;
import application.entity.Stationery;
import application.entity.Tea;
import application.entity.User;
import application.repository.OnlineOrderRepository;
import application.repository.SaleRepository;
import application.repository.TeaRepository;
import application.repository.UserRepository;
import application.validator.StationeryValidator;
import application.validator.TeaValidator;
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
public class TeaController {

    @Autowired
    private TeaRepository teaRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private OnlineOrderRepository onlineOrderRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/tea", method = RequestMethod.GET)
    public String show(@PathParam("search") String search, Model model){
        if(search != null){
            List<Tea> teas = teaRepository.findByBarcodeOrNameOrType(
                    search.replace("_", " "),
                    search.replace("_", " "),
                    search.replace("_", " ")
            );
            model.addAttribute("teas", teas);
            return "/tea";
        }
        return "/tea";
    }

    @RequestMapping(value = "/tea", method = RequestMethod.POST)
    public String search(HttpServletRequest request){
        String search = request.getParameter("search");
        return "redirect:/tea?search="+search.replaceAll(" ","_");
    }

    @RequestMapping(value = "/employee/add_tea", method = RequestMethod.GET)
    public String showAddTea(){
        return "/employee/add_tea";
    }

    @RequestMapping(value = "/employee/add_tea", method = RequestMethod.POST )
    public String addTea(HttpServletRequest request){
        Tea tea = new Tea();
        tea.setBarcode(request.getParameter("barcode"));
        tea.setName(request.getParameter("name"));
        tea.setPrice(Float.parseFloat(request.getParameter("price")));
        tea.setStock(Integer.parseInt(request.getParameter("stock")));
        tea.setType(request.getParameter("type"));
        tea.setImage("images/"+request.getParameter("image"));

        try{
            teaRepository.save(tea);
            return "redirect:/home";
        }catch (ConstraintViolationException ex){
            return "redirect:/tea_error";
        }
    }

    @RequestMapping(value = "/employee/update_tea", method = RequestMethod.GET)
    public String showUpdate(@PathParam("param") String param, Model model){
        long id = Long.parseLong(param);
        Tea tea = teaRepository.findOne(id);

        if(tea != null){
            model.addAttribute("tea", tea);
            return "/employee/update_tea";
        }else return "redirect:/tea_error";
    }

    @RequestMapping(value = "/employee/update_tea", method = RequestMethod.POST)
    public String update(HttpServletRequest request){
        Tea tea = teaRepository.findOne(Long.parseLong(request.getParameter("id")));
        if(tea != null){
            tea.setStock(Integer.parseInt(request.getParameter("stock")));
            tea.setPrice(Float.parseFloat(request.getParameter("price")));
            teaRepository.save(tea);
            return "redirect:/home";
        }else return "redirect:/tea_error";
    }

    @RequestMapping(value = "/employee/delete_tea", method = RequestMethod.GET)
    public String delete(@PathParam("param") String param){
        long id = Long.parseLong(param);
        Tea tea = teaRepository.findOne(id);

        if(tea != null){
            teaRepository.delete(tea);
            return "redirect:/home";
        }else return "redirect:/tea_error";
    }

    @RequestMapping(value = "/user/buy_tea", method = RequestMethod.GET)
    public String showBuy(@PathParam("param") String param, Model model){
        long id = Long.parseLong(param);
        Tea tea = teaRepository.findOne(id);

        if(tea != null) {
            model.addAttribute("tea", tea);
            return "/user/buy_tea";
        }else return "redirect:/tea_error";
    }

    @RequestMapping(value = "/user/buy_tea", method = RequestMethod.POST)
    public String buy(HttpServletRequest request){
        Tea tea = teaRepository.findOne(Long.parseLong(request.getParameter("id")));
        int quantity = Integer.parseInt(request.getParameter("q"));

        if(tea != null){
            TeaValidator validator = new TeaValidator();
            if(validator.validate(tea, quantity)){
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String name = auth.getName();
                User user = userRepository.findByUsername(name);
                OnlineOrder order = new OnlineOrder(new Date(),user, null, null, tea);
                tea.setStock(tea.getStock() - quantity);
                teaRepository.save(tea);
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
