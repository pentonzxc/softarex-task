import React from "react";
import { useState, useCallback, useRef } from "react";
import status from "http-status";
import key from "weak-key";
import { nanoid } from "nanoid";

export default function Development() {
  // radio button , checkbox , combobox
  // date custom

  const questionnaire = [
    {
      unique_id: nanoid(),
      label: "Gender",
      type: "RADIO_BUTTON",
      options: [
        {
          unique_id: nanoid(),
          value: "man",
        },
        {
          unique_id: nanoid(),
          value: "woman",
        },
      ],
      required: true,
    },
    {
      label: "Learn",
      type: "CHECKBOX",
      options: [
        {
          unique_id: nanoid(),
          value: "Хочу изучать CSS",
        },
        {
          unique_id: nanoid(),
          value: "Хочу изучать HTML",
        },
      ],
      required: true,
    },
    {
      unique_id: nanoid(),
      label: "Sugar",
      type: "COMBOBOX",
      options: [
        {
          unique_id: nanoid(),
          value: "indifferent",
        },
        {
          unique_id: nanoid(),
          value: "hate",
        },
        {
          unique_id: nanoid(),
          value: "love",
        },
      ],
      required: true,
    },
    {
      unique_id: nanoid(),
      label: "Birth",
      type: "DATE",
      options: [],
      required: true,
    },
    {
      unique_id: nanoid(),
      label: "Small text",
      type: "SINGLE_LINE_TEXT",
      options: [],
      required: true,
    },
    {
      unique_id: nanoid(),
      label: "Big text",
      type: "MULTILINE_TEXT",
      options: [],
      required: true,
    },
  ];

  const initialResponse = questionnaire.map((question) => ({
    label: question.label,
    value: "",
  }));

  const [response, setResponse] = useState(initialResponse);

  const updateResponseData = useCallback(
    (index, type) => (event) => {
      setResponse(...response);
    },
    []
  );

  return (
    <div className="container-fluid">
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
                Create questionnaire
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
                <div>
                  {questionnaire.map((questionnaire, index) => {
                    return (
                      <div className="mb-3" key={questionnaire.unique_id}>
                        <label htmlFor="exampleInputEmail1" className="col">
                          {questionnaire.label}
                        </label>
                        {questionnaire.type === "RADIO_BUTTON" && (
                          <div>
                            {questionnaire.options.map((option) => {
                              return (
                                <div
                                  className="form-check"
                                  key={option.unique_id}
                                >
                                  <input
                                    className="form-check-input"
                                    type="radio"
                                    name="flexRadioDefault"
                                    id="flexRadioDefault1"
                                    onChange={(event) =>
                                      updateResponseData(index)
                                    }
                                  />
                                  <label
                                    className="form-check-label"
                                    htmlFor="flexRadioDefault1"
                                  >
                                    {option.value}
                                  </label>
                                </div>
                              );
                            })}
                          </div>
                        )}
                        {questionnaire.type === "CHECKBOX" && (
                          <div>
                            {questionnaire.options.map((option) => {
                              return (
                                <div
                                  className="form-check"
                                  key={option.unique_id}
                                >
                                  <input
                                    className="form-check-input"
                                    type="checkbox"
                                    value=""
                                    id="flexCheckDefault"
                                  />
                                  <label
                                    className="form-check-label"
                                    htmlFor="flexCheckDefault"
                                  >
                                    {option.value}
                                  </label>
                                </div>
                              );
                            })}
                          </div>
                        )}
                        {questionnaire.type === "COMBOBOX" && (
                          <select
                            className="form-select"
                            aria-label="Default select example"
                          >
                            {questionnaire.options.map((option) => {
                              return (
                                <option
                                  key={option.unique_id}
                                  value={option.value}
                                >
                                  {option.value}
                                </option>
                              );
                            })}
                          </select>
                        )}
                        {questionnaire.type === "DATE" && (
                          <input
                            className="form-control"
                            type="date"
                            id="example-date-input"
                          />
                        )}
                        {questionnaire.type === "SINGLE_LINE_TEXT" && (
                          <input
                            type="text"
                            className="form-control"
                            id="exampleInputEmail1"
                            aria-describedby="emailHelp"
                          />
                        )}
                        {questionnaire.type === "MULTILINE_TEXT" && (
                          <textarea
                            className="form-control"
                            id="exampleFormControlTextarea1"
                            rows="3"
                          ></textarea>
                        )}
                      </div>
                    );
                  })}
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
              <button type="button" className="btn btn-primary">
                Save changes
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
