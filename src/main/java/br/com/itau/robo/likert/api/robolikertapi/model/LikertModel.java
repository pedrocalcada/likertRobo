package br.com.itau.robo.likert.api.robolikertapi.model;

import org.springframework.data.annotation.Id;

public class LikertModel {

	@Id
	private String id;
	private String email;

	public LikertModel(String email) {
		super();
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
