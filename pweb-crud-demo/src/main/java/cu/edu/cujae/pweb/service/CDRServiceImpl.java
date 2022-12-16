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

import cu.edu.cujae.pweb.dto.CDRDto;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;

import java.util.Map;

/* Esta anotiacioon le indica a spring que esta clase es un servicio y por tanto luego podr� inyectarse en otro lugar usando
 * @Autowired. En estas implementaciones luego se pondraan las llamadas al proyecto backend
 */
@Service
public class CDRServiceImpl implements CDRService{

	@Autowired
	private RestService restService;

	@Override
	public List<CDRDto> getCDRs() {
		
		List<CDRDto> cdrList = new ArrayList<CDRDto>();
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<CDRDto> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.GET("/api/v1/cdrs/", params, String.class).getBody();
		    cdrList = apiRestMapper.mapList(response, CDRDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cdrList;
	}

	// @Override
	// public CDRDto getCDRById(int userId) { // Originalmente era String
	// 	return getCDRs().stream().filter(r -> r.getId().equals(userId)).findFirst().get();
	// }

	@Override
    public CDRDto getCDRById(int idCDR) {
        CDRDto cdr = null;

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<CDRDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/cdrs/" + "{idCDR}");
            String uri = template.expand(idCDR).toString();
            String response = (String) restService.GET(uri, params, String.class).getBody();
            cdr = apiRestMapper.mapOne(response, CDRDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cdr;
    }

	@Override
    public int getIdByName(String cdrName) {
        int idCdr = 0;

        try {
            String uri = "/api/v1/cdrs/" + "name/{name}";
            Map<String, String> map = new HashMap<>();
            map.put("name", cdrName);

            String response = (String) restService.GETEntity(
                    uri, map,
                    String.class).getBody();

            idCdr = Integer.parseInt(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idCdr;
    }

	@Override
	public void createCDR(CDRDto cdr) {
		// TODO Auto-generated method stub
		restService.POST("/api/v1/cdrs/" + "", cdr, String.class).getBody();
	}

	@Override
	public void updateCDR(CDRDto cdr) {
		// TODO Auto-generated method stub
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        restService.PUT("/api/v1/cdrs/" + "", params, cdr, String.class).getBody();
	}

	@Override
	public void deleteCDR(int idCDR) { // Originalmente era String
		// TODO Auto-generated method stub
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        UriTemplate template = new UriTemplate("/api/v1/cdrs/" + "{idCDR}");
        String uri = template.expand(idCDR).toString();
        restService.DELETE(uri, params, String.class, null).getBody();
	}
	
}
