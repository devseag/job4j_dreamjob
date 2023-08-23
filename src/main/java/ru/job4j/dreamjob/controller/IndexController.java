package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpSession;

@ThreadSafe
//@RestController
@Controller
public class IndexController {

//    @GetMapping("/index")
//    public String getIndex() {
////        return "Hello World!";
//        return "index";
//    }

    @GetMapping({"/", "/index"})
    public String getIndex(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Gost");
        }
        model.addAttribute("user", user);
        return "index";
    }

}