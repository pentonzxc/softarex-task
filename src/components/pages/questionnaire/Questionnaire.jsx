import React from "react";
import { nanoid } from "nanoid";
import { useEffect, useState, useCallback } from "react";
import { useParams, useLocation } from "react-router-dom";
import $ from "jquery";
// import SockJS from "sockjs-client";
// import { Client, Message } from "@stomp/stompjs";

// var Stomp = require("stompjs");

export default function Questionnaire() {
  const status = require("http-status");
  // const initialClient = new Client({
  //   brokerURL: "ws://localhost:8080/v1/api/ws",
  //   debug: function (str) {
  //     console.log(str);
  //   },
  //   reconnectDelay: 5000,
  //   heartbeatIncoming: 4000,
  //   heartbeatOutgoing: 4000,
  // });

  // const [client, setClient] = useState(initialClient);

  const [questionnaire, setQuestionnaire] = useState([]);
  const [response, setResponse] = useState([]);
  const { id } = useParams();
  const location = useLocation();

  const [requiredCheckboxes, setRequiredCheckboxes] = useState([]);

  const user_id = location.pathname.split("/")[2];

  // fetch all questions from the server and set them unique_id and set them to the state
  useEffect(() => {
    fetch(`http://localhost:8080/v1/api/questionnaire/${id}`, {
      method: "GET",
      headers: {
        cors: "cors",
        credentials: "include",
      },
    })
      .then((response) => {
        if (response.status === status.OK) {
          return response.json();
        } else {
          throw new Error("Something went wrong");
        }
      })
      .then(({ fieldList }) => {
        console.log(fieldList);
        const question = fieldList.map((question) => {
          return {
            ...question,
            unique_id: nanoid(),
            options: question.options.map((option) => {
              return {
                option,
                unique_id: nanoid(),
              };
            }),
          };
        });
        console.log(question);
        setQuestionnaire(question);
        const requiredCheckboxes = question.filter((question) => {
          return question.required === true && question.type === "CHECKBOX";
        });

        setRequiredCheckboxes(requiredCheckboxes);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const updateResponse = useCallback(
    ({ label, unique_id, type }) =>
      (event) => {
        const answer = event.target.value;
        const checked = event.target.checked;

        setResponse((prevResponse) => {
          if (type === "RADIO_BUTTON") {
            let index = prevResponse.find(
              (question) => question.label === label
            );
            if (index !== undefined) {
              return prevResponse.map((question) => {
                if (question.label === label) {
                  return { label, answer, unique_id };
                } else {
                  return question;
                }
              });
            }
            return [...prevResponse, { label, answer, unique_id }];
          }
          const index = prevResponse.find(
            (question) => question.unique_id === unique_id
          );

          if (type === "CHECKBOX") {
            if (index !== undefined && checked === false) {
              return prevResponse.filter(
                (question) => question.unique_id !== unique_id
              );
            }
          }

          if (index !== undefined) {
            return prevResponse.map((question) => {
              if (question.unique_id === unique_id) {
                return { label, answer, unique_id };
              } else {
                return question;
              }
            });
          }
          return [...prevResponse, { label, answer, unique_id }];
        });
      },
    [response]
  );

  //   function addMissingQuestions() {
  //     const missingQuestions = questionnaire.filter(
  //       (question) => !response.find((res) => res.label === question.label)
  //     );
  //     const missingQuestionsWithAnswer = missingQuestions.map((question) => {
  //       return {
  //         label: question.label,
  //         answer: "",
  //         unique_id: question.unique_id,
  //       };
  //     });
  //     console.log(missingQuestionsWithAnswer);
  //     setResponse((prevResponse) => {
  //       return [...prevResponse, ...missingQuestionsWithAnswer];
  //     });
  //   }

  // function connectWS() {
  //   let socket = new SockJS("http://localhost:8080/v1/api/ws");
  //   let stompClient = Stomp.over(socket);

  //   stompClient.connect({}, onConnectSubscribe, (error) => console.log(error));

  //   stompClient.connect({}}
  // }

  // function onConnectSubscribe() {
  //   console.log("connected");
  // }

  // function sendMessage() {
  //   stompClient.send("/user/", {}, JSON.stringify({ name: "John" }));
  // }

  function createQuestionnaire() {
    fetch(`http://localhost:8080/v1/api/questionnaire/${id}`, {
      method: "POST",
      cors: "cors",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(response),
    })
      .then((server) => {
        console.log(server);
        if (response.status === status.CREATED) {
          return response.json();
        } else {
          throw new Error("Something went wrong");
        }
      })
      .then((data) => {
        console.log(data);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  function handleSubmit(event) {
    event.preventDefault();
    event.stopPropagation();

    requiredCheckboxes.forEach((checkbox) => {
      if (checkbox.required) {
        let checkboxes = $(
          `input:checkbox[id^="option-${checkbox.unique_id}"]`
        );

        checkboxes.prop("required", true);

        if (checkboxes.is(":checked")) {
          checkboxes.prop("required", false);
        }
      }
    });


    
    if (event.currentTarget.checkValidity() === false) {
      event.currentTarget.classList.add("was-validated");
    } else {
      console.log("success");
      // createQuestionnaire();
    }
  }

  return (
    <div className="container col-3 h-100 d-flex justify-content-center align-items-center mt-4">
      <div className="row">
        <div className="col">
          <div className="card">
            <div className="card-body">
              <form
                className="row flex-column gap-3 mx-1"
                noValidate
                onSubmit={handleSubmit}
              >
                {questionnaire.map((question, index) => {
                  return (
                    <div className="form-group" key={index}>
                      <label className="col">{question.label}</label>
                      {question.type === "RADIO_BUTTON" && (
                        <div>
                          {question.options.map((option, index) => {
                            return (
                              <div
                                className="form-check"
                                key={option.unique_id}
                              >
                                <input
                                  required={question.required}
                                  className="form-check-input"
                                  type="radio"
                                  id={option.unique_id}
                                  //   key={index}
                                  name={question.unique_id}
                                  value={option.option}
                                  onChange={updateResponse({
                                    label: question.label,
                                    unique_id: option.unique_id,
                                    type: "RADIO_BUTTON",
                                  })}
                                />
                                <label
                                  className="form-check-label"
                                  htmlFor={option.unique_id}
                                >
                                  {option.option}
                                </label>
                              </div>
                            );
                          })}
                        </div>
                      )}
                      {question.type === "CHECKBOX" && (
                        <div id={question.unique_id + "checkbox"}>
                          {question.options.map((option, index) => {
                            return (
                              <div
                                className="form-check"
                                key={option.unique_id}
                              >
                                <input
                                  className="form-check-input"
                                  type="checkbox"
                                  id={`option-${question.unique_id}${index}`}
                                  value={option.option}
                                  onChange={updateResponse({
                                    label: question.label,
                                    unique_id: option.unique_id,
                                    type: "CHECKBOX",
                                  })}
                                />
                                <label className="form-check-label">
                                  {option.option}
                                </label>
                              </div>
                            );
                          })}
                        </div>
                      )}
                      {question.type === "COMBOBOX" && (
                        <select
                          className="form-select"
                          aria-label="Default select example"
                          required={question.required}
                          onChange={updateResponse({
                            label: question.label,
                            unique_id: question.unique_id,
                            type: "DEFAULT",
                          })}
                        >
                          <option selected hidden disabled value="">
                            Select option
                          </option>
                          {question.options.map((option) => {
                            return (
                              <option
                                key={option.unique_id}
                                value={option.value}
                              >
                                {option.option}
                              </option>
                            );
                          })}
                        </select>
                      )}
                      {question.type === "DATE" && (
                        <input
                          className="form-control"
                          type="date"
                          required={question.required}
                          onChange={updateResponse({
                            label: question.label,
                            unique_id: question.unique_id,
                            type: "DEFAULT",
                          })}
                        />
                      )}
                      {question.type === "SINGLE_LINE_TEXT" && (
                        <input
                          type={
                            question.label.toLowerCase() === "email" ||
                            question.label.toLowerCase() === "email address" ||
                            question.label.toLowerCase() === "e-mail" ||
                            question.label.toLowerCase() === "e-mail address"
                              ? "email"
                              : "text"
                          }
                          className="form-control"
                          aria-describedby="emailHelp"
                          required={question.required}
                          onChange={updateResponse({
                            label: question.label,
                            unique_id: question.unique_id,
                            type: "DEFAULT",
                          })}
                        />
                      )}
                      {question.type === "MULTILINE_TEXT" && (
                        <textarea
                          className="form-control"
                          id="exampleFormControlTextarea1"
                          required={question.required}
                          rows="3"
                          onChange={updateResponse({
                            label: question.label,
                            unique_id: question.unique_id,
                            type: "DEFAULT",
                          })}
                        ></textarea>
                      )}
                    </div>
                  );
                })}
                <div className="d-flex">
                  <button
                    type="submit"
                    className="btn col-xxl-3 col-lg-6 col-12 btn-sm btn-primary"
                  >
                    Submit
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
