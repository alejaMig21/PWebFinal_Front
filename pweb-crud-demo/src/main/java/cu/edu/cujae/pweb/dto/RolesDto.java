package cu.edu.cujae.pweb.dto;

public class RolesDto {
	private int id;
	private String role_name;
	private String description;

	public RolesDto() {
		super();
	}

	public RolesDto(int id_user, String user_name, String full_name, String user_password, String email,
                    int roles) {
		super();
		this.id = id_user;
		this.role_name = user_name;
		this.description = full_name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
