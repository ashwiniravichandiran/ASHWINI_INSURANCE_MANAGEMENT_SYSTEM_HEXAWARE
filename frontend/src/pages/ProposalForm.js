import { useEffect, useState } from "react";
import ProposalService from "../services/ProposalService";
import PolicyService from "../services/PolicyService";
import "../App.css";
import { useNavigate } from "react-router-dom";

const steps = ["Vehicle Details", "Policy Selection", "Review"];

const ProposalForm = () => {
  const [policies, setPolicies] = useState([]);
  const [currentStep, setCurrentStep] = useState(0);

  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    vehicleType: "",
    vehicleModel: "",
    vehicleNumber: "",
    policyId: "",
    zeroDep: false,
    rsa: false,
    paCover: false,
    duration: "1",
  });

  useEffect(() => {
    fetchPolicies();
  }, []);

  const fetchPolicies = async () => {
    try {
      const res = await PolicyService.getAllPolicies();
      setPolicies(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const selectedPolicy = policies.find(
    (p) => p.policyId === Number(formData.policyId)
  );

  const handleSubmit = async () => {
    try {
      const response = await ProposalService.createProposal(formData);

      console.log(response.data);
      alert("Proposal submitted successfully ✅");

      setFormData({
        vehicleType: "",
        vehicleModel: "",
        vehicleNumber: "",
        policyId: "",
        zeroDep: false,
        rsa: false,
        paCover: false,
        duration: "1",
      });

      setCurrentStep(0);
    } catch (error) {
      console.error(error);

      if (error.response) {
        alert(error.response.data.message || "Submission failed");
      } else {
        alert("Server error");
      }
    }
    navigate("/dashboard");
  };

  return (
    <div className="container py-5">
      <div className="mx-auto" style={{ maxWidth: "600px" }}>

        <div className="d-flex justify-content-between mb-4">
          {steps.map((step, index) => (
            <div key={step} className="text-center flex-fill">
              <div
                className={`rounded-circle mx-auto mb-2 d-flex align-items-center justify-content-center ${
                  index <= currentStep ? "bg-dark text-white" : "bg-light"
                }`}
                style={{ width: "35px", height: "35px" }}
              >
                {index + 1}
              </div>
              <small>{step}</small>
            </div>
          ))}
        </div>

        <div className="card shadow p-4">

          {currentStep === 0 && (
            <>
              <h5 className="mb-3">Vehicle Details</h5>

              <select
                name="vehicleType"
                value={formData.vehicleType}
                onChange={handleChange}
                className="form-control mb-3"
              >
                <option value="">Select Vehicle Type</option>
                <option value="CAR">Car</option>
                <option value="BIKE">Bike</option>
                <option value="CAMPER_VAN">Camper Van</option>
              </select>

              <input
                name="vehicleModel"
                value={formData.vehicleModel}
                onChange={handleChange}
                className="form-control mb-3"
                placeholder="Vehicle Model"
              />

              <input
                name="vehicleNumber"
                value={formData.vehicleNumber}
                onChange={handleChange}
                className="form-control"
                placeholder="Vehicle Number"
              />
            </>
          )}

          {currentStep === 1 && (
            <>
              <h5 className="mb-3">Select Policy</h5>

              <select
                name="policyId"
                value={formData.policyId}
                onChange={handleChange}
                className="form-control mb-3"
              >
                <option value="">Select Policy</option>
                {policies.map((policy) => (
                  <option key={policy.policyId} value={policy.policyId}>
                    {policy.policyName}
                  </option>
                ))}
              </select>

              <h6 className="mt-3">Select Add-ons</h6>

              <div className="form-check">
                <input
                  type="checkbox"
                  name="zeroDep"
                  checked={formData.zeroDep}
                  onChange={handleChange}
                  className="form-check-input"
                />
                <label className="form-check-label">Zero Depreciation</label>
              </div>

              <div className="form-check">
                <input
                  type="checkbox"
                  name="rsa"
                  checked={formData.rsa}
                  onChange={handleChange}
                  className="form-check-input"
                />
                <label className="form-check-label">Roadside Assistance</label>
              </div>

              <div className="form-check">
                <input
                  type="checkbox"
                  name="paCover"
                  checked={formData.paCover}
                  onChange={handleChange}
                  className="form-check-input"
                />
                <label className="form-check-label">
                  Personal Accident Cover
                </label>
              </div>

              <div className="mt-4">
                <label className="form-label fw-semibold">
                  Policy Duration
                </label>

                <select
                  name="duration"
                  value={formData.duration}
                  onChange={handleChange}
                  className="form-control"
                >
                  <option value="1">1 Year</option>
                  <option value="2">2 Years</option>
                  <option value="3">3 Years</option>
                </select>

                <small className="text-muted">
                  Selected: {formData.duration} Year(s)
                </small>
              </div>
            </>
          )}

          {/* STEP 3 */}
          {currentStep === 2 && (
            <>
              <h5 className="mb-3">Review Details</h5>

              <p><strong>Vehicle Type:</strong> {formData.vehicleType}</p>
              <p><strong>Vehicle Model:</strong> {formData.vehicleModel}</p>
              <p><strong>Vehicle Number:</strong> {formData.vehicleNumber}</p>
              <p><strong>Policy:</strong> {selectedPolicy?.policyName}</p>
              <p><strong>Duration:</strong> {formData.duration} Year(s)</p>

              <hr />

              <p><strong>Add-ons:</strong></p>
              <p>Zero Dep: {formData.zeroDep ? "Yes" : "No"}</p>
              <p>RSA: {formData.rsa ? "Yes" : "No"}</p>
              <p>PA Cover: {formData.paCover ? "Yes" : "No"}</p>
            </>
          )}

          {/* Buttons */}
          <div className="d-flex justify-content-between mt-4">
            <button
              className="btn btn-outline-secondary"
              onClick={() => setCurrentStep(currentStep - 1)}
              disabled={currentStep === 0}
            >
              Previous
            </button>

            {currentStep < steps.length - 1 ? (
              <button
                className="btn btn-dark"
                onClick={() => setCurrentStep(currentStep + 1)}
              >
                Next
              </button>
            ) : (
              <button className="btn btn-success" onClick={handleSubmit}>
                Submit
              </button>
            )}
          </div>

        </div>
      </div>
    </div>
  );
};

export default ProposalForm;