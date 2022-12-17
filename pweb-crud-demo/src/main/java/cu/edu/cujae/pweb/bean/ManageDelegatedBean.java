package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.DelegatedDto;
import cu.edu.cujae.pweb.service.DelegatedService;
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
public class ManageDelegatedBean {

    private List<DelegatedDto> delegateds;
    private DelegatedDto selectedDelegated;

    public List<DelegatedDto> getDelegateds() {
        return this.delegateds;
    }

    public void setDelegateds(List<DelegatedDto> delegateds) {
        this.delegateds = delegateds;
    }

    public DelegatedDto getSelectedDelegated() {
        return this.selectedDelegated;
    }

    public void setSelectedDelegated(DelegatedDto selectedDelegated) {
        this.selectedDelegated = selectedDelegated;
    }

    public DelegatedService getService() {
        return this.service;
    }

    public void setService(DelegatedService service) {
        this.service = service;
    }

    @Autowired
    private DelegatedService service;
    @Autowired
    private NominatedService nominatedService;
    @Autowired
    private VoterService voterService;

    public VoterService getVoterService() {
        return voterService;
    }

    public void setVoterService(VoterService voterService) {
        this.voterService = voterService;
    }

    public NominatedService getNominatedService() {
        return nominatedService;
    }

    public void setNominatedService(NominatedService nominatedService) {
        this.nominatedService = nominatedService;
    }

    public ManageDelegatedBean() {

    }

    @PostConstruct
    public void init() {
        delegateds = service.getDelegateds();
    }

    public void openNew() {
        this.selectedDelegated = new DelegatedDto();
    }

    public void openForEdit() {

    }

    public void saveDelegated() {
        if (this.selectedDelegated.getId() == 0) {
            service.createDelegated(selectedDelegated);

            JsfUtils.addInfoMessageFromBundle("message_inserted_delegated");
        } else {
            service.updateDelegated(selectedDelegated);

            JsfUtils.addInfoMessageFromBundle("message_updated_delegated");
        }

        delegateds = service.getDelegateds();

        PrimeFaces.current().executeScript("PF('manageDelegatedDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-delegateds");

    }

    public void deleteDelegated() {

        service.deleteDelegated(selectedDelegated.getId());
        this.selectedDelegated = null;

        delegateds = service.getDelegateds();

        JsfUtils.addInfoMessageFromBundle("message_deleted_delegated");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-delegateds");

    }
}