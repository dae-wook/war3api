package com.daesoo.war3api.user.googleAuth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

//import static org.springframework.security.config.http.OAuth2ResourceServerBeanDefinitionParser.OpaqueTokenBeanDefinitionParser.CLIENT_ID;
@Component
@RequiredArgsConstructor
public class GoogleAuth {

    private static final String CLIENT_ID = "899883406328-71437rt8e2ba6v8ivtkglju4k23jc5af.apps.googleusercontent.com";
    public String getUserEmail(String idTokenString) throws GeneralSecurityException, IOException {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(CLIENT_ID)).build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();

            // Get profile information from payload
            String email = payload.getEmail();
//            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
//            String name = (String) payload.get("name");
//            String pictureUrl = (String) payload.get("picture");
//            String locale = (String) payload.get("locale");
//            String familyName = (String) payload.get("family_name");
//            String givenName = (String) payload.get("given_name");
            // Use or store profile information
            // ...
            return email;

        } else {
            return null;
        }


    }

}
