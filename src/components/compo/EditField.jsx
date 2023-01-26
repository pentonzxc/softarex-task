import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPenToSquare } from "@fortawesome/free-solid-svg-icons";
import { useState, useCallback } from "react";

export default function EditField({ edit,item, id }) {
  const status = require("http-status");

  const initialFieldData = JSON.parse(JSON.stringify(item));

  const [fieldData, setFieldData] = useState(initialFieldData);

  const updateFieldData = useCallback(
    (type) => (event) => {
      if (type === "required" || type === "active") {
        setFieldData({ ...fieldData, [type]: event.target.checked });
      } else {
        setFieldData({ ...fieldData, [type]: event.target.value });
      }
    },
    [fieldData]
  );

  const editField = () => {
    fetch(`http://localhost:8080/v1/api/field/edit/${id}`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(fieldData),
      credentials: "include",
      mode: "cors",
    })
      .then((response) => {
        console.log(response);
        if (response.status === status.OK) {
          edit(fieldData);
        } else {    
          throw new Error("Field isn't edited");
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const formHandler = (e) => {
    e.preventDefault();
    

    editField();
  };

  return (
    <div>
      <button
        type="button"
        className="btn"
        data-bs-toggle="modal"
        data-bs-target={"#editModal" + id}
      >
        <FontAwesomeIcon icon={faPenToSquare} style={{ color: "gray" }} />
      </button>
      <div
        className="modal fade"
        id={"editModal" + id}
        tabIndex="-1"
        aria-labelledby="editModalLabel"
        aria-hidden="true"
      >
        <div className="modal-dialog modal-dialog-centered modal-dialog-scrollable">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="editModalLabel">
                Edit field
              </h5>
              {/* <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button> */}
            </div>
            <div className="modal-body">
              <form>
                <div className="mb-3">
                  <label htmlFor="label" className="form-label">
                    Label
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="label"
                    value={fieldData.label}
                    onChange={updateFieldData("label")}
                  />
                </div>

                <div className="mb-3">
                  <label htmlFor="type" className="form-label">
                    Type
                  </label>
                  <select
                    className="form-select "
                    aria-label="Default select example"
                    id="type"
                    value={fieldData.type}
                    onChange={updateFieldData("type")}
                  >
                    <option value="SINGLE_LINE_TEXT" selected>
                      Single line text
                    </option>
                    <option value="MULTILINE_TEXT">Multiline text</option>
                    <option value="RADIO_BUTTON">Radio button</option>
                    <option value="CHECKBOX">Checkbox</option>
                    <option value="COMBOBOX">Combobox</option>
                    <option value="DATE">Date</option>
                  </select>
                </div>
                <div className="mb-3">
                  <div className="form-outline">
                    <label className="form-label" htmlFor="textAreaExample">
                      Options
                    </label>
                    <textarea
                      className="form-control"
                      id="textAreaExample"
                      rows="6"
                      value={fieldData.options}
                      onChange={updateFieldData("options")}
                    ></textarea>
                  </div>
                </div>
                <div className="d-flex  justify-content-center gap-3">
                  <div className="form-check">
                    <input
                      className="form-check-input"
                      type="checkbox"
                      onChange={updateFieldData("required")}
                      value={fieldData.required}
                      id="required"
                    />
                    <label className="form-check-label" htmlFor="required">
                      Required
                    </label>
                  </div>
                  <div className="form-check">
                    <input
                      className="form-check-input"
                      type="checkbox"
                      value={fieldData.active}
                      onChange={updateFieldData("active")}
                      id="active"
                    />
                    <label className="form-check-label" htmlFor="active">
                      Is active
                    </label>
                  </div>
                </div>
              </form>
            </div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-secondary"
                data-bs-dismiss="modal"
                onClick={() => {
                  setFieldData(item);
                }}
              >
                Close
              </button>
              <button
                type="button"
                className="btn btn-primary"
                data-bs-dismiss="modal"
                onClick={formHandler}
              >
                Save changes
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
