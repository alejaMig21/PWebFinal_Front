package cu.edu.cujae.pweb.service;

import java.util.List;

import cu.edu.cujae.pweb.dto.NominatedDto;

public interface NominatedService {
	List<NominatedDto> getNominateds();
	NominatedDto getNominatedById(int idNominated);
	int getIdByName(String nameNominated);
	void insert(NominatedDto nominated);
	void update(NominatedDto nominated);
	void delete(int idNominated);
	int getVoterByNominated(int id_nominated);
}
