package msTask.service;

import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import msTask.data.entity.User;

public interface JwtService {

	String extractUsername(String token);

	<T> T extractClaim(String token, Function<Claims, T> claimsResolver);

	String generateToken(User user);

	String generateToken(Map<String, Object> extraClaims, User user);

	boolean isTokenValid(String token, UserDetails userDetails);

}
