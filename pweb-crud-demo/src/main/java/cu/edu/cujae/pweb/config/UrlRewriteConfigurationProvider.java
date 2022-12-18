package cu.edu.cujae.pweb.config;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

@RewriteConfiguration
public class UrlRewriteConfigurationProvider extends HttpConfigurationProvider {

    @Override
    public Configuration getConfiguration(ServletContext context) {
        return ConfigurationBuilder.begin()

                .addRule(Join.path("/security-users").to("/pages/security/users/lista_user.jsf"))
                .addRule(Join.path("/security-roles").to("/pages/security/users/lista_roles.jsf"))
                .addRule(Join.path("/welcome").to("/pages/welcome/welcome.jsf"))
                .addRule(Join.path("/lista_municipality").to("/pages/menu/lista_municipality.jsf"))
                .addRule(Join.path("/lista_district").to("/pages/menu/lista_district.jsf"))
                .addRule(Join.path("/lista_college").to("/pages/menu/lista_college.jsf"))
                .addRule(Join.path("/lista_cdr").to("/pages/menu/lista_cdr.jsf"))
                .addRule(Join.path("/lista_voter").to("/pages/menu/lista_votantes.jsf"))
                .addRule(Join.path("/lista_nominated").to("/pages/menu/lista_nominados.jsf"))
                .addRule(Join.path("/lista_delegated").to("/pages/menu/lista_delegated.jsf"));


        //ej using params
        //.addRule(Join.path("/shop/{category}").to("/faces/viewCategory.jsf"));
    }

    @Override
    public int priority() {
        return 0;
    }

}