import { useState, useEffect } from "react";
import claimService from "../services/ClaimService";

const getStatusClass = (status) => {
  if (status === "APPROVED") return "badge bg-success";
  if (status === "REJECTED") return "badge bg-danger";
  return "badge bg-warning text-dark";
};

function AdminClaim() {
  const [claims, setClaims] = useState([]);
  const [statusFilter, setStatusFilter] = useState("All");

  useEffect(() => {
    fetchClaims();
  }, []);

  const fetchClaims = async () => {
    try {
      const res = await claimService.getAllClaims();
      setClaims(res.data);
    } catch (err) {
      console.error("Error fetching claims:", err);
    }
  };


  const filteredClaims = claims.filter(c => {
  if (statusFilter === "All") return true;
  return c.status === statusFilter;
});

  const updateStatus = async (claimId, newStatus) => {
    try {
      await claimService.updateClaimStatus(claimId, newStatus);

      fetchClaims();

      alert(`Claim ${claimId} ${newStatus}`);
    } catch (err) {
      console.error("Error updating:", err);
      alert("Update failed");
    }
  };

  return (
    <div className="container py-5">

      <div className="mb-4">
        <h3 className="fw-bold">Claims Administration</h3>
        <small className="text-muted">
          Review and manage all submitted claims
        </small>
      </div>

      <div className="mb-3 d-flex justify-content-end">
  <select
    className="form-select w-auto"
    value={statusFilter}
    onChange={(e) => setStatusFilter(e.target.value)}
  >
    <option value="All">All</option>
    <option value="APPROVED">Approved</option>
    <option value="REJECTED">Rejected</option>
    <option value="PENDING">Pending</option>
  </select>
</div>

      <div className="card shadow-sm">
        <div className="table-responsive">
          <table className="table table-hover align-middle mb-0">

            <thead className="table-light">
              <tr>
                <th>ID</th>
                <th>Policy ID</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Date</th>
                <th className="text-end">Actions</th>
              </tr>
            </thead>

            <tbody>
              {filteredClaims.map((c) => (
                <tr key={c.claimId}>
                  <td>{c.claimId}</td>
                  <td>{c.userPolicyId}</td>
                  <td>₹{c.claimAmount?.toLocaleString()}</td>

                  <td>
                    <span className={getStatusClass(c.status)}>
                      {c.status}
                    </span>
                  </td>

                  <td>
                    {new Date(c.createdDate).toLocaleDateString()}
                  </td>

                  <td className="text-end">
                    {c.status === "PENDING" ? (
                      <>
                        <button
                          className="btn btn-sm btn-outline-success me-2"
                          onClick={() => updateStatus(c.claimId, "APPROVED")}
                        >
                          Approve
                        </button>

                        <button
                          className="btn btn-sm btn-outline-danger"
                          onClick={() => updateStatus(c.claimId, "REJECTED")}
                        >
                          Reject
                        </button>
                      </>
                    ) : (
                      <span className="text-muted small">—</span>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>

          </table>
        </div>
      </div>

    </div>
  );
}

export default AdminClaim;