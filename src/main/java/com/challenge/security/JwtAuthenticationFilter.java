package com.challenge.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		AuthCredentials authCredentials = new AuthCredentials();

		try {
			authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
				authCredentials.getEmail(), authCredentials.getPassword(), Collections.emptyList());
		return getAuthenticationManager().authenticate(usernamePAT);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		UserDetailsImpl userDetail = (UserDetailsImpl) authResult.getPrincipal();
		String token = TokenUtils.createToken(userDetail.getNombre(), userDetail.getUsername());	
		String jsonToken = generarJson(token);

		// Obtener el objeto PrintWriter del HttpServletResponse
		PrintWriter writer = response.getWriter();

		// Escribir la cadena de texto JSON en el objeto PrintWriter
		writer.print(jsonToken);
		writer.flush();

		// Establecer el tipo de contenido de la respuesta como "application/json"
		response.setContentType("application/json");

		
		super.successfulAuthentication(request, response, chain, authResult);
	}

	private String generarJson(String token) {
		// Crear un objeto ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();

		// Crear un objeto Java que deseas convertir a JSON
		JsonToken tokenObj = JsonToken.builder()
				.token(token)
				.build();

		// Convertir el objeto Java en una cadena JSON
		String json = "";
		try {
		    json = objectMapper.writeValueAsString(tokenObj);
		} catch (JsonProcessingException e) {
		    e.printStackTrace();
		}
		return json;
	}

}
