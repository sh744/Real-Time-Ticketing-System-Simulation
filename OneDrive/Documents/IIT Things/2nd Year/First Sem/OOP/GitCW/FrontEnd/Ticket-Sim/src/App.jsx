import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Config from './Config';
import Simulation from './Simulation';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Config />} />
        <Route path="/simulation" element={<Simulation />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
