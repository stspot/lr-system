package msTask.security.jwt;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import msTask.data.entity.Role;
import msTask.data.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

	private static final String SECRET_KEY = "897s8976f87s68s6d57a85dfa85d4f8as76df5sa5df412312234234";

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String generateToken(User user) {
		return generateToken(new HashMap<>(), user);
	}

	public String getUsernameFromJWT(String token) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();

			return claims.getSubject();
		} catch (Exception e) {
			System.out.println("Failed to extract емаил from token: " + e.getMessage());
		}
		return null;
	}

	public String generateToken(Map<String, Object> extraClaims, User user) {

		List<String> userRolesStr = new ArrayList<>();
		for (Role r : user.getRoles()) {
			userRolesStr.add(r.getName());
		}
		extraClaims.put("roles", userRolesStr);
		extraClaims.put("id", user.getId());

		return Jwts.builder().setClaims(extraClaims).setSubject(user.getEmail())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 10800000))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		User u = (User) userDetails;
		final String username = extractUsername(token);
		boolean isUsernameValid = username.equals(u.getEmail());
		boolean isTokenNotExpired = !isTokenExpired(token);
		return isUsernameValid && isTokenNotExpired;
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {

		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
			return true;
		} catch (ExpiredJwtException e) {
			System.out.println("Token has expired.");
		} catch (SignatureException e) {
			System.out.println("Invalid token signature.");
		} catch (Exception e) {
			System.out.println("Token validation failed: " + e.getMessage());
		}
		return false;
	}
}
