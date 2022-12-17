package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.DelegatedDto;

import java.util.List;

public interface DelegatedService {
	List<DelegatedDto> getDelegateds();
	DelegatedDto getDelegatedById(int id_delegated); // Originalmente era String
//	int getIdByName(String delegatedName);
	void createDelegated(DelegatedDto delegated);
	void updateDelegated(DelegatedDto delegated);
	void deleteDelegated(int id_delegated); // Originalmente era String
}
