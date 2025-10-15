import { GoogleLogin} from "@react-oauth/google";
import { jwtDecode } from "jwt-decode";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const handleGoogleSuccess = async (credentialResponse: any) => {
    const token = credentialResponse.credential;

    console.log("Google ID token received:", credentialResponse.credential);
    const decoded: any = jwtDecode(token);
    if (!token) {
      setError("No credential received from Google.");
      return;
    }

    try {
      // Send Google ID token directly in Authorization header
      const res = await fetch("http://localhost:8080/api/auth/me", {
        method: "GET",
        headers: { "Authorization": `Bearer ${token}` },
      });

      if (!res.ok) {
        const text = await res.text();
        console.error("Backend login failed:", text);
        setError("Backend login failed");
        return;
      }

      // Login succeeded — store token locally for future API calls
      localStorage.setItem("token", token);
      localStorage.setItem("userName", decoded.given_name)

      // Redirect to dashboard
      navigate("/dashboard");
    } catch (err) {
      console.error("Error logging in:", err);
      setError("An error occurred during login.");
    }
  };

  const handleGoogleFailure = () => {
    console.error("Google login failed");
    setError("Google login failed.");
  };

  return (
    <>
      <h1>Login:</h1>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <div style={{ marginTop: "20px" }}>
        <GoogleLogin
          onSuccess={handleGoogleSuccess}
          onError={handleGoogleFailure}
        />
      </div>
    </>
  );
}

export default Login;
