package cu.edu.cujae.pweb.bean;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import cu.edu.cujae.pweb.utils.JsfUtils;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@ManagedBean
@SessionScoped
public class LenguageBean {
    private Locale locale;

    @PostConstruct
    public void init(){
        locale = JsfUtils.getCurrentLocale();
    }

    public Locale getLocale(){
        return locale;
    }

    public String getLanguage(){
        return locale.getLanguage();
    }

    public void setLanguage(String language){
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
