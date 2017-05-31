package pl.tomaszmiller.AppNo2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.tomaszmiller.AppNo2.TicketRepository;
import pl.tomaszmiller.AppNo2.models.Ticket;

/**
 * Created by Peniakoff on 31.05.2017.
 */
@Controller
public class MainController {

    @Autowired
    TicketRepository ticketRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String home() {
        Ticket ticket = new Ticket("To jest przykładowa wiadomość.", "Tomasz M.");
        ticketRepository.save(ticket);
        return "Zapisano do bazy danych.";
    }


}
