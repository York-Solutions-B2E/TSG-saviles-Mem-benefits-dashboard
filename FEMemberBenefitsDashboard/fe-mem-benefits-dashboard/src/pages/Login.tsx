import { GoogleLogin } from "@react-oauth/google";
import { jwtDecode } from "jwt-decode";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const [error, setError] = useState("");
  const navigate = useNavigate(); // <-- useNavigate hook

  const handleGoogleSuccess = async (credentialResponse: any) => {
    if (!credentialResponse.credential) {
      setError("No credential received from Google.");
      return;
    }

    try {
      // Decode Google ID token (optional, for display)
      const decoded: any = jwtDecode(credentialResponse.credential);
      console.log("✅ Google user info:", decoded);

      // Send Google ID token to your backend to get app JWT
      const res = await fetch("http://localhost:8080/api/auth/me", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ idToken: credentialResponse.credential }),
      });

      if (!res.ok) {
        setError("Backend login failed.");
        return;
      }

      const appJwt = await res.text();
      console.log("✅ App JWT from backend:", appJwt);

      // Store JWT for future API calls
      localStorage.setItem("appJwt", appJwt);
      localStorage.setItem("userName", decoded.given_name);

      // Redirect to dashboard using navigate
      navigate("/dashboard"); // <-- replaces window.location.href
    } catch (err) {
      console.error("Error logging in:", err);
      setError("An error occurred during login.");
    }
  };

  const handleGoogleFailure = () => {
    console.error("❌ Google login failed");
    setError("Google login failed.");
  };

  return (
    <>
      <h1>Login:</h1>

      {/* Display any error messages */}
      {error && <p style={{ color: "red" }}>{error}</p>}

      {/* OIDC Google login */}
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
