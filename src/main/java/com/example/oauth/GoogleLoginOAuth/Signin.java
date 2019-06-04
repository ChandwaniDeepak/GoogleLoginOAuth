package com.example.oauth.GoogleLoginOAuth;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;


@Controller
public class Signin {

    @Autowired
    private SendEmail sendEmail;

    final static String CLIENT_ID = "YOUR_CLIENT_ID";

    @RequestMapping("/signin")
    private String signin(Model model, HttpServletRequest req) {

        String name, email, idTokenString;
        idTokenString = req.getParameter("t");

        System.out.println("token ->" + idTokenString);
        email = verify(idTokenString);
        if (email != null) {
            String message = "congratulations you are successfully registerd with us";
            sendEmail.sendEmail(email, message);
        }
        return "signin.jsp";
    }

    public String verify(String idTokenString) {
        final NetHttpTransport transport;
        try {
            transport = GoogleNetHttpTransport.newTrustedTransport();

            final JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                if (payload != null) {
                    // Print user identifier
                    String userId = payload.getSubject();
                    System.out.println("User ID: " + userId);

                    // Get profile information from payload
                    String email = payload.getEmail();
                    boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                    String name = (String) payload.get("name");
                    String pictureUrl = (String) payload.get("picture");
                    String locale = (String) payload.get("locale");
                    String familyName = (String) payload.get("family_name");
                    String givenName = (String) payload.get("given_name");
                    System.out.println("Payload-> "+
                            " name: "+name+
                            " email: "+email+
                            " givenName: "+givenName+
                            " emailVerified: "+emailVerified
                    );

                    // Use or store profile information
                    // ...

                    return email;
                }

                return null;
            } else {
                System.out.println("Invalid ID token.");
                return null;
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
