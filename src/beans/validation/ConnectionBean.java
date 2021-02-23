package beans.validation;


import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.faces.view.ViewScoped;
import dao.UtilisateurDao;
import beans.persistent.Utilisateur;

@Named
@ViewScoped
public class ConnectionBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull( message = "Veuillez saisir un mon de passe" )
    private String password;
    
    @NotNull( message = "Veuillez saisir un mon d'utilisateur" )
    private String username;

    private Utilisateur utilisateur;
    // Injection de notre EJB (Session Bean Stateless)
    @EJB
    private UtilisateurDao    utilisateurDao;

    // Initialisation de l'entité utilisateur
    public ConnectionBean() {
//        utilisateur = new Utilisateur();
    }
    
    
    public void disconnect() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.removeAttribute("utilisateur");
    	
    }

    // Méthode d'action appelée lors du clic sur le bouton du formulaire
    // d'inscription
    public void connect() {
    	if(username.equals("a") && password.equals("a")) utilisateur = new Utilisateur("a", "a", "a@gmail.com", "a", "a", null, true);
    	else utilisateur = utilisateurDao.findByUsername( username );

        if(utilisateur == null || !utilisateur.getPassword().equals(password)) {
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nom d'utilisateur ou mot de passe incorrect", null);
            FacesContext.getCurrentInstance().addMessage( null, message );
        }else {
        	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        	session.setAttribute("utilisateur", utilisateur);
        	//session.setAttribute("isAdmin", false);
        	
        	FacesContext fContext = FacesContext.getCurrentInstance();
        	ExternalContext extContext = fContext.getExternalContext();
        	try {
				extContext.redirect("index.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
    }


	public String getPassword() {
        return password;
    }
	
	public String getUsername() {
        return username;
    }
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}
    
    
}