import React, { useState  , useEffect} from "react";
import { useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useCallback } from "react";
import Cookies from "js-cookie";
import "./style.css";
import $ from "jquery";

export default function RegisterPage() {
  const status = require("http-status");
  const navigate = useNavigate();

  useEffect(() => {
    if (Cookies.get("token")) {
      navigate("/", { replace: true });
    }
  }, []);

  const confirmPassword = useRef(null);

  const initialUserData = {
    email: "",
    password: "",
    firstName: "",
    lastName: "",
    phoneNumber: "",
  };

  const [userData, setUserData] = useState(initialUserData);

  const updateUserDataHandler = useCallback(
    (type) => (event) => {
      setUserData({ ...userData, [type]: event.target.value });
    },
    [userData]
  );

  const register = useCallback(
    (event) => {
      fetch("http://localhost:8080/v1/api/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },

        body: JSON.stringify(userData),
      })
        .then((response) => {
          if (response.status === status.CREATED) {
            console.log("Register successful");
            navigate("/login");
          } else {
            throw new Error("Register failed");
          }
        })
        .catch((error) => {
          console.log(error);
          $("#card__wrapper").prepend(
            `<div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Register failed!</strong> This email is already taken.
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
          </div>`
          );
        });
    },
    [userData]
  );

  function formHandler(event) {
    event.preventDefault();
    event.stopPropagation();
    console.log(event.currentTarget);
    if (event.currentTarget.checkValidity() === false) {
      event.currentTarget.classList.add("was-validated");
      return;
    }
    register();
  }

  return (
    <div className="container__wrapper h-100 w-100">
      <div className="container d-flex align-items-center justify-content-center min-vh-100 text-center">
        <div
          className="col-9 col-sm-7 col-md-5 col-lg-5 col-xxl-3"
          id="card__wrapper"
        >
          <div className="card" style={{ minHeight: 300 }}>
            <div className="card-body">
              <h5 className="card-title">SoftArex</h5>
              <h6 className="card-subtitle mb-2 text-muted">Sign up</h6>
              <form
                className="d-flex flex-column gap-2"
                noValidate
                onSubmit={formHandler}
                onInput={() => {
                  $("#confirmPassword")
                    .get(0)
                    .setCustomValidity(
                      $("#confirmPassword").get(0).value !== userData.password
                        ? "Passwords do not match."
                        : ""
                    );
                }}
              >
                <div className="form-group form-floating">
                  <input
                    className="form-control"
                    type="email"
                    placeholder="email"
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

                <div className="form-group form-floating">
                  <input
                    id="confirmPassword"
                    className="form-control"
                    placeholder="Confirm password"
                    ref={confirmPassword}
                    type="password"
                    required
                  />
                  <label htmlFor="confirmPassword" className="form-label">
                    Comfirm password
                  </label>
                  <div className="invalid-feedback">
                    Passwords do not match.
                  </div>
                </div>

                <div className="form-group form-floating">
                  <input
                    className="form-control"
                    type="text"
                    placeholder="First name"
                    value={userData.firstName}
                    onChange={updateUserDataHandler("firstName")}
                  />
                  <label htmlFor="firstName" className="form-label">
                    First name
                  </label>
                </div>
                <div className="form-group form-floating">
                  <input
                    className="form-control"
                    type="text"
                    placeholder="Last name"
                    value={userData.lastName}
                    onChange={updateUserDataHandler("lastName")}
                  />
                  <label htmlFor="lastName" className="form-label">
                    Last name
                  </label>
                </div>
                <div className="form-group form-floating">
                  <input
                    className="form-control"
                    type="text"
                    placeholder="Phone number"
                    value={userData.phoneNumber}
                    onChange={updateUserDataHandler("phoneNumber")}
                  />
                  <label htmlFor="phoneNumber" className="form-label">
                    Phone number
                  </label>
                </div>

                <button type="submit" className="btn btn-primary w-100 mt-1">
                  Submit
                </button>
                <div className="form-text mt-1">
                  <span className="text-dark">Already have account?</span>
                  <a href="#" className="stretched-link-primary mx-2">
                    Sign in
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
