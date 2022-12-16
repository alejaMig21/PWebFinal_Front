package cu.edu.cujae.pweb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import cu.edu.cujae.pweb.dto.VoterDto;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;

@Service
public class VoterServiceImpl implements VoterService{

	@Autowired
	private RestService restService;

	@Override
	public List<VoterDto> getVoters() {
		
		List<VoterDto> cdrList = new ArrayList<VoterDto>();
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<VoterDto> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.GET("/api/v1/voters/", params, String.class).getBody();
		    cdrList = apiRestMapper.mapList(response, VoterDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cdrList;
	}

    @Override
    public List<VoterDto> listVotersByCdr(int cdr){
        List<VoterDto> voterList = new ArrayList<VoterDto>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<VoterDto> apiRestMapper = new ApiRestMapper<>();
            String response = (String)restService.GET("/api/v1/voters/" + "{cdr}", params, String.class).getBody();
            voterList = apiRestMapper.mapList(response, VoterDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voterList;
    }

	@Override
    public VoterDto getVoterById(int idVoter) {
        VoterDto voter = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<VoterDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/voters/" + "{idVoter}");
            String uri = template.expand(idVoter).toString();
            String response = (String) restService.GET(uri, params, String.class).getBody();
            voter = apiRestMapper.mapOne(response, VoterDto.class);

            System.out.println("El votante es " + voter.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return voter;
    }

	@Override
    public int getIdByName(String voterName) {
        int idVoter = 0;

        try {
            String uri = "/api/v1/voters/" + "name/{name}";
            Map<String, String> map = new HashMap<>();
            map.put("name", voterName);

            String response = (String) restService.GETEntity(
                    uri, map,
                    String.class).getBody();

            idVoter = Integer.parseInt(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idVoter;
    }

	@Override
	public void createVoter(@NotNull VoterDto voter) {
		// TODO Auto-generated method stub
        System.out.println("000000000000000000000000000    " + voter.getDate() + "    0000000000000000000000000000000");
		restService.POST("/api/v1/voters/" + "", voter, String.class).getBody();
	}

	@Override
	public void updateVoter(VoterDto voter) {
		// TODO Auto-generated method stub
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        restService.PUT("/api/v1/voters/" + "", params, voter, String.class).getBody();
	}

	@Override
	public void deleteVoter(int idVoter) { // Originalmente era String
		// TODO Auto-generated method stub
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        UriTemplate template = new UriTemplate("/api/v1/voters/" + "{idVoter}");
        String uri = template.expand(idVoter).toString();
        restService.DELETE(uri, params, String.class, null).getBody();
	}
	
}