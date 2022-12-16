package cu.edu.cujae.pweb.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;

import cu.edu.cujae.pweb.dto.DistrictDto;
import cu.edu.cujae.pweb.service.DistrictService;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.CollegeDto;
import cu.edu.cujae.pweb.service.CollegeService;
import cu.edu.cujae.pweb.utils.JsfUtils;

@Component
@ManagedBean
@ViewScoped
public class ManageCollegeBean {

    private List<CollegeDto> colleges;
    private CollegeDto selectedCollege;
    private int selectedDistrictName = 0;

    public List<CollegeDto> getColleges() {
        return this.colleges;
    }

    public void setColleges(List<CollegeDto> colleges) {
        this.colleges = colleges;
    }

    public CollegeDto getSelectedCollege() {
        return this.selectedCollege;
    }

    public void setSelectedCollege(CollegeDto selectedCollege) {
        this.selectedCollege = selectedCollege;
    }

    public int getSelectedDistrictName() {
        return this.selectedDistrictName;
    }

    public void setSelectedDistrictName(int selectedDistrictName) {
        this.selectedDistrictName = selectedDistrictName;
    }

    public CollegeService getService() {
        return this.service;
    }

    public void setService(CollegeService service) {
        this.service = service;
    }

    @Autowired
    private CollegeService service;

    @Autowired
    private DistrictService districtService;

    public ManageCollegeBean() {

    }

    @PostConstruct
    public void init() {
        colleges = service.getColleges();
    }

    public void openNew() {
        this.selectedCollege = new CollegeDto();
    }

    public void openForEdit() {

    }

    public void saveCollege() {
        if (this.selectedCollege.getIdCollege() == 0) {
            service.createCollege(selectedCollege);

            JsfUtils.addInfoMessageFromBundle("message_inserted_college");
        } else {
            service.updateCollege(selectedCollege);

            JsfUtils.addInfoMessageFromBundle("message_updated_college");
        }

        colleges = service.getColleges();

        PrimeFaces.current().executeScript("PF('manageCollegeDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-colleges");

    }

    public void deleteCollege() {

        service.deleteCollege(selectedCollege.getIdCollege());
        this.selectedCollege = null;

        colleges = service.getColleges();

        JsfUtils.addInfoMessageFromBundle("message_deleted_college");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-colleges");

    }

    public DistrictService getDistrictService() {
        return districtService;
    }

    public void setDistrictService(DistrictService districtService) {
        this.districtService = districtService;
    }
}