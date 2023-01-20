import React from "react";
import { useUser } from "../../context/UserProvider";
import { UserContext } from "../../context/UserProvider";
import { useEffect, useState, useContext } from "react";
import { useOutletContext } from "react-router-dom";
import Cookies from "js-cookie";

export default function FieldsPage() {
  const [fields, setFields] = useState([]);
  const email = localStorage.getItem("email");
  console.log("test");

  useEffect(() => {
    const controller = new AbortController();
    const getAllFields = async ({ controller }) => {
      await fetch(`http://localhost:8080/v1/api/user/${email}/fields`, {
        method: "GET",
        cors: "cors",
        credentials: "include",
        signal: controller.signal,
      })
        .then((response) => {
          console.log(response);
          if (response.status === 200) {
            return response.json();
          } else {
            throw new Error("Failed to fetch fields");
          }
        })
        .then((data) => setFields(data))
        .catch((error) => console.log(error));
    };
    getAllFields({ controller });

    return () => {
      try {
        controller.abort();
      } catch (error) {
        console.error(error);
      }
    };
  }, []);

  return (
    <table className="table table-striped table-hover">
      <thead>
        <tr>
          <th scope="col">Label</th>
          <th scope="col">Type</th>
          <th scope="col">Required</th>
          <th scope="col">Is Active</th>
        </tr>
      </thead>
      <tbody>
        {fields.map((field, index) => (
          <tr key={index}>
            <td>{field.label}</td>
            <td>{field.type}</td>
            <td>{field.isRequired}</td>
            <td>{field.isActive}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
