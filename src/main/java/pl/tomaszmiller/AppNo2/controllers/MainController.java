package pl.tomaszmiller.AppNo2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.tomaszmiller.AppNo2.TicketRepository;
import pl.tomaszmiller.AppNo2.models.Ticket;

import java.util.Optional;

/**
 * Created by Peniakoff on 31.05.2017.
 */
@Controller
public class MainController {

    @Autowired
    TicketRepository ticketRepository;

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.GET)
    @ResponseBody
    public String home(@PathVariable("ticketId") int id) {
        Optional<Ticket> ticket = ticketRepository.findOne(id); //Optional for exceptions management
        if (ticket.isPresent()) {
            return "The message form the DB is: " + ticket.get().getMessage(); //it returns the message with ID in the url
        }
        return "Wrong ID used!";
    }


}
