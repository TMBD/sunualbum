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

import beans.persistent.Album;
import beans.persistent.Utilisateur;
import dao.AlbumDao;
import dao.UtilisateurDao;


@ManagedBean
@ViewScoped
public class CommonAlbumBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    AlbumDao albumDao;
    
    @EJB
    UtilisateurDao utilisateurDao;
    
    private List<Album> allAlbums;
    private List<Album> allPublicAlbums;
    private Album albumDetails = null;
    


    public CommonAlbumBean() {
    	allAlbums = new ArrayList<Album>();
    	allPublicAlbums = new ArrayList<Album>();
    }
    
    @PostConstruct
    public void initializeAllAlbums(){
    	//if(CommonUtilisateurBean.isAdminOrRedirect()) {
    		this.allAlbums = albumDao.findAll();
    	//}
    }
    
    public void initializeAllPublicAlbums(){
    	this.allPublicAlbums = albumDao.findAllPublicAcces();
    }
    
    public void initializeAlbumDetailsById(int id){
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
    	Album a = albumDao.findById(id);
    	if(userHasAccesToAlbumOrRedirect(utilisateurCourant, a)) {
    		this.albumDetails = a;
    	}
    }

    
    public void supprimerAlbumById(int id) {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
    	Album a = albumDao.findById(id);
    	if(userCanModifyAlbumOrRedirect(utilisateurCourant, a)) {
    		albumDao.remove(a.getId());
    	}
    	
    }
    
    

	public List<Album> getAllAlbums() {
        return this.allAlbums;
    }
	
	public void setAllAlbums(List<Album> allAlbums) {
        this.allAlbums = allAlbums;
    }

	public List<Album> getAllPublicAlbums() {
        return this.allPublicAlbums;
    }
	
	public void setAllPublicAlbums(List<Album> allPublicAlbums) {
        this.allPublicAlbums = allPublicAlbums;
    }
	
	public Album getAlbumDetails() {
        return this.albumDetails;
    }
	
	public void setAlbumDetails(Album albumDetails) {
        this.albumDetails = albumDetails;
    }
    
	public static boolean userCanModifyAlbum(Utilisateur u, Album a ){
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
    	if(utilisateurCourant != null && utilisateurCourant.getEstAdmin()) return true;
    	if(a.getProprietaire().equals(u)) return true;
    	return false;
    }
	
	
	public static boolean userCanModifyAlbumOrRedirect(Utilisateur u, Album a ){
		if(userCanModifyAlbum(u, a) == false) {
    		FacesContext fContext = FacesContext.getCurrentInstance();
        	ExternalContext extContext = fContext.getExternalContext();
        	try {
    			extContext.redirect("index_albums.xhtml");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	return false;
    	}
    	
    	return true;
    }
	
	
    public static boolean userHasAccesToAlbum(Utilisateur u, Album a ){
    	if(a.getPrive() == false) return true;
    	if(userCanModifyAlbum(u, a)) return true;
    	return a.getUtilisateursAutorises().contains(u);
    }
    
    public static boolean userHasAccesToAlbumOrRedirect(Utilisateur u, Album a ){
    	if(userHasAccesToAlbum(u, a) == false) {
    		FacesContext fContext = FacesContext.getCurrentInstance();
        	ExternalContext extContext = fContext.getExternalContext();
        	try {
    			extContext.redirect("index_albums.xhtml");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	return false;
    	}
    	
    	return true;
    }
    
    
    

    
    
}