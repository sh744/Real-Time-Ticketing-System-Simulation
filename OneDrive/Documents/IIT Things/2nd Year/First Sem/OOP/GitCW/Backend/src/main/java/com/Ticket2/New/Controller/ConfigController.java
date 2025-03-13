package com.Ticket2.New.Controller;

import com.Ticket2.New.Model.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/simulation/config")
public class ConfigController {

    @Autowired
    private Configuration configuration;

    @PostMapping("/set")
    public ResponseEntity<String> setConfiguration(@RequestParam int ticketReleaseRate, @RequestParam int customerRetrievalRate, @RequestParam int maxTicketCapacity) {
        configuration.setTicketReleaseRate(ticketReleaseRate);
        configuration.setCustomerRetrievalRate(customerRetrievalRate);
        configuration.setMaxTicketCapacity(maxTicketCapacity);

        return ResponseEntity.ok("Configuration set successfully");
    }
}
