import React from "react";
import "./test.css";

export default function TestLogin() {
  return (
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
                    type="email"
                    placeholder="Email"
                    className="form-control"
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
