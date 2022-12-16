package cu.edu.cujae.pweb.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.MunicipalityDto;
import cu.edu.cujae.pweb.service.MunicipalityService;
import cu.edu.cujae.pweb.utils.JsfUtils;

@Component
@ManagedBean
@ViewScoped
public class ManageMunicipalityBean {

    private List<MunicipalityDto> municipalitys;
    private MunicipalityDto selectedMunicipality;

    @Autowired
    private MunicipalityService service;

    public ManageMunicipalityBean() {

    }

    @PostConstruct
    public void init() {
        municipalitys = service.getMunicipalitys();
    }

    public void openNew() {
        this.selectedMunicipality = new MunicipalityDto();
    }

    public void openForEdit() {

    }

    public void saveMunicipality() {
        System.out.println(selectedMunicipality.getNameMunicipality());
        System.out.println("El CODIGO ES " + selectedMunicipality.getCodMun());
        if (this.selectedMunicipality.getCodMun() == 0) {
            service.createMunicipality(selectedMunicipality);

            JsfUtils.addInfoMessageFromBundle("message_inserted_municipality");
        } else {
            service.updateMunicipality(selectedMunicipality);

            JsfUtils.addInfoMessageFromBundle("message_updated_municipality");
        }

        municipalitys = service.getMunicipalitys();

        PrimeFaces.current().executeScript("PF('manageMunicipalityDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-municipalitys");

    }

    public void deleteMunicipality() {

        service.deleteMunicipality(selectedMunicipality.getCodMun());
        this.selectedMunicipality = null;

        municipalitys = service.getMunicipalitys();

        JsfUtils.addInfoMessageFromBundle("message_deleted_municipality");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-municipalitys");

    }

    public List<MunicipalityDto> getMunicipalitys() {
        return this.municipalitys;
    }

    public void setMunicipalitys(List<MunicipalityDto> municipalitys) {
        this.municipalitys = municipalitys;
    }

    public MunicipalityDto getSelectedMunicipality() {
        return this.selectedMunicipality;
    }

    public void setSelectedMunicipality(MunicipalityDto selectedMunicipality) {
        this.selectedMunicipality = selectedMunicipality;
    }

    public MunicipalityService getService() {
        return this.service;
    }

    public void setService(MunicipalityService service) {
        this.service = service;
    }

}