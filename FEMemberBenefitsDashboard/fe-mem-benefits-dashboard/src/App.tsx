import { BrowserRouter, Routes, Route, Link, useLocation} from "react-router-dom";
import './App.css';
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import ProtectedRoute from "./assets/ProtectedRoute";
import PublicRoute from "./assets/PublicRoute";
import Welcome from "./pages/Welcome";
import ClaimDetail from "./pages/ClaimDetail";
import ClaimsList from "./pages/ClaimsList";
import Learning from "./pages/Learning";

function AppContent() {
  
  const location = useLocation();
  const showNav = location.pathname === "/" || location.pathname === "/login";

  return (
    <>
      {showNav && (
        <nav>
          <Link to="/">Home</Link> | <Link to="/login">Login</Link>
        </nav>
      )}

      <Routes> {/* "when we're at this URL, rendor this component" */}
        <Route element={<PublicRoute />}>
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Welcome />} />
          <Route path="/learning" element={<Learning />} /> {/* REMOVE THIS AND DELETE LEARNING FILE */}
        </Route>

        <Route element={<ProtectedRoute />}>
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/claims/:claimId" element={<ClaimDetail />} />
          <Route path="/claimslist" element={<ClaimsList />} />
        </Route>
      </Routes>
    </>
  );
}

export default function App() {
  return (
    <BrowserRouter> {/* "everything inside here can use Routes/Links" keeps track of URL changes*/}
      <AppContent />
    </BrowserRouter>
  );
}
