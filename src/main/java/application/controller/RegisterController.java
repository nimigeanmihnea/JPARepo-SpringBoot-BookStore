package application.controller;

import application.entity.Details;
import application.entity.User;
import application.repository.DetailsRepository;
import application.repository.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Mihnea on 28/05/2017.
 */

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DetailsRepository detailsRepository;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(HttpServletRequest request){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(userRepository.findByUsername(request.getParameter("username")) == null
                && detailsRepository.findByPnc(request.getParameter("pnc")) == null) {
            User user = new User(request.getParameter("username"), encoder.encode(request.getParameter("password")), "ROLE_USER");
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
