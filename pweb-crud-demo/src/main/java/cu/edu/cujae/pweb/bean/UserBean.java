package cu.edu.cujae.pweb.bean;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.RoleDto;
import cu.edu.cujae.pweb.service.RoleService;

@Component
@ManagedBean
@ViewScoped
public class UserBean {
	
	private String username;
	private String password;
	
	private List<RoleDto> roles;
	
	@Autowired
	private RoleService roleService;
	
	//Esta anotacioon permite que se ejecute code luego de haberse ejecuta el constructor de la clase. 
	@PostConstruct
    public void init() {
		roles = roleService.getRoles();
    }
	
	public UserBean() {
		// TODO Auto-generated constructor stub
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String login() {
		if(username.equalsIgnoreCase("pweb") && password.equals("pweb")) {
			try {
				getFacesContext().getExternalContext().redirect(getRequest().getContextPath() +
					    "/pages/welcome/welcome.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return  null;
	}
	
	protected HttpServletRequest getRequest() {
	    return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
	}
	
	protected FacesContext getFacesContext() {
	    return FacesContext.getCurrentInstance();
	}
}
