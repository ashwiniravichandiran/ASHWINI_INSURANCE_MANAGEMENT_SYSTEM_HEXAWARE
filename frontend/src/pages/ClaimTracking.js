import { useState, useEffect } from "react";
import claimService from "../services/ClaimService";

const getStatusClass = (status) => {
  if (status === "APPROVED") return "badge bg-success";
  if (status === "REJECTED") return "badge bg-danger";
  return "badge bg-warning text-dark";
};

function ClaimTracking() {
  const [claims, setClaims] = useState([]);
  const [expanded, setExpanded] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchClaims();
  }, []);

  const fetchClaims = async () => {
    try {
      const res = await claimService.getUserClaims();
      setClaims(res.data);
      console.log(res.data);
    } catch (err) {
      console.error("Error fetching claims:", err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="text-center py-5">Loading claims...</div>;
  }

  return (
    <div className="container py-5">

      <div className="mb-4">
        <h3 className="fw-bold">My Claims</h3>
        <small className="text-muted">
          Track your claim status
        </small>
      </div>

      {claims.length === 0 && (
        <div className="text-muted">No claims found</div>
      )}

      {claims.map((c) => (
        <div key={c.claimId} className="card mb-3 shadow-sm">

          <div
            className="card-body d-flex justify-content-between align-items-center"
            style={{ cursor: "pointer" }}
            onClick={() =>
              setExpanded(expanded === c.claimId ? null : c.claimId)
            }
          >
            <div>
              <h6 className="fw-semibold mb-1">
                Claim #{c.claimId}
              </h6>
            <small className="text-muted">
              🚗 {c.vehicleModel} - {c.vehicleNumber}
            </small>
            </div>

            <div className="text-end">
              <span className={getStatusClass(c.status)}>
                {c.status}
              </span>

              <div className="fw-bold mt-1">
                ₹{c.claimAmount?.toLocaleString()}
              </div>
            </div>
          </div>

          {expanded === c.claimId && (
            <div className="card-body border-top">

              <p>
                <strong>Reason:</strong> {c.reason}
              </p>

              <p>
                <strong>Date:</strong>{" "}
                {new Date(c.createdDate).toLocaleString()}
              </p>

            </div>
          )}

        </div>
      ))}

    </div>
  );
}

export default ClaimTracking;