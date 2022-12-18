package cu.edu.cujae.pweb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cu.edu.cujae.pweb.dto.*;
import org.jetbrains.annotations.NotNull;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;

/* Esta anotiacioon le indica a spring que esta clase es un servicio y por tanto luego podr� inyectarse en otro lugar usando
 * @Autowired. En estas implementaciones luego se pondraan las llamadas al proyecto backend
 */
@Service
public class MunicipalityServiceImpl implements MunicipalityService {

	@Autowired
	private RestService restService;
	@Autowired
	private ElectoralProcessService electoralProcessService;

	public ElectoralProcessService getElectoralProcessService() {
		return electoralProcessService;
	}

	public void setElectoralProcessService(ElectoralProcessService electoralProcessService) {
		this.electoralProcessService = electoralProcessService;
	}

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
	public void createMunicipality(@NotNull MunicipalityDto municipality) {
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

	@Override
	public String getMunicipalityNameById(int id){
		for(MunicipalityDto municipality : getMunicipalitys()){
			if(municipality.getCodMun() == id) return municipality.getNameMunicipality();
		}
		return "None";
	}

	@Autowired
	private NominatedService nominatedService;
	@Autowired
	private VoterService voterService;
	@Autowired
	private CDRService cdrService;
	@Autowired
	private CollegeService collegeService;
	@Autowired
	private DistrictService districtService;

	@Override
	public int totalNominateds(int id_municipality){
		int total = 0;
		for(NominatedDto nominated : nominatedService.getNominateds()){
			for(VoterDto voter : voterService.getVoters()){
				if(voter.getId_voter() == nominated.getId_voter()){
					for(CDRDto cdr : cdrService.getCDRs()){
						if(voter.getCdr() == cdr.getId_cdr()){
							for(CollegeDto college : collegeService.getColleges()){
								if(cdr.getCollege() == college.getIdCollege()){
									for(DistrictDto district : districtService.getDistricts()){
										if(college.getDistrict() == district.getCodDis()){
											if(district.getIdMunicipality() == id_municipality){
												total++;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return total;
	}

	@Autowired
	private DelegatedService delegatedService;
	@Override
	public String secondRound(int id_municipality){
		int newMinimun = 0;
		int totalSimilar = 0; // Contador de votos similares
		int idDelegated = 0; // Si encuentra un nuevo minimo recoge al posible delegado
		int idDistrict = 0; // Si encuentra posible delegado registra la circunscripcion

		for(NominatedDto nominated : nominatedService.getNominateds()){
			for(VoterDto voter : voterService.getVoters()){
				if(voter.getId_voter() == nominated.getId_voter()){
					for(CDRDto cdr : cdrService.getCDRs()){
						if(voter.getCdr() == cdr.getId_cdr()){
							for(CollegeDto college : collegeService.getColleges()){
								if(cdr.getCollege() == college.getIdCollege()){
									for(DistrictDto district : districtService.getDistricts()){
										if(college.getDistrict() == district.getCodDis()){
											if(district.getIdMunicipality() == id_municipality){
												if(nominated.getCant_vote() > newMinimun){ // Encuentra un nuevo minimo
													newMinimun = nominated.getCant_vote(); // Lo usa como referencia
													totalSimilar = 0;
													idDelegated = nominated.getId();
													idDistrict = district.getCodDis();
												}else if(nominated.getCant_vote() == newMinimun){
													totalSimilar++;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if(totalSimilar == 0 && newMinimun > 0) {
			delegatedService.createDelegated(new DelegatedDto(0,idDelegated,0)); // ESTO REALMENTE VA EN LA PANTALLA DE VOTACION
			return "1ra Vuelta";
		}
		return "2da Vuelta";
	}

}
