package com.backendbyte.userauth.dto;

public class RoleDTO {
	
	private int id;
	private String roleName;
	
	public RoleDTO() {}
	
	public RoleDTO(int id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	

	@Override
	public String toString() {
		return "RoleDTO [id=" + id + ", roleName=" + roleName + "]";
	}
}
