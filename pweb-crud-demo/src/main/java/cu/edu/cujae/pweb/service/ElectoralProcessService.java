package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.ElectoralProcessDto;

import java.util.List;

public interface ElectoralProcessService {
	List<ElectoralProcessDto> listElectoralProcess();
	ElectoralProcessDto getElectoralProcessById(int id_electoral_process);
//	int getIdByName(String delegatedName);
	void createElectoralProcess(ElectoralProcessDto electoralProcess);
	void updateElectoralProcess(ElectoralProcessDto electoralProcess);
	void deleteElectoralProcess(int id_electoral_process);
}
