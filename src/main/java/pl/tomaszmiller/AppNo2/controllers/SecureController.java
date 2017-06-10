package pl.tomaszmiller.AppNo2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.tomaszmiller.AppNo2.models.UserInfoBean;

/**
 * Created by Peniakoff on 10.06.2017.
 */
@Controller
public class SecureController {

    @Autowired
    UserInfoBean userInfoBean;

    @GetMapping("/test/{message}")
    @ResponseBody
    public String setUserInfo(@PathVariable("message") String message) {
        userInfoBean.isUserLogged();
        userInfoBean.setMessage(message);
        return "Text is set for you: " + message;
    }

    @GetMapping("/test")
    @ResponseBody
    public String getUserInfo(){
        return "Your text is: " + userInfoBean.getMessage();
    }

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
