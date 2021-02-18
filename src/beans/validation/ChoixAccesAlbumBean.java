package beans.validation;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import beans.persistent.Album;
import beans.persistent.Utilisateur;
import beans.utils.UtilisateurAcces;
import dao.AlbumDao;
import dao.UtilisateurDao;


@ManagedBean
@ViewScoped
public class ChoixAccesAlbumBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    UtilisateurDao utilisateurDao;
    
    @EJB 
    AlbumDao albumDao;
    
    private Album           album;
    private List<UtilisateurAcces> utilisateursAcces;
    


    // Initialisation du bean fichier
    public ChoixAccesAlbumBean() {
        album = new Album();
        utilisateursAcces = new ArrayList<UtilisateurAcces>();
    }
    
    @PostConstruct
    public void initialize(){
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	album = (Album)session.getAttribute("newAlbum");
    	if(album == null) {
    		FacesContext fContext = FacesContext.getCurrentInstance();
        	ExternalContext extContext = fContext.getExternalContext();
        	try {
    			extContext.redirect("creer_album.xhtml");
    			return;
    		} catch (IOException e) {
    			e.printStackTrace();
    		} 
    	}
    	initialiserUtilisateurAcces();
    }

    private void initialiserUtilisateurAcces() {
    	List<Utilisateur> utilisateurs = utilisateurDao.findAll();
    	utilisateurs.remove(album.getProprietaire());
    	
    	for (Utilisateur utilisateur : utilisateurs) {
			this.utilisateursAcces.add(new UtilisateurAcces(utilisateur, hasAccess(utilisateur.getUsername())));
		}
    	System.out.println("Utilisateurs sans proprio = "+utilisateurs);
		
	}

	public void add() throws IOException {
    	Set<Utilisateur> u = getAllAuthorisedUsers();
    	album.setUtilisateursAutorises(u);
    	albumDao.update(album);
    	FacesContext fContext = FacesContext.getCurrentInstance();
    	ExternalContext extContext = fContext.getExternalContext();
    	try {
    		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        	session.setAttribute("newAlbum", null);
			extContext.redirect("index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}    	
    }
    
    private Set<Utilisateur> getAllAuthorisedUsers() {
    	HashSet<Utilisateur> result = new HashSet<Utilisateur>();
		for ( UtilisateurAcces utilisateurAcces : this.utilisateursAcces) {
			if(utilisateurAcces.getAcces()) result.add(utilisateurAcces.getUtilisateur());
		}
		return result;
	}

	public boolean hasAccess(String username) {
    	return Utilisateur.exist(username, album.getUtilisateursAutorises());
    }
    
    
    public Album getAlbum() {
        return album;
    }

    public void setAlbum( Album album ) {
        this.album = album;
    }
    
    public List<UtilisateurAcces> getUtilisateursAcces() {
        return this.utilisateursAcces;
    }

    public void setUtilisateurs( List<UtilisateurAcces> utilisateursAcces ) {
        this.utilisateursAcces = utilisateursAcces;
    }
    
    
    
}