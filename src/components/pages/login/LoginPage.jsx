import React, { useCallback, useState, useEffect } from "react";
import Cookies from "js-cookie";
import { useNavigate, useLocation } from "react-router-dom";
import "./style.css";
import $ from "jquery";

export default function LoginPage() {
  const navigate = useNavigate();

  useEffect(() => {
    if (Cookies.get("token")) {
      navigate("/", { replace: true });
    }

    return () => {
      localStorage.removeItem("token_state");
    };
  }, []);
  const location = useLocation();
  // const showTokenExpiredMsg = location.state?.message;
  // const setShowTokenExpiredMsg = location.state?.setMessage;
  // console.log(showTokenExpiredMsg);

  setTimeout(function () {
    if ($("#token__expired").length > 0) {
      $("#token__expired").remove();
    }
  }, 5000);

  const status = require("http-status");

  const initUserData = {
    email: "",
    password: "",
    rememberMe: false,
  };

  const [userData, setUserData] = useState(initUserData);

  const updateUserDataHandler = useCallback(
    (type) => (event) => {
      if (type === "rememberMe")
        setUserData({ ...userData, [type]: event.target.checked });
      else {
        setUserData({ ...userData, [type]: event.target.value });
      }
    },
    [userData]
  );

  const login = useCallback(() => {
    fetch("http://localhost:8080/v1/api/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userData),
      credentials: "include",
      mode: "cors",
    })
      .then((response) => {
        if (response.status === status.OK) {
          console.log("Login successful");

          navigate("/", { replace: true });
        } else {
          throw new Error("Login failed");
        }
      })
      .catch((error) => {
        console.log(error);
        console.log("Login failed");
        $("#error_msg").remove();
        $("#card__wrapper").prepend(
          `<div class ="alert alert-danger alert-dismissible fade show" role="alert" id="error_msg">
          <strong>Login failed!</strong> Your email or password is incorrect.
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>`
        );
      });
  }, [userData]);

  function formHandler(event) {
    event.preventDefault();
    event.stopPropagation();
    if (event.currentTarget.checkValidity() === false) {
      {
        event.currentTarget.classList.add("was-validated");
        return;
      }
    } else {
      login();
    }
  }

  return (
    <div className="container__wrapper">
      <div className="container d-flex align-items-center justify-content-center  text-center">
        <div
          className="col-9 col-sm-7 col-md-5 col-lg-5 col-xxl-3"
          id="card__wrapper"
        >
          {localStorage.getItem("token_state") === "invalid" && (
            <div
              className="alert alert-warning alert-dismissible fade show"
              role="alert"
              id="token__expired"
            >
              <strong>Token expired!</strong> Please login again.
              <button
                type="button"
                className="btn-close"
                data-bs-dismiss="alert"
                aria-label="Close"
                onClick={() => {
                  localStorage.removeItem("token_state");
                }}
              ></button>
            </div>
          )}

          <div className="card" style={{ minHeight: 300 }}>
            <div className="card-body">
              <h5 className="card-title">SoftArex</h5>
              <h6 className="card-subtitle mb-2 text-muted">Sign in</h6>
              <form
                className="d-flex flex-column gap-2"
                noValidate
                onSubmit={formHandler}
              >
                <div className="form-group form-floating">
                  <input
                    type="email"
                    placeholder="Email"
                    className="form-control"
                    value={userData.email}
                    onChange={updateUserDataHandler("email")}
                    required
                  />

                  <label htmlFor="email" className="form-label">
                    Email
                  </label>
                </div>
                <div className="form-group form-floating">
                  <input
                    className="form-control"
                    type="password"
                    placeholder="password"
                    value={userData.password}
                    onChange={updateUserDataHandler("password")}
                    required
                  />
                  <label htmlFor="password" className="form-label">
                    Password
                  </label>
                </div>
                <div className="d-flex flex-column flex-sm-row justify-content-between align-items-center gap-2">
                  <div className="form-check mt-2">
                    <input
                      className="form-check-input"
                      type="checkbox"
                      id="remember"
                      value={userData.rememberMe}
                      onChange={updateUserDataHandler("rememberMe")}
                    />
                    <label
                      className="form-check-label"
                      style={{ whiteSpace: "nowrap" }}
                      htmlFor="remember"
                    >
                      Remember me
                    </label>
                  </div>
                  <div className="form-text">
                    <a
                      href="#"
                      className="stretched-link-primary fw-bold"
                      style={{ whiteSpace: "nowrap" }}
                    >
                      Forgot your password?
                    </a>
                  </div>
                </div>
                <button type="submit" className="btn btn-primary w-100 mt-1">
                  Submit
                </button>
                <div className="form-text mt-1">
                  <span className="text-dark">Don't have account?</span>
                  <a href="#" className="stretched-link-primary mx-2">
                    Sign up?
                  </a>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
