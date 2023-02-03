import React from "react";
import { useEffect, useState, useCallback } from "react";
import { useUser } from "../../context/UserProvider";

export default function EditProfile() {
  const context = useUser();
  const [fullName, setFullName] = context;
  const initialData = {
    email: "",
    firstName: "",
    lastName: "",
    phoneNumber: "",
  };

  const [initialProfileData, setInitialProfileData] = useState(initialData);
  const [profileData, setProfileData] = useState(initialProfileData);

  const [okResponse, setOkResponse] = useState(null);

  useEffect(() => {
    fetchUser();
  }, []);

  const updateProfileDataHandler = useCallback(
    (type) => (event) => {
      setProfileData({ ...profileData, [type]: event.target.value });
    },
    [profileData]
  );

  const id = localStorage.getItem("user_id");
  const status = require("http-status");

  function formHandler(event) {
    event.preventDefault();
    event.stopPropagation();

    if (event.currentTarget.checkValidity() === false) {
      event.currentTarget.classList.add("was-validated");
      return;
    }
    if (
      profileData.email === initialProfileData.email &&
      profileData.firstName === initialProfileData.firstName &&
      profileData.lastName === initialProfileData.lastName &&
      profileData.phoneNumber === initialProfileData.phoneNumber
    ) {
      console.log("no changes");
      setOkResponse(true);
      return;
    }

    changeProfile();
  }

  function changeProfile() {
    fetch(`http://localhost:8080/v1/api/user/profile/updateProfile`, {
      method: "PUT",
      cors: "cors",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        oldEmail: initialProfileData.email,
        newEmail: profileData.email,
        firstName: profileData.firstName,
        lastName: profileData.lastName,
        phoneNumber: profileData.phoneNumber,
      }),
    })
      .then((response) => {
        if (response.status === status.OK) {
          setOkResponse(true);
          if (
            localStorage.getItem("user_full_name") !==
            profileData.firstName + " " + profileData.lastName
          ) {
            localStorage.setItem(
              "user_full_name",
              profileData.firstName + " " + profileData.lastName
            );
            setFullName(profileData.firstName + " " + profileData.lastName);
          }
        } else {
          throw new Error("Failed to update profile");
        }
      })
      .catch((error) => {
        setOkResponse(false);
      });
  }

  async function fetchUser() {
    fetch(`http://localhost:8080/v1/api/user/${id}`, {
      method: "GET",
      cors: "cors",
      credentials: "include",
    })
      .then((response) => {
        console.log(response);
        if (response.status === status.OK) {
          return response.json();
        } else {
          throw new Error("Failed to fetch user");
        }
      })
      .then((data) => {
        setInitialProfileData(data);
        setProfileData(data);
      })
      .catch((error) => console.log(error));
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
                <strong>Profile is updated!</strong>
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
                <strong>Something went wrong</strong>
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
                Edit profile
              </h6>
              <hr></hr>
              <form
                className="d-flex flex-column gap-2"
                noValidate
                onSubmit={formHandler}
              >
                <div className="form-group form-floating">
                  <input
                    className="form-control"
                    type="email"
                    placeholder="email"
                    value={profileData.email}
                    onChange={updateProfileDataHandler("email")}
                    required
                  />
                  <label htmlFor="email" className="form-label">
                    Email
                  </label>
                </div>
                <div className="form-group form-floating">
                  <input
                    className="form-control"
                    type="text"
                    placeholder="First name"
                    value={profileData.firstName}
                    onChange={updateProfileDataHandler("firstName")}
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
                    value={profileData.lastName}
                    onChange={updateProfileDataHandler("lastName")}
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
                    value={profileData.phoneNumber}
                    onChange={updateProfileDataHandler("phoneNumber")}
                  />
                  <label htmlFor="phoneNumber" className="form-label">
                    Phone number
                  </label>
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
