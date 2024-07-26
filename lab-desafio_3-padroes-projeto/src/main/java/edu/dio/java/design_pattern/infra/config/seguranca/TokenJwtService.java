package edu.dio.java.design_pattern.infra.config.seguranca;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.dio.java.design_pattern.model.domain.entity.Usuario.Usuario;
import io.jsonwebtoken.Jwts;

@Service
public class TokenJwtService {
	
	@Value("${jwt.expiracao}")
	private Long validade;

	private SecretKey chave = Jwts.SIG.HS256.key().build();
	
	public String gerarToken(Usuario usuario) {
		LocalDateTime expiracao = LocalDateTime.now().plusMinutes(validade);
		Date dataExpiracao = java.util.Date.from(
				expiracao
					.atZone(ZoneId.systemDefault())
					.toInstant());
		
		return
			Jwts
				.builder()
				.issuer("DIO Desenvolvimento Java com IA")
				.subject(usuario.getEmail())
				.expiration(dataExpiracao)
				.claim("nome", usuario.getNome())
				.signWith(chave)
				.compact();
	}
	
	public String getSubject(String token) {
		return
			Jwts
				.parser()
				.verifyWith(chave)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
}
