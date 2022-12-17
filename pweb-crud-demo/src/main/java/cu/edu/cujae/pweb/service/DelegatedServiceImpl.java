package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.CollegeDto;
import cu.edu.cujae.pweb.dto.DelegatedDto;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Esta anotiacioon le indica a spring que esta clase es un servicio y por tanto luego podr� inyectarse en otro lugar usando
 * @Autowired. En estas implementaciones luego se pondraan las llamadas al proyecto backend
 */
@Service
public class DelegatedServiceImpl implements DelegatedService{

	@Autowired
	private RestService restService;

	@Override
	public List<DelegatedDto> getDelegateds() {
		
		List<DelegatedDto> delegatedList = new ArrayList<DelegatedDto>();
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<DelegatedDto> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.GET("/api/v1/delegateds/", params, String.class).getBody();
		    delegatedList = apiRestMapper.mapList(response, DelegatedDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return delegatedList;
	}

	@Override
    public DelegatedDto getDelegatedById(int id_delegated) {
        DelegatedDto delegated = null;

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<DelegatedDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/delegateds/" + "{id_delegated}");
            String uri = template.expand(id_delegated).toString();
            String response = (String) restService.GET(uri, params, String.class).getBody();
            delegated = apiRestMapper.mapOne(response, DelegatedDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return delegated;
    }

//	@Override
//    public int getIdByName(String delegatedName) {
//        int idDelegated = 0;
//
//        try {
//            String uri = "/api/v1/delegateds/" + "name/{name}";
//            Map<String, String> map = new HashMap<>();
//            map.put("name", delegatedName);
//
//            String response = (String) restService.GETEntity(
//                    uri, map,
//                    String.class).getBody();
//
//            idDelegated = Integer.parseInt(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return idDelegated;
//    }

	@Override
	public void createDelegated(DelegatedDto delegated) {
		// TODO Auto-generated method stub
		restService.POST("/api/v1/delegateds/" + "", delegated, String.class).getBody();
	}

	@Override
	public void updateDelegated(DelegatedDto delegated) {
		// TODO Auto-generated method stub
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        restService.PUT("/api/v1/delegateds/" + "", params, delegated, String.class).getBody();
	}

	@Override
	public void deleteDelegated(int id_delegated) { // Originalmente era String
		// TODO Auto-generated method stub
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        UriTemplate template = new UriTemplate("/api/v1/delegateds/" + "{id_delegated}");
        String uri = template.expand(id_delegated).toString();
        restService.DELETE(uri, params, String.class, null).getBody();
	}
	
}
