import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import proposalService from "../services/ProposalService";
import { CreditCard, Shield } from "lucide-react";
import { FaGooglePay, FaCcVisa, FaUniversity } from "react-icons/fa";
import { SiPaytm, SiPhonepe } from "react-icons/si";

function Payment() {

  const { proposalId } = useParams();

  const [invoice, setInvoice] = useState(null);
  const [method, setMethod] = useState("GPay");

  const navigate = useNavigate();

  useEffect(() => {
    proposalService.getProposalById(proposalId)
      .then(res => {
        console.log("Invoice Data:", res.data);
        setInvoice(res.data);
      })
      .catch(err => console.error(err));
  }, [proposalId]);

  if (!invoice) return <p className="text-center mt-5">Loading payment details...</p>;

  const breakdown = [
    { label: "Base Premium", amount: invoice.basePremium },

    ...(invoice.zeroDep
      ? [{ label: "Zero Depreciation", amount: 42 }]
      : []),

    ...(invoice.rsa
      ? [{ label: "Roadside Assistance", amount: 18 }]
      : []),

    ...(invoice.paCover
      ? [{ label: "Personal Accident Cover", amount: 24 }]
      : []),

    { label: "GST (18%)", amount: invoice.gstAmount }
  ];

  const total = invoice.premiumAmount;

  const handlePayment = async() => {
    try {
    await proposalService.updateStatus(proposalId, "ACTIVE");
    alert(`Payment successful ₹${total.toFixed(2)} ✅`);
  } catch (err) {
    console.error(err);
    alert("Payment failed ❌");
    
  }

  navigate("/dashboard");
  };

  return (
    <div className="container my-5">

      <div className="text-center mb-4">
        <h2 className="fw-bold">Premium Quote</h2>
        <p className="text-muted">
          {invoice.vehicleModel} — Policy ID{invoice.policyId}
        </p>
      </div>

      <div className="row justify-content-center">
        <div className="col-md-6">

          {/* Breakdown Card */}
          <div className="card shadow-sm border-0 mb-4">
            <div className="card-body">

              <h5 className="fw-semibold mb-3">Premium Breakdown</h5>

              {breakdown.map((item, index) => (
                <div
                  key={index}
                  className="d-flex justify-content-between mb-2"
                >
                  <span className="text-muted">{item.label}</span>
                  <span className="fw-medium">
                    ₹{item.amount.toFixed(2)}
                  </span>
                </div>
              ))}

              <hr />

              <div className="d-flex justify-content-between">
                <span className="fw-bold">Total Amount</span>
                <span className="fs-4 fw-bold text-secondary">
                  ₹{total.toFixed(2)}
                </span>
              </div>

            </div>
          </div>

          <div className="card shadow-sm border-0 text-center">
  <div className="card-body">

    <h5 className="mb-3 fw-semibold">Choose Payment Method</h5>

    <div className="d-flex flex-wrap justify-content-center gap-3 mb-4">

      <div
        className={`p-3 border rounded ${method === "GPay" ? "border-success" : ""}`}
        onClick={() => setMethod("GPay")}
        style={{ cursor: "pointer" }}
      >
        <FaGooglePay size={28} color="#34A853" />
        <div>GPay</div>
      </div>

      <div
        className={`p-3 border rounded ${method === "Paytm" ? "border-primary" : ""}`}
        onClick={() => setMethod("Paytm")}
        style={{ cursor: "pointer" }}
      >
        <SiPaytm size={28} color="#00BAF2" />
        <div>Paytm</div>
      </div>

      <div
        className={`p-3 border rounded ${method === "PhonePe" ? "border-purple" : ""}`}
        onClick={() => setMethod("PhonePe")}
        style={{ cursor: "pointer" }}
      >
        <SiPhonepe size={28} color="#6739B7" />
        <div>PhonePe</div>
      </div>

      <div
        className={`p-3 border rounded ${method === "Card" ? "border-dark" : ""}`}
        onClick={() => setMethod("Card")}
        style={{ cursor: "pointer" }}
      >
        <FaCcVisa size={28} />
        <div>Card</div>
      </div>

      <div
        className={`p-3 border rounded ${method === "Bank" ? "border-warning" : ""}`}
        onClick={() => setMethod("Bank")}
        style={{ cursor: "pointer" }}
      >
        <FaUniversity size={28} />
        <div>Net Banking</div>
      </div>

    </div>

    <p className="text-muted mb-3">
      Selected: <strong>{method}</strong>
    </p>

    <button
      className="btn btn-dark w-100 mb-3"
      onClick={handlePayment}
    >
      <CreditCard size={18} className="me-2" />
      Pay ₹{total.toFixed(2)}
    </button>

    <div className="d-flex justify-content-center align-items-center text-muted small">
      <Shield size={14} className="me-1" />
      Secured by 256-bit encryption
    </div>

  </div>
</div>

        </div>
      </div>

    </div>
  );
}

export default Payment;