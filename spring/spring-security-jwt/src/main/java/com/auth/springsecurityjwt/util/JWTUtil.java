package com.auth.springsecurityjwt.util;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;

/**
 * JWT
 */
@Component
@Log4j2
public class JWTUtil {

    @Value("${com.auth.springsecurityjwt.secret}")
    private String key;

    public String generateToken(Map<String, Object> valueMap, int days) {

        log.info("💡 generateKey =====> " + key);
        //Header Part
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload Part
        Map<String, Object> payloads = new HashMap<>();
        payloads.putAll(valueMap);
        int time = (10) * days; //유효 시간 (1) - test 1분

        String jwtStr = Jwts.builder()
            .setHeader(headers)
            .setClaims(payloads)
            .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
            .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant()))
            .signWith(SignatureAlgorithm.HS256, key.getBytes())
            .compact();

        return jwtStr;
    }

    public Map<String, Object> validateToken(String token) throws JwtException {

        Map<String, Object> claim = null;

        claim = Jwts.parser()
            .setSigningKey(key.getBytes()) //Set Key
            .parseClaimsJws(token) //파싱 및 검증, 실패시 에러
            .getBody();
        return claim;
    }

}
