package cu.edu.cujae.pweb.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.DistrictDto;
import cu.edu.cujae.pweb.service.DistrictService;
import cu.edu.cujae.pweb.service.MunicipalityService;
import cu.edu.cujae.pweb.utils.JsfUtils;

@Component
@ManagedBean
@ViewScoped
public class ManageDistrictBean {

    private List<DistrictDto> districts;
    private DistrictDto selectedDistrict;
    private String selectedCollegeName;
    private String selectedMunicipalityName = "Default";

    public String getSelectedMunicipalityName() {
        return this.selectedMunicipalityName;
    }

    public void setSelectedMunicipalityName(String selectedMunicipalityName) {
        this.selectedMunicipalityName = selectedMunicipalityName;
    }

    public String getSelectedCollegeName() {
        return this.selectedCollegeName;
    }

    public void setSelectedCollegeName(String selectedCollegeName) {
        this.selectedCollegeName = selectedCollegeName;
    }

    @Autowired
    private DistrictService districtService;

    @Autowired
    private MunicipalityService municipalityService;

    public ManageDistrictBean() {

    }

    @PostConstruct
    public void init() {
        districts = districtService.getDistricts();
    }

    public void openNew() {
        this.selectedDistrict = new DistrictDto();
    }

    public void openForEdit() {

    }

    public void saveDistrict() {
        //selectedDistrict.setIdMunicipality(municipalityService.getIdByName(selectedMunicipalityName));
        if (this.selectedDistrict.getCodDis() == 0) {
            districtService.createDistrict(selectedDistrict);

            JsfUtils.addInfoMessageFromBundle("message_inserted_district");
        } else {
            districtService.updateDistrict(selectedDistrict);

            JsfUtils.addInfoMessageFromBundle("message_updated_district");
        }

        districts = districtService.getDistricts();

        PrimeFaces.current().executeScript("PF('manageDistrictDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-districts");

    }

    public void deleteDistrict() {

        districtService.deleteDistrict(selectedDistrict.getCodDis());
        this.selectedDistrict = null;

        districts = districtService.getDistricts();

        JsfUtils.addInfoMessageFromBundle("message_deleted_district");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-districts");

    }

    public List<DistrictDto> getDistricts() {
        return this.districts;
    }

    public void setDistricts(List<DistrictDto> districts) {
        this.districts = districts;
    }

    public DistrictDto getSelectedDistrict() {
        return this.selectedDistrict;
    }

    public void setSelectedDistrict(DistrictDto selectedDistrictService) {
        this.selectedDistrict = selectedDistrictService;
    }

    public DistrictService getDistrictService() {
        return this.districtService;
    }

    public void setDistrictService(DistrictService service) {
        this.districtService = service;
    }

    public MunicipalityService getMunicipalityService() {
        return this.municipalityService;
    }

    public void setMunicipalityService(MunicipalityService municipalityService) {
        this.municipalityService = municipalityService;
    }

}