package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;

@ThreadSafe
//@RestController
@Controller
public class IndexController {

    @GetMapping("/index")
    public String getIndex() {
//        return "Hello World!";
        return "index";
    }

}