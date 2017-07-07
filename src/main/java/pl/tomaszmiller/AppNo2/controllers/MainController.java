package pl.tomaszmiller.AppNo2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.tomaszmiller.AppNo2.MailService;
import pl.tomaszmiller.AppNo2.TicketRepository;
import pl.tomaszmiller.AppNo2.UserRepository;
import pl.tomaszmiller.AppNo2.models.Ticket;
import pl.tomaszmiller.AppNo2.models.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Peniakoff on 31.05.2017.
 */
@Controller
public class MainController {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    @Autowired
    TemplateEngine templateEngine;

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.GET)
    @ResponseBody
    public String messageId(@PathVariable("ticketId") int id) {

        Optional<Ticket> ticket = ticketRepository.findOne(id); //Optional for exceptions management
        if (ticket.isPresent()) {
            return "The message form the DB is: " + ticket.get().getMessage(); //it returns the message with ID in the url
        }

        return "Wrong ID used!";
    }

    @RequestMapping(value = "/author", method = RequestMethod.GET)
    @ResponseBody
    public String messageAuthor() {

        List<Ticket> tickets = ticketRepository.findByAuthor("Tomasz M.");


        String message = "Tomasz M.'s tickets: ";
        for (Ticket ticket : tickets) {
            message += ticket.getMessage() + ", ";
        }

        return message;

//        return tickets.stream().map(s -> s.getMessage()).collect(
//                Collectors.joining(", ", "Tickety: ", ""));

    }


    @RequestMapping(value = "/message/{prefix}", method = RequestMethod.GET)
    @ResponseBody
    public String messagePrefix(@PathVariable("prefix") String prefixString) {

        List<Ticket> tickets = ticketRepository.findByMessageLike(prefixString + "%"); //Optional for exceptions management

        String message = "Tickets started from " + prefixString + ": ";
        for (Ticket ticket : tickets) {
            message += ticket.getMessage() + ", ";
        }

        return message;

    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public String user() {

//        User user = userRepository.findOne(183);
//        return "Czas: " + user.getDatetime().toString();

//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date1 = null;
//        Date date2 = null;
//        try {
//            date1 = dateFormat.parse("2017-04-12 16:00:00");
//            date2 = dateFormat.parse("2017-06-14 16:00:00");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        List<User> users = userRepository.findByDatetimeBetween(date1, date2);

//        List<User> users = userRepository.findByUsernameContainingAndIdGreaterThan("tam", 180);

//        List<User> users = userRepository.findByRole("ADMIN");

//        return users.stream().map(s -> s.getUsername()).collect(Collectors.joining(" , ", "Role: ", ""));

        Page<User> currentPage = userRepository.findAll(new PageRequest(0, 4)); //we are looking for page No. 0 and its size is 4; KEY CLASS
        StringBuilder builder = new StringBuilder();

        for (User user : currentPage.getContent()) {
            builder.append("Username: " + user.getUsername() + "<br />");
        }

        builder.append("Liczba stron: " + currentPage.getTotalPages() + "<br />");
        builder.append("Czy zawiera następną stronę? " + currentPage.hasNext() + "<br />");
        builder.append("Czy zawiera poprzednią stronę? " + currentPage.hasPrevious() + "<br />");

        currentPage = userRepository.findAll(currentPage.nextPageable());

        builder.append("------------------<br />");

        for (User user : currentPage.getContent()) {
            builder.append("Username: " + user.getUsername() + "<br />");
        }

        builder.append("Liczba stron: " + currentPage.getTotalPages() + "<br />");
        builder.append("Czy zawiera następną stronę? " + currentPage.hasNext() + "<br />");
        builder.append("Czy zawiera poprzednią stronę? " + currentPage.hasPrevious() + "<br />");
        builder.append("------------------<br />");

        currentPage = userRepository.findAll(currentPage.nextPageable());

        for (User user : currentPage.getContent()) {
            builder.append("Username: " + user.getUsername() + "<br />");
        }

        builder.append("Liczba stron: " + currentPage.getTotalPages() + "<br />");
        builder.append("Czy zawiera następną stronę? " + currentPage.hasNext() + "<br />");
        builder.append("Czy zawiera poprzednią stronę? " + currentPage.hasPrevious() + "<br />");
        builder.append("------------------<br />");

        return builder.toString();

    }

    @RequestMapping(value = "/mail/{cash}", method = RequestMethod.GET)
    @ResponseBody
    public String email(@PathVariable("cash") int cash) {
        Context context = new Context();
        context.setVariable("welcome", "Witaj, ktosiu!");
        context.setVariable("message", "Teraz mi wisisz " + cash + "!");

        String bodyHtml = templateEngine.process("mail", context);
        String EmailAddress = ""; // set your e-mail address
        
        mailService.sendMail(EmailAddress, bodyHtml, "Wiadomość testowa");
        return "Wysłano maila";
    }

}
