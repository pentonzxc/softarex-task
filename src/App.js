import "./App.css";
import LoginPage from "./components/pages/login/LoginPage";
import RegisterPage from "./components/pages/register/RegisterPage";
import { Route, Routes } from "react-router-dom";
import AuthRoute from "./components/route/AuthRoute";
import TestResponse from "./TestResponse";
import { useUser } from "./components/context/UserProvider";
import FieldsPage from "./components/pages/fields/FieldsPage";
import Delevopment from "./Development";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import TestLogin from "./TestLogin";
import TestRegister from "./TestRegister";
import Navbar from "./Navbar";
import BootsrapJs from "./BootsrapJs";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";


function App() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/" element={<AuthRoute />}>
        <Route path="home" element={<div>Home page</div>} />
        <Route path="fields" element={<FieldsPage />} />
      </Route>
      <Route path="/development" element={<Delevopment />} />
      <Route path="/testL" element={<TestLogin />} />
      <Route path="/testR" element={<TestRegister />} />
      <Route path="/nav" element={<Navbar />} />
      <Route path="/bjs" element={<BootsrapJs/>}/>
    </Routes>
  );
}

export default App;
