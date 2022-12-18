package cu.edu.cujae.pweb.service;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.pweb.dto.DistrictDto;

public interface DistrictService {
	List<DistrictDto> getDistricts();
	DistrictDto getDistrictById(int districtId);
	int getIdByName(String districtName);
	void createDistrict(DistrictDto user);
	void updateDistrict(DistrictDto user);
	void deleteDistrict(int id);
	int getMunicipalityByDistrict(int id_district);
	String getDistrictNameById(int id);
}