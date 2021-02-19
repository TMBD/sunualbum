package beans.validation;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import beans.persistent.Utilisateur;
import dao.UtilisateurDao;


@ManagedBean
@ViewScoped
public class CommonUtilisateurBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    UtilisateurDao utilisateurDao;
    
    private List<Utilisateur> utilisateurs;
    private Utilisateur utilisateurProfilDetails = null;
    


    public CommonUtilisateurBean() {
        utilisateurs = new ArrayList<Utilisateur>();
    }
    
    
    public void initializeAllUtilisateur(){
    	if(isAdminOrRedirect()) {
    		utilisateurs = utilisateurDao.findAll();
    	}
    	
    }
    
    
    public void initialiserUtilisateurProfilDetailsByUsername(String username) {
    	if(isAdminOrSelProfilOrRedirect(username)) {
    		this.utilisateurProfilDetails = utilisateurDao.findByUsername(username);
    	}
    }

    
    public void supprimerUtilisateurByUsername(String username) {
    	try {
    		if(isAdminOrSelProfilOrRedirect(username)) {
        		utilisateurDao.delete(username);
        		
        		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
            	if(utilisateurCourant.getEstAdmin()) {
            		FacesContext fContext = FacesContext.getCurrentInstance();
                	ExternalContext extContext = fContext.getExternalContext();
                	try {
            			extContext.redirect("list_utilisateur.xhtml");
            		} catch (IOException e) {
            			e.printStackTrace();
            		}
            	}else {
            		FacesContext fContext = FacesContext.getCurrentInstance();
                	ExternalContext extContext = fContext.getExternalContext();
                	try {
                		session.removeAttribute("utilisateur");
            			extContext.redirect("index.xhtml");
            		} catch (IOException e) {
            			e.printStackTrace();
            		}
            	}
        	}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Une erreur s'est produite lors de la suppression !", null));
		}
    	
    }
    
    

	public List<Utilisateur> getUtilisateurs() {
        return this.utilisateurs;
    }

    public void setUtilisateurs( List<Utilisateur> utilisateurs ) {
        this.utilisateurs = utilisateurs;
    }
    
    public Utilisateur getUtilisateurProfilDetails() {
        return this.utilisateurProfilDetails;
    }

    public void setUtilisateurProfilDetails( Utilisateur utilisateur ) {
        this.utilisateurProfilDetails = utilisateur;
    }
    
    public static boolean isAdminOrRedirect() {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
    	if(utilisateurCourant == null || utilisateurCourant.getEstAdmin() == false) {
    		FacesContext fContext = FacesContext.getCurrentInstance();
        	ExternalContext extContext = fContext.getExternalContext();
        	try {
    			extContext.redirect("connexion.xhtml");
    			return false;
    		} catch (IOException e) {
    			e.printStackTrace();
    		} 
    	}
    	return true;
    }
    
    public static boolean isAdminOrSelProfilOrRedirect(String username) {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
    	if(utilisateurCourant != null && (utilisateurCourant.getEstAdmin() || utilisateurCourant.getUsername().equals(username))) {
    		return true;
    	}else {
    		FacesContext fContext = FacesContext.getCurrentInstance();
        	ExternalContext extContext = fContext.getExternalContext();
        	try {
    			extContext.redirect("connexion.xhtml");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	return false;
    	}
    	
	}
    
    public static boolean hasAuthenticated() {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	try {
    		Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
        	if(utilisateurCourant != null) {
        		return true;
        	}
            return false;
		} catch (Exception e) {
			return false;
		}
    	
    }
    
    public static void hasAuthenticatedOrRedirect() {
    	if(hasAuthenticated()) {
    		return;
    	}else {
    		FacesContext fContext = FacesContext.getCurrentInstance();
        	ExternalContext extContext = fContext.getExternalContext();
        	try {
    			extContext.redirect("connexion.xhtml");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	return;
    	}
    }
    
    
}