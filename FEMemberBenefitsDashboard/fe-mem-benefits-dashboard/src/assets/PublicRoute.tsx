import { Navigate, Outlet } from "react-router-dom";

function PublicRoute() {
  const token = localStorage.getItem("token");

  if (token) {
    return <Navigate to="/dashboard"/>;
  }
  //works as a placeholder to render child route (if no token)
  return <Outlet />;
}

export default PublicRoute;

