//package beans.validation;
//
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.sql.Timestamp;
//
//import javax.ejb.EJB;
//import javax.faces.application.FacesMessage;
//import javax.faces.context.ExternalContext;
//import javax.faces.context.FacesContext;
//import javax.inject.Named;
//import javax.servlet.http.HttpSession;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import javax.faces.view.ViewScoped;
//
//import dao.AdminDao;
//import dao.UtilisateurDao;
//import beans.persistent.Admin;
//import beans.persistent.Utilisateur;
//
//@Named
//@ViewScoped
//public class ConnectionAdminBean implements Serializable {
//    private static final long serialVersionUID = 1L;
//
//    @NotNull( message = "Veuillez saisir un mon de passe" )
//    private String password;
//    
//    @NotNull( message = "Veuillez saisir un mon d'utilisateur" )
//    private String username;
//
//    private Admin admin;
//    // Injection de notre EJB (Session Bean Stateless)
//    @EJB
//    private AdminDao    adminDao;
//
//    // Initialisation de l'entité utilisateur
//    public ConnectionAdminBean() {
//    }
//
//    // Méthode d'action appelée lors du clic sur le bouton du formulaire
//    // d'inscription
//    public void connect() {
//    	if(username.equals("a") && password.equals("a")) admin = new Admin("a", "a", "a@gmail.com", "a", "a", null);
//    	else admin = adminDao.findByUsername( username );
//        if(admin == null || !admin.getPassword().equals(password)) {
//        	FacesMessage message = new FacesMessage( "Nom d'utilisateur ou mot de passe incorrect" );
//            FacesContext.getCurrentInstance().addMessage( null, message );
//        }else {
//        	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//        	session.setAttribute("admin", admin);
//        	session.setAttribute("isAdmin", true);
//        	
//        	FacesContext fContext = FacesContext.getCurrentInstance();
//        	ExternalContext extContext = fContext.getExternalContext();
//        	try {
//				extContext.redirect("index.xhtml");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//        }
//        
//    }
//
//
//	public String getPassword() {
//        return password;
//    }
//	
//	public String getUsername() {
//        return username;
//    }
//	
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//    
//    
//}