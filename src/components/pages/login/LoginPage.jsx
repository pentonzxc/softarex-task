import React, { useCallback, useState } from "react";
import Cookies from "js-cookie";
import { useNavigate, useLocation } from "react-router-dom";
import './style.css'

export default function LoginPage() {
  const navigate = useNavigate();

  React.useEffect(() => {
    if (Cookies.get("token")) {
      navigate("/", { replace: true });
    }
  }, []);

  const status = require("http-status");
  // const user = useUser();

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

          // user.tokens.access[1](Cookies.get("token"));
          // user.tokens.refresh[1](Cookies.get("refresh_token"));
          navigate("/", { replace: true });
          // it's doesn't work ...
        } else {
          throw new Error("Login failed");
        }
      })
      .catch((error) => {
        console.log(error);
      });
  }, [userData]);

  // const validation = (event) => {
  //   const form = event.currentTarget;
  //   if (!form.checkValidity()) {
  //     event.stopPropagation();
  //     form.classList.add("was-validated");
  //     return true;
  //   }
  //   return false;
  // };

  function formHandler(event) {
    event.preventDefault();
    // if (!validation(event)) {
    login();
    // }
  }

  return (
    // <CenterContainer>
    //   <Card cardHeight={50} logo={logo} title={title}>
    //     <div className="form-responsive">
    //       <form noValidate={true} className="mt-3" onSubmit={formHandler}>
    //         <div className="form-group form-floating">
    //           <input
    //             className="form-control"
    //             type="email"
    //             placeholder="Email"
    //             value={userData.email}
    //             onChange={updateUserDataHandler("email")}
    //             required
    //           />
    //           <label htmlFor="email" className="form-label">
    //             Email
    //           </label>
    //           {/* <div className="invalid-feedback">Invalid</div>
    //           <div className="valid-feedback">Valid</div> */}
    //         </div>
    //         <div className="form-group form-floating mt-3 ">
    //           <input
    //             className="form-control"
    //             type="password"
    //             placeholder="password"
    //             value={userData.password}
    //             onChange={updateUserDataHandler("password")}
    //             required
    //           />
    //           <label htmlFor="password" className="form-label">
    //             Password
    //           </label>
    //         </div>
    //         <div className="d-flex align-items-center gap-3">
    //           <div className="form-check mt-2">
    //             <input
    //               className="form-check-input"
    //               type="checkbox"
    //               value=""
    //               id="remember"
    //             />
    //             <label
    //               className="form-check-label"
    //               htmlFor="remember"
    //               value={userData.rememberMe}
    //               onChange={updateUserDataHandler("rememberMe")}
    //             >
    //               Remember me
    //             </label>
    //           </div>
    //           <div className="form-text mt-1">
    //             <a href="#" className="stretched-link-primary">
    //               Forgot your password?
    //             </a>
    //           </div>
    //         </div>
    //         <button type="submit" className="btn btn-primary w-100 mt-1">
    //           Submit
    //         </button>
    //         <div className="d-flex justify-content-center align-items-center">
    //           <div className="form-text mt-1">
    //             <span className="text-dark">Don't have account?</span>
    //             <a href="#" className="stretched-link-primary mx-2">
    //               Sign up?
    //             </a>
    //           </div>
    //         </div>
    //       </form>
    //     </div>
    //   </Card>
    // </CenterContainer>

    <div className="container__wrapper min-vh-100 min-vw-100">
      <div className="container d-flex align-items-center justify-content-center min-vh-100 text-center">
        <div className="col-9 col-sm-7 col-md-5 col-lg-5 col-xxl-3">
          <div className="card" style={{ minHeight: 300 }}>
            <div className="card-body">
              <h5 className="card-title">SoftArex</h5>
              <h6 className="card-subtitle mb-2 text-muted">Sign in</h6>
              <form className="d-flex flex-column gap-2" onSubmit={formHandler}>
                <div className="form-group form-floating">
                  <input
                    type="email"
                    placeholder="Email"
                    className="form-control"
                    value={userData.email}
                    onChange={updateUserDataHandler("email")}
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
