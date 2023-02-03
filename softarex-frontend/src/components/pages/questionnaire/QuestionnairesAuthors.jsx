import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function QuestionnairesAuthors() {
  const navigate = useNavigate();
  const [authors, setAuthors] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/v1/api/questionnaire/authors", {
      method: "GET",
      cors: "cors",
      credentials: "include",
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        setAuthors(data);
      });
  }, []);

  return (
    <div className="row d-flex justify-content-center align-content-start min-vh-100">
      <ul className="list-group col-10">
        <li className="list-group-item d-flex justify-content-between align-content-center">
          <h5 className="mb-0">Author</h5>
          <h5 className="mb-0">Questionnaire</h5>
        </li>
        {authors.map((author, index) => {
          return (
            <li
              key={index}
              className="list-group-item d-flex  justify-content-between align-content-center"
            >
              <h5 key={`fullName${index}`} className="mb-0" style={{flexShrink: 0, flexGrow: 0}}>
                {author.fullName}
              </h5> 
              <a
                href={`/questionnaire/${author.id}`}
                className="btn btn-primary btn-sm mx-0 my-0"
                style={{flexShrink: 0, flexGrow: 0}}
              >
                Go to questionnaire
              </a>
              <h5 key={`id${index}`} className="mb-0" style={{flexShrink: 1, flexGrow: 0}}>
                {author.id}
              </h5>
            </li>
          );
        })}
      </ul>
    </div>
  );
}
