import { Navigate, Outlet } from "react-router-dom";

function ProtectedRoute() {
  const token = localStorage.getItem("token");

  if (!token) {
    return <Navigate to="/login" />;
  }
  // works as a placeholder to render child route
  return <Outlet />;
};

export default ProtectedRoute;
