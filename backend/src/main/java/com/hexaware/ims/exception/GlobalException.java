package com.hexaware.ims.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
	 @ExceptionHandler(PaymentNotFoundException.class)
	    public String handlePayment(PaymentNotFoundException ex) {
	        return ex.getMessage();
	    }

	    @ExceptionHandler(ProposalNotFoundException.class)
	    public String handleProposal(ProposalNotFoundException ex) {
	        return ex.getMessage();
	    }
	    
	    @ExceptionHandler(UserNotFoundException.class)
	    public String handleUser(UserNotFoundException ex) {
	    	return ex.getMessage();
	    }
	    
	    @ExceptionHandler(PolicyNotFoundException.class)
	    public String handlePolicy(PolicyNotFoundException ex) {
	    	return ex.getMessage();
	    }
	    
	    @ExceptionHandler(IllegalStateException.class)
	    public String handleIllegalState(IllegalStateException ex) {
	    	return ex.getMessage();
	    }
	    
	    @ExceptionHandler(UserPolicyNotFoundException.class)
	    public String handleUserPolicy(UserPolicyNotFoundException ex) {
	    	return ex.getMessage();
	    }
}
