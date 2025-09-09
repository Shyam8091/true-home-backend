package com.example.true_home.util;

import com.example.true_home.exception.TrueHomException;
import com.example.true_home.security.AESUtil;
import com.example.true_home.security.CustomUserDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.example.true_home.util.TrueHomeConstants.SESSION_EXPIRATION_TIME;

@Component
public class TrueHomeUtil {

    public HttpHeaders getHttpHeaders(int byId) {
        long currentTime = System.currentTimeMillis();
        long expirationTime = currentTime + SESSION_EXPIRATION_TIME;
        long sessionCreationTime = currentTime;
        String sessionData = byId + ":" + expirationTime + ":" + sessionCreationTime;
        String encryptedSessionId = null;
        try {
            encryptedSessionId = AESUtil.encrypt(sessionData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Authorization", "Bearer " + encryptedSessionId);
        return responseHeaders;
    }

    public int getUserIdFromAuthentication() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUserId();
        }
        System.out.println("No user found");
        throw new TrueHomException(null, "Invalid User", "OS_400");
    }
}
