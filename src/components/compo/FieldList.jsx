import React, { useCallback } from "react";
import { useEffect, useState } from "react";
import AddField from "./AddField";

export default function FieldList() {
  const [fields, setFields] = useState([]);
  const email = localStorage.getItem("email");

  const addField = useCallback(
    (field) => {
      setFields((fields) => [...fields, field]);
    },
    [fields]
  );

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
    <div>
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
          {console.log(fields)}
          {fields.map((field, index) => (
            <tr key={index}>
              <td>{field.label}</td>
              <td>{field.type}</td>
              <td>{field.active ? "true" : "false"}</td>
              <td>{field.required ? "true" : "false"}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <AddField add={addField} list={fields}/>
    </div>
  );
}
