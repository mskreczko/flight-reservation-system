import { Route, Routes } from "react-router-dom";
import Main from "./components/main/Main";
import Signin from "./components/auth/Signin";
import Signup from "./components/auth/Signup";
import Contact from "./components/contact/Contact";
import About from "./components/about/About";
import FlightSearch from "./components/flight-search/FlightSearch";
import BookFlight from "./components/flight-booking/BookFlight";
import ProtectedRoute from "./components/auth/ProtectedRoute";
import './App.css';
import Logout from "./components/auth/Logout";
import { RecoilRoot } from "recoil";
import AccountDetails from "./components/user/AccountDetails";
import SuccessfulBook from "./components/flight-booking/SuccessfulBook";
import VerifiedEmail from "./components/auth/VerifiedEmail";
import NotFoundErrorPage from "./components/error-pages/404";

function App() {
  return (
    <RecoilRoot>
      <div className="App">
        <Routes>
          <Route path="/user" element={ <ProtectedRoute/> }>
            <Route index element={ <FlightSearch/> }/>
            <Route path="book-flight" element={ <BookFlight/> }/>
            <Route path="success" element={ <SuccessfulBook/> }/>
            <Route path="account" element={ <AccountDetails/> }/>
            <Route path="logout" element={ <Logout/> }/>
            <Route path="contact" element={ <Contact/> }/>
            <Route path="about" element={ <About/> }/>
          </Route>
          <Route path="/" element={ <Main/> }>
            <Route index element={ <FlightSearch/> }/>
            <Route path="signin" element={ <Signin/> }/>
            <Route path="signup" element={ <Signup/> }/>
            <Route path="contact" element={ <Contact/> }/>
            <Route path="about" element={ <About/> }/>
            <Route path="activateAccount" element={ <VerifiedEmail/> }/>
          </Route>
          <Route path="*" element={ <NotFoundErrorPage/> }/>
        </Routes>
      </div>
    </RecoilRoot>
  );
}

export default App;
