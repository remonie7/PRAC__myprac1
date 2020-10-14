package net.myprac1.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User extends AbstractEntity {

	@Column(nullable=false, length=20, unique=true)
	@JsonProperty
	private String userId;
	
	@JsonIgnore
	private String userPassword;
	
	@JsonProperty
	private String userName;
	
	@JsonProperty
	private String userEmail;
	
	

	


	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public boolean matchPassword(String newPassword) {
		if(newPassword==null) {
			return false;
		}
		return newPassword.equals(userPassword);
	}
	


	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public void update(User updateUser) {
		// TODO Auto-generated method stub
		this.userPassword = updateUser.userPassword;
		this.userName = updateUser.userName;
		this.userEmail = updateUser.userEmail;
	}
	
	public boolean matchId(Long newId) {
		if(newId==null) {
			return false;
		}
		return newId.equals(getId());
	}
	


	@Override
	public String toString() {
		return "User [" + super.toString() + ", userPassword=" + userPassword + ", userName=" + userName + ", userEmail="
				+ userEmail + "]";
	}



}
