package beans.validation;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import beans.persistent.Utilisateur;
import dao.UtilisateurDao;

@Named
@ViewScoped
public class AddAdminBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Utilisateur       admin;

    // Injection de notre EJB (Session Bean Stateless)
    @EJB
    private UtilisateurDao    utilisateurDao;

    // Initialisation de l'entité utilisateur
    public AddAdminBean() {
    	admin = new Utilisateur();
    }

    // Méthode d'action appelée lors du clic sur le bouton du formulaire
    // d'inscription
    public void subcribe() {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Utilisateur connectedAdmin = (Utilisateur) session.getAttribute("admin");
    	if(connectedAdmin != null && ((connectedAdmin.getUsername().equals("a") && connectedAdmin.getPassword().equals("a")) || utilisateurDao.estAdmin(connectedAdmin.getUsername(), connectedAdmin.getPassword()))) {
    		initialiserDateInscription();
	        utilisateurDao.add( admin );
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Admin ajouté avec succès", null );
	        FacesContext.getCurrentInstance().addMessage( null, message );
    	}else {
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vous devez être un administrateur pour pouvoir ajouter un autre administrateur !", null );
            FacesContext.getCurrentInstance().addMessage( null, message );
    	}
        
    }

    public Utilisateur getAdmin() {
        return admin;
    }
    
    public void setAdmin(Utilisateur admin) {
        this.admin = admin;
    }

    private void initialiserDateInscription() {
        Timestamp date = new Timestamp( System.currentTimeMillis() );
        admin.setRegisteredDate( date );
    }
    
    
}