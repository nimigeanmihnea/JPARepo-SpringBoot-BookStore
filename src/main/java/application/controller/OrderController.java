package application.controller;

import application.entity.OnlineOrder;
import application.repository.OnlineOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Created by Mihnea on 28/05/2017.
 */

@Controller
public class OrderController {

    @Autowired
    private OnlineOrderRepository onlineOrderRepository;

    @RequestMapping(value = "/employee/view", method = RequestMethod.GET)
    public String show(Model model){
        List<OnlineOrder> orders = onlineOrderRepository.findAll();
        model.addAttribute("orders", orders);
        return "/employee/view";
    }
}
