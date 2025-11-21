package atlix.config.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        System.out.println("=== JWT FILTER ===");
        System.out.println("Request URL: " + request.getRequestURI());
        System.out.println("Authorization Header: " + authHeader);

        // Si no hay header o no es Bearer, continuar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No Bearer token found, skipping filter");
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);

        System.out.println("Token extracted: '" + token + "'");
        System.out.println("Token length: " + token.length());
        System.out.println("Token is literal 'null': " + "null".equals(token));
        System.out.println("Token is literal 'undefined': " + "undefined".equals(token));

        // Si el token es literalmente "null" o "undefined", ignorar la autenticación
        if ("null".equals(token) || "undefined".equals(token)) {
            System.out.println("Token is literal 'null' or 'undefined', skipping authentication");
            filterChain.doFilter(request, response);
            return;
        }

        // Si el token está vacío, ignorar
        if (token.trim().isEmpty()) {
            System.out.println("Token is empty, skipping authentication");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String username = jwtService.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(token, userDetails.getUsername())) {
                    var authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Authentication successful for user: " + username);
                }
            }
        } catch (Exception e) {
            System.out.println("JWT Authentication failed: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}

//08b0c233-3de2-457d-ae7b-8591a6b641a7