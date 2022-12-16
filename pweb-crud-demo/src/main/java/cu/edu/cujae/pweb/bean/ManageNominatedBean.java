package cu.edu.cujae.pweb.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import cu.edu.cujae.pweb.service.VoterService;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.NominatedDto;
import cu.edu.cujae.pweb.service.NominatedService;
import cu.edu.cujae.pweb.utils.JsfUtils;

@Component
@ManagedBean
@ViewScoped
public class ManageNominatedBean {

    private List<NominatedDto> nominateds;
    private NominatedDto selectedNominated;
    private String selectedMunicipalityName;
    
    public void setNominateds(List<NominatedDto> nominateds) {
        this.nominateds = nominateds;
    }

    public String getSelectedMunicipalityName() {
        return this.selectedMunicipalityName;
    }

    public void setSelectedMunicipalityName(String selectedMunicipalityName) {
        this.selectedMunicipalityName = selectedMunicipalityName;
    }

    @Autowired
    private NominatedService service;

    @Autowired
    private VoterService voterService;

    public VoterService getVoterService() {
        return voterService;
    }

    public void setVoterService(VoterService voterService) {
        this.voterService = voterService;
    }

    public ManageNominatedBean() {

    }

    @PostConstruct
    public void init() {
        nominateds = service.getNominateds();
    }

    public void openNew() {
        this.selectedNominated = new NominatedDto();
    }

    public void openForEdit() {

    }

    public void saveNominated() {
        System.out.println("Se esta salvando el Nominado, su ID es " + selectedNominated.getId());
        if(selectedNominated == null){
            System.out.println("No se esta construyendo el Nominado");
        }
        if (this.selectedNominated.getId() == 0) {
            service.insert(selectedNominated);

            JsfUtils.addInfoMessageFromBundle("message_inserted_nominated");
        } else {
            service.update(selectedNominated);

            JsfUtils.addInfoMessageFromBundle("message_updated_nominated");
        }

        nominateds = service.getNominateds();

        PrimeFaces.current().executeScript("PF('manageNominatedDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-nominateds");

    }

    public void deleteNominated() {

        service.delete(selectedNominated.getId());
        this.selectedNominated = null;

        nominateds = service.getNominateds();

        JsfUtils.addInfoMessageFromBundle("message_deleted_nominated");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-nominateds");

    }

    public List<NominatedDto> getNominateds() {
        return this.nominateds;
    }

    // public void setCdrs(List<NominatedDto> nominateds) {
    //     this.nominateds = nominateds;
    // }

    public NominatedDto getSelectedNominated() {
        return this.selectedNominated;
    }

    public void setSelectedNominated(NominatedDto selectedNominated) {
        this.selectedNominated = selectedNominated;
    }

    public NominatedService getService() {
        return this.service;
    }

    public void setService(NominatedService service) {
        this.service = service;
    }

}