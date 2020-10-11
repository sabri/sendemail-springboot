package com.sabri.sendemail;


import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;

@AllArgsConstructor
@RestController
@RequestMapping("/email")
public class EmailController {


    private  EmailConfig emailConfig;



    @PostMapping
    public void  sendemail(@RequestBody Feedback feedback,
                           BindingResult bindingRest){
        if (bindingRest.hasErrors()){throw new ValidationException("is not valide");
        }
//create email sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfig.getHost());
        mailSender.setPort(this.emailConfig.getPort());
        mailSender.setPassword(this.emailConfig.getPassword());
        mailSender.setUsername(this.emailConfig.getUsername());

        // create email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(feedback.getEmail());
        mailMessage.setTo("sabri@gmail.com");
        mailMessage.setSubject("New Fedback from " + feedback.getName());
        mailMessage.setText(feedback.getFeedback());
// mail sender
        mailSender.send(mailMessage);

    }
}
