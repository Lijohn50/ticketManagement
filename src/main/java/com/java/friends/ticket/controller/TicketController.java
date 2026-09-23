package com.java.friends.ticket.controller;

import com.java.friends.ticket.model.Ticket;
import com.java.friends.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TicketController {

    private final TicketRepository ticketRepository;

    // Read: Display all tickets and form
    @GetMapping
    public String viewHomePage(Model model) {
        model.addAttribute("tickets", ticketRepository.findAll());
        model.addAttribute("ticket", new Ticket());
        return "tickets";
    }

    // Create & Update (Save Ticket)
    @PostMapping("/save")
    public String saveTicket(@ModelAttribute("ticket") Ticket ticket) {
        ticketRepository.save(ticket);
        return "redirect:/";
    }

    // Prepare for Update (Load ticket data into form)
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket Id:" + id));
        model.addAttribute("ticket", ticket);
        model.addAttribute("tickets", ticketRepository.findAll());
        return "tickets";
    }

    // Delete Ticket
    @GetMapping("/delete/{id}")
    public String deleteTicket(@PathVariable String id) {
        ticketRepository.deleteById(id);
        return "redirect:/";
    }
}