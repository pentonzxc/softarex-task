import "./App.css";
import LoginPage from "./components/pages/login/LoginPage";
import RegisterPage from "./components/pages/register/RegisterPage";
import { Route, Routes } from "react-router-dom";
import AuthRoute from "./components/route/AuthRoute";
import TestResponse from "./TestResponse";
import { useUser } from "./components/context/UserProvider";
import FieldsPage from "./components/pages/fields/FieldsPage";
import Delevopment from "./Development";
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min'


function App() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/" element={<AuthRoute />}>
        <Route path="home" element={
          <div>Home page</div>
        } />
        <Route path="fields" element={<FieldsPage/>} />
      </Route>
      <Route path="/development" element={<Delevopment/>}>
      </Route>
     
    </Routes>
  );
}

export default App;
