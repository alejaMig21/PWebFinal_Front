package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.UserDto;
import cu.edu.cujae.pweb.service.RolesService;
import cu.edu.cujae.pweb.service.UserService;
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
public class ManageUserBean {

    private List<UserDto> users;
    private UserDto selectedUser;

    @Autowired
    private UserService userService;
    @Autowired
    private RolesService rolesService;

    public ManageUserBean() {

    }

    @PostConstruct
    public void init() {
        users = userService.getUsers();
    }
    public void openNew() {
        this.selectedUser = new UserDto();
    }
    public void openForEdit() {

    }

    public void saveUser() {
//        selectedVoter.setCdr(cdrService.getIdByName(selectedCDRName));
//        selectedVoter.setDate(selectedDate.toString());

        if (this.selectedUser.getId_user() == 0) {
            userService.createUser(selectedUser);

            JsfUtils.addInfoMessageFromBundle("message_inserted_user");
        } else {
            userService.updateUser(selectedUser);

            JsfUtils.addInfoMessageFromBundle("message_updated_user");
        }

        users = userService.getUsers();

        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-users");

    }

    public void deleteUser() {

        userService.deleteUser(selectedUser.getId_user());
        this.selectedUser = null;

        users = userService.getUsers();

        JsfUtils.addInfoMessageFromBundle("message_deleted_user");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-users");

    }

    public List<UserDto> getUsers() {
        return this.users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }

    public UserDto getSelectedUser() {
        return this.selectedUser;
    }

    public void setSelectedUser(UserDto selectedUser) {
        this.selectedUser = selectedUser;
    }

    public UserService getUserService() {
        return this.userService;
    }

    public void setUserService(UserService service) {
        this.userService = service;
    }

    public RolesService getRolesService() { return rolesService; }

    public void setRolesService(RolesService rolesService) { this.rolesService = rolesService; }
}