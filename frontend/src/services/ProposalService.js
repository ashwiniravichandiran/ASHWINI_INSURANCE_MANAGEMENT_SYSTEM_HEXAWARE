import http from "./http-common";

class ProposalService {
  createProposal(data) {
    return http.post("/proposals/add", data);
  }

  getUserProposals() {
    return http.get(`/proposals/user`);
  }

  getProposalById(id) {
    return http.get(`/proposals/get/${id}`);
  }


  getAll(){
    return http.get("/proposals/all");
  }

  updateStatus(proposalId, pstatus){
    return http.put(`/proposals/status/${proposalId}/${pstatus}`);
  }
  
  generateQuote(proposalId){
    return http.put(`/proposals/quote/${proposalId}`);
  }
}

const proposalService = new ProposalService();
export default proposalService;