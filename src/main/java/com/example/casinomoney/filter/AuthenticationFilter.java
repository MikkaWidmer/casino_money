package com.example.casinomoney.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return; // Bad request if no token provided
        }

        // Extract token
        String token = authorization.substring(7); // Remove "Bearer " prefix

        // Validate token with Google's OAuth2
        URL url = new URL("https://oauth2.googleapis.com/tokeninfo?id_token=" + token);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        // Check response code
        if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
            response.setStatus(con.getResponseCode());
            return; // Pass the error response back to the client
        }

        // If valid, continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
