package cu.edu.cujae.pweb.dto;

public class MunicipalityDto {
	private int codMun = 0;
	private String nameMunicipality;
	private int id_electoral_process;
	
	public MunicipalityDto() {
		super();
	}
	public MunicipalityDto(int codMun, String namMun, int id_electoral_process) {
		super();
		this.codMun = codMun;
		this.nameMunicipality = namMun;
		this.id_electoral_process = id_electoral_process;
	}

	public int getId_electoral_process() {
		return id_electoral_process;
	}

	public void setId_electoral_process(int id_electoral_process) {
		this.id_electoral_process = id_electoral_process;
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
