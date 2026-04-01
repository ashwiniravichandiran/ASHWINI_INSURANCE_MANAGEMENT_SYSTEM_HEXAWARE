
import { useEffect, useState } from "react";
import { CheckCircle2, XCircle, FileUp } from "lucide-react";
import proposalService from "../services/ProposalService";

const getStatusClass = (status) => {
  if (status === "APPROVED") return "badge bg-success";
  if (status === "REJECTED") return "badge bg-danger";
  if (status === "DOCUMENTS REQUESTED") return "badge bg-info";
  return "badge bg-warning text-dark"; 
};

function OfficerDashboard() {
  const [proposals, setProposals] = useState([]);
  const [statusFilter, setStatusFilter] = useState("All");

  useEffect(() => {
    fetchProposals();
  }, []);

  const fetchProposals = () => {
    proposalService.getAll()
      .then(res => {
        console.log(res.data);
        setProposals(res.data);
      })
      .catch(err => console.error(err));
  };

  const filteredProposals = proposals.filter(p => {
    if (statusFilter === "All") return true;
    return p.status === statusFilter;
  });

  const updateStatus = (id, newStatus) => {
    proposalService.updateStatus(id, newStatus)
      .then(() => fetchProposals())
      .catch(err => console.error(err));

    console.log(`Proposal ${id} → ${newStatus}`);
  };

  const generateQuote = (proposalId) => {
    proposalService.generateQuote(proposalId)
      .then(() => fetchProposals())
      .catch(err => console.error(err));

    console.log(`Proposal ${proposalId} -> Quote Generated`);
  };

  return (
    <div className="container-fluid">
      <div className="row">
        <div className="col-md-10 d-flex flex-column min-vh-100">

          <main className="container my-4 flex-grow-1">

            <div className="mb-4">
              <h2 className="fw-bold">Officer Dashboard</h2>
              <p className="text-muted">Review and manage insurance proposals</p>
            </div>

            <div className="mb-3 d-flex justify-content-end">
              <select
                className="form-select w-auto"
                value={statusFilter}
                onChange={(e) => setStatusFilter(e.target.value)}
              >
                <option value="All">All</option>
                <option value="PENDING">Pending</option>
                <option value="APPROVED">Approved</option>
                <option value="REJECTED">Rejected</option>
                <option value="DOCUMENTS REQUESTED">Documents Requested</option>
              </select>
            </div>

            <div className="card shadow-sm border-0">
              <div className="table-responsive">
                <table className="table align-middle">

                  <thead className="table-light">
                    <tr>
                      <th>Proposal ID</th>
                      <th>Applicant</th>
                      <th>Vehicle</th>
                      <th>Type</th>
                      <th>Status</th>
                      <th className="text-end">Actions</th>
                      <th className="text-end">Generate Quote</th>
                    </tr>
                  </thead>

                  <tbody>
                    {filteredProposals.length > 0 ? (
                      filteredProposals.map((p) => (
                        <tr key={p.proposalId}>
                          <td className="fw-semibold">PRO-{p.proposalId}</td>
                          <td>{p.userName || p.userId}</td>
                          <td>{p.vehicleModel}</td>
                          <td>{p.vehicleType}</td>

                          <td>
                            <span className={getStatusClass(p.status)}>
                              {p.status}
                            </span>
                          </td>

                          <td className="text-end">
                            <div className="d-flex gap-2 justify-content-end">
                              {p.status === "PENDING" && (
                                <>
                                  <button
                                    className="btn btn-sm btn-success"
                                    onClick={() => updateStatus(p.proposalId, "Approved")}
                                  >
                                    <CheckCircle2 size={16} />
                                  </button>

                                  <button
                                    className="btn btn-sm btn-danger"
                                    onClick={() => updateStatus(p.proposalId, "Rejected")}
                                  >
                                    <XCircle size={16} />
                                  </button>

                                  <button
                                    className="btn btn-sm btn-primary"
                                    onClick={() => updateStatus(p.proposalId, "Documents Requested")}
                                  >
                                    <FileUp size={16} />
                                  </button>
                                </>
                              )}

                              {p.status !== "Pending" && (
                                <span className="text-muted small">—</span>
                              )}
                            </div>
                          </td>

                          <td className="text-end">
                            {p.status === "Approved" ? (
                              <button
                                className="btn btn-sm btn-secondary"
                                onClick={() => generateQuote(p.proposalId)}
                              >
                                Generate Quote
                              </button>
                            ) : (
                              <span className="text-muted small">—</span>
                            )}
                          </td>

                        </tr>
                      ))
                    ) : (
                      <tr>
                        <td colSpan="7" className="text-center text-muted py-3">
                          No proposals found
                        </td>
                      </tr>
                    )}
                  </tbody>

                </table>
              </div>
            </div>

          </main>
        </div>
      </div>
    </div>
  );
}

export default OfficerDashboard;

