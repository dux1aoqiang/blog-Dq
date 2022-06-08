package com.dq.blog;

import io.jsonwebtoken.*;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @project: blog_parent
 * @ClassName: JwtTest
 * @author: dq
 * @creat: 2022/5/17 21:05
 */
public class JwtTest {

    private String accessKey;
    @Test
    public void JwtTest(){
            JwtBuilder jwtBuilder = Jwts.builder();
            String security = "Dq333";
            String jwtToken = jwtBuilder
                    //设置头信息
                    .setHeaderParam("type","JWT")
                    .setHeaderParam("algorithm","SH256")
                    //载荷
                    .claim("username","dq")
                    .claim("role","root")
                    .setSubject("hello World")
                    .setExpiration(new Date(System.currentTimeMillis()+24*1000*60*60))
                    .setId(UUID.randomUUID().toString())
                    .signWith(SignatureAlgorithm.HS256,security)
                    .compact();
        System.out.println(jwtToken);
    }

    @Test
    public void solve(){
        final String JwtName = "eyJ0eXBlIjoiSldUIiwiYWxnb3JpdGhtIjoiU0gyNTYiLCJhbGciOiJub25lIn0.eyJ1c2VybmFtZSI6ImRxIiwicm9sZSI6InJvb3QiLCJzdWIiOiJoZWxsbyBXb3JsZCIsImV4cCI6MTY1Mjg3OTg4NX0.";
        JwtParser jwtParser = Jwts.parser();
        //通过密钥对token解析
        Jwt<Header, Claims> claimsJwt = jwtParser.setSigningKey("Dq333").parseClaimsJwt(JwtName);
        Claims JwtBody = claimsJwt.getBody();
        System.out.println(new DateTime(JwtBody.getExpiration()).toString("yyyy-MM-dd HH:ss:mm"));
    }


}
