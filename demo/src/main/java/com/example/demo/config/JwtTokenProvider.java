package com.example.demo.config;

import com.example.demo.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
//step 1
@Component
public class JwtTokenProvider implements Serializable  {

    private static final long serialVersionUID = 2569800841756370596L;

    private String secretKey = "huutri";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    private long validityInMillSeconds = 2 * 60 * 60;

    public String createToken(String username, Role role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", role);

        Date now = new Date();
        return Jwts.builder().setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityInMillSeconds))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();

    }

    @Autowired
    private UserDetailsService userDetailsService;

    public Authentication getAuthentication(String token) {
//        encoder jwt get information about username was contained in body of payload
//        param is secretKey to decode, analyze string about token param
        String username = Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token).getBody().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        return information object include : username , password , role
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token).getBody();
        return true;
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }


}
