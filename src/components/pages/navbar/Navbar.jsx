import React, { useEffect } from "react";
import { Outlet } from "react-router-dom";
import { useLocation, Link } from "react-router-dom";
import { useUser } from "../../context/UserProvider";

export default function Navbar() {
  const location = useLocation();
  const context = useUser();
  const [fullName, setFullName] = context;

  return (
    <div className="min-vh-100 w-100 bg-light d-flex flex-column">
      <nav className="navbar navbar-expand-lg bg-white border-bottom border-1">
        <div className="container">
          <Link to="/" className="navbar-brand">
            Softarex
          </Link>
          <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
            <ul className="navbar-nav ms-auto d-flex gap-5">
              {location.pathname === "/register" ||
              location.pathname === "/login" ||
              location.pathname.match("^/questionnaire/\\d+$") ||
              location.pathname === "/questionnaires" ? (
                <>
                  <li className="nav-item">
                    <a className="nav-link" href="/login">
                      Log in
                    </a>
                  </li>
                </>
              ) : (
                <>
                  <li className="nav-item">
                    <Link to="/fields" className="nav-link active">
                      Fields
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/responses" className="nav-link active">
                      Responses
                    </Link>
                  </li>
                  <li className="nav-item dropdown">
                    <a
                      className="nav-link link-dark dropdown-toggle"
                      data-bs-toggle="dropdown"
                    >
                      {fullName}
                    </a>
                    <div className="dropdown-menu">
                      <Link to="/editProfile" className="dropdown-item">
                        Edit profile
                      </Link>
                      <Link to="/changePassword" className="dropdown-item">
                        Change password
                      </Link>
                      <div className="dropdown-divider"></div>
                      <Link to="/logout" className="dropdown-item">
                        Log out
                      </Link>
                    </div>
                  </li>
                </>
              )}
            </ul>
          </div>
        </div>
      </nav>
      <div className="container my-lg-auto p-0">
        <Outlet />
      </div>
    </div>
  );
}
