package cu.edu.cujae.pweb.service;

import java.util.List;

import cu.edu.cujae.pweb.dto.VoterDto;

public interface VoterService {
	List<VoterDto> getVoters();
	List<VoterDto> listVotersByCdr(int cdr);
	VoterDto getVoterById(int voterId);
	int getIdByName(String name);
	void createVoter(VoterDto voter);
	void updateVoter(VoterDto voter);
	void deleteVoter(int id);
	String getVoterNameById(int id);
}
