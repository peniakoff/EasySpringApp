package pl.tomaszmiller.AppNo2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import pl.tomaszmiller.AppNo2.UserRepository;
import pl.tomaszmiller.AppNo2.models.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.logging.Handler;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * Created by Peniakoff on 05.06.2017.
 */
@Controller
@RequestMapping(value = "/api")
public class ApiController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/user", method = GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getAllUser(@RequestHeader("Access-Password") String key) {
        if (!key.equals("akademiakodujestfajna")) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Iterable<User> users = userRepository.findAll();
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userName}", method = GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getOneUser(@RequestHeader("Access-Password") String key,
                                     @PathVariable("userName") String userName) {
        if (!key.equals("akademiakodujestfajna")) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userRepository.findByUsername(userName);
        if (!user.isPresent()) {
            return new ResponseEntity("User not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(user.get(), HttpStatus.OK); //get() is the method to the OPTIONAL, but before we have to check, if the object isn't null!!!
        }
    }

    @RequestMapping(value = "/user", method = PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE) // with PUT we can update data!
    public ResponseEntity editUser(@RequestHeader("Access-Password") String key,
                                   @RequestBody User user) {
        if (!key.equals("akademiakodujestfajna")) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (user == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        User userLocal = userRepository.findOne(user.getId());
        if (user == null) {
            return new ResponseEntity("User not exist", HttpStatus.NOT_FOUND);
        }

        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

//    @RequestMapping(value = "/user", method = GET, produces = MediaType.APPLICATION_XML_VALUE)
//    public ResponseEntity getAllUser() {
//        Iterable<User> users = userRepository.findAll();
//        return new ResponseEntity(users, HttpStatus.OK);
//    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity saveUser(@RequestBody User user,
                                   @RequestHeader("Access-Password") String key) {
        if (!key.equals("akademiakodujestfajna")) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (user == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Optional<User> userLocal = userRepository.findByUsername(user.getUsername());

        if (userLocal.isPresent()) {
            return new ResponseEntity("Username is not available!", HttpStatus.BAD_REQUEST);
        }

        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deteleUser(@RequestHeader("Access-Password") String key,
                                     @PathVariable("username") String username) {
        if (!key.equals("akademiakodujestfajna")) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Optional<User> userLocal = userRepository.findByUsername(username);
        if (!userLocal.isPresent()) {
            return new ResponseEntity("User ist'n exist!", HttpStatus.BAD_REQUEST);
        }
        userRepository.delete(userLocal.get());
        return new ResponseEntity(HttpStatus.OK);

    }

    // dynamic URLs
    @RequestMapping(value = "/tomasz/**", method = RequestMethod.GET)
    @ResponseBody
    public String tomaszGet(HttpServletRequest servletRequest) throws Exception {
        String path = (String) servletRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        //String[] split = path.split("/");
        return "Path to: " + path;
    }

}
