package np.com.csangharsha.chatsapp.config.securities;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import np.com.csangharsha.chatsapp.config.securities.model.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class TokenProvider {

    @Value("${jwt.refreshToken.secretKey}")
    private String refreshTokenSecretKey;

    @Value("${jwt.accessToken.secretKey}")
    private String accessTokenSecretKey;

    @Value("${jwt.refreshToken.expiresIn}")
    private Long refreshTokenExpireInMs;

    @Value("${jwt.accessToken.expiresIn}")
    private Long accessTokenExpireInMs;

    public String generateToken(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpireInMs);
        return Jwts.builder()
                .setSubject(Long.toString(customUserDetails.getId()))
                .setIssuedAt(expiryDate)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = this.accessTokenSecretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateAccessToken(String accessToken)
            throws SignatureException, MalformedJwtException, ExpiredJwtException, UnsupportedOperationException, IllegalArgumentException {
        Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(accessToken);
        return true;
    }
}
