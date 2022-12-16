package cu.edu.cujae.pweb.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.RoleDto;
import cu.edu.cujae.pweb.dto.UserDto;
import cu.edu.cujae.pweb.service.RoleService;
import cu.edu.cujae.pweb.service.UserService;
import cu.edu.cujae.pweb.utils.JsfUtils;


@Component
@ManagedBean
@ViewScoped 
public class ManageUserBean {
	
	private UserDto userDto;
	private UserDto selectedUser;
	private List<UserDto> users;
	private Long[] selectedRoles;
	
	private List<RoleDto> roles;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	public ManageUserBean() {
		
	}
	
	//Esta anotacioon permite que se ejecute code luego de haberse ejecuta el constructor de la clase. 
	@PostConstruct
    public void init() {
		users = userService.getUsers();
		roles = roleService.getRoles();
    }
	
	//Se ejecuta al dar clic en el button Nuevo
	public void openNew() {
        this.selectedUser = new UserDto();
        this.selectedRoles = null;
    }
	
	//Se ejecuta al dar clic en el button con el lapicito
	public void openForEdit() {
		List<RoleDto> roles = this.selectedUser.getRoles();
		this.selectedRoles = roles.stream().map(r -> r.getId()).toArray(Long[]::new);
	}
	
	//Se ejecuta al dar clic en el button dentro del dialog para salvar o registrar al usuario
	public void saveUser() {
        if (this.selectedUser.getId() == null) {
            this.selectedUser.setId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            List<RoleDto> rolesToAdd = new ArrayList<RoleDto>();
            for(int i = 0; i < this.selectedRoles.length; i++) {
            	rolesToAdd.add(roleService.getRolesById(selectedRoles[i]));
            }
            this.selectedUser.setRoles(rolesToAdd);
            
            //register user
            userService.createUser(this.selectedUser);
            
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_user_added"); //Este code permite mostrar un mensaje exitoso (FacesMessage.SEVERITY_INFO) obteniendo el mensage desde el fichero de recursos, con la llave message_user_added
        }
        else {
        	//register user
            userService.updateUser(this.selectedUser);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_user_edited");
        }

        //load datatable again with new values
        users = userService.getUsers();
        
        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-users");
    }

	//Permite eliminar un usuario
    public void deleteUser() {
    	try {
    		//delete user
    		userService.deleteUser(this.selectedUser.getId());
            this.selectedUser = null;
            
            //load datatable again with new values
            users = userService.getUsers();
            
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_user_deleted");
            PrimeFaces.current().ajax().update("form:dt-users");// Este code es para refrescar el componente con id dt-users que se encuentra dentro del formulario con id form
		} catch (Exception e) {
			JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "message_error");
		}
        
    }

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public UserDto getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserDto selectedUser) {
		this.selectedUser = selectedUser;
	}

	public List<UserDto> getUsers() {
		
		return users;
	}

	public void setUsers(List<UserDto> users) {
		
		this.users = users;
	}

	public Long[] getSelectedRoles() {
		return selectedRoles;
	}

	public void setSelectedRoles(Long[] selectedRoles) {
		this.selectedRoles = selectedRoles;
	}

	public List<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

}
