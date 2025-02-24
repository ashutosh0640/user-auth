package com.backendbyte.userauth.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Profile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	private String mobileNo;
	
	private String email;
	
	private String image;
	
	private Timestamp lastLogin;
	
	@OneToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	public Profile() {}

	public Profile(Long id, String firstName, String middleName, String lastName, String mobileNo, String email,
			String image, Timestamp lastLogin, User user) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.mobileNo = mobileNo;
		this.email = email;
		this.image = image;
		this.lastLogin = lastLogin;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Profile [id=" + id + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", mobileNo=" + mobileNo + ", email=" + email + ", image=" + image + ", lastLogin="
				+ lastLogin + ", user=" + user + "]";
	}

}
