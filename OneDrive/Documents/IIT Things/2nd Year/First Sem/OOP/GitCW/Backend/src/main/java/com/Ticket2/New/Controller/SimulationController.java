package com.Ticket2.New.Controller;

import com.Ticket2.New.Service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/simulation")
public class SimulationController {
    private final SimulationService simulationService;

    @Autowired
    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @PostMapping("/start")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<String> startSimulation() {
        String response = simulationService.startSimulation();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/stop")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<String> stopSimulation() {
        String response = simulationService.stopSimulation();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    public ResponseEntity<Boolean> getSimulationStatus() {
        return ResponseEntity.ok(simulationService.isRunning());
    }

    @GetMapping("/results")
    public ResponseEntity<String> getSimulationResults() {
        String terminalOutput = simulationService.getTerminalOutput();
        return ResponseEntity.ok(terminalOutput);
    }
}
