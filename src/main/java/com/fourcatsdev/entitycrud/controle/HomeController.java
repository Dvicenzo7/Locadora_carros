package com.fourcatsdev.entitycrud.controle;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

        @RequestMapping("/")
        public String index(Model model) {
            model.addAttribute("msnBemVindo", "Bem-vindo à Locadora Vi");
            return "publica-index";
        }

        @RequestMapping("/login")
        public String login() {
            return "login";
        }

}
