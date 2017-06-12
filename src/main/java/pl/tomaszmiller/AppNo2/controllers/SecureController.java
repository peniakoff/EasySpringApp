package pl.tomaszmiller.AppNo2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.tomaszmiller.AppNo2.models.User;
import pl.tomaszmiller.AppNo2.models.UserInfo;

/**
 * Created by Peniakoff on 10.06.2017.
 */
@Controller
public class SecureController {

    @Autowired
    UserInfo userInfo;

    @GetMapping("/checkuser")
    @ResponseBody
    public String checkUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return "Czy user jest zalogowany? " + auth.getPrincipal();
    }

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam("password") String password,
                            @RequestParam("username") String username) {
        System.out.println("Username: " + username + ", password: " + password);
        return "login";
    }

    @GetMapping("/administrator")
    public String admin() {
        return "adminview";
    }

    @GetMapping("/403")
    @ResponseBody
    public String error403() {
        return "Access is denied!";
    }

}
