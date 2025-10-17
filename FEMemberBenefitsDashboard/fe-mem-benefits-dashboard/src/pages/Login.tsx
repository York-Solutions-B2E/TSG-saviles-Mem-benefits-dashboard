import { GoogleLogin} from "@react-oauth/google";
import { jwtDecode } from "jwt-decode";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleGoogleSuccess = async (credentialResponse: any) => {
    const token = credentialResponse.credential;

    const decoded: any = jwtDecode(token); //Use "any" becuase we know the structure and can extract given_name

    try {
      const res = await fetch("http://localhost:8080/api/auth/me", { //using fetch and setting response to res
        method: "GET",
        headers: { "Authorization": `Bearer ${token}` },
      });

      if (!res.ok) { //Response was sucessful but maybe token wasn't accepted
        const text = await res.text();
        console.error("Backend login failed:", text);
        setError("Backend login failed");
        return;
      }

      localStorage.setItem("token", token);
      localStorage.setItem("userName", decoded.given_name)
      navigate("/dashboard");

    } catch (err) { //network level erorr (something like no backend was running)
      console.error("Error logging in:", err);
      setError("An error occurred during login.");
    }
  };


  return (
    <>
      <h2>Login:</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <div style={{ marginTop: "20px" }}>
        <GoogleLogin
          onSuccess={handleGoogleSuccess}
        />
      </div>
    </>
  );
}

export default Login;
