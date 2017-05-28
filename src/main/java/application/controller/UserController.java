package application.controller;

import application.entity.Details;
import application.entity.Tea;
import application.entity.User;
import application.repository.DetailsRepository;
import application.repository.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.websocket.server.PathParam;

/**
 * Created by Mihnea on 28/05/2017.
 */

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DetailsRepository detailsRepository;

    @RequestMapping(value = "/admin/view", method = RequestMethod.GET)
    public String view(@PathParam("search") String search, Model model){
        if(search != null){
            User user = userRepository.findByUsername(search);
            model.addAttribute("users", user);
            return "/admin/view";
        }else return "/admin/view";
    }

    @RequestMapping(value = "/admin/view", method = RequestMethod.POST)
    public String search(HttpServletRequest request){
        return "redirect:/admin/view?search="+request.getParameter("search");
    }

    @RequestMapping(value = "/admin/delete", method = RequestMethod.GET)
    public String delete(@PathParam("param") String param){
        long id = Long.parseLong(param);
        User user = userRepository.findOne(id);
        if(user != null){
            Details details = detailsRepository.findByUser(user);
            detailsRepository.delete(details);
            userRepository.delete(user);
            return "redirect:/home";
        }else return "redirect:/user_error";
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.GET)
    public String showUpdate(@PathParam("param") String param, Model model){
        long id = Long.parseLong(param);
        User user = userRepository.findOne(id);

        if(user != null){
            Details details = detailsRepository.findByUser(user);
            model.addAttribute("user", details);
            return "/admin/update";
        }else return "redirect:/user_error";
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public String update(HttpServletRequest request){
        Details details = detailsRepository.findOne(Long.parseLong(request.getParameter("id")));
        if(details != null){
            details.setAddress(request.getParameter("address"));
            details.setName(request.getParameter("name"));
            details.setPhone(request.getParameter("phone"));
            details.setEmail(request.getParameter("email"));
            try{
                detailsRepository.save(details);
                return "redirect:/home";
            }catch (ConstraintViolationException ex){
                return "redirect:/user_error";
            }
        }else return "redirect:/tea_error";
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.GET)
    public String showAdd(){
        return "/admin/add";
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public String add(HttpServletRequest request){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(userRepository.findByUsername(request.getParameter("username")) == null
                && detailsRepository.findByPnc(request.getParameter("pnc")) == null) {
            User user = new User(request.getParameter("username"), encoder.encode(request.getParameter("password")),"ROLE_EMPLOYEE");
            Details details = new Details();
            details.setPnc(request.getParameter("pnc"));
            details.setName(request.getParameter("name"));
            details.setEmail(request.getParameter("email"));
            details.setPhone(request.getParameter("phone"));
            details.setAddress(request.getParameter("address"));
            details.setUser(user);
            userRepository.save(user);
            detailsRepository.save(details);
            return "redirect:/login";
        }else return "redirect:/register_error";
    }
}
