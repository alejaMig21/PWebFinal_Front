package cu.edu.cujae.pweb.service;

import java.util.List;

import cu.edu.cujae.pweb.dto.CDRDto;

public interface CDRService {
	List<CDRDto> getCDRs();
	CDRDto getCDRById(int cdrId); // Originalmente era String
	int getIdByName(String cdrName);
	void createCDR(CDRDto cdr);
	void updateCDR(CDRDto cdr);
	void deleteCDR(int id); // Originalmente era String
}
