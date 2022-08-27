package com.itheima.reggie.common;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Map;

public class JwtUtil {

    public static String createToken(Map claims){
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256,"reggie").compact();
    }

    public static Map parseToken(String token){
        try{
            return Jwts.parser().setSigningKey("reggie").parseClaimsJws(token).getBody();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
