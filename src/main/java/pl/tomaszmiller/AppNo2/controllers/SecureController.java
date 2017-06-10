package pl.tomaszmiller.AppNo2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Peniakoff on 10.06.2017.
 */
@Controller
public class SecureController {

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
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
