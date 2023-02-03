import React from "react";

import { useState } from "react";
import $ from "jquery";

export default function ChangePassword() {
  const status = require("http-status");

  const [passwordData, setPasswordData] = useState({
    oldPassword: "",
    newPassword: "",
    confirmNewPassword: "",
  });

  const [okResponse, setOkResponse] = useState(null);

  const updatePasswordDataHandler = (type) => (e) => {
    setPasswordData({
      ...passwordData,
      [type]: e.target.value,
    });
  };

  function formHandler(event) {
    event.preventDefault();
    event.stopPropagation();

    if (event.currentTarget.checkValidity() === false) {
      event.currentTarget.classList.add("was-validated");
      return;
    }

    changePassword();
  }

  function changePassword() {
    let url = new URL(
      `http://localhost:8080/v1/api/user/profile/changePassword`
    );
    let params = { id: localStorage.getItem("user_id") };
    url.search = new URLSearchParams(params).toString();

    fetch(url, {
      method: "PUT",
      cors: "cors",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        oldPassword: passwordData.oldPassword,
        newPassword: passwordData.newPassword,
      }),
    })
      .then((response) => {
        if (response.status === status.OK) {
          setOkResponse(true);
          return response.text();
        } else {
          setOkResponse(false);
          throw new Error("Failed to change password");
        }
      })
      .then((data) => {
        console.log(data);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  return (
    <div className="container__wrapper">
      <div className="container d-flex align-items-center justify-content-center text-center">
        <div
          className="col-9 col-sm-7 col-md-5 col-lg-5 col-xxl-3"
          id="card__wrapper"
        >
          {okResponse === true && (
            <>
              <div
                className="alert alert-success my-0 alert-dismissible fade show"
                role="alert"
              >
                <strong>Veryfy your new password!</strong> Message was sent to
                your email.
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="alert"
                  aria-label="Close"
                  onClick={() => {
                    setOkResponse(null);
                  }}
                ></button>
              </div>
            </>
          )}
          {okResponse === false && (
            <>
              <div
                className="alert alert-danger my-0 alert-dismissible fade show"
                role="alert"
              >
                <strong>Invalid current password</strong>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="alert"
                  aria-label="Close"
                  onClick={() => {
                    setOkResponse(null);
                  }}
                ></button>
              </div>
            </>
          )}
          <div className="card" style={{ minHeight: 300 }}>
            <div className="card-body">
              <h6
                className="card-subtitle mb-2 fs-5"
                style={{ textAlign: "left" }}
              >
                Change password
              </h6>
              <hr></hr>
              <form
                className="d-flex flex-column gap-2"
                noValidate
                onSubmit={formHandler}
                onInput={() => {
                  $("#confirmNewPassword")
                    .get(0)
                    .setCustomValidity(
                      $("#newPassword").get(0).value !==
                        $("#confirmNewPassword").get(0).value
                        ? "Passwords do not match."
                        : ""
                    );
                }}
              >
                <div className="form-group form-floating">
                  <input
                    className="form-control"
                    type="text"
                    placeholder="password"
                    value={passwordData.oldPassword}
                    onChange={updatePasswordDataHandler("oldPassword")}
                    required
                  />
                  <label htmlFor="password" className="form-label">
                    Current password
                  </label>
                </div>
                <div className="form-group form-floating">
                  <input
                    className="form-control"
                    type="text"
                    id="newPassword"
                    placeholder="New password"
                    value={passwordData.newPassword}
                    onChange={updatePasswordDataHandler("newPassword")}
                    required
                  />
                  <label htmlFor="firstName" className="form-label">
                    New password
                  </label>
                </div>
                <div className="form-group form-floating">
                  <input
                    className="form-control"
                    type="text"
                    placeholder="Confirm new password"
                    id="confirmNewPassword"
                    value={passwordData.confirmNewPassword}
                    onChange={updatePasswordDataHandler("confirmNewPassword")}
                    required
                  />
                  <label htmlFor="lastName" className="form-label">
                    Confirm new password
                  </label>
                  <div className="invalid-feedback">
                    Passwords do not match.
                  </div>
                </div>

                <button type="submit" className="btn btn-primary w-100 mt-1">
                  Submit
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
