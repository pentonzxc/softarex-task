import React, { useState } from "react";
import { useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useCallback } from "react";
import Cookies from "js-cookie";
import './style.css'

export default function RegisterPage() {
  const status = require("http-status");
  const logo = "logo";
  const title = "Sign up";
  const navigate = useNavigate();


  React.useEffect(() => {
    if(Cookies.get("token")){
      navigate("/" , {replace: true});
    }
  }, []);

  // const [emailInvalidFeedback , setEmailEnvalidFeedback] = useState("Please enter a valid email address");


  const email = useRef(null);
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
      }).then((response) => {
        if (response.status === status.CREATED) {
          console.log("Register successful");
          navigate("/login");
        } else {
          // document.getElementById("email").classList.add("is-invalid");
          // setEmailEnvalidFeedback("Email is already in use");
          // email.current.classList.add("is-invalid");
          console.log("Register failed");
          // addValidation(event);
        }
      });
    },
    [userData]
  );

  // const formValidation = (event) => {
  //   const form = event.currentTarget;
  //   if (!form.checkValidity()) {
  //     addValidation(event);
  //     return true;
  //   }
  //   console.log(userData.password + " " + confirmPassword.current.value); 
  //   if (userData.password !== confirmPassword.current.value) {
  //     try {
  //       // confirmPassword.current.setCustomValidity("Passwords do not match");
  //       // confirmPassword.current.validity.valid = false;
  //     } catch (ignore) {}
  //     addValidation(event);
  //     return true;
  //   }
  //   try {
  //     console.log("here");
  //     // confirmPassword.current.setCustomValidity("");
  //   } catch (ignore) {}
  //   event.preventDefault();
  //   return false;
  // };

  // const addValidation = (event) => {
  //   event?.currentTarget.classList.add("was-validated");
  // };

  function formHandler(event) {
    event.preventDefault();
    console.log(userData);
    // if (!formValidation(event)) {
      register();
    // }
  };

  // const checkPassword = (event) => {
  //   if (userData.password !== confirmPassword.current.value) {
  //     try {
  //       confirmPassword.current.setCustomValidity("Passwords do not match");
  //       confirmPassword.current.validity.valid = false;
  //     } catch (ignore) {}
  //   } else {
  //     try {
  //       console.log("here");
  //       confirmPassword.current.validity.valid = true;
  //     } catch (ignore) {}
  //   }
  // };


  return (
    // <CenterContainer>
    //   <Card cardHeight={76} logo={logo} title={title}>
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
    //             ref={email}
    //           />
    //           <label htmlFor="email" className="form-label">
    //             Email
    //           </label>
    //           <div className="invalid-feedback">{emailInvalidFeedback}</div>
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
    //         <div className="form-group form-floating mt-3 ">
    //           <input
    //             ref={confirmPassword}
    //             className="form-control"
    //             placeholder="Confirm password"
    //             // onChange={checkPassword}
    //             type="password"
    //             required
    //           />
    //           <label htmlFor="confirmPassword" className="form-label">
    //             Comfirm password
    //           </label>
    //           <div className="invalid-feedback">Passwords do not match</div>
    //         </div>
    //         <div className="form-group form-floating mt-3 ">
    //           <input
    //             className="form-control"
    //             type="text"
    //             placeholder="First name"
    //             value={userData.firstName}
    //             onChange={updateUserDataHandler("firstName")}
    //           />
    //           <label htmlFor="firstName" className="form-label">
    //             First name
    //           </label>
    //         </div>
    //         <div className="form-group form-floating mt-3 ">
    //           <input
    //             className="form-control"
    //             type="text"
    //             placeholder="Last name"
    //             value={userData.lastName}
    //             onChange={updateUserDataHandler("lastName")}
    //           />
    //           <label htmlFor="lastName" className="form-label">
    //             Last name
    //           </label>
    //         </div>
    //         <div className="form-group form-floating mt-3 ">
    //           <input
    //             className="form-control"
    //             type="text"
    //             placeholder="Phone number"
    //             value={userData.phoneNumber}
    //             onChange={updateUserDataHandler("phoneNumber")}
    //           />
    //           <label htmlFor="phoneNumber" className="form-label">
    //             Phone number
    //           </label>
    //         </div>
    //         <button type="submit" className="btn btn-primary w-100 mt-1">
    //           Submit
    //         </button>
    //         <div className="d-flex justify-content-center align-items-center">
    //           <div className="form-text mt-1">
    //             <span className="text-dark">Already have account?</span>
    //             <a href="#" className="stretched-link-primary mx-2">
    //               Sign in
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
              <h6 className="card-subtitle mb-2 text-muted">Sign up</h6>
              <div className="d-flex flex-column gap-2">
                <div className="form-group form-floating">
                  <input
                    className="form-control"
                    type="email"
                    placeholder="email"
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

                <div className="form-group form-floating">
                  <input
                    className="form-control"
                    placeholder="Confirm password"
                    type="password"
                  />
                  <label htmlFor="confirmPassword" className="form-label">
                    Comfirm password
                  </label>
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
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    



  );
}
