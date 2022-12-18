package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.RolesDto;

import java.util.List;

public interface RolesService {
	List<RolesDto> getRoles();
	RolesDto getRolesById(int voterId);
	void createRoles(RolesDto roles);
	void updateRoles(RolesDto roles);
	void deleteRoles(int id);
	String getRolesNameById(int id);
}