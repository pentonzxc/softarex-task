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
  const [fullName, setFullName] = context;
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
              localStorage.removeItem("user_full_name");
              setFullName(null);
              localStorage.setItem("token_state", "invalid");
            }
            throw new Error("Tokens are invalid");
          }
          setIsTokenValid(true);
          return response.json();
        })
        .then((body) => {
          for (const property in body) {
            localStorage.setItem("user_id", property);
            localStorage.setItem("user_full_name", body[property]);
            setFullName(body[property]);
          }
        })
        .catch((error) => {
          console.error(error);
          Cookies.remove("token");
          Cookies.remove("refresh_token");
          setIsTokenValid(false);
        });

      setIsLoading(false);
      // if (localStorage.getItem("user_full_name") === null) {
      //   getUserFullName();
      // }
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

  async function getUserFullName() {
    fetch(
      `http://localhost:8080/v1/api/user/profile/${localStorage.getItem(
        "user_id"
      )}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
        cors: "cors",
        credentials: "include",
      }
    )
      .then((response) => {
        if (response.status === status.OK) {
          console.log(response);
          return response.text();
        } else {
          throw new Error("Failed to fetch user");
        }
      })
      .then((data) => {
        localStorage.setItem("user_full_name", data);
      });
  }

  return (
    <>
      {isLoading && (
        <div className="d-flex justify-content-center align-items-center h-100 w-100">
          <div
            className="spinner-border ml-auto"
            role="status"
            aria-hidden="true"
          ></div>
        </div>
      )}
      {isTokenValid === true && <Outlet />}
      {isTokenValid === false && <Navigate to="/login" />}
    </>
  );
}
