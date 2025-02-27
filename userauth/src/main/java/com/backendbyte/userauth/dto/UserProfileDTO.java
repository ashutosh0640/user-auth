package com.backendbyte.userauth.dto;

import java.sql.Timestamp;
import java.util.Set;

public class UserProfileDTO {
	
	private Long userId;
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String mobileNo;
    private Timestamp lastLoginDate;
    private String image;
    private Set<String> roles;
    
	public UserProfileDTO() {}
	
	public UserProfileDTO(Long userId, String username, String firstName, String middleName, String lastName,
			String email, String mobileNo, Timestamp lastLoginDate, String image, Set<String> roles) {
		super();
		this.userId = userId;
		this.username = username;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNo = mobileNo;
		this.lastLoginDate = lastLoginDate;
		this.image = image;
		this.roles = roles;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Timestamp getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "UserProfileDTO [userId=" + userId + ", username=" + username + ", firstName=" + firstName
				+ ", middleName=" + middleName + ", lastName=" + lastName + ", email=" + email + ", mobileNo="
				+ mobileNo + ", lastLoginDate=" + lastLoginDate + ", image=" + image + ", roles=" + roles + "]";
	}
	
    
    
    

}
