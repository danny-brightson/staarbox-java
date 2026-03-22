package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
public class AuthFilter implements Filter {

	@Autowired
    private JwtUtil jwtTokenUtil;

	 @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		 System.out.println("AuthFilter invoked"); 
        HttpServletRequest req = (HttpServletRequest) request;
        String authHeader = req.getHeader("Authorization");
        HttpServletResponse res = (HttpServletResponse) response;
        // ✅ Let preflight (OPTIONS) requests pass through
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
            res.setHeader("Access-Control-Allow-Credentials", "true");
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        
        String path = req.getRequestURI();
        // Skip token validation for public endpoints
        if (path.equals("/api/verify-otp") || path.equals("/api/refresh") || path.equals("/api/login") || path.equals("/api/checkout")) {
            try {
				chain.doFilter(request, response);
			} catch (java.io.IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtTokenUtil.validateToken(token)) {
                try {
					chain.doFilter(request, response);
				} catch (java.io.IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                return;
            }
        }

        try {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

