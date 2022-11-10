package com.api.blog.auth.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.api.blog.auth.db.entity.TbAuth;
import com.api.blog.auth.db.repository.TbAuthRepository;
import com.api.blog.auth.model.auth.PostGenerateRequestModel;
import com.api.blog.auth.util.TokenUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthControllerTests {

	@Autowired
	private AuthController authController;
	
	@MockBean
	private TbAuthRepository tbAuthRepository;
	
	@MockBean
	private TokenUtil tokenUtil;
	
	@Test
	public void postGenerate() throws Exception {
		PostGenerateRequestModel requestModel = new PostGenerateRequestModel();
		requestModel.setRequestDate("2022-11-07T21:57:35.839000");
		requestModel.setRequestId("1pl53s0ier");
		requestModel.setTbaEmail("admin@mail.com");
		requestModel.setTbaIdLogin("admin@mail.com");
		requestModel.setTbaPassword("123");
		
		TbAuth tbAuth = new TbAuth();
		tbAuth.setTbaEmail("admin@mail.com");
		Optional<TbAuth> optTbAuthByEmail = Optional.of(tbAuth);
		when(tbAuthRepository.findOne(any())).thenReturn(optTbAuthByEmail);
		
		when(tokenUtil.generate(any(), any())).thenReturn("token dummy");
		
		when(tbAuthRepository.save(any())).thenReturn(null);
		
		when(tokenUtil.claims(any())).thenReturn(null);
		
		ResponseEntity<?> responseEntity = (ResponseEntity<?>) authController.postGenerate(requestModel);
		assertEquals(200, responseEntity.getStatusCodeValue());
	}
}
