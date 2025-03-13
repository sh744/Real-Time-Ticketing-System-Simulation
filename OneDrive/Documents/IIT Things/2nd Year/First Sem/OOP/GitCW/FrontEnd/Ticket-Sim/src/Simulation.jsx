import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Simulation.css';

function Simulation() {
    const [simulationRunning, setSimulationRunning] = useState(false);
    const [terminalOutput, setTerminalOutput] = useState('');
    const [error, setError] = useState(null);

    useEffect(() => {
        const intervalId = setInterval(() => {
            if (simulationRunning) {
                axios.get('http://localhost:8080/simulation/results')
                    .then(response => {
                        console.log('Received logs:', response.data);
                        if (response.data) {
                            setTerminalOutput(prev => prev + response.data);
                        }
                    })
                    .catch(error => {
                        console.error('Error fetching logs:', error);
                        setError('Error fetching simulation logs');
                    });
            }
        }, 1000); // update every second
        
        return () => clearInterval(intervalId);
    }, [simulationRunning]);

    const handleStartSimulation = () => {
        setError(null);
        setTerminalOutput(''); // Clear previous output
        
        axios.post('http://localhost:8080/simulation/start')
            .then(response => {
                console.log('Simulation started:', response.data);
                setSimulationRunning(true);
            })
            .catch(error => {
                console.error('Error starting simulation:', error);
                setError('Failed to start simulation');
                setSimulationRunning(false);
            });
    };
        
    const handleStopSimulation = () => {
        axios.post('http://localhost:8080/simulation/stop')
            .then(response => {
                console.log('Simulation stopped:', response.data);
                setSimulationRunning(false);
            })
            .catch(error => {
                console.error('Error stopping simulation:', error);
                setError('Failed to stop simulation');
            });
    };

    // Function to scroll terminal to bottom whenever new content is added
    useEffect(() => {
        const terminal = document.getElementById('terminal');
        if (terminal) {
            terminal.scrollTop = terminal.scrollHeight;
        }
    }, [terminalOutput]);

    return (
        <div className="container">
            <h1>Ticket Simulation</h1>
            <div className="button-container">
                <button 
                    id="start-button" 
                    onClick={handleStartSimulation}
                    disabled={simulationRunning}
                >
                    Start Simulation
                </button>
                <button 
                    id="stop-button" 
                    onClick={handleStopSimulation} 
                    disabled={!simulationRunning}
                >
                    Stop Simulation
                </button>
            </div>
            
            {error && <div className="error-message">{error}</div>}
            
            <div className="terminal" id="terminal">
                <pre>{terminalOutput || 'Waiting for simulation to start...'}</pre>
            </div>
        </div>
    );
}

export default Simulation;