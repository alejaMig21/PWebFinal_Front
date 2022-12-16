package cu.edu.cujae.pweb.service;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.pweb.dto.MunicipalityDto;

public interface MunicipalityService {
	List<MunicipalityDto> getMunicipalitys();

	MunicipalityDto getMunicipalityById(int municipalityId); // originalmente el parametro era String

	int getIdByName(String nameMunicipality);

	void createMunicipality(MunicipalityDto municipality);

	void updateMunicipality(MunicipalityDto municipality);

	void deleteMunicipality(int id); // originalmente el parametro era String
}
