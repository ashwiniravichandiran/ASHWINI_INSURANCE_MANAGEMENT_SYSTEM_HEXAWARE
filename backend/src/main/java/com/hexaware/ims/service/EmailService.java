package com.hexaware.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendLoginSuccessEmail(String toEmail) {
        sendEmail(
            toEmail,
            "Login Successful",
            "Hello,\n\nYour login was successful.\n\nIf this wasn't you, please contact support immediately."
        );
    }

    public void sendRegistrationEmail(String toEmail) {
        sendEmail(
            toEmail,
            "Welcome to Insurance System 🎉",
            "Hello,\n\nYour registration was successful.\n\nThank you for joining us!"
        );
    }

    public void sendProposalStatusEmail(String toEmail, String status) {
        sendEmail(
            toEmail,
            "Proposal Status Update",
            "Hello,\n\nYour proposal status is now: " + status + ".\n\nThank you."
        );
    }

    public void sendPaymentSuccessEmail(String toEmail, double amount) {
        sendEmail(
            toEmail,
            "Payment Successful 💰",
            "Hello,\n\nYour payment of ₹" + amount + " was successful.\n\nThank you!"
        );
    }

    public void sendPolicyCreatedEmail(String toEmail) {
        sendEmail(
            toEmail,
            "Policy Created ✅",
            "Hello,\n\nYour policy has been created successfully.\n\nThank you."
        );
    }

    public void sendClaimStatusEmail(String toEmail, String status) {
        sendEmail(
            toEmail,
            "Claim Status Update",
            "Hello,\n\nYour claim status is: " + status + ".\n\nWe will keep you updated."
        );
    }
}