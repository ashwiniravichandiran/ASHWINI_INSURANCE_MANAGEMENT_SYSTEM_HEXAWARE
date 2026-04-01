import { useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();

  return (
    <nav className="navbar shadow-sm px-4">
      <span className="navbar-brand fw-bold">InsureDrive</span>

      <div className="ms-auto">
        <button 
          className="btn btn-dark me-2"
          onClick={() => navigate("/login")}
        >
          Login
        </button>

        <button 
          className="btn btn-outline-dark"
          onClick={() => navigate("/register")}
        >
          Get Started
        </button>
      </div>
    </nav>
  );
}

export default Navbar;