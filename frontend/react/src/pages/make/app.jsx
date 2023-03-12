import React, { useState } from "react";
import axios from "axios";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const handleLogin = async () => {
    try {
      const response = await axios.post("http://localhost:8123/login", { username, password });
      const { token } = response.data;
      localStorage.setItem("token", token);
      setIsLoggedIn(true);
    } catch (error) {
      console.error(error);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    setIsLoggedIn(false);
  };

  const handleSignup = async () => {
    try {
      await axios.post("http://localhost:8123/signup", { username, password });
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div>
      {isLoggedIn ? (
        <>
          <p>Welcome {username}!</p>
          <button type="button" onClick={handleLogout}>
            Logout
          </button>
        </>
      ) : (
        <>
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button type="button" onClick={handleLogin}>
            Login
          </button>
          <button type="button" onClick={handleSignup}>
            Sign Up
          </button>
        </>
      )}
    </div>
  );
}

export default App;
