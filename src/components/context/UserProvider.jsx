import React from "react";
import Cookies from "js-cookie";
import { useContext , useState} from "react";

export const UserContext = React.createContext();

export default function UserProvider({ children }) {
  const [fullName, setFullName] = useState(localStorage.getItem("user_full_name"));

  return (
    <UserContext.Provider
      value={[fullName , setFullName]}
    >
      {children}
    </UserContext.Provider>
  );
}
function useUser() {
  const context = useContext(UserContext);

  return context;
}

export { useUser };
