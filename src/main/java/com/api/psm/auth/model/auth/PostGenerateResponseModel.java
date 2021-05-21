package com.api.psm.auth.model.auth;

import com.api.psm.auth.model.ResponseModel;

import io.jsonwebtoken.Claims;

public class PostGenerateResponseModel extends ResponseModel {

	public PostGenerateResponseModel(PostGenerateRequestModel requestModel) {
		super(requestModel);
	}

	private String token;
	
	private Claims claims;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Claims getClaims() {
		return claims;
	}

	public void setClaims(Claims claims) {
		this.claims = claims;
	}
}
