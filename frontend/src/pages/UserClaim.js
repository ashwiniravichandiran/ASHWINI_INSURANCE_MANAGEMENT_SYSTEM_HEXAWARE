import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import ClaimService from "../services/ClaimService";

function UserClaim() {
  const navigate = useNavigate();
  const { userPolicyId } = useParams();

  const [hasPending, setHasPending] = useState(false);
  const [claimAmount, setClaimAmount] = useState("");
  const [reason, setReason] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const checkPendingClaim = async () => {
      try {
        const res = await ClaimService.getClaimsByUserPolicy(userPolicyId);
        const pending = res.data.some(c => c.status === "PENDING");
        setHasPending(pending);

        if (pending) {
          alert("⚠️ You already have a pending claim!");
          navigate("/dashboard/claimtracking");
        }

      } catch (err) {
        console.error(err);
      }
    };

    checkPendingClaim();
  }, [userPolicyId, navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!claimAmount || !reason) {
      alert("Please fill all fields");
      return;
    }

    try {
      setLoading(true);

      await ClaimService.createClaim({
        userPolicyId: parseInt(userPolicyId),
        claimAmount: parseFloat(claimAmount),
        reason,
      });

      alert("✅ Claim submitted!");
      navigate("/dashboard/claimtracking");

    } catch (err) {
      console.error(err);

      if (err.response?.data) {
        alert(err.response.data);
      } else {
        alert("❌ Failed to submit claim");
      }

    } finally {
      setLoading(false);
    }
  };

  return (

  <div className="container py-5">


    <br></br>
    <br></br>
    <h3 className="text-center mb-1">Raise Your Insurance Claim</h3>
    <p className="text-center text-muted mb-4">
      Submit your claim quickly and track its progress in real-time
    </p>

    <div className="d-flex justify-content-center">
      <div className="card shadow" style={{ width: "450px" }}>

        <div className="card-header bg-muted fw-bold">
          Raise Claim
        </div>
        <br></br>

        <div className="card-body">

          {hasPending && (
            <div className="alert alert-warning">
              You already have a pending claim.
            </div>
          )}

          <form onSubmit={handleSubmit}>

            <input
              type="number"
              className="form-control mb-3"
              placeholder="Claim Amount"
              value={claimAmount}
              onChange={(e) => setClaimAmount(e.target.value)}
              disabled={hasPending}
            />

            <br></br>

            <textarea
              className="form-control mb-3"
              placeholder="Reason"
              value={reason}
              onChange={(e) => setReason(e.target.value)}
              disabled={hasPending}
            />
            
            <br></br>

            <button
              className="btn btn-dark w-100"
              disabled={loading || hasPending}
            >
              {hasPending
                ? "Claim Already Pending"
                : loading
                  ? "Submitting..."
                  : "Submit Claim"}
            </button>

          </form>

        </div>
      </div>
    </div>

  </div>
);
}

export default UserClaim;