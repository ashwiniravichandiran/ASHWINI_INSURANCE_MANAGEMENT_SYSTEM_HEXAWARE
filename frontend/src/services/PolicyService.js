import http from "./http-common";

class PolicyService {

  addPolicy(data) {
    return http.post("/policies/add", data);
  }

  getAllPolicies() {
    return http.get("/policies/all");
  }

  getPolicyById(id) {
    return http.get(`/policies/get/${id}`);
  }

  deletePolicy(id) {
    return http.delete(`/policies/delete/${id}`);
  }

  updatePolicy(id, data) {
    return http.put(`/policies/update/${id}`, data);
  }

  getByVehicleType(vehicleType) {
    return http.get(`/policies/vehicletype/${vehicleType}`);
  }
    getPolicyByProposal(proposalId) {
    return http.get(`/userpolicies/policy/by-proposal/${proposalId}`);
  }
  getPoliciesByUser(userId) {
    return http.get(`/userpolicies/user/${userId}`);
  }
}

const policyService = new PolicyService();
export default policyService;