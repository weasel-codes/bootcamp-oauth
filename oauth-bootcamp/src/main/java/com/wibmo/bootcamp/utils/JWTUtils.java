package com.wibmo.bootcamp.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wibmo.bootcamp.constant.Constants;
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

	public String generateAccessToken(UserDetails user) {

		LOGGER.info("Encrypting : " + user);
		String token = Jwts.builder().setSubject(user.getEmail() + "#" + user.getPassword()).setIssuer(Constants.ISSUER)
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + Constants.EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, Constants.SECRET_KEY).compact();

//		System.out.println(Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject() 
//				+ " : " + Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getIssuer() 
//				+ " : " + Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration());

		return token;
	}

	public boolean validateAccessToken(String token) throws ExpiredJwtException {

		try {
			Jwts.parser().setSigningKey(Constants.SECRET_KEY).parseClaimsJws(token);
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

	public String getSubject(String token) throws ExpiredJwtException {
		return parseClaims(token).getSubject();
	}

	private Claims parseClaims(String token) throws ExpiredJwtException {
		return Jwts.parser().setSigningKey(Constants.SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public boolean isExpired(String token) throws ExpiredJwtException {
		if (Jwts.parser().setSigningKey(Constants.SECRET_KEY).parseClaimsJws(token).getBody().getExpiration().before(new Date()))
			return true;
		else
			return false;
	}

//	public static void main(String[] args) throws InterruptedException {
//		JWTUtils utils = new JWTUtils();
//		System.out.println(Constants.SECRET_KEY);
//
//		UserDetails details = new UserDetails("nitin.sharma", 
//				"qwer1234", 
//				"Nitin Sharma", 
//				7417457165l,
//				"nitin.sharma@wibmo.com", 
//				null);
//		String token = utils.generateAccessToken(details);
//		details.setJwt_token(token);
//
//		System.out.println("ISExpired? : " + utils.isExpired(token));
//		Thread.sleep(5000);
//		System.out.println("ISExpired? : " + utils.isExpired(token));
//	}
}
