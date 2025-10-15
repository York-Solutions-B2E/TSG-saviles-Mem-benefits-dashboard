import React from "react";
import { Navigate, Outlet } from "react-router-dom";

// This component wraps protected routes
const ProtectedRoute: React.FC = () => {
  // Check for JWT in localStorage
  const token = localStorage.getItem("appJwt");

  // If no token, redirect to /login
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // If token exists, render the child route(s)
  return <Outlet />;
};

export default ProtectedRoute;
