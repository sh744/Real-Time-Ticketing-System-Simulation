import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios'; 
import './Config.css';

function Config() {
  const [ticketReleaseRate, setTicketReleaseRate] = useState('');
  const [customerRetrievalRate, setCustomerRetrievalRate] = useState('');
  const [maxCapacity, setMaxCapacity] = useState('');
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const validateForm = (event) => {
    event.preventDefault();

    if (ticketReleaseRate === '' || customerRetrievalRate === '' || maxCapacity === '') {
      setError('Please fill in all fields.');
      return;
    }

    if (isNaN(ticketReleaseRate) || isNaN(customerRetrievalRate) || isNaN(maxCapacity)) {
      setError('Please enter valid numbers.');
      return;
    }

    if (parseInt(ticketReleaseRate) < 1 || parseInt(ticketReleaseRate) > 10) {
      setError('Ticket Release Rate must be between 1 and 10.');
      return;
    }

    if (parseInt(customerRetrievalRate) < 1 || parseInt(customerRetrievalRate) > 5) {
      setError('Customer Retrieval Rate must be between 1 and 5.');
      return;
    }

    if (parseInt(maxCapacity) <= parseInt(ticketReleaseRate)) {
      setError('Maximum Ticket Capacity must be greater than Ticket Release Rate.');
      return;
    }

    // Form is valid, you can submit it here
    axios.post('http://localhost:8080/simulation/config/set', null, {
      params: {
        ticketReleaseRate: ticketReleaseRate,
        customerRetrievalRate: customerRetrievalRate,
        maxTicketCapacity: maxCapacity,
      },
    })
    .then((response) => {
      if (response.data === 'Configuration set successfully') {
        navigate('/simulation');
      } else {
        setError(response.data);
      }
    })
    .catch((error) => {
      setError(error.message);
    });
  };

  return (
    <div className='container'>
      <h1>My Ticket Simulation</h1>
      <form onSubmit={validateForm}>
        <label htmlFor="ticketReleaseRate">Ticket Release Rate (1-10):</label>
        <input
          type="number"
          id="ticketReleaseRate"
          name="ticketReleaseRate"
          value={ticketReleaseRate}
          onChange={(event) => setTicketReleaseRate(event.target.value)}
          required
        />

        <label htmlFor="customerRetrievalRate">Customer Retrieval Rate (1-5):</label>
        <input
          type="number"
          id="customerRetrievalRate"
          name="customerRetrievalRate"
          value={customerRetrievalRate}
          onChange={(event) => setCustomerRetrievalRate(event.target.value)}
          required
        />

        <label htmlFor="maxCapacity">Maximum Ticket Capacity (greater than Ticket Release Rate):</label>
        <input
          type="number"
          id="maxCapacity"
          name="maxCapacity"
          value={maxCapacity}
          onChange={(event) => setMaxCapacity(event.target.value)}
          required
        />

        {error && <p style={{ color: 'red' }}>{error}</p>}

        <input type="submit" value="Submit" />
      </form>
    </div>
  );
}

export default Config;