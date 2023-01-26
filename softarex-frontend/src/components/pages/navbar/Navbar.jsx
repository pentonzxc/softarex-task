import React from "react";
import { faTrashCan } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPenToSquare } from "@fortawesome/free-solid-svg-icons";
import { Outlet } from "react-router-dom";
import { useLocation, useNavigate, Link } from "react-router-dom";

export default function Navbar() {
  const location = useLocation();
  const navigate = useNavigate();
  const fields = [
    {
      label: "First Name",
      type: "text",
      required: true,
      active: true,
    },
    {
      label: "Last Name",
      type: "text",

      required: true,
      active: true,
    },
  ];

  return (
    <div className="h-100 w-100 bg-light">
      <nav className="navbar navbar-expand-lg bg-white border-bottom border-1">
        <div className="container">
          <a className="navbar-brand">Logo</a>
          <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
            <ul className="navbar-nav ms-auto d-flex gap-5">
              {location.pathname === "/register" ||
              location.pathname === "/login" ? (
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
                      Your name
                    </a>
                    <div className="dropdown-menu">
                      <Link to="/profile" className="dropdown-item">
                        Profile
                      </Link>
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

      <Outlet />
    </div>
  );
}
