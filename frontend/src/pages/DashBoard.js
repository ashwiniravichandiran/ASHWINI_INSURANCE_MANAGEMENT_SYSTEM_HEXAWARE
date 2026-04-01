import { Outlet, Link } from "react-router-dom";
import { useSelector } from "react-redux";


function DashBoard() {
  const user = useSelector((state) => state.auth.user);

  return (
    <div className="container-fluid">
      <div className="row">

        <div className="col-md-2 bg-dark text-white min-vh-100 p-3">

          <ul className="nav flex-column">
            <li className="nav-item mb-2 h2">
              <Link to="/dashboard" className="nav-link text-white fw-semibold">
                Dashboard
              </Link>
            </li>

            <li className="nav-item mb-2">
              <Link to="/" className="nav-link text-white">Home</Link>
            </li>

            {user?.role === "USER" && (
              <li className="nav-item mb-2">
                <Link to="/dashboard/proposalform" className="nav-link text-white">
                  Create Proposal
                </Link>
              </li>
            )}
                        {user?.role === "USER" && (
              <li className="nav-item mb-2">
                <Link to="/dashboard/claimtracking" className="nav-link text-white">
                  Track Claims
                </Link>
              </li>
            )}
            {user?.role === "ADMIN" && (
              <li className="nav-item mb-2">
                <Link to="/dashboard/AdminClaim" className="nav-link text-white">
                  Claim Management
                </Link>
              </li>
            )}

            
              <li className="nav-item mb-2">
                <Link to="/dashboard/policy" className="nav-link text-white">
                  {user?.role === "ADMIN" ? "Manage Policies":"View Policies"}
                </Link>
              </li>


            <li className="nav-item mb-2">
              <Link to="/" className="nav-link text-white">Logout</Link>
            </li>
          </ul>
        </div>

        <div className="col-md-10 p-4 bg-light">

          <Outlet />

        </div>

      </div>
    </div>
  );
}

export default DashBoard;

