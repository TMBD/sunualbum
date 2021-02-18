package beans.validation;


import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;

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
    

    // Initialisation de l'entité utilisateur
    public SubcribeBean() {
        utilisateur = new Utilisateur();
    }

    // Méthode d'action appelée lors du clic sur le bouton du formulaire
    // d'inscription
    public void subcribe() {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Boolean forUpdate = (Boolean)session.getAttribute("forUpdate");
    	if(forUpdate == null || forUpdate == false) {
    		initialiserDateInscription();
            utilisateurDao.add( utilisateur );
            FacesMessage message = new FacesMessage( "Succès de l'inscription !" );
            FacesContext.getCurrentInstance().addMessage( null, message );
    	}else {
    		utilisateurDao.update( utilisateur );
    		Utilisateur utilisateurCourant = (Utilisateur) session.getAttribute("utilisateur");
    		if(utilisateurCourant.getEstAdmin() == false) {
    			session.setAttribute("utilisateur", utilisateur);
    		}
    		session.removeAttribute("forUpdate");
    		FacesContext fContext = FacesContext.getCurrentInstance();
        	ExternalContext extContext = fContext.getExternalContext();
        	try {
    			extContext.redirect("profil_utilisateur.xhtml?username="+utilisateur.getUsername());
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    public void gotToSubcibePageForUpdate(String username) {
    	if(username == null) return;
    	try {
    		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        	session.setAttribute("forUpdate", true);
        	
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
    	if(username != null && CommonUtilisateurBean.isAdminOrSelProfilOrRedirect(username)){
    		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        	boolean forUpdate = (boolean) session.getAttribute("forUpdate");
        	if(forUpdate) {
            	this.utilisateur = utilisateurDao.findByUsername(username);
            	System.out.println("this is a user : "+this.utilisateur);
        	}
    	}
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    private void initialiserDateInscription() {
        Timestamp date = new Timestamp( System.currentTimeMillis() );
        utilisateur.setRegisteredDate( date );
    }
    
    @PreDestroy
    private void beforeDesctroy(){
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	session.removeAttribute("forUpdate");
    }
    
    
}