package breakout.vr.BreakoutVR.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTUtil {
    private String secret;
    private String issuer;
    @Getter
    private AuthHelper authHelper = new AuthHelper();

    @Value("breakout")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Value("BreakoutVR")
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    @PostConstruct
    public void setCurrentToken() {
        authHelper.setToken(generateToken());
        System.out.println(authHelper.getToken());
    }

    public String generateToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("admin", true);

        return generateJWTToken(claims);
    }

    private String generateJWTToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean isValidToken(String token) {
        return authHelper.getToken().compareTo(token) == 0;
    }
}
