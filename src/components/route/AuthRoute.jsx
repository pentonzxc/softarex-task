import Cookies from "js-cookie";
import React, { useContext, useEffect } from "react";
import { useState } from "react";
import { useNavigate, Outlet, Navigate } from "react-router-dom";
import { useUser } from "../context/UserProvider";
import LoginPage from "../pages/login/LoginPage";
import UserContext from "../context/UserProvider";
import status from "http-status";

export default function AuthRoute() {
  const context = useUser();
  const status = require("http-status");

  const [isLoading, setIsLoading] = useState(true);
  const [isTokenValid, setIsTokenValid] = useState(null);

  useEffect(() => {
    const validate = async () => {
      const tokenResponse = await fetch(
        "http://localhost:8080/v1/api/auth/validate",
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "Cookie": "token=" + Cookies.get("token") + "; refresh_token=" + Cookies.get("refresh_token")
          },
          cors: "cors",
          credentials: "include",
        }
      )
        .then((response) => {
          if (response.status !== status.OK) {
            throw new Error("Tokens are invalid");
          }
          setIsTokenValid(true);
        })
        .catch((error) => {
          console.error(error);
          setIsTokenValid(false);
        });
        setIsLoading(false);
    };
    validate();
  }, [context.tokens.access[0], context.tokens.refresh[0]]);

  return (
    <>
      {isLoading && <div>Loading...</div>}
      {isTokenValid === true && <Outlet />}
      {isTokenValid === false && <Navigate to="/login" />}
    </>
  );
}
