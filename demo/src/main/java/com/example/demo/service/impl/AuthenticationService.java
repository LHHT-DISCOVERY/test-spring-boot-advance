package com.example.demo.service.impl;

import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.request.IntrospectTokenRequest;
import com.example.demo.dto.request.LogoutRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.dto.response.IntrospectResponse;
import com.example.demo.entity.InvalidateToken;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.InvalidateRepository;
import com.example.demo.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    UserRepository userRepository;

    // has created bean in securityConfig file
    PasswordEncoder passwordEncoder;

    InvalidateRepository invalidateRepository;


    @NonFinal
    @Value("${jwt.signKey}")
    protected String SIGNER_KEY;

    public IntrospectResponse introspect(IntrospectTokenRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
//        using again algorithms MACSigner to Sign with key (SIGNER_KEY) previous, take again token
//        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());
//        parse from string token get it to Sign, then using it to verified
//        SignedJWT signedJWT = SignedJWT.parse(token);
//       when has two problem above, next: verified it (it will return true or false) as below
//        var verified = signedJWT.verify(jwsVerifier);
//        check plus token has expired yet ?
//        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean isValue = true;
        try {
            verifyToken(token);
        } catch (AppException e) {
            isValue = false;
        }
        return IntrospectResponse.builder().valid(isValue).build();
    }


    public AuthenticationResponse authenticated(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);
//        authenticate ok => generate token
        var token = generateToken(user);
        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    private String generateToken(User user) {
//        header là Algorithms của token
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
//        cần payload , data trong body gọi là claimSet
//        claim : subject đại diện user login, issuer từ ai thường là domain , issuer time , thời gian hết hạn expire time, hết hạn sau 1 tiếng
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("devTri.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
//        Algorithms Sign token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("can not create token");
            throw new RuntimeException(e.getMessage());
        }
    }


    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
//        add custom prefix "ROLE_" while build token to declare between ROLE and PERMISSION
        if (!user.getRoles().isEmpty()) {
            user.getRoles().forEach(role -> {
                        stringJoiner.add("ROLE_" + role.getName());
                        if (!CollectionUtils.isEmpty(role.getPermissions()))
                            role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
                    }
            );

        }
        return stringJoiner.toString();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        var signToken = verifyToken(request.getToken());
        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
        InvalidateToken invalidateToken = InvalidateToken.builder().id(jit).expiryTime(expiryTime).build();
        invalidateRepository.save(invalidateToken);
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        var verified = signedJWT.verify(jwsVerifier);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        if (!verified && expiryTime.after(new Date())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        // if logout -> token will exist in table InvalidateToken Entity -> contain tokens invalid -> UNAUTHENTICATED
        if (invalidateRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.PASSWORD_INVALID);
        }
        return signedJWT;
    }
}
