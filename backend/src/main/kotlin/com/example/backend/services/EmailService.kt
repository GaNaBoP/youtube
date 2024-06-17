package com.example.backend.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService {
    @Autowired
    private lateinit var emailSender: JavaMailSender
    
    fun sendEmail(toAddress: String, subject: String, message: String) {
        val simpleMailMessage = SimpleMailMessage()
        simpleMailMessage.setTo(toAddress)
        simpleMailMessage.setSubject(subject)
        simpleMailMessage.setText(message)
        emailSender.send(simpleMailMessage)
    }
}