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

  const optionsRef = useRef(null);

  const [fieldData, setFieldData] = useState(initialFieldData);

  const updateFieldData = useCallback(
    (type) => (event) => {
      setFieldData({ ...fieldData, [type]: event.target.value });
    },
    [fieldData]
  );

  const addField = () => {
    setFieldData({
      ...fieldData,
      options: optionsRef.current.value.split("\n"),
    });

    fetch(`http://localhost:8080/v1/api/user/${email}/addField`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(fieldData),
      credentials: "include",
      mode: "cors",
    })
      .then((response) => {
        console.log(response);
        if (response.status === status.CREATED) {
          return response.text();
        } else {
          throw new Error("Field not added");
        }
      })
      .then((data) => {
        console.log(data);
        props.add(fieldData);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const formHandler = (e) => {
    e.preventDefault();
    addField();
  };

  return (
    <div>
      <button
        type="button"
        class="btn btn-primary"
        data-bs-toggle="modal"
        data-bs-target="#exampleModal"
      >
        Launch demo modal
      </button>
      <div
        class="modal fade"
        id="exampleModal"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
      >
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">
                Create field
              </h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
              <form>
                <div class="mb-3">
                  <label for="label" className="form-label">
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

                <div class="mb-3">
                  <label for="type" className="form-label">
                    Type
                  </label>
                  <select
                    class="form-select "
                    aria-label="Default select example"
                    id="type"
                    value={fieldData.type}
                    onChange={updateFieldData("type")}
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
                <div class="mb-3">
                  <div class="form-outline">
                    <label class="form-label" for="textAreaExample">
                      Options
                    </label>
                    <textarea
                      class="form-control"
                      id="textAreaExample"
                      rows="6"
                      ref={optionsRef}
                    ></textarea>
                  </div>
                </div>
                <div className="d-flex  justify-content-center gap-3">
                  <div class="form-check">
                    <input
                      class="form-check-input"
                      type="checkbox"
                      onChange={updateFieldData("required")}
                      value={fieldData.required}
                      id="required"
                    />
                    <label class="form-check-label" for="required">
                      Required
                    </label>
                  </div>
                  <div class="form-check">
                    <input
                      class="form-check-input"
                      type="checkbox"
                      value={fieldData.active}
                      onChange={updateFieldData("active")}
                      id="active"
                    />
                    <label class="form-check-label" for="active">
                      Is active
                    </label>
                  </div>
                </div>
              </form>
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Close
              </button>
              <button
                type="button"
                class="btn btn-primary"
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
