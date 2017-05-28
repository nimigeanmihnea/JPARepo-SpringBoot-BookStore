package application.controller;

import application.entity.Book;
import application.entity.OnlineOrder;
import application.entity.User;
import application.repository.BookRepository;
import application.repository.OnlineOrderRepository;
import application.repository.SaleRepository;
import application.repository.UserRepository;
import application.validator.BookValidator;
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
import javax.xml.ws.RequestWrapper;
import java.util.Date;
import java.util.List;

/**
 * Created by Mihnea on 27/05/2017.
 */

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OnlineOrderRepository onlineOrderRepository;

    @Autowired
    private SaleRepository saleRepository;

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String show(@PathParam("search") String search, Model model){
        if(search != null){
            List<Book> books = bookRepository.findByTitleOrAuthorOrIsbnOrGenre(search.replace("_", " "),
                    search.replace("_", " "),
                    search.replace("_", " "),
                    search.replace("_", " "));
            model.addAttribute("books", books);
            return "/book";
        }
        return "/book";
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public String search(HttpServletRequest request){
        String search = request.getParameter("search");
        return "redirect:/book?search="+search.replaceAll(" ","_");
    }

    @RequestMapping(value = "/employee/add_book", method = RequestMethod.GET)
    public String showAddBook(){
        return "/employee/add_book";
    }

    @RequestMapping(value = "/employee/add_book", method = RequestMethod.POST )
    public String addBook(HttpServletRequest request){
        Book book = new Book();
        book.setIsbn(request.getParameter("isbn"));
        book.setTitle(request.getParameter("title"));
        book.setAuthor(request.getParameter("author"));
        book.setGenre(request.getParameter("genre"));
        book.setFormat(request.getParameter("format"));
        book.setPrice(Float.parseFloat(request.getParameter("price")));
        book.setPages(Integer.parseInt(request.getParameter("pages")));
        book.setPublisher(request.getParameter("pub"));
        book.setStock(Integer.parseInt(request.getParameter("stock")));
        book.setImage("images/"+request.getParameter("image"));

        try{
            bookRepository.save(book);
            return "redirect:/home";
        }catch (ConstraintViolationException ex){
            return "redirect:/book_error";
        }
    }

    @RequestMapping(value = "/employee/update_book", method = RequestMethod.GET)
    public String showUpdate(@PathParam("param") String param, Model model){
        long id = Long.parseLong(param);
        Book book = bookRepository.findOne(id);

        if(book != null){
            model.addAttribute("book", book);
            return "/employee/update_book";
        }else return "redirect:/book_error";
    }

    @RequestMapping(value = "/employee/update_book", method = RequestMethod.POST)
    public String update(HttpServletRequest request){
        Book book = bookRepository.findOne(Long.parseLong(request.getParameter("id")));
        if(book != null){
            book.setStock(Integer.parseInt(request.getParameter("stock")));
            book.setPrice(Float.parseFloat(request.getParameter("price")));
            bookRepository.save(book);
            return "redirect:/home";
        }else return "redirect:/book_error";
    }

    @RequestMapping(value = "/employee/delete_book", method = RequestMethod.GET)
    public String delete(@PathParam("param") String param){
        long id = Long.parseLong(param);
        Book book = bookRepository.findOne(id);

        if(book != null){
            bookRepository.delete(book);
            return "redirect:/home";
        }else return "redirect:/book_error";
    }

    @RequestMapping(value = "/user/buy_book", method = RequestMethod.GET)
    public String showBuy(@PathParam("param") String param, Model model){
        long id = Long.parseLong(param);
        Book book = bookRepository.findOne(id);

        if(book != null) {
            model.addAttribute("book", book);
            return "/user/buy_book";
        }else return "redirect:/book_error";
    }

    @RequestMapping(value = "/user/buy_book", method = RequestMethod.POST)
    public String buy(HttpServletRequest request){
        Book book = bookRepository.findOne(Long.parseLong(request.getParameter("id")));
        int quantity = Integer.parseInt(request.getParameter("q"));

        if(book != null){
            BookValidator validator = new BookValidator();
            if(validator.validate(book, quantity)){
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String name = auth.getName();
                User user = userRepository.findByUsername(name);
                OnlineOrder order = new OnlineOrder(new Date(),user, book, null, null);
                book.setStock(book.getStock() - quantity);
                bookRepository.save(book);
                onlineOrderRepository.save(order);
                for (User aux:userRepository.findAll()){
                    if(user.getRole().equals("ROLE_EMPLOYEE")){
                        user.setShouldBeNotified(true);
                        userRepository.save(user);
                    }
                }
                return "redirect:/home";
            }else return "redirect:/book_error";
        }else return "redirect:/book_error";
    }
}
