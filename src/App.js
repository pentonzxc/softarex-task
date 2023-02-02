import "./App.css";
import LoginPage from "./components/pages/login/LoginPage";
import RegisterPage from "./components/pages/register/RegisterPage";
import { Route, Router, Routes, useLocation } from "react-router-dom";
import AuthRoute from "./components/route/AuthRoute";
import FieldsPage from "./components/pages/fields/FieldsPage";
import Navbar from "./components/pages/navbar/Navbar";
import HomePage from "./components/pages/home/HomePage";
import Questionnaire from "./components/pages/questionnaire/Questionnaire";
import Responses from "./components/pages/responses/Responses";
import EditProfile from "./components/pages/profile/EditProfile";
import ChangePassword from "./components/pages/profile/ChangePassword";
import LogoutPage from "./components/pages/logout/LogoutPage";
import QuestionnairesAuthors from "./components/pages/questionnaire/QuestionnairesAuthors";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Navbar />}>
        <Route path="login" element={<LoginPage />} />
        <Route path="register" element={<RegisterPage />} />
        <Route path="/" element={<AuthRoute />}>
          <Route index element={<HomePage />} />
          <Route path="fields" element={<FieldsPage/>}/>
          <Route path="responses" element={<Responses />} />
          <Route path="editProfile" element={<EditProfile />} />
          <Route path="changePassword" element={<ChangePassword />} />
          <Route path="logout" element={<LogoutPage />} />
        </Route>
        <Route path="/questionnaires" element={<QuestionnairesAuthors/>} />
        <Route path="/questionnaire/:id" element={<Questionnaire/>} />
      </Route>
    </Routes>
  );
}

export default App;
