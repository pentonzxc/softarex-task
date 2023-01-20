import React from "react";
import Cookies from "js-cookie";
import { useContext } from "react";

export const UserContext = React.createContext();

export default function UserProvider({ children }) {
  const [token, setToken] = React.useState(Cookies.get("token"));
  const [refreshToken, setRefreshToken] = React.useState(
    Cookies.get("refresh_token")
  );
  const [email, setEmail] = React.useState("");

  const [some , setSome] = React.useState("some");

  const tokens = {
    access: [token, setToken],
    refresh: [refreshToken, setRefreshToken],
  };

  const user = {
    data: [email, setEmail],
  };

  const any = {
    some: [some , setSome]
  }

  return (
    <UserContext.Provider value={{ tokens, user , any}}>
      {children}
    </UserContext.Provider>
  );
}
function useUser() {
  const context = useContext(UserContext);
  if (context === undefined) {
    throw new Error("useUser must be used within a UserProvider");
  }

  return context;
}

export { useUser };
