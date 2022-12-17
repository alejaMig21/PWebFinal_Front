package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.DelegatedDto;
import cu.edu.cujae.pweb.dto.ElectoralProcessDto;
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

/* Esta anotiacioon le indica a spring que esta clase es un servicio y por tanto luego podr� inyectarse en otro lugar usando
 * @Autowired. En estas implementaciones luego se pondraan las llamadas al proyecto backend
 */
@Service
public class ElectoralProcessServiceImpl implements ElectoralProcessService{

	@Autowired
	private RestService restService;

	@Override
	public List<ElectoralProcessDto> listElectoralProcess() {
		
		List<ElectoralProcessDto> electoralProcessList = new ArrayList<ElectoralProcessDto>();
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<ElectoralProcessDto> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.GET("/api/v1/electoralprcs/", params, String.class).getBody();
		    electoralProcessList = apiRestMapper.mapList(response, ElectoralProcessDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return electoralProcessList;
	}

	@Override
    public ElectoralProcessDto getElectoralProcessById(int id_electoral_process) {
		ElectoralProcessDto eprocess = null;

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<ElectoralProcessDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/electoralprcs/" + "{id_electoral_process}");
            String uri = template.expand(id_electoral_process).toString();
            String response = (String) restService.GET(uri, params, String.class).getBody();
            eprocess = apiRestMapper.mapOne(response, ElectoralProcessDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eprocess;
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
	public void createElectoralProcess(ElectoralProcessDto eprocess) {
		// TODO Auto-generated method stub
		restService.POST("/api/v1/electoralprcs/" + "", eprocess, String.class).getBody();
	}

	@Override
	public void updateElectoralProcess(ElectoralProcessDto eprocess) {
		// TODO Auto-generated method stub
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        restService.PUT("/api/v1/electoralprcs/" + "", params, eprocess, String.class).getBody();
	}

	@Override
	public void deleteElectoralProcess(int id_electoral_process) { // Originalmente era String
		// TODO Auto-generated method stub
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        UriTemplate template = new UriTemplate("/api/v1/electoralprcs/" + "{id_electoral_process}");
        String uri = template.expand(id_electoral_process).toString();
        restService.DELETE(uri, params, String.class, null).getBody();
	}
	
}
