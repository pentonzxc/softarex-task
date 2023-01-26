import React from "react";
import Cookies from "js-cookie";
import { useContext } from "react";
import { useEffect } from "react";

export const UserContext = React.createContext();

export default function UserProvider({ children }) {
  const [token, setToken] = React.useState(Cookies.get("token"));
  const [refreshToken, setRefreshToken] = React.useState(
    Cookies.get("refresh_token")
  );

  const tokens = {
    access: [token, setToken],
    refresh: [refreshToken, setRefreshToken],
  };

  return <UserContext.Provider value={tokens}>{children}</UserContext.Provider>;
}
function useUser() {
  const context = useContext(UserContext);
  
  return context;
}

export { useUser };
