package com.hexaware.ims.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ims.dto.ProposalRequestDTO;
import com.hexaware.ims.dto.ProposalResponseDTO;
import com.hexaware.ims.entity.Payment;
import com.hexaware.ims.entity.Policy;
import com.hexaware.ims.entity.Proposal;
import com.hexaware.ims.entity.User;
import com.hexaware.ims.entity.UserPolicy;
import com.hexaware.ims.exception.ProposalNotFoundException;
import com.hexaware.ims.repo.PaymentRepo;
import com.hexaware.ims.repo.PolicyRepo;
import com.hexaware.ims.repo.ProposalRepo;
import com.hexaware.ims.repo.UserPolicyRepo;
import com.hexaware.ims.repo.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class ProposalServiceImpl implements IProposalService {

    @Autowired
    ProposalRepo repo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PolicyRepo policyRepo;
    
    @Autowired
    PaymentRepo paymentRepo;
    
    @Autowired
    UserPolicyRepo userPolicyRepo;
    
    @Autowired
    EmailService emailService;

    public Proposal convertToEntity(ProposalRequestDTO dto, User user, Policy policy) {

        Proposal proposal = new Proposal();

        proposal.setUser(user);
        proposal.setPolicy(policy);
        proposal.setVehicleType(dto.getVehicleType());
        proposal.setVehicleNumber(dto.getVehicleNumber());
        proposal.setVehicleModel(dto.getVehicleModel());
        proposal.setPremiumAmount(policy.getBasePremium());
        proposal.setCreatedDate(LocalDateTime.now());
        proposal.setStatus("PENDING");
        proposal.setPaCover(dto.isPaCover());
        proposal.setRsa(dto.isRsa());
        proposal.setZeroDep(dto.isZeroDep());
        proposal.setExpiry(LocalDateTime.now().plusYears(dto.getDuration()));

        return proposal;
    }

    public ProposalResponseDTO convertToDTO(Proposal proposal) {

        ProposalResponseDTO dto = new ProposalResponseDTO();

        dto.setProposalId(proposal.getProposalId());
        dto.setUserId(proposal.getUser().getUserId());
        dto.setPolicyId(proposal.getPolicy().getPolicyId());
        dto.setUserPolicyId(
        	    proposal.getUserPolicy() != null
        	        ? proposal.getUserPolicy().getUserPolicyId()
        	        : 0
        	);
        dto.setVehicleType(proposal.getVehicleType());
        dto.setVehicleNumber(proposal.getVehicleNumber());
        dto.setVehicleModel(proposal.getVehicleModel());
        dto.setPremiumAmount(proposal.getPremiumAmount());
        dto.setCreatedDate(proposal.getCreatedDate());
        dto.setStatus(proposal.getStatus());
        dto.setExpiry(proposal.getExpiry());
        dto.setUserName(proposal.getUser().getName());
        dto.setAddOnAmount(proposal.getAddOnAmount());
        dto.setBasePremium(proposal.getBasePremium());
        dto.setGstAmount(proposal.getGstAmount());
        dto.setRsa(proposal.isRsa());
        dto.setPaCover(proposal.isPaCover());
        dto.setZeroDep(proposal.isZeroDep());

        return dto;
    }

    public ProposalResponseDTO addProposal(ProposalRequestDTO dto, String email) {

    	User user = userRepo.findByEmail(email).orElse(null);
    	
        Policy policy = policyRepo.findById(dto.getPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        Proposal proposal = convertToEntity(dto, user, policy);

        Proposal saved = repo.save(proposal);

        return convertToDTO(saved);
    }

    @Override
    public ProposalResponseDTO getProposalById(int id) {

        Proposal proposal = repo.findById(id)
                .orElseThrow(() -> new ProposalNotFoundException(
                        "Proposal not found with id " + id));

        return convertToDTO(proposal);
    }

    @Override
    public List<ProposalResponseDTO> getProposalByUserId(int userId) {

        List<Proposal> proposals = repo.findByUserUserId(userId);

        return proposals.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    


    @Transactional
    public ProposalResponseDTO updateStatus(int proposalId, String status) {

        Proposal proposal = repo.findById(proposalId)
                .orElseThrow(() -> new ProposalNotFoundException(
                        "Proposal not found with id " + proposalId));

        String oldStatus = proposal.getStatus();

        proposal.setStatus(status);
        Proposal updated = repo.save(proposal);

        User user = proposal.getUser();

        if (!"ACTIVE".equalsIgnoreCase(oldStatus) &&
            "ACTIVE".equalsIgnoreCase(status)) {

            if (!paymentRepo.existsByProposal(proposal)) {
                Payment payment = new Payment(
                        proposal,
                        proposal.getPremiumAmount(),
                        "SUCCESS"
                );
                payment.setPaymentDate(LocalDateTime.now());
                paymentRepo.save(payment);
            }

             if (!userPolicyRepo.existsByProposal(proposal)) {
                UserPolicy policy = new UserPolicy(
                        proposal, "ACTIVE", user
                );
                userPolicyRepo.save(policy);
            }
        }

        emailService.sendProposalStatusEmail(user.getEmail(), status);

        return convertToDTO(updated);
    }

    @Override
    public ProposalResponseDTO generateQuote(int proposalId) {
        Proposal proposal = repo.findById(proposalId)
                .orElseThrow(() -> new ProposalNotFoundException(
                        "Proposal not found with id " + proposalId));
        Policy policy = proposal.getPolicy();
        double premium = policy.getBasePremium();
        proposal.setBasePremium(proposal.getPolicy().getBasePremium());
        double addOns = 0;
        if(proposal.isZeroDep()) addOns += 42;
        if(proposal.isRsa()) addOns += 18;
        if(proposal.isPaCover()) addOns += 24;
        double subtotal = premium + addOns;

        double gst = subtotal * 0.18;
        proposal.setAddOnAmount(addOns);
        proposal.setGstAmount(gst);

        double total = subtotal + gst;

        proposal.setPremiumAmount(total);

        proposal.setStatus("QUOTE_GENERATED");

        Proposal updated = repo.save(proposal);

        return convertToDTO(updated);
    }

    @Override
    public List<ProposalResponseDTO> viewAllProposals() {

        List<Proposal> proposals = repo.findAll();

        return proposals.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProposalResponseDTO> getProposalByStatus(String status) {

        List<Proposal> proposals = repo.findByStatus(status);

        return proposals.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProposalResponseDTO> getProposalByUser(String email) {

        User user = userRepo.findByEmail(email).orElse(null);

        if(user == null) {
            throw new RuntimeException("User not found");
        }

        List<Proposal> proposals = repo.findByUser(user);

        return proposals.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}