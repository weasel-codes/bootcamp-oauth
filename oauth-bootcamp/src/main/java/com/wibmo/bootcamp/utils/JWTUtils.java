package com.wibmo.bootcamp.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wibmo.bootcamp.model.entity.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtils.class);
	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

	private static final String SECRET_KEY = "12345@abcde";

	public String generateAccessToken(UserDetails user) {

		return Jwts.builder().setSubject(user.getPhone() + "#" + user.getEmail() + "#" + user.getPassword())
				.setIssuer("NITIN@TUSHAR").setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
	}

	public boolean validateAccessToken(String token) {

		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException ex) {
			LOGGER.error("JWT expired", ex.getMessage());
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
		} catch (MalformedJwtException ex) {
			LOGGER.error("JWT is invalid", ex);
		} catch (UnsupportedJwtException ex) {
			LOGGER.error("JWT is not supported", ex);
		} catch (SignatureException ex) {
			LOGGER.error("Signature validation failed");
		}

		return false;
	}

	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}

	private Claims parseClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public static void main(String[] args) {
		JWTUtils utils = new JWTUtils();
		System.out.println(SECRET_KEY);

		UserDetails details = new UserDetails("nitin.sharma", "qwer1234", "Nitin Sharma", 7417457165l, "nitin.sharma@wibmo.com");
		String token = utils.generateAccessToken(details);

		System.out.println(token);

		System.out.println(utils.validateAccessToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3NDE3NDU3MTY1I25pdGluLnNoYXJtYUB3aWJtby5jb20jcXdlcjEyMzQiLCJpc3MiOiJOSVRJTkBUVVNIQVIiLCJpYXQiOjE2NjMxMDM0NDksImV4cCI6MTY2MzE4OTg0OX0.LDspav7SmouISI8Nw1ANeSH-X79NVYSlC8Oiz0L-Gg20NgUe6bWvZcxsPf2NSlQM6oTj4-zYKrURhOszgK7kWQ"));
		System.out.println(utils.getSubject(token));

	}
}
