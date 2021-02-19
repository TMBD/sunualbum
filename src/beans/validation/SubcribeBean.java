package beans.validation;


import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.faces.view.ViewScoped;
import dao.UtilisateurDao;
import beans.persistent.Utilisateur;

@Named
@ViewScoped
public class SubcribeBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Utilisateur       utilisateur;

    // Injection de notre EJB (Session Bean Stateless)
    @EJB
    private UtilisateurDao    utilisateurDao;
    
    private String usernameForUpdate;
    // Initialisation de l'entité utilisateur
    public SubcribeBean() {
        utilisateur = new Utilisateur();
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	session.setAttribute("forUpdate", false);
    }
    

    // Méthode d'action appelée lors du clic sur le bouton du formulaire
    // d'inscription
    public void subcribe() {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	if(usernameForUpdate == null || usernameForUpdate.equals("")) {
    		initialiserDateInscription();
            utilisateurDao.add( utilisateur );
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès de l'inscription !", null);
            FacesContext.getCurrentInstance().addMessage( null, message );
    	}else {
    		if(CommonUtilisateurBean.isAdminOrSelProfilOrRedirect(usernameForUpdate)) {
	    		utilisateurDao.update( utilisateur );
	    		Utilisateur utilisateurCourant = (Utilisateur) session.getAttribute("utilisateur");
	    		if(utilisateurCourant.getEstAdmin() == false) {
	    			session.setAttribute("utilisateur", utilisateur);
	    		}
	    		session.setAttribute("forUpdate", false);
	    		FacesContext fContext = FacesContext.getCurrentInstance();
	        	ExternalContext extContext = fContext.getExternalContext();
	        	try {
	    			extContext.redirect("profil_utilisateur.xhtml?username="+utilisateur.getUsername());
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
    		}
    	}
    }
    
    public void gotToSubcibePageForUpdate(String username) {
    	if(username == null) return;
    	try {
        	FacesContext fContext = FacesContext.getCurrentInstance();
        	ExternalContext extContext = fContext.getExternalContext();
        	try {
    			extContext.redirect("inscription.xhtml?username="+username);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Une erreur interne s'est produite !", null));
		}
    }
    
    public void getUserInformationForUpdate(String username) {
    	if(username != null && username.equals("") == false && CommonUtilisateurBean.isAdminOrSelProfilOrRedirect(username)){
    		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        	session.setAttribute("forUpdate", true);
            this.utilisateur = utilisateurDao.findByUsername(username);
            this.usernameForUpdate = username;
    	}
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    
    public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

    public String getUsernameForUpdate() {
		return usernameForUpdate;
	}


	public void setUsernameForUpdate(String usernameForUpdate) {
		this.usernameForUpdate = usernameForUpdate;
	}


	private void initialiserDateInscription() {
        Timestamp date = new Timestamp( System.currentTimeMillis() );
        utilisateur.setRegisteredDate( date );
    }
    
    @PreDestroy
    private void beforeDesctroy(){
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	session.setAttribute("forUpdate", false);
    }
    
}