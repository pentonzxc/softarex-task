import React from "react";
import { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCaretLeft } from "@fortawesome/free-solid-svg-icons";
import { faCaretRight } from "@fortawesome/free-solid-svg-icons";
import SockJS from "sockjs-client";
import { nanoid } from "nanoid";


var Stomp = require("stompjs");
var stompClient = null;
var socket = null;

export default function Responses() {
  const id = localStorage.getItem("user_id");

  const [responses, setResponses] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [elements, setElements] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [update, setUpdate] = useState(false);

  const [fieldLabels, setFieldLabels] = useState([]);

  const status = require("http-status");

  useEffect(() => {
    connectWS();
    const getFieldLabels = async () => {
      let url = new URL(`http://localhost:8080/v1/api/user/${id}/fields`);

      await fetch(url, {
        method: "GET",
        cors: "cors",
        credentials: "include",
      })
        .then((response) => {
          if (response.status === 200) {
            return response.json();
          } else {
            throw new Error("Failed to fetch fields labels");
          }
        })
        .then(({ data }) => {
          setFieldLabels([data.map((field) => field.label)]);
        })
        .catch((error) => console.log(error));
    };

    getFieldLabels();
  }, []);

  useEffect(() => {
    return () => {
      stompClient.disconnect();
      socket.close();
    };
  }, []);

  useEffect(() => {
    const controller = new AbortController();
    getResponses({ controller });
    return () => {
      try {
        controller.abort();
      } catch (error) {
        console.error(error);
      }
    };
  }, [currentPage, rowsPerPage, totalElements, update]);

  function getResponses({ controller }) {
    let url = new URL(`http://localhost:8080/v1/api/user/${id}/responses`);
    if (currentPage !== null && rowsPerPage !== null) {
      let params = { page: currentPage, size: rowsPerPage };
      url.search = new URLSearchParams(params).toString();
    }
    fetch(url, {
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
          throw new Error("Failed to fetch responses");
        }
      })
      .then(({ data, totalElements, totalPages }) => {
        const newData = [];
        data.forEach((element) => {
          // sortByTitle();
          element = mergeCheckboxes(element);
          sortByTitle(element);
          newData.push(element);
          // element.sort((a, b) => a.label.localeCompare(b.label));
        });
        setResponses(newData);
        setTotalPages(totalPages);
        setElements(eval(currentPage * rowsPerPage + data.length));
        setTotalElements(totalElements);
      })
      .catch((error) => console.log(error));
  }

  function connectWS() {
    socket = new SockJS("http://localhost:8080/ws");
    stompClient = Stomp.over(socket);
    stompClient.connect(
      { username: id },
      function (frame) {
        console.log("Connected: " + frame);
        stompClient.subscribe("/user/queue/update", function (response) {
          console.log(response);
          if (response.body === "update") setUpdate((update) => !update);
        });
      },
      (error) => console.log(error)
    );
  }

  // async function getFieldLabels() {
  //   let url = new URL(`http://localhost:8080/v1/api/user/${id}/fields`);

  //   await fetch(url, {
  //     method: "GET",
  //     cors: "cors",
  //     credentials: "include",
  //   })
  //     .then((response) => {
  //       console.log("do that shit")
  //       if (response.status === 200) {
  //         return response.json();
  //       } else {
  //         throw new Error("Failed to fetch fields labels");
  //       }
  //     })
  //     .then(({ data }) => {
  //       setFieldLabels([data.map((field) => field.label)]);
  //     })
  //     .catch((error) => console.log(error));
  // }

  function sortByTitle(element) {
    for (let i = 0; i < fieldLabels.length; i++) {
      const label = fieldLabels[i];

      element.sort((a, b) => {
        if (a.label === label) {
          return -1;
        } else if (b.label === label) {
          return 1;
        } else {
          return 0;
        }
      });
    }
  }

  function mergeCheckboxes(element) {
    const answer = [];
    let field_label = "";
    element.forEach((field) => {
      if (field.type === "CHECKBOX") {
        answer.push(field.answer);
        field_label = field.label;
      }
    });
    element = element.filter((field) => field.type !== "CHECKBOX");
    answer[0] = '[' + answer[0];
    answer[answer.length - 1] = answer[answer.length - 1] + ']';
    element.push({
      label: field_label,
      answer: answer.join(","),
      type: "CHECKBOX",
    });

    return element;
  }

  return (
    <div className="custom__border container col-6 mb-lg-5 bg-white p-0">
      <div className="d-flex justify-content-between px-3 py-2">
        <span className="fs-5">Responses</span>
      </div>
      <hr className="col-xs-12 mt-0" />
      <div className="col-lg-12">
        {responses.length === 0 ? (
          <div className="d-flex justify-content-center mb-lg-2">
            <span className="fs-5">No responses found</span>
          </div>
        ) : (
          <div className="table-responsive text-nowrap px-3">
            <table className="table table-sm table-striped">
              <thead>
                <tr>
                  {responses.length > 0 &&
                    responses[0].map((response, index) => (
                      <th scope="col" key={index}>
                        {response.label}
                      </th>
                    ))}
                </tr>
              </thead>
              <tbody>
                {responses.map((response, index) => (
                  <tr key={index + response[0].label}>
                    {response.map((element, ind) => (
                      <td key={ind + element.label + "code" + index}>
                        {element.answer}
                      </td>
                    ))}
                  </tr>
                ))}
              </tbody>
            </table>
            <nav aria-label="Page navigation example">
              <ul className="pagination justify-content-between align-items-center flex-column flex-sm-row gap-1">
                <li>
                  <span>
                    {elements} of {totalElements}
                  </span>
                </li>
                <div className="d-flex align-content-center justify-content-center">
                  <li className="page-item">
                    <a
                      className="page-link"
                      onClick={(e) => {
                        if (currentPage > 0) setCurrentPage((prev) => prev - 1);
                      }}
                    >
                      <span aria-hidden="true">
                        <FontAwesomeIcon
                          icon={faCaretLeft}
                          style={{ color: "gray" }}
                        />
                      </span>
                    </a>
                  </li>
                  <li className="page-item">
                    <a className="page-link">{currentPage + 1}</a>
                  </li>
                  <li className="page-item">
                    <a
                      className="page-link"
                      onClick={(e) => {
                        if (currentPage < totalPages - 1)
                          setCurrentPage((prev) => prev + 1);
                      }}
                    >
                      <span aria-hidden="true">
                        <FontAwesomeIcon
                          icon={faCaretRight}
                          style={{ color: "gray" }}
                        />
                      </span>
                    </a>
                  </li>
                </div>
                <div>
                  <li className="d-flex align-items-center">
                    <select
                      defaultValue={5}
                      className="form-select form-select-sm"
                      aria-label="form-select-sm example"
                      onChange={(e) => {
                        if (e.target.value == "All") {
                          setRowsPerPage(null);
                          setCurrentPage(null);
                          return;
                        }
                        setRowsPerPage(e.target.value);
                        setCurrentPage(0);
                        // console.log(e.target.value);
                      }}
                    >
                      <option value="5">5</option>
                      <option value="10">10</option>
                      <option value="All">All</option>
                    </select>
                  </li>
                </div>
              </ul>
            </nav>
          </div>
        )}
      </div>
    </div>
  );
}
