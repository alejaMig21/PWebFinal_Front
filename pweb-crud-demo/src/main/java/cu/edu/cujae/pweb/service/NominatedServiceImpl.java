package cu.edu.cujae.pweb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import cu.edu.cujae.pweb.dto.NominatedDto;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;

/* Esta anotiacioon le indica a spring que esta clase es un servicio y por tanto luego podr� inyectarse en otro lugar usando
 * @Autowired. En estas implementaciones luego se pondraan las llamadas al proyecto backend
 */
@Service
public class NominatedServiceImpl implements NominatedService{

	@Autowired
	private RestService restService;

	@Override
	public List<NominatedDto> getNominateds() {
		
		List<NominatedDto> nominatedList = new ArrayList<NominatedDto>();
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<NominatedDto> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.GET("/api/v1/nominateds/" + "", params, String.class).getBody();
		    nominatedList = apiRestMapper.mapList(response, NominatedDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nominatedList;
	}

	@Override
    public NominatedDto getNominatedById(int idNominated) {
        NominatedDto nominated = null;

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<NominatedDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/nominateds/" + "{idNominated}");
            String uri = template.expand(idNominated).toString();
            String response = (String) restService.GET(uri, params, String.class).getBody();
            nominated = apiRestMapper.mapOne(response, NominatedDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nominated;
    }

	@Override
    public int getIdByName(String nominatedName) {
        NominatedDto nominated = null;

        try {
            String uri = "/api/v1/nominateds/" + "name/{name}";
            Map<String, String> map = new HashMap<>();
            map.put("name", nominatedName);

            String response = (String) restService.GETEntity(
                    uri, map,
                    String.class).getBody();

            ApiRestMapper<NominatedDto> apiRestMapper = new ApiRestMapper<>();
            nominated = apiRestMapper.mapOne(response, NominatedDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nominated.getId();
    }

	@Override
	public void insert(NominatedDto nominated) {
		// TODO Auto-generated method stub
		restService.POST("/api/v1/nominateds/" + "", nominated, String.class).getBody();
	}

	@Override
	public void update(NominatedDto nominated) {
		// TODO Auto-generated method stub
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        restService.PUT("/api/v1/nominateds/" + "", params, nominated, String.class).getBody();
	}

	@Override
	public void delete(int idNominated) { // Originalmente era String
		// TODO Auto-generated method stub
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        UriTemplate template = new UriTemplate("/api/v1/nominateds/" + "{idNominated}");
        String uri = template.expand(idNominated).toString();
        restService.DELETE(uri, params, String.class, null).getBody();
	}
	
}
