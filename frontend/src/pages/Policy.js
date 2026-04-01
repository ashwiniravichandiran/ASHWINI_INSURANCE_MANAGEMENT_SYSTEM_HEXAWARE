

import { useState, useEffect } from "react";
import PolicyService from "../services/PolicyService";
import { useSelector } from "react-redux";

function Policy() {
  const [policies, setPolicies] = useState([]);
  const [view, setView] = useState("list");
  const [editingPolicy, setEditingPolicy] = useState(null);
  const [searchId, setSearchId] = useState("");
  const [vehicleFilter, setVehicleFilter] = useState("");

  const { user } = useSelector((state) => state.auth);

  const [formData, setFormData] = useState({
    policyName: "",
    description: "",
    basePremium: "",
    vehicleType: ""
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

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingPolicy) {
        await PolicyService.updatePolicy(editingPolicy.policyId, formData);
      } else {
        await PolicyService.addPolicy(formData);
      }
      fetchPolicies();
      setView("list");
    } catch (err) {
      console.error(err);
    }
  };

  const deletePolicy = async (id) => {
    if (window.confirm("Confirm deletion of this policy?")) {
      try {
        await PolicyService.deletePolicy(id);
        fetchPolicies();
      } catch (err) {
        console.error(err);
      }
    }
  };

  const openAddForm = () => {
    setEditingPolicy(null);
    setFormData({ policyName: "", description: "", basePremium: "", vehicleType: "" });
    setView("form");
  };

  const openEditForm = (p) => {
    setEditingPolicy(p);
    setFormData(p);
    setView("form");
  };

const filteredPolicies = policies.filter(p => {
  return (
    (!vehicleFilter || p.vehicleType === vehicleFilter) &&
    (!searchId || (p.policyId && p.policyId.toString().includes(searchId)))
  );
});


  if (view === "form") {
    return (
      <div className="container py-5">
        <div className="row justify-content-center">
          <div className="col-md-6">
            <div className="card border-dark shadow-sm">
              <div className="card-header text-dark">
                <h4 className="mb-0">{editingPolicy ? "Update Policy" : "Create New Policy"}</h4>
              </div>
              <div className="card-body bg-light">
                <form onSubmit={handleSubmit}>
                  <div className="mb-3">
                    <label className="form-label fw-bold small">Policy Name</label>
                    <input
                      className="form-control border-dark"
                      value={formData.policyName}
                      onChange={(e) => setFormData({ ...formData, policyName: e.target.value })}
                      required
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label fw-bold small">Description</label>
                    <textarea
                      className="form-control border-dark"
                      rows="3"
                      value={formData.description}
                      onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                    />
                  </div>

                  <div className="row">
                    <div className="col-md-6 mb-3">
                      <label className="form-label fw-bold small">Base Premium</label>
                      <div className="input-group">
                        <span className="input-group-text">₹</span>
                        <input
                          type="number"
                          className="form-control border-dark"
                          value={formData.basePremium}
                          onChange={(e) => setFormData({ ...formData, basePremium: e.target.value })}
                          required
                        />
                      </div>
                    </div>

                    <div className="col-md-6 mb-3">
                      <label className="form-label fw-bold small">Vehicle Type</label>
                      <select
                        className="form-select border-dark"
                        value={formData.vehicleType}
                        onChange={(e) => setFormData({ ...formData, vehicleType: e.target.value })}
                        required
                      >
                        <option value="">Choose...</option>
                        <option>BIKE</option>
                        <option>CAR</option>
                        <option>CAMPER</option>
                      </select>
                    </div>
                  </div>

                  <div className="d-flex gap-2 mt-4">
                    <button type="submit" className="btn btn-outline-success w-100">
                      {editingPolicy ? "UPDATE" : "SAVE"}
                    </button>
                    <button type="button" className="btn btn-outline-dark w-100" onClick={() => setView("list")}>
                      CANCEL
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="container py-5">
      <div className="d-flex justify-content-between align-items-center mb-4 border-bottom border-dark pb-2">
        <h1>{user?.role === "ADMIN" ? "Policy Management" : "Policies"}</h1>

        {user?.role === "ADMIN" && (
          <button className="btn btn-dark" onClick={openAddForm}>
            + Add Policy
          </button>
        )}
      </div>

      <div className="row g-2 mb-4 bg-light p-3 border border-dark rounded">

        <div className="col-md-6">
          <input
            placeholder="Search by ID..."
            className="form-control border-dark"
            value={searchId}
            onChange={(e) => setSearchId(e.target.value)}
          />
        </div>

        <div className="col-md-6">
          <select
            className="form-select border-dark"
            value={vehicleFilter}
            onChange={(e) => setVehicleFilter(e.target.value)}
          >
            <option value="">All Vehicles</option>
            <option>BIKE</option>
            <option>CAR</option>
            <option>CAMPER</option>
          </select>
        </div>

      </div>


      <div className="table-responsive shadow-sm">
        <table className="table table-hover border-dark">
          <thead>
            <tr>
              <th>ID</th>
              <th>NAME</th>
              <th>DESCRIPTION</th>
              <th>PREMIUM</th>
              <th>VEHICLE</th>
              {user?.role === "ADMIN" && <th className="text-center">ACTIONS</th>}
            </tr>
          </thead>

          <tbody>
            {filteredPolicies.length > 0 ? (
              filteredPolicies.map((p) => (
                <tr key={p.policyId}>
                  <td>{p.policyId}</td>
                  <td>{p.policyName}</td>
                  <td>{p.description}</td>
                  <td>₹{p.basePremium}</td>
                  <td>{p.vehicleType}</td>

                  {user?.role === "ADMIN" && (
                    <td className="text-center">
                      <button className="btn btn-sm btn-outline-primary me-2" onClick={() => openEditForm(p)}>
                        EDIT
                      </button>
                      <button className="btn btn-sm btn-outline-danger" onClick={() => deletePolicy(p.policyId)}>
                        DELETE
                      </button>
                    </td>
                  )}
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="6" className="text-center text-muted py-3">
                  No policies found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Policy;

