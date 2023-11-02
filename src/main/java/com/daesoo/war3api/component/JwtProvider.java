package com.daesoo.war3api.component;

import com.daesoo.war3api.user.model.dto.UserDto;
import com.daesoo.war3api.user.model.entity.User;
import com.google.gson.Gson;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    private final String baseKey = "seeecretKeyseeecretKeyseeecretKeyseeecretKeyseeecretKeyseeecretKeyseeecretKey!";
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private Key createKey() {
        // signiture에 대한 정보는 Byte array로 구성되어있습니다.
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(baseKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    public String createAccessToken(User user) {
        Map<String, Object> headerMap = new HashMap<String, Object>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");



        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + 1000L * 60 * 60 * 24 * 90);
//        expireTime.setTime(expireTime.getTime() + 1000 * 5);
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("email", user.getEmail());
        claims.put("siteNick", user.getSiteNick());
        claims.put("role", user.getRole());
        claims.put("join_limit", user.getJoinLimit().toString());
        claims.put("gameNick", user.getGameNick());
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        LocalDateTime localDateTime = expireTime.toInstant().atZone(zoneId).toLocalDateTime();
        claims.put("expireTime", localDateTime.atZone(zoneId).toInstant().toEpochMilli());

        JwtBuilder builder = Jwts.builder().setHeader(headerMap).setClaims(claims).setExpiration(expireTime).signWith(createKey(), signatureAlgorithm);

        String result = builder.compact();
        return result;
    }

    public Boolean checkJwt(String jwt) {
        if(jwt == null) {
            return false;
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(baseKey))
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
//            System.out.println("Id : " + claims.get("id"));
//            System.out.println("Name : " + claims.get("name"));
        } catch (ExpiredJwtException e) {
//            e.printStackTrace();
            return false;
        } catch (JwtException e) {
//            e.printStackTrace();
            return false;
        }
        return true;
    }
Gson gson = new Gson();
    public String getUserInfo(String jwt) {
        if(jwt == null) {
            return "not found token";
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(baseKey))
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            UserDto userDto = UserDto.builder()
                    .email((String)claims.get("email"))
                    .siteNick((String)claims.get("siteNick"))
                    .role((String)claims.get("role"))
                    .gameNick((String)claims.get("gameNick"))
//                    .joinLimit((LocalDateTime)claims.get("join_limit"))
                    .expireTime(String.valueOf(claims.get("expireTime")))
                    .build();
            return gson.toJson(userDto);
        } catch (ExpiredJwtException e) {
            return "expired token";
        } catch (JwtException e) {
            return "not found token";
        }
//        return "";
    }
    public String siteNick(String jwt) {
        if(jwt == null) {
            return "not found token";
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(baseKey))
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            return (String)claims.get("siteNick");
        } catch (ExpiredJwtException e) {
            return "expired token";
        } catch (JwtException e) {
            return "not found token";
        }
//        return "";
    }


}
