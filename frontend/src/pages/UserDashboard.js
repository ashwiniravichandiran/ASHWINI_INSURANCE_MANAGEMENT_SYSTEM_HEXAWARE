import { useState, useEffect } from "react";
import { FileText, FileCheck, ShieldCheck, AlertTriangle } from "lucide-react";
import { Outlet, useNavigate } from "react-router-dom";
import ProposalService from "../services/ProposalService";
import PolicyService from "../services/PolicyService";
import ClaimService from "../services/ClaimService";

const getStatusBadge = (status) => {
  switch (status?.toUpperCase()) {
    case "ACTIVE":
      return "success";
    case "PENDING":
      return "primary";
    case "EXPIRED":
      return "danger";
    case "QUOTE_GENERATED":
      return "warning";
    case "REJECTED":
      return "danger";
    case "DOCUMENTS_REQUESTED":
      return "info"
    default:
      return "secondary";
  }
};

function UserDashboard() {
  const [statusCards, setStatusCards] = useState([]);
  const [proposals, setProposals] = useState([]);
  const [claims, setClaims] = useState([]); // 🔥 NEW
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();
  const fetchPolicies = async () => {
    try {
      const res = await ProposalService.getUserProposals();
      const data = res.data;
      console.log(data);
      setProposals(data);

      const total = data.length;
      const quoted = data.filter(p => p.status === "QUOTE_GENERATED").length;
      const active = data.filter(p => p.status === "ACTIVE").length;
      const expired = data.filter(p => {
        if (!p.expiry) return false;
        return new Date(p.expiry) < new Date();
      }).length;

      setStatusCards([
        { label: "Proposal Submitted", value: total, icon: FileText, color: "primary" },
        { label: "Quote Generated", value: quoted, icon: FileCheck, color: "warning" },
        { label: "Active Policies", value: active, icon: ShieldCheck, color: "success" },
        { label: "Expired", value: expired, icon: AlertTriangle, color: "danger" },
      ]);

    } catch (err) {
      console.error(err);
    }
  };

  const fetchClaims = async () => {
    try {
      const res = await ClaimService.getUserClaims();
      setClaims(res.data);
      console.log(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    const loadData = async () => {
      await fetchPolicies();
      await fetchClaims();
      setLoading(false);
    };
    loadData();
  }, []);

  const handleClaim = async (proposalId) => {
    const res = await PolicyService.getPolicyByProposal(proposalId);
    const userPolicyId = res.data.userPolicyId;

    navigate(`/claim/${userPolicyId}`);
  };

  if (loading) {
    return <div className="text-center py-5">Loading dashboard...</div>;
  }

  return (
    <>
      <div className="col-md-10 p-4 bg-light">

        <div className="row mb-4">
          {statusCards.map((card, i) => {
            const Icon = card.icon;

            return (
              <div key={i} className="col-md-3 mb-3">
                <div className="card shadow-sm border-0 hover-card">
                  <div className="card-body d-flex justify-content-between">
                    <div>
                      <h4>{card.value}</h4>
                      <p>{card.label}</p>
                    </div>
                    <Icon size={28} className={`text-${card.color}`} />
                  </div>
                </div>
              </div>
            );
          })}
        </div>

        <div className="card shadow-sm">
          <div className="card-header fw-semibold">My Proposals</div>

          <table className="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Vehicle</th>
                <th>Status</th>
                <th>Expiry</th>
                <th>Premium</th>
                <th>Invoice</th>
                <th>Claim</th>
              </tr>
            </thead>

            <tbody>
              {proposals.map((p) => {

                const hasPending = claims.some(
                  c => c.userPolicyId === p.userPolicyId && c.status === "PENDING"
                );

                return (
                  <tr key={p.proposalId}>
                    <td>{p.proposalId}</td>
                    <td>{p.vehicleNumber}</td>

                    <td>
                      <span className={`badge bg-${getStatusBadge(p.status)}`}>
                        {p.status}
                      </span>
                    </td>

                    <td>
                      {p.expiry ? new Date(p.expiry).toLocaleString() : "-"}
                    </td>

                    <td>₹{p.premiumAmount}</td>

                    <td>
                      {p.status === "QUOTE_GENERATED" ? (
                        <button
                          className="btn btn-success btn-sm"
                          onClick={() => navigate(`/invoice/${p.proposalId}`)}
                        >
                          Invoice
                        </button>
                      ) : (
                        "—"
                      )}
                    </td>

                    <td>
                      {p.status === "ACTIVE" && (
                        hasPending ? (
                          <button className="btn btn-secondary btn-sm" disabled>
                            Claim Pending
                          </button>
                        ) : (
                          <button
                            className="btn btn-warning btn-sm"
                            onClick={() => handleClaim(p.proposalId)}
                          >
                            Raise Claim
                          </button>
                        )
                      )}
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>

          <Outlet />
        </div>
      </div>
    </>
  );
}

export default UserDashboard;