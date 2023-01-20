import React from "react";
import { useUser } from "../../context/UserProvider";
import { useEffect, useState } from "react";

export default function FieldsPage() {
  const [fields, setFields] = useState([]);
  const context = useUser();
  const [email, setEmail] = context.user.data;

  useEffect(() => {
    fetch(`http://localhost:8080/api/v1/user/${email}fields`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
      cors: "cors",
      credentials: "include",
    })
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          throw new Error("Failed to fetch fields");
        }
      })
      .then((data) => setFields(data))
      .catch((error) => console.log(error));
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
