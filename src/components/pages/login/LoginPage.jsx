import React, { useCallback, useState } from "react";
import { useUser } from "../../context/UserProvider";
import Cookies from "js-cookie";
import { status } from "http-status";
import {useNavigate} from "react-router-dom";
import MyButton from "../../UI/button/MyButton";
import MyInput from "../../UI/input/MyInput";
import Card from "../../UI/card/Card";
import CenterContainer from "../../UI/container/CenterContainer";

export default function LoginPage() {
  const logo = "logo";
  const title = "Log in";
  const navigate = useNavigate();

  const status = require("http-status");
  const user = useUser();

  const initialUserData = {
    email: "",
    password: "",
    rememberMe: false,
  };

  const [userData, setUserData] = useState(initialUserData);

  const updateUserDataHandler = useCallback(
    (type) => (event) => {
      setUserData({ ...userData, [type]: event.target.value });
    },
    [userData]
  );

  const loginHandler = useCallback(() => {
    console.log(userData);

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

          user.tokens.access.setToken(Cookies.get("token"));
          user.tokens.refresh.setRefreshToken(Cookies.get("refresh_token"));
          navigate("/home" , {replace: true});
          return response.json();
        } else {
          throw new Error("Login failed");
        }
      }).then((name) => {
        user.data.setUserName(name);
      }).catch((error) => {
        console.log(error);
      });
  }, [userData]);

  const validation = (event) => {
    const form = event.currentTarget;
    if (!form.checkValidity()) {
      event.stopPropagation();
      form.classList.add("was-validated");
      return true;
    }
    return false;
  };

  const formHandler = (event) => {
    event.preventDefault();
    if (!validation(event)) {
      loginHandler();
    }
  };

  return (
    <CenterContainer>
      <Card cardHeight={50} logo={logo} title={title}>
        <div className="form-responsive">
          <form noValidate={true} className="mt-3" onSubmit={formHandler}>
            <div className="form-group form-floating">
              <input
                className="form-control"
                type="email"
                placeholder="Email"
                value={userData.email}
                onChange={updateUserDataHandler("email")}
                required
              />
              <label htmlFor="email" className="form-label">
                Email
              </label>
              {/* <div className="invalid-feedback">Invalid</div>
              <div className="valid-feedback">Valid</div> */}
            </div>
            <div className="form-group form-floating mt-3 ">
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
            <div className="d-flex align-items-center gap-3">
              <div className="form-check mt-2">
                <input
                  className="form-check-input"
                  type="checkbox"
                  value=""
                  id="remember"
                />
                <label
                  className="form-check-label"
                  htmlFor="remember"
                  value={userData.rememberMe}
                  onChange={updateUserDataHandler("rememberMe")}
                >
                  Remember me
                </label>
              </div>
              <div className="form-text mt-1">
                <a href="#" className="stretched-link-primary">
                  Forgot your password?
                </a>
              </div>
            </div>
            <button type="submit" className="btn btn-primary w-100 mt-1">
              Submit
            </button>
            <div className="d-flex justify-content-center align-items-center">
              <div className="form-text mt-1">
                <span className="text-dark">Don't have account?</span>
                <a href="#" className="stretched-link-primary mx-2">
                  Sign up?
                </a>
              </div>
            </div>
          </form>
        </div>
      </Card>
    </CenterContainer>
  );
}
