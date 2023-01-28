import React from "react";
import { useEffect, useState, useCallback } from "react";
import { faTrashCan } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCaretLeft } from "@fortawesome/free-solid-svg-icons";
import { faCaretRight } from "@fortawesome/free-solid-svg-icons";
import AddField from "../../compo/AddField";
import EditField from "../../compo/EditField";
import { nanoid } from "nanoid";
import './style.css'

export default function FieldsPage() {
  const [fields, setFields] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [elements, setElements] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  const id = localStorage.getItem("user_id");

  const addField = useCallback(
    (field) => {
      setFields((fields) => [...fields, field]);
      setTotalElements(totalElements + 1);
    },
    [fields]
  );

  const tot = 0;

  const handlePageChange = (isNext) => {};

  const deleteField = useCallback(
    (field_id) => {
      fetch(`http://localhost:8080/v1/api/field/remove/${field_id}`, {
        method: "DELETE",
        cors: "cors",
        credentials: "include",
      })
        .then((response) => {
          console.log(response);
          if (response.status === 200) {
            console.log("Field deleted");
            setFields((fields) => fields.filter((field) => field.id !== id));
            if (totalElements % rowsPerPage === 1 && currentPage !== 0) {
              setCurrentPage(currentPage - 1);
            }
            setTotalElements(totalElements - 1);
          } else {
            throw new Error("Failed to delete field");
          }
        })
        .catch((error) => console.log(error));
    },
    [fields]
  );

  const editField = useCallback(
    (field) => {
      setFields((fields) => {
        const newFields = fields.map((f) => {
          if (f.id === field.id) {
            return field;
          }
          return f;
        });
        return newFields;
      });
    },
    [fields]
  );

  useEffect(() => {
    const controller = new AbortController();
    const getAllFields = async ({ controller }) => {
      let url = new URL(`http://localhost:8080/v1/api/user/${id}/fields`);
      if (currentPage !== null && rowsPerPage !== null) {
        let params = { page: currentPage, size: rowsPerPage };
        url.search = new URLSearchParams(params).toString();
      }
      await fetch(url, {
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
        .then(({ data, totalElements, totalPages }) => {
          setFields(data);
          setTotalPages(totalPages);
          setElements(eval(currentPage * rowsPerPage + data.length));
          setTotalElements(totalElements);
        })
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
  }, [currentPage, rowsPerPage, totalElements]);

  return (
    <div className="custom__border container col-6 mt-lg-5 bg-white p-0">
      <div className="d-flex justify-content-between px-3 py-2">
        <span className=" fs-5">Fields</span>
        <AddField add={addField} />
      </div>
      <hr className="col-xs-12 mt-0" />
      <div className="col-lg-12">
        <div className="table-responsive text-nowrap px-3">
          <table className="table table-sm table-striped">
            <thead>
              <tr>
                <th scope="col">Label</th>
                <th scope="col">Type</th>
                <th scope="col">Required</th>
                <th scope="col">Is Active</th>
                <th scope="col"></th>
              </tr>
            </thead>
            <tbody>
              {fields.map((field, index) => (
                <tr key={nanoid()}>
                  <td className="col-sm-4 col-auto">{field.label}</td>
                  <td className="col-sm-4 col-auto">{field.type}</td>
                  <td className="col-sm-2 col-auto">
                    {field.active ? "true" : "false"}
                  </td>
                  <td className="col-sm-2 col-auto">
                    {field.required ? "true" : "false"}
                  </td>
                  <td className="col-sm-2 col-auto">
                    <div className="d-flex flex-row justify-content-end">
                      <EditField edit={editField} item={field} id={index} />
                      <div>
                        <button
                          type="button"
                          className="btn"
                          onClick={(e) => deleteField(field.id)}
                        >
                          <FontAwesomeIcon
                            icon={faTrashCan}
                            style={{ color: "gray" }}
                          />
                        </button>
                      </div>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <nav aria-label="Page navigation example">
            <ul className="pagination justify-content-between align-items-center flex-column flex-sm-row gap-1">
              <li>
                <span>
                  {/* {eval(currentPage) * eval(rowsPerPage) + fields.length} of {totalElements} */}
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
                      console.log(e.target.value);
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
      </div>
    </div>
  );
}
