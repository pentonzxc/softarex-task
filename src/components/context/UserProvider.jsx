import React from "react";
import Cookies from "js-cookie";
import { useContext } from "react";

export const UserContext = React.createContext();

export default function UserProvider({ children }) {
  const [token, setToken] = React.useState(Cookies.get("token"));
  const [refreshToken, setRefreshToken] = React.useState(Cookies.get("refresh_token"));
  const [userName, setUserName] = React.useState(Cookies.get("username"));

  const tokens = {
    access: [token, setToken],
    refresh: [refreshToken, setRefreshToken]
  };

  const user = {
    data: [userName, setUserName]
  };
      

  return (
    <UserContext.Provider value={{tokens, user}}>{children}</UserContext.Provider>
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