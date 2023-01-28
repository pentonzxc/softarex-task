import Cookies from "js-cookie";
import React, { useContext, useEffect } from "react";
import { useState } from "react";
import { useNavigate, Outlet, Navigate } from "react-router-dom";
import { useUser } from "../context/UserProvider";
import LoginPage from "../pages/login/LoginPage";
import UserContext from "../context/UserProvider";
import status from "http-status";
import { Nav } from "react-bootstrap";

export default function AuthRoute() {
  const context = useUser();
  const status = require("http-status");

  const [isLoading, setIsLoading] = useState(true);
  const [isTokenValid, setIsTokenValid] = useState(null);

  useEffect(() => {
    const controller = new AbortController();
    const validate = async ({ conroller }) => {
      await fetch("http://localhost:8080/v1/api/auth/validate", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Cookie:
            "token=" +
            Cookies.get("token") +
            "; refresh_token=" +
            Cookies.get("refresh_token"),
        },
        cors: "cors",
        credentials: "include",
        signal: controller.signal,
      })
        .then((response) => {
          console.log(response);
          if (response.status !== status.OK) {
            if (localStorage.getItem("user_id")) {
              localStorage.removeItem("user_id");
              localStorage.setItem("token_state", "invalid");
            }
            throw new Error("Tokens are invalid");
          }
          setIsTokenValid(true);
          return response.text();
        })
        .then((id) => {
          const idOpt = localStorage.getItem("user_id");
          if (!idOpt || idOpt !== id) {
            localStorage.setItem("user_id", id);
          }
        })
        .catch((error) => {
          console.error(error);
          setIsTokenValid(false);
        });
      setIsLoading(false);
    };
    validate({ controller });
    return () => {
      try {
        controller.abort();
      } catch (error) {
        console.error(error);
      }
    };
  }, []);

  return (
    <>
      {isLoading && (
        <div class="d-flex justify-content-center align-items-center h-100 w-100">
          <div
            class="spinner-border ml-auto"
            role="status"
            aria-hidden="true"
          ></div>
        </div>
      )}
      {isTokenValid === true && <Outlet />}
      {isTokenValid === false && (
        <Navigate
          to="/login"
        />
      )}
    </>
  );
}
