package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.RolesDto;
import cu.edu.cujae.pweb.service.RolesService;
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
public class ManageRolesBean {

    private List<RolesDto> roles;
    private RolesDto selectedRoles;

    @Autowired
    private RolesService rolesService;

    public ManageRolesBean() {

    }

    @PostConstruct
    public void init() {
        roles = rolesService.getRoles();
    }
    public void openNew() {
        this.selectedRoles = new RolesDto();
    }
    public void openForEdit() {

    }

    public void saveRoles() {
//        selectedVoter.setCdr(cdrService.getIdByName(selectedCDRName));
//        selectedVoter.setDate(selectedDate.toString());

        if (this.selectedRoles.getId() == 0) {
            rolesService.createRoles(selectedRoles);

            JsfUtils.addInfoMessageFromBundle("message_inserted_roles");
        } else {
            rolesService.updateRoles(selectedRoles);

            JsfUtils.addInfoMessageFromBundle("message_updated_roles");
        }

        roles = rolesService.getRoles();

        PrimeFaces.current().executeScript("PF('manageRolesDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-roless");

    }

    public void deleteRoles() {

        rolesService.deleteRoles(selectedRoles.getId());
        this.selectedRoles = null;

        roles = rolesService.getRoles();

        JsfUtils.addInfoMessageFromBundle("message_deleted_roles");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-roless");

    }

    public List<RolesDto> getRoles() {
        return this.roles;
    }

    public void setRoles(List<RolesDto> roles) {
        this.roles = roles;
    }

    public RolesDto getSelectedRoles() {
        return this.selectedRoles;
    }

    public void setSelectedRoles(RolesDto selectedRoles) {
        this.selectedRoles = selectedRoles;
    }

    public RolesService getRolesService() {
        return this.rolesService;
    }

    public void setRolesService(RolesService service) {
        this.rolesService = service;
    }

}