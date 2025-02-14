package com.ye.task.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {


    /**
     * Step 1 : Generate a JWT token using the user's Details
     * call the overload methos with an empty extraClaim Map
     * @param userDetails
     * @return generated jwt token
     */
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>() , userDetails );
    }

    /**
     * Step 2 : Generate a JWT token using the user's Details and extra claims
     * - set custom claims if provided
     * - uses the username as the subject
     * - defines issue and expiration dates
     * - signs the token using a secret key
     * @param extraClaims
     * @param userDetails
     * @return signed token as a string
     */
    public String generateToken(Map<String , Object> extraClaims , UserDetails userDetails ) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))

                // one day validation
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 ))
                .signWith(getSigningKey() , SignatureAlgorithm.HS256).compact();
    }

    /**
     *  step 3: Get the signing key for JWT
     *  - use a base64-encoded secret key
     *  - converts the key into a key object using base 64 encoding
     * @return signing key
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
        return Keys.hmacShaKeyFor(keyBytes);
    }


    /**
     * step 4 : validate the token
     * - extracts username from the token
     * - checks if the token belongs to the given user
     * - ensures the token is not expired
     *
     * @param token
     * @param userDetails
     * @return true if the token is valid , false otherwise
     */
    public boolean isTokenValid ( String token , UserDetails userDetails ) {
        final String userName = extractUserName(token);
        return ( userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * step 5 : extract the username from the token
     * - uses the extractclaim method to get the subject the fields
     *
     * @param token
     * @return username ( subject ) inside them
     */
    public String extractUserName(String token ) {
        return extractClaim(token , Claims::getSubject);
    }

    /**
     * Step 6: Check if the token is expired.
     * - Retrieves the expiration date and compares it with the current time.
     *
     * @param token The JWT token.
     * @return True if the token is expired, false otherwise.
     */
    private boolean isTokenExpired(String token ) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Step 7: Extract the expiration date from the token.
     * - Uses extractClaim to get the expiration field.
     *
     * @param token The JWT token.
     * @return Expiration date of the token.
     */
    private Date extractExpiration(String token ) {
        return extractClaim(token , Claims::getExpiration);
    }
    /**
     * Step 8: Extract a specific claim from the token.
     * - Uses extractAllClaims to get all claims.
     * - Applies a function to extract the required claim.
     *
     * @param token         The JWT token.
     * @param claimsResolver A function to extract a specific claim.
     * @param <T>           Generic return type.
     * @return Extracted claim value.
     */
    private <T> T extractClaim(String token , Function<Claims , T> claimsResolver ) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Step 9: Extract all claims from the token.
     * - Parses the token using the signing key.
     * - Returns the claims body.
     *
     * @param token The JWT token.
     * @return Claims object containing all token details.
     */
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
