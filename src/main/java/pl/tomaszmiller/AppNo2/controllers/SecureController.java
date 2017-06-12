package pl.tomaszmiller.AppNo2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.tomaszmiller.AppNo2.UserRepository;
import pl.tomaszmiller.AppNo2.models.User;
import pl.tomaszmiller.AppNo2.models.UserInfo;

import java.util.Optional;

/**
 * Created by Peniakoff on 10.06.2017.
 */
@Controller
public class SecureController {

    @Autowired
    UserInfo userInfo;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/checkuser")
    @ResponseBody
    public String checkUser() {
        return "Czy user jest zalogowany? " + userInfo.isLogged();
    }

    @GetMapping("/")
    public String main() {
        if (userInfo.isLogged()) {
            System.out.println("Rola: " + userInfo.getUser().getRole());
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        if (userInfo.isLogged()) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam("password") String password,
                            @RequestParam("username") String username,
                            Model model) {
//        System.out.println("Username: " + username + ", password: " + password);
        Optional<User> user = userRepository.findByUsername(username);
        if (password.equals(user.get().getPassword())) {
            userInfo.setLogged(true);
            userInfo.setUser(user.get());
            model.addAttribute("info", "Zalogowano się poprawnie!");
        }
        model.addAttribute("info", "Błędny login lub hasło!");
        return "login";
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout() {
        userInfo.setLogged(false);
        return "<center><h1>Wylogowano!</center></h1>";
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
