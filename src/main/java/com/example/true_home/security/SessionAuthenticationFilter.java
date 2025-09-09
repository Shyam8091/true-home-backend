package com.example.true_home.security;

import com.example.true_home.entity.User;
import com.example.true_home.exception.TrueHomException;
import com.example.true_home.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class SessionAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepo userRepo;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String encryptedSessionId = header.substring(7);
            try {
                // Decrypt the session ID
                String decryptedSessionData = AESUtil.decrypt(encryptedSessionId);
                String[] sessionParts = decryptedSessionData.split(":");

                String userId = sessionParts[0];
                long expirationTime = Long.parseLong(sessionParts[1]);
                long sessionCreationTime = Long.parseLong(sessionParts[2]);

                // Check if the session has expired
                if (System.currentTimeMillis() > expirationTime) {
                    throw new TrueHomException(null, "Session has expired", "OS_500");
                }

                // Validate the user from the user repository
                User user = userRepo.findById(Integer.parseInt(userId)).orElseThrow(() -> new TrueHomException(null, "Invalid USER or Session has expired", "OS_500"));
                CustomUserDetails userDetails = new CustomUserDetails(user);
                // Set the authentication in the SecurityContext
                SessionAuthenticationToken authToken = new SessionAuthenticationToken(userDetails, null, Collections.emptyList());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (TrueHomException e) {
                // Clear the security context
                SecurityContextHolder.clearContext();

                // Set the response status and message
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"" + e.getMessage() + "\", \"code\":\"" + e.getErrorCode() + "\"}");
                response.getWriter().flush();
                response.getWriter().close();

                // Stop further processing by returning immediately
                return;

            } catch (Exception e) {
                // If the session ID is invalid or expired, clear the security context
                SecurityContextHolder.clearContext();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired session");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
