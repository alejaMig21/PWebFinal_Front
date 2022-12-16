package cu.edu.cujae.pweb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import cu.edu.cujae.pweb.dto.CollegeDto;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;

import java.util.Map;

/* Esta anotiacioon le indica a spring que esta clase es un servicio y por tanto luego podr� inyectarse en otro lugar usando
 * @Autowired. En estas implementaciones luego se pondraan las llamadas al proyecto backend
 */
@Service
public class CollegeServiceImpl implements CollegeService{

	@Autowired
	private RestService restService;

	@Override
	public List<CollegeDto> getColleges() {
		
		List<CollegeDto> collegeList = new ArrayList<CollegeDto>();
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<CollegeDto> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.GET("/api/v1/colleges/", params, String.class).getBody();
		    collegeList = apiRestMapper.mapList(response, CollegeDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return collegeList;
	}

	@Override
    public CollegeDto getCollegeById(int id_college) {
        CollegeDto college = null;

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<CollegeDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/colleges/" + "{id_college}");
            String uri = template.expand(id_college).toString();
            String response = (String) restService.GET(uri, params, String.class).getBody();
            college = apiRestMapper.mapOne(response, CollegeDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return college;
    }

	@Override
    public int getIdByName(String collegeName) {
        int idCollege = 0;

        try {
            String uri = "/api/v1/colleges/" + "name/{name}";
            Map<String, String> map = new HashMap<>();
            map.put("name", collegeName);

            String response = (String) restService.GETEntity(
                    uri, map,
                    String.class).getBody();

            idCollege = Integer.parseInt(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idCollege;
    }

	@Override
	public void createCollege(CollegeDto college) {
		// TODO Auto-generated method stub
		restService.POST("/api/v1/colleges/" + "", college, String.class).getBody();
	}

	@Override
	public void updateCollege(CollegeDto college) {
		// TODO Auto-generated method stub
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        restService.PUT("/api/v1/colleges/" + "", params, college, String.class).getBody();
	}

	@Override
	public void deleteCollege(int id_college) { // Originalmente era String
		// TODO Auto-generated method stub
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        UriTemplate template = new UriTemplate("/api/v1/colleges/" + "{id_college}");
        String uri = template.expand(id_college).toString();
        restService.DELETE(uri, params, String.class, null).getBody();
	}
	
}
