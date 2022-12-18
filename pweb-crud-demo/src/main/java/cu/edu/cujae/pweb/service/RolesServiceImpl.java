package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.RolesDto;
import cu.edu.cujae.pweb.dto.UserDto;
import cu.edu.cujae.pweb.dto.VoterDto;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RolesServiceImpl implements RolesService{
	
	@Autowired
	private RestService restService;

	@Override
	public List<RolesDto> getRoles(){
		List<RolesDto> rolesList = new ArrayList<RolesDto>();
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<RolesDto> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.GET("/api/v1/roles/", params, String.class).getBody();
		    rolesList = apiRestMapper.mapList(response, RolesDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rolesList;
	}

	@Override
	public RolesDto getRolesById(int rolesId) {
		RolesDto roles = null;

		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<RolesDto> apiRestMapper = new ApiRestMapper<>();
		    
		    UriTemplate template = new UriTemplate("/api/v1/roles/{rolesId}");
		    String uri = template.expand(rolesId).toString();
		    String response = (String)restService.GET(uri, params, String.class).getBody();
		    roles = apiRestMapper.mapOne(response, RolesDto.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return roles;
	}

	@Override
	public void createRoles(RolesDto roles) {
		restService.POST("/api/v1/roles/", roles, String.class).getBody();
	}

	@Override
	public void updateRoles(RolesDto roles) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		restService.PUT("/api/v1/roles/", params, roles, String.class).getBody();
	}

	@Override
	public void deleteRoles(int rolesId) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		UriTemplate template = new UriTemplate("/api/v1/roles/{rolesId}");
	    String uri = template.expand(rolesId).toString();
		restService.DELETE(uri, params, String.class, null).getBody();
	}

	@Override
	public String getRolesNameById(int id){
		for(RolesDto roles : getRoles()){
			if(roles.getId() == id) return roles.getRole_name();
		}
		return "None";
	}
	
}
