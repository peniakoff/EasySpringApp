package pl.tomaszmiller.AppNo2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.tomaszmiller.AppNo2.TicketRepository;
import pl.tomaszmiller.AppNo2.models.Ticket;

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


}
