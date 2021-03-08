package br.com.alura.api.Forum.Alura.configuration.security;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.api.Forum.Alura.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class TokenService {

	@Value("${forum.jwt.expiration")
	private String expiration;

	@Value("${forum.jwt.secret")
	private String secret;

	public String gerarToken(Authentication authenticaion) {
		Usuario logado = (Usuario) authenticaion.getPrincipal();
		Date hoje = new Date(0);
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		return Jwts.builder().setIssuer("API do form da Alura").setSubject(logado.getId().toString()).setIssuedAt(hoje)
				.setExpiration(dataExpiracao).signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secret).compact();
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return false;
		} catch(Exception e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long .parseLong(claims.getSubject());
	}

}
