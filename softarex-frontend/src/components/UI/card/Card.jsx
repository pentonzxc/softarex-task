import React from "react";

export default function Card({ logo, title, children, cardHeight}) {
   const height = "h-" + cardHeight;

  return (
    <div className={"card w-25 " + height}>
      <div className="card-body d-sm-flex flex-column align-items-center gap-2">
        <h2 className="card-title">{logo}</h2>
        <span className="card-subtitle">{title}</span>
        {children}
      </div>
    </div>
  );
}
