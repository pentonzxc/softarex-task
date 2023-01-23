import React, { useCallback } from "react";
import { useEffect, useState } from "react";
import AddField from "./AddField";
import { faTrashCan } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPenToSquare } from "@fortawesome/free-solid-svg-icons";

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
          {fields.map((field, index) => (
            <tr key={index}>
              <td>{field.label}</td>
              <td>{field.type}</td>
              <td>{field.active ? "true" : "false"}</td>
              <td>{field.required ? "true" : "false"}</td>
              <td>
                <div className="d-flex flex-row justify-content-end gap-3">
                  <div>
                    <button type="button" className="btn" onClick={(e) => console.log(index)}>
                    <FontAwesomeIcon icon={faPenToSquare} style={{color: 'gray'}} />
                    </button>
                  </div>
                  <div>
                    <button type="button" className="btn" onClick={ (e) =>console.log(index)}>
                    <FontAwesomeIcon icon={faTrashCan} style={{color: 'gray'}} />
                    </button>
                  </div>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <AddField add={addField} list={fields} />
    </div>
  );
}
