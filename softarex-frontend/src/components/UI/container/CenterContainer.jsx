import React from "react";

export default function CenterContainer({children}) {
  return (
    <div className="container vh-100 vw-100 d-flex align-items-center justify-content-center g-0">
      {children}
    </div>
  );
}
