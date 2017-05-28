package application.controller;

import application.entity.Book;
import application.entity.Stationery;
import application.entity.Tea;
import application.entity.User;
import application.repository.BookRepository;
import application.repository.StationeryRepository;
import application.repository.TeaRepository;
import application.repository.UserRepository;
import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Mihnea on 27/05/2017.
 */

@Controller
public class HomeController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StationeryRepository stationeryRepository;

    @Autowired
    private TeaRepository teaRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String show(Model model){
        List<Book> books = bookRepository.findAll();
        List<Stationery> stationeries = stationeryRepository.findAll();
        List<Tea> teas = teaRepository.findAll();
        model.addAttribute("books", books);
        model.addAttribute("stationeries", stationeries);
        model.addAttribute("teas", teas);
        return "/home";
    }

    @ResponseBody
    @RequestMapping(value = "/home/polling", method = RequestMethod.POST)
    public Boolean polling(){
        for (User user:userRepository.findAll()){
            if(user.getRole().equals("ROLE_EMPLOYEE")){
                if (user.isShouldBeNotified() == true) {
                    user.setShouldBeNotified(false);
                    userRepository.save(user);
                    return true;
                }
            }
        }
        return null;
    }
}
