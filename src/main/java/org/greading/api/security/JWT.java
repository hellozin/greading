package org.greading.api.security;

import static com.auth0.jwt.JWT.require;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.util.Date;

public class JWT {

    private final String issuer;

    private final String clientSecret;

    private final int expirySeconds;

    private final Algorithm algorithm;

    private final JWTVerifier jwtVerifier;

    public JWT(String issuer, String clientSecret, int expirySeconds) {
        this.issuer = issuer;
        this.clientSecret = clientSecret;
        this.expirySeconds = expirySeconds;
        this.algorithm = Algorithm.HMAC512(clientSecret);
        this.jwtVerifier = require(algorithm)
                .withIssuer(issuer)
                .build();
    }

    public String newToken(Claims claims) {
        Date now = new Date();
        JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
        builder.withIssuer(issuer);
        builder.withIssuedAt(now);
        if (expirySeconds > 0) {
            builder.withExpiresAt(new Date(now.getTime() + expirySeconds * 1_000L));
        }
        builder.withClaim("memberKey", claims.memberKey);
        builder.withClaim("username", claims.username);
        builder.withClaim("email", claims.email);
        builder.withArrayClaim("roles", claims.roles);
        return builder.sign(algorithm);
    }

    public String refreshToken(String token) {
        Claims claims = verify(token);
        claims.eraseIat();
        claims.eraseExp();
        return newToken(claims);
    }

    public Claims verify(String token) {
        return new Claims(jwtVerifier.verify(token));
    }

    public static class Claims {
        Long memberKey;
        String username;
        String email;
        String[] roles;
        Date iat;
        Date exp;

        private Claims() {}

        Claims(DecodedJWT decodedJWT) {
            Claim memberKey = decodedJWT.getClaim("memberKey");
            if(!memberKey.isNull())
                this.memberKey = memberKey.asLong();

            Claim memberId = decodedJWT.getClaim("username");
            if (!memberId.isNull())
                this.username = memberId.asString();

            Claim email = decodedJWT.getClaim("email");
            if (!email.isNull())
                this.email = email.asString();

            Claim roles = decodedJWT.getClaim("roles");
            if (!roles.isNull())
                this.roles = roles.asArray(String.class);

            this.iat = decodedJWT.getIssuedAt();
            this.exp = decodedJWT.getExpiresAt();
        }

        public static Claims of(Long memberKey, String username, String email, String[] roles) {
            Claims claims = new Claims();
            claims.memberKey = memberKey;
            claims.username = username;
            claims.email = email;
            claims.roles = roles;
            return claims;
        }

        Long iat() {
            return iat != null ? iat.getTime() : -1;
        }

        Long exp() {
            return exp != null ? exp.getTime() : -1;
        }

        void eraseIat() {
            iat = null;
        }

        void eraseExp() {
            exp = null;
        }

    }
}
