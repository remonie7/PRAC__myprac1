package net.myprac1.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false, length=20, unique=true)
	private String userId;
	

	private String userPassword;
	private String userName;
	private String userEmail;
	
	

	
	public boolean matchId(Long newId) {
		if(newId==null) {
			return false;
		}
		return newId.equals(id);
	}

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

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userPassword=" + userPassword + ", userName=" + userName + ", userEmail="
				+ userEmail + "]";
	}



}
