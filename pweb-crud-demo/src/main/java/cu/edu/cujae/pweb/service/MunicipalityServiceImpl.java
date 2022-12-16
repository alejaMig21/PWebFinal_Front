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

import cu.edu.cujae.pweb.dto.MunicipalityDto;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;

/* Esta anotiacioon le indica a spring que esta clase es un servicio y por tanto luego podr� inyectarse en otro lugar usando
 * @Autowired. En estas implementaciones luego se pondraan las llamadas al proyecto backend
 */
@Service
public class MunicipalityServiceImpl implements MunicipalityService {

	@Autowired
	private RestService restService;

	@Override
	public List<MunicipalityDto> getMunicipalitys() {

		List<MunicipalityDto> municipalityList = new ArrayList<MunicipalityDto>();
		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			ApiRestMapper<MunicipalityDto> apiRestMapper = new ApiRestMapper<>();
			String response = (String) restService.GET("/api/v1/municipalitys/", params, String.class).getBody();
			municipalityList = apiRestMapper.mapList(response, MunicipalityDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return municipalityList;
	}

	@Override
	public MunicipalityDto getMunicipalityById(int municipalityId) {
		MunicipalityDto municipality = null;

		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			ApiRestMapper<MunicipalityDto> apiRestMapper = new ApiRestMapper<>();

			UriTemplate template = new UriTemplate("/api/v1/municipalitys/" + "{municipalityId}");
			String uri = template.expand(municipalityId).toString();
			String response = (String) restService.GET(uri, params, String.class).getBody();
			municipality = apiRestMapper.mapOne(response, MunicipalityDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return municipality;
	}

	@Override
	public int getIdByName(String municipalityName) {
		int idMunicipality = 0;

		try {
			String uri = "/api/v1/municipalitys/" + "name/{name}";
			Map<String, String> map = new HashMap<>();
			map.put("name", municipalityName);

			String response = (String) restService.GETEntity(
					uri, map,
					String.class).getBody();

			idMunicipality = Integer.parseInt(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idMunicipality;
	}

	@Override
	public void createMunicipality(MunicipalityDto municipality) {
		// TODO Auto-generated method stub
		System.out.println(municipality.getNameMunicipality());
		restService.POST("/api/v1/municipalitys/", municipality, String.class).getBody();
	}

	@Override
	public void updateMunicipality(MunicipalityDto municipality) {
		// TODO Auto-generated method stub
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		restService.PUT("/api/v1/municipalitys/" + "", params, municipality, String.class).getBody();
	}

	@Override
	public void deleteMunicipality(int municipalityId) { // Originalmente era String
		// TODO Auto-generated method stub
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		UriTemplate template = new UriTemplate("/api/v1/municipalitys/" + "{municipalityId}");
		String uri = template.expand(municipalityId).toString();
		restService.DELETE(uri, params, String.class, null).getBody();
	}

}
