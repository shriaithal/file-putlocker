package edu.sjsu.cloud.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.cloud.dto.CustomerDTO;
import edu.sjsu.cloud.request.LoginRequest;

public class CustomerAuthenticationProvider implements AuthenticationProvider {

	private static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		CustomerDTO customer = getCustomer(username, password);

		if (null == customer) {
			throw new BadCredentialsException("Invalid Username or password");
		}
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("USER"));

		UsernamePasswordAuthenticationToken wrappingAuth = new UsernamePasswordAuthenticationToken(username, password,
				grantedAuthorities);
		return null;
	}

	private CustomerDTO getCustomer(String userName, String password) {
		CustomerDTO customer = null;

		String url = "http://localhost:9090/login/action";
		LoginRequest request = new LoginRequest();
		request.setUserName(userName);
		request.setPassword(password);
		String jsonRequest = null;

		try {
			jsonRequest = mapper.writeValueAsString(request);
		} catch (JsonProcessingException e) {
		}

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(jsonRequest, headers);

		HttpHeaders respHeaders = null;
		HttpStatus statusCode = null;
		try {

			ResponseEntity<CustomerDTO> response = restTemplate.postForEntity(url, entity, CustomerDTO.class);

			respHeaders = response.getHeaders();
			statusCode = response.getStatusCode();
			customer = response.getBody();

		} catch (HttpClientErrorException e) {
			respHeaders = e.getResponseHeaders();
			statusCode = e.getStatusCode();
		}

		return customer;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
