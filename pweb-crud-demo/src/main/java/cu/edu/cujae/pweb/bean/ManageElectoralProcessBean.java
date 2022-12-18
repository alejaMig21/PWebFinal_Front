package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.ElectoralProcessDto;
import cu.edu.cujae.pweb.service.ElectoralProcessService;
import cu.edu.cujae.pweb.service.MunicipalityService;
import cu.edu.cujae.pweb.service.NominatedService;
import cu.edu.cujae.pweb.service.VoterService;
import cu.edu.cujae.pweb.utils.JsfUtils;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.util.List;

@Component
@ManagedBean
@ViewScoped
public class ManageElectoralProcessBean {

    private List<ElectoralProcessDto> eprocesss;
    private ElectoralProcessDto selectedElectoralProcess;

    public List<ElectoralProcessDto> getEprocesss() {
        return this.eprocesss;
    }

    public void setEprocesss(List<ElectoralProcessDto> eprocesss) {
        this.eprocesss = eprocesss;
    }

    public ElectoralProcessDto getSelectedElectoralProcess() {
        return this.selectedElectoralProcess;
    }

    public void setSelectedElectoralProcess(ElectoralProcessDto selectedElectoralProcess) {
        this.selectedElectoralProcess = selectedElectoralProcess;
    }

    public ElectoralProcessService getService() {
        return this.service;
    }

    public void setService(ElectoralProcessService service) {
        this.service = service;
    }

    @Autowired
    private ElectoralProcessService service;
    @Autowired
    private VoterService voterService;
    @Autowired
    private NominatedService nominatedService;
    @Autowired
    private MunicipalityService municipalityService;

    public NominatedService getNominatedService() {
        return nominatedService;
    }

    public void setNominatedService(NominatedService nominatedService) {
        this.nominatedService = nominatedService;
    }

    public MunicipalityService getMunicipalityService() {
        return municipalityService;
    }

    public void setMunicipalityService(MunicipalityService municipalityService) {
        this.municipalityService = municipalityService;
    }

    public VoterService getVoterService() {
        return voterService;
    }

    public void setVoterService(VoterService voterService) {
        this.voterService = voterService;
    }

    public ManageElectoralProcessBean() {

    }

    @PostConstruct
    public void init() {
        eprocesss = service.listElectoralProcess();
    }

    public void openNew() {
        this.selectedElectoralProcess = new ElectoralProcessDto();
    }

    public void openForEdit() {

    }

    public void saveDelegated() {
        if (this.selectedElectoralProcess.getId_electoral_process() == 0) {
            service.createElectoralProcess(selectedElectoralProcess);

            JsfUtils.addInfoMessageFromBundle("message_inserted_electoral_process");
        } else {
            service.updateElectoralProcess(selectedElectoralProcess);

            JsfUtils.addInfoMessageFromBundle("message_updated_electoral_process");
        }

        eprocesss = service.listElectoralProcess();

        PrimeFaces.current().executeScript("PF('manageElectoralProcessDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-electoralprocesss");

    }

    public void deleteElectoralProcess() {

        service.deleteElectoralProcess(selectedElectoralProcess.getId_electoral_process());
        this.selectedElectoralProcess = null;

        eprocesss = service.listElectoralProcess();

        JsfUtils.addInfoMessageFromBundle("message_deleted_electoral_process");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-electoralprocesss");

    }
}