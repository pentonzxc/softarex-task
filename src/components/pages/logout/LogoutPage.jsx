import React from "react";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function LogoutPage() {
  const navigate = useNavigate();
  useEffect(() => {
    fetch("http://localhost:8080/v1/api/auth/logout", {
      method: "GET",
      cors: "cors",
      credentials: "include",
    }).then((response) => {
      localStorage.removeItem("user_id");
      localStorage.removeItem("user_full_name");
      navigate("/login", { replace: true });
    });
  }, []);
  return <></>;
}
