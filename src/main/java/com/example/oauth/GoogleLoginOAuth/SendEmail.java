package com.example.oauth.GoogleLoginOAuth;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class SendEmail {

    //@RequestMapping("/fire-email")
    public String register(Model model){
        String emailAddress = "TO@gmail.com";
        String message = "Welcome "+emailAddress+" you are successfully registered";

        sendEmail(emailAddress, message);

        System.out.println("------------------ Register New USer---------------");
        model.addAttribute("userEmail", emailAddress);
        System.out.println("------------------ Register New USer---------------");
        return "signin.jsp";
    }

    public void sendEmail(String useremail, String message) {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            //String no_reply = "no-reply@numarketplace.com";
            String no_reply = "USERNAME@gmail.com";
            String pas = "PASSWORD";
            email.setAuthenticator(new DefaultAuthenticator(no_reply, pas));
            email.setSSLOnConnect(true);
            email.setFrom(no_reply); // This user email does not
            // exist
            email.setSubject("Congratulations! you are registered");
            email.setMsg(message); // Retrieve email from the DAO and send this
            email.addTo(useremail);
            email.send();
            System.out.println("Email sent");
        } catch (EmailException e) {
            System.out.println("Email cannot be sent");
        }
    }
}
