package com.api.psm.auth.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.api.psm.auth.db.entity.TbAuth;
import com.api.psm.auth.db.repository.TbAuthRepository;
import com.api.psm.auth.model.auth.GetInvalidateRequestModel;
import com.api.psm.auth.model.auth.GetInvalidateResponseModel;
import com.api.psm.auth.model.auth.PostAddRequestModel;
import com.api.psm.auth.model.auth.PostAddResponseModel;
import com.api.psm.auth.model.auth.PostCheckRequestModel;
import com.api.psm.auth.model.auth.PostCheckResponseModel;
import com.api.psm.auth.model.auth.PostGenerateRequestModel;
import com.api.psm.auth.model.auth.PostGenerateResponseModel;
import com.api.psm.auth.model.auth.PutUpdateRequestModel;
import com.api.psm.auth.model.auth.PutUpdateResponseModel;
import com.api.psm.auth.util.SimpleMapper;
import com.api.psm.auth.util.TokenUtil;

import io.jsonwebtoken.Claims;

@Service
public class AuthService {
	
	private TokenUtil tokenUtil = new TokenUtil();
	
	@Autowired
	private Environment env;
	
	@Autowired
	private TbAuthRepository tbAuthRepository;
	
	public PutUpdateResponseModel putUpdate(PutUpdateRequestModel requestModel) {
		PutUpdateResponseModel responseModel = new PutUpdateResponseModel(requestModel);
		
		TbAuth exampleTbAuth = new TbAuth();
		exampleTbAuth.setTbaEmail(requestModel.getTbaEmail());
		exampleTbAuth.setTbaPassword(requestModel.getTbaPassword());
		exampleTbAuth.setTbaStatus(TbAuth.statusActive);
		Optional<TbAuth> optTbAuth = tbAuthRepository.findOne(Example.of(exampleTbAuth));
		
		optTbAuth.ifPresentOrElse(tbAuth -> {
			SimpleMapper simpleMapper = new SimpleMapper();
			tbAuth = (TbAuth) simpleMapper.assign(requestModel, tbAuth);
			
			tbAuth.setTbaUpdateDate(new Date());
			tbAuth.setTbaUpdateId(0);
			
			if (requestModel.getNewPassword() != null) {
				tbAuth.setTbaPassword(requestModel.getNewPassword());
			}

			responseModel.setStatus("200");
			responseModel.setMessage("Auth updated");
		}, () -> {
			responseModel.setStatus("404");
			responseModel.setMessage("Not found");
		});		
		
		
		return responseModel;
	}
	
	public PostAddResponseModel postAdd(PostAddRequestModel requestModel) {
		PostAddResponseModel responseModel = new PostAddResponseModel(requestModel);
		
		TbAuth exampleTbAuth = new TbAuth();
		exampleTbAuth.setTbaEmail(requestModel.getTbaEmail());
		Optional<TbAuth> optTbAuth = tbAuthRepository.findOne(Example.of(exampleTbAuth));
		optTbAuth.ifPresentOrElse(tbUser -> {
			responseModel.setStatus("208");
			responseModel.setMessage("Email already exists");
		}, () -> {
			TbAuth tbAuth = new TbAuth();
			SimpleMapper simpleMapper = new SimpleMapper();
			tbAuth = (TbAuth) simpleMapper.assign(requestModel, tbAuth);
			
			tbAuth.setTbaStatus(TbAuth.statusActive);
			tbAuth.setTbaCreateDate(new Date());
			tbAuth.setTbaCreateId(0);
			tbAuthRepository.save(tbAuth);
			
			responseModel.setStatus("200");
			responseModel.setMessage("Email added");
		});		
		
		
		return responseModel;
	}
	
	public PostGenerateResponseModel postGenerate(PostGenerateRequestModel requestModel) {
		PostGenerateResponseModel responseModel = new PostGenerateResponseModel(requestModel);
		
		TbAuth exampleTbAuthByEMail = new TbAuth();
		exampleTbAuthByEMail.setTbaEmail(requestModel.getTbaEmail());
		exampleTbAuthByEMail.setTbaPassword(requestModel.getTbaPassword());
		exampleTbAuthByEMail.setTbaStatus(TbAuth.statusActive);
		Optional<TbAuth> optTbAuthByEmail = tbAuthRepository.findOne(Example.of(exampleTbAuthByEMail));
		
		optTbAuthByEmail.ifPresentOrElse(tbAuth -> {
			String token = tokenUtil.generate(optTbAuthByEmail.get().getTbaEmail(), new String[] {
					env.getProperty("services.rest.member.url"),
					env.getProperty("services.rest.payroll.url")
			});
			
			tbAuth.setTbaTokenSalt(TokenUtil.keyMap.get(tbAuth.getTbaEmail()));
			
			try {
				requestModel.setEmail(optTbAuthByEmail.get().getTbaEmail());
				requestModel.setToken(token);
				Claims claims = tokenUtil.claims(requestModel);
				responseModel.setClaims(claims);
				
				responseModel.setToken(token);
				responseModel.setStatus("200");
				responseModel.setMessage("Auth generated");
			} catch (Exception e) {
				responseModel.setStatus("500");
				responseModel.setError(e.getMessage());
			}
		}, () -> {
			TbAuth exampleTbAuthByIdLogin = new TbAuth();
			exampleTbAuthByIdLogin.setTbaIdLogin(requestModel.getTbaIdLogin());
			exampleTbAuthByIdLogin.setTbaPassword(requestModel.getTbaPassword());
			exampleTbAuthByIdLogin.setTbaStatus(TbAuth.statusActive);
			Optional<TbAuth> optTbAuthByIdLogin = tbAuthRepository.findOne(Example.of(exampleTbAuthByIdLogin));
			
			optTbAuthByIdLogin.ifPresentOrElse(tbAuth -> {
				String token = tokenUtil.generate(optTbAuthByIdLogin.get().getTbaEmail(), new String[] {
						env.getProperty("services.rest.member.url")
				});
				
				tbAuth.setTbaTokenSalt(TokenUtil.keyMap.get(tbAuth.getTbaEmail()));
				
				try {
					requestModel.setEmail(optTbAuthByIdLogin.get().getTbaEmail());
					requestModel.setToken(token);
					Claims claims = tokenUtil.claims(requestModel);
					responseModel.setClaims(claims);
					
					responseModel.setToken(token);
					responseModel.setStatus("200");
					responseModel.setMessage("Auth generated");
				} catch (Exception e) {
					responseModel.setStatus("500");
					responseModel.setError(e.getMessage());
				}
			}, () -> {
				responseModel.setStatus("401");
				responseModel.setError("Invalid login");
			});
		});
		
		return responseModel;
	}
	
	public PostCheckResponseModel postCheck(PostCheckRequestModel requestModel) {
		PostCheckResponseModel responseModel = new PostCheckResponseModel(requestModel);
		
		try {
			requestModel.setEmail(requestModel.getTbaEmail());
			requestModel.setToken(requestModel.getTbaToken());
			Claims claims = tokenUtil.claims(requestModel);
			responseModel.setClaims(claims);
			
			responseModel.setStatus("200");
			responseModel.setMessage("Auth checked");
		} catch (Exception e) {
			responseModel.setStatus("500");
			responseModel.setError(e.getMessage());
		}
		
		return responseModel;
	}
	
	public GetInvalidateResponseModel getInvalidate(GetInvalidateRequestModel requestModel) throws Exception {
		GetInvalidateResponseModel responseModel = new GetInvalidateResponseModel(requestModel);
		
		tokenUtil.claims(requestModel);
		
		TbAuth exampleTbAuth = new TbAuth();
		exampleTbAuth.setTbaEmail(requestModel.getEmail());
		exampleTbAuth.setTbaStatus(TbAuth.statusActive);
		Optional<TbAuth> optTbAuth = tbAuthRepository.findOne(Example.of(exampleTbAuth));
		
		optTbAuth.ifPresentOrElse(tbAuth -> {
			tokenUtil.invalidate(tbAuth.getTbaEmail(), new String[] {
					env.getProperty("services.rest.member.url")
			});
			
			responseModel.setStatus("200");
			responseModel.setMessage("Auth invalidated");
		}, () -> {
			responseModel.setStatus("404");
			responseModel.setMessage("Not found");
		});
		
		return responseModel;
	}
}
