package cu.edu.cujae.pweb.dto;

public class MunicipalityDto {
	private int codMun = 0;
	private String nameMunicipality;
	
	public MunicipalityDto() {
		super();
	}
	public MunicipalityDto(int codMun, String namMun) {
		super();
		this.codMun = codMun;
		this.nameMunicipality = namMun;
	}
	public int getCodMun() {
		return codMun;
	}
	public void setCodMun(int codMun) {
		this.codMun = codMun;
	}
	public String getNameMunicipality() {
		return nameMunicipality;
	}
	public void setNameMunicipality(String namMun) {
		this.nameMunicipality = namMun;
	}
}
