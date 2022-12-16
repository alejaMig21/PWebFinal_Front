package cu.edu.cujae.pweb.service;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.pweb.dto.DistrictDto;

public interface DistrictService {
	List<DistrictDto> getDistricts();
	DistrictDto getDistrictById(int districtId); // originalmente el parametro era String
	int getIdByName(String districtName);
	void createDistrict(DistrictDto user);
	void updateDistrict(DistrictDto user);
	void deleteDistrict(int id); // originalmente el parametro era String
}