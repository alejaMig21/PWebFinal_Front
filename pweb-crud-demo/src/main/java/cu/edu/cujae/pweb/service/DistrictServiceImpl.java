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

import cu.edu.cujae.pweb.dto.DistrictDto;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;

/* Esta anotiacioon le indica a spring que esta clase es un servicio y por tanto luego podr� inyectarse en otro lugar usando
 * @Autowired. En estas implementaciones luego se pondraan las llamadas al proyecto backend
 */
@Service
public class DistrictServiceImpl implements DistrictService{

	@Autowired
	private RestService restService;

	@Override
	public List<DistrictDto> getDistricts() {
		
		List<DistrictDto> districtList = new ArrayList<DistrictDto>();
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<DistrictDto> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.GET("/api/v1/districts/", params, String.class).getBody();
		    districtList = apiRestMapper.mapList(response, DistrictDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return districtList;
	}

	@Override
    public DistrictDto getDistrictById(int idDistrict) {
        DistrictDto district = null;

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<DistrictDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/districts/" + "{idDistrict}");
            String uri = template.expand(idDistrict).toString();
            String response = (String) restService.GET(uri, params, String.class).getBody();
            district = apiRestMapper.mapOne(response, DistrictDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return district;
    }

	@Override
    public int getIdByName(String districtName) {
        int idDistrict = 0;

        try {
            String uri = "/api/v1/districts/" + "name/{name}";
            Map<String, String> map = new HashMap<>();
            map.put("name", districtName);

            String response = (String) restService.GETEntity(
                    uri, map,
                    String.class).getBody();

            idDistrict = Integer.parseInt(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idDistrict;
    }

	@Override
	public void createDistrict(DistrictDto district) {
		// TODO Auto-generated method stub
		restService.POST("/api/v1/districts/" + "", district, String.class).getBody();
	}

	@Override
	public void updateDistrict(DistrictDto district) {
        System.out.println("El municipio de la circunscripcion es " + district.getIdMunicipality());
		// TODO Auto-generated method stub
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        restService.PUT("/api/v1/districts/" + "", params, district, String.class).getBody();
	}

	@Override
	public void deleteDistrict(int idDistrict) { // Originalmente era String
		// TODO Auto-generated method stub
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        UriTemplate template = new UriTemplate("/api/v1/districts/" + "{idDistrict}");
        String uri = template.expand(idDistrict).toString();
        restService.DELETE(uri, params, String.class, null).getBody();
	}
	
}
