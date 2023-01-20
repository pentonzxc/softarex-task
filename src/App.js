import "./App.css";
import LoginPage from "./components/pages/login/LoginPage";
import RegisterPage from "./components/pages/register/RegisterPage";
import { Route, Routes } from "react-router-dom";
import AuthRoute from "./components/route/AuthRoute";
import TestResponse from "./TestResponse";
import FieldsPage from "./components/pages/fields/FieldsPage";
import 'bootstrap/dist/css/bootstrap.min.css'

function App() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/" element={<AuthRoute />}>
        <Route path="home" element={<div>Home</div>} />
        <Route path="fields" element={<FieldsPage/>} />
      </Route>
    </Routes>
  );
}

export default App;
