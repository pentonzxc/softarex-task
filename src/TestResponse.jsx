import React, { useEffect } from "react";

// Test Response from server

export default function TestResponse() {
  useEffect(() => {
    {
      fetch("http://localhost:8080/v1/api/user/some", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
        mode: "cors",
      }).then((response) => {
        console.log("unknown path");
        console.log(response);
        console.log(response.status);
      });
    }
  }, []);
  return <></>;
}
