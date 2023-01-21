import React from "react";
import { useUser } from "../../context/UserProvider";
import { UserContext } from "../../context/UserProvider";
import { useEffect, useState, useContext } from "react";
import { useOutletContext } from "react-router-dom";
import Cookies from "js-cookie";
import FieldList from "../../compo/FieldList";

export default function FieldsPage() {
  return (
    <div>
      <FieldList />
      
    </div>
  );
}
