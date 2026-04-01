package com.hexaware.ims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ims.dto.ProposalRequestDTO;
import com.hexaware.ims.dto.ProposalResponseDTO;
import com.hexaware.ims.service.IProposalService;

@RestController
@RequestMapping("/api/proposals")
public class ProposalController {

    @Autowired
    IProposalService service;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('USER')")
    public ProposalResponseDTO addProposal(@RequestBody ProposalRequestDTO dto) {

        // ✅ get logged-in user email from token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return service.addProposal(dto, email);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/get/{id}")
    public ProposalResponseDTO getInvoice(@PathVariable int id) {
        return service.getProposalById(id);
    }

    
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user")
    public List<ProposalResponseDTO> getProposalByUser() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String email = auth.getName();
        return service.getProposalByUser(email);
    }
    

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PutMapping("/status/{proposalId}/{status}")
    public ProposalResponseDTO updateStatus(@PathVariable int proposalId,
                                            @PathVariable String status) {
        return service.updateStatus(proposalId, status);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/quote/{proposalId}")
    public ProposalResponseDTO generateQuote(@PathVariable int proposalId) {
        return service.generateQuote(proposalId);
    }

    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public List<ProposalResponseDTO> viewAllProposals() {
        return service.viewAllProposals();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/status/{status}")
    public List<ProposalResponseDTO> getProposalByStatus(@PathVariable String status) {
        return service.getProposalByStatus(status);
    }
}