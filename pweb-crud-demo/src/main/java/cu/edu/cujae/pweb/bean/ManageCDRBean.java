package cu.edu.cujae.pweb.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import cu.edu.cujae.pweb.dto.VoterDto;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.CDRDto;
import cu.edu.cujae.pweb.service.CDRService;
import cu.edu.cujae.pweb.service.CollegeService;
import cu.edu.cujae.pweb.service.VoterService;
import cu.edu.cujae.pweb.utils.JsfUtils;

///------------------------------------------------------------------------------------
@Component
@ManagedBean
@ViewScoped
public class ManageCDRBean {

    private List<CDRDto> cdrs;
    private CDRDto selectedCDR;
    private String selectedCollegeName = "Default College";
    private String selectedVoterName = "Default President";

    public String getSelectedVoterName() {
        return this.selectedVoterName;
    }

    public void setSelectedVoterName(String selectedVoterName) {
        this.selectedVoterName = selectedVoterName;
    }

    public CollegeService getCollegeService() {
        return this.collegeService;
    }

    public void setCollegeService(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    public String getSelectedCollegeName() {
        return this.selectedCollegeName;
    }

    public void setSelectedCollegeName(String selectedCollegeName) {
        this.selectedCollegeName = selectedCollegeName;
    }

    @Autowired
    private CDRService cdrService;
    @Autowired
    private CollegeService collegeService;
    @Autowired
    private VoterService voterService;

    public VoterService getVoterService() {
        return this.voterService;
    }

    public void setVoterService(VoterService voterService) {
        this.voterService = voterService;
    }

    public ManageCDRBean() {

    }

    @PostConstruct
    public void init() {
        cdrs = cdrService.getCDRs();
    }

    public void openNew() {
        this.selectedCDR = new CDRDto();
    }

    public void openForEdit() {

    }

    public void saveCDR() {
//        selectedCDR.setId_college(collegeService.getIdByName(selectedCollegeName));
//        selectedCDR.setId_presidentCDR(voterService.getIdByName(selectedVoterName));

        if (this.selectedCDR.getId_cdr() == 0) {
            System.out.println("Efectivamente esta creando el CDR " + selectedCDR.getName_cdr() +
                    " cuyo presidente es " + selectedCDR.getId_president());
            cdrService.createCDR(selectedCDR);

            JsfUtils.addInfoMessageFromBundle("message_inserted_cdr");
        } else {
            cdrService.updateCDR(selectedCDR);

            JsfUtils.addInfoMessageFromBundle("message_updated_cdr");
        }

        cdrs = cdrService.getCDRs();

        PrimeFaces.current().executeScript("PF('manageCDRDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-cdrs");
    }

    public boolean votersInCdr(int cdr){
        for(VoterDto voter : voterService.getVoters()){
            if(voter.getCdr() == cdr) return true;
        }
        return false;
    }

    public void deleteCDR() {
        if(!votersInCdr(selectedCDR.getId_cdr())) { // Si ningun votante pertenece a dicho CDR
            cdrService.deleteCDR(selectedCDR.getId_cdr());
            this.selectedCDR = null;

            cdrs = cdrService.getCDRs();

            JsfUtils.addInfoMessageFromBundle("message_deleted_cdr");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-cdrs");
            return;
        }
        JsfUtils.addInfoMessageFromBundle("message_not_deleted_cdr");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cdrs");
    }

    public List<CDRDto> getCdrs() {
        return this.cdrs;
    }

    public void setCdrs(List<CDRDto> cdrs) {
        this.cdrs = cdrs;
    }

    public CDRDto getSelectedCDR() {
        return this.selectedCDR;
    }

    public void setSelectedCDR(CDRDto selectedCDR) {
        this.selectedCDR = selectedCDR;
    }

    public CDRService getCdrService() {
        return this.cdrService;
    }

    public void setCdrService(CDRService service) {
        this.cdrService = service;
    }

}