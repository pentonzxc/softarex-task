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
              <h6 className="card-subtitle mb-2 text-muted">Sign in</h6>
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
                <div className="d-flex flex-column flex-sm-row justify-content-between align-items-center gap-2">
                  <div className="form-check mt-2">
                    <input
                      className="form-check-input"
                      type="checkbox"
                      value=""
                      id="remember"
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
                      style={{ whiteSpace: "nowrap"}}
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
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
