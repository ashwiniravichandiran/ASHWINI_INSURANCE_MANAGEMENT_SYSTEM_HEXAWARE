import http from "./http-common";


class ClaimService {


  createClaim(data) {
    return http.post(`/claim/`, data);
  }

  getUserClaims() {
    return http.get(`/claim/user`);
  }
  getAllClaims() {
    return http.get(`/claim/`);
  }

  updateClaimStatus(claimId, status) {
    return http.put(`/claim/${claimId}/${status}`);
  }
}

const claimService = new ClaimService();
export default claimService;