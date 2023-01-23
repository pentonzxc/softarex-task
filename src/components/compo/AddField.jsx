import React from "react";
import { useState, useRef, useCallback } from "react";

export default function AddField(props) {
  const email = localStorage.getItem("email");
  const status = require("http-status");

  const initialFieldData = {
    id: null,
    label: "",
    type: "SINGLE_LINE_TEXT",
    options: [],
    required: false,
    active: false,
  };

  const [options, setOptions] = useState("");

  const [fieldData, setFieldData] = useState(initialFieldData);

  const updateFieldData = useCallback(
    (type) => (event) => {
        setFieldData({ ...fieldData, [type]: event.target.value });
      }
    ,
    [fieldData]
  );

  const addField = () => {
    console.log(fieldData);

    //   fetch(`http://localhost:8080/v1/api/user/${email}/addField`, {
    //     method: "POST",
    //     headers: {
    //       "Content-Type": "application/json",
    //     },
    //     body: JSON.stringify(fieldData),
    //     credentials: "include",
    //     mode: "cors",
    //   })
    //     .then((response) => {
    //       console.log(response);
    //       if (response.status === status.CREATED) {
    //         return response.text();
    //       } else {
    //         throw new Error("Field not added");
    //       }
    //     })
    //     .then((data) => {
    //       console.log(data);
    //       props.add(fieldData);
    //     })
    //     .catch((error) => {
    //       console.log(error);
    //     });
  };

  const formHandler = (e) => {
    e.preventDefault();
    addField();
  };

  return (
    <div>
      <button
        type="button"
        className="btn btn-primary"
        data-bs-toggle="modal"
        data-bs-target="#exampleModal"
      >
        Launch demo modal
      </button>
      <div
        className="modal fade"
        id="exampleModal"
        tabIndex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
      >
        <div className="modal-dialog modal-dialog-centered modal-dialog-scrollable">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="exampleModalLabel">
                Create field
              </h5>
              <button
                type="button"
                className="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
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
                    onChange={(event) => {
                      
                    }}
                  >
                    <option value="SINGLE_LINE_TEXT" defaultValue>
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
                      value={options}
                      onChange={(event) => {
                        setOptions(event.target.value);
                        updateFieldData("options")(event);
                        }
                      }
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
              >
                Close
              </button>
              <button
                type="button"
                className="btn btn-primary"
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
