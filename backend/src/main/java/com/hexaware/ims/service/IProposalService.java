package com.hexaware.ims.service;

import java.util.List;

import com.hexaware.ims.dto.ProposalRequestDTO;
import com.hexaware.ims.dto.ProposalResponseDTO;

public interface IProposalService {

    public ProposalResponseDTO addProposal(ProposalRequestDTO dto, String email);

    ProposalResponseDTO getProposalById(int id);

    List<ProposalResponseDTO> getProposalByUserId(int userId);
    
    List<ProposalResponseDTO> getProposalByUser(String Email);

    ProposalResponseDTO updateStatus(int proposalId, String status);

    ProposalResponseDTO generateQuote(int proposalId);

    List<ProposalResponseDTO> viewAllProposals();

    List<ProposalResponseDTO> getProposalByStatus(String status);
}