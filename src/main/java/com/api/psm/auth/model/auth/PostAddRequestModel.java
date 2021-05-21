package com.api.psm.auth.model.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.api.psm.auth.model.RequestModel;

public class PostAddRequestModel extends RequestModel {
	@NotEmpty(message = "Email may not be empty")
	@Email(message = "Email should be valid")
	@Size(min = 1, max = 255, message = "Email must be between 1 and 255 characters long")
	private String tbaEmail;

	@NotEmpty(message = "Password may not be empty")
	@Size(min = 1, max = 32, message = "Password must be between 1 and 32 characters long")
	private String tbaPassword;

	@NotEmpty(message = "Member Id may not be empty")
	@Size(min = 1, max = 100, message = "Member Id must be between 1 and 100 characters long")
	private String tbaMemberId;

	@NotEmpty(message = "Id Login may not be empty")
	@Size(min = 1, max = 255, message = "Id Login must be between 1 and 255 characters long")
	private String tbaIdLogin;

	public String getTbaEmail() {
		return tbaEmail;
	}

	public void setTbaEmail(String tbaEmail) {
		this.tbaEmail = tbaEmail;
	}

	public String getTbaPassword() {
		return tbaPassword;
	}

	public void setTbaPassword(String tbaPassword) {
		this.tbaPassword = tbaPassword;
	}

	public String getTbaMemberId() {
		return tbaMemberId;
	}

	public void setTbaMemberId(String tbaMemberId) {
		this.tbaMemberId = tbaMemberId;
	}

	public String getTbaIdLogin() {
		return tbaIdLogin;
	}

	public void setTbaIdLogin(String tbaIdLogin) {
		this.tbaIdLogin = tbaIdLogin;
	}
}
