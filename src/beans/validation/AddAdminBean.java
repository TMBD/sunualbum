package beans.validation;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import beans.persistent.Admin;
import dao.AdminDao;

@Named
@ViewScoped
public class AddAdminBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Admin       admin;

    // Injection de notre EJB (Session Bean Stateless)
    @EJB
    private AdminDao    adminDao;

    // Initialisation de l'entité utilisateur
    public AddAdminBean() {
    	admin = new Admin();
    }

    // Méthode d'action appelée lors du clic sur le bouton du formulaire
    // d'inscription
    public void subcribe() {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Admin connectedAdmin = (Admin) session.getAttribute("admin");
    	if(connectedAdmin != null && ((connectedAdmin.getUsername().equals("a") && connectedAdmin.getPassword().equals("a")) || adminDao.exist(connectedAdmin.getUsername(), connectedAdmin.getPassword()))) {
    		initialiserDateInscription();
	        adminDao.add( admin );
	        FacesMessage message = new FacesMessage( "Admin ajouté avec succès" );
	        FacesContext.getCurrentInstance().addMessage( null, message );
    	}else {
    		FacesMessage message = new FacesMessage( "Vous devez être un administrateur pour pouvoir ajouter un autre administrateur !" );
            FacesContext.getCurrentInstance().addMessage( null, message );
    	}
        
    }

    public Admin getAdmin() {
        return admin;
    }

    private void initialiserDateInscription() {
        Timestamp date = new Timestamp( System.currentTimeMillis() );
        admin.setRegisteredDate( date );
    }
    
    
}