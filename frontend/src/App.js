import { Route, Routes } from "react-router-dom";
import Main from "./components/main/Main";
import Signin from "./components/auth/Signin";
import Signup from "./components/auth/Signup";
import Contact from "./components/contact/Contact";
import About from "./components/about/About";
import FlightSearch from "./components/flight-search/FlightSearch";

import './App.css';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Main/>}>
          <Route index element={<FlightSearch/>}/>
          <Route path="signin" element={<Signin/>}/>
          <Route path="signup" element={<Signup/>}/>
          <Route path="contact" element={<Contact/>}/>
          <Route path="about" element={<About/>}/>
        </Route>
      </Routes>
    </div>
  );
}

export default App;
