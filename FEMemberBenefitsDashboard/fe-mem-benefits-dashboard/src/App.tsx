import { BrowserRouter, Routes, Route, Link, useLocation } from "react-router-dom";
import './App.css';
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import ProtectedRoute from "./assets/ProtectedRoute";
import PublicRoute from "./assets/PublicRoute";
import Welcome from "./pages/Welcome";

function AppContent() {
  const location = useLocation();
  const isLoggedIn = !!localStorage.getItem("token"); 

  const showNav = !isLoggedIn && (location.pathname === "/" || location.pathname === "/login");

  return (
    <>
      {showNav && (
        <nav>
          <Link to="/">Home</Link> | <Link to="/login">Login</Link>
        </nav>
      )}

      <Routes>
        <Route element={<PublicRoute />}>
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Welcome />} />
        </Route>

        <Route element={<ProtectedRoute />}>
          <Route path="/dashboard" element={<Dashboard />} />
        </Route>
      </Routes>
    </>
  );
}

export default function App() {
  return (
    <BrowserRouter>
      <AppContent />
    </BrowserRouter>
  );
}
