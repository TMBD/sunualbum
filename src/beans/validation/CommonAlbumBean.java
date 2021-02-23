package beans.validation;


import java.io.File;
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
    //private List<Album> allPublicAlbums;
    private Album albumDetails = null;
    private boolean userCanModify = false;
    private String motsCles = "";


    public CommonAlbumBean() {
    	allAlbums = new ArrayList<Album>();
    	//allPublicAlbums = new ArrayList<Album>();
    }
    
    
    
    public void initListAlbums(String username, String type) {
    	if(username == null || username.trim().equals("")) {
    		if(CommonUtilisateurBean.hasAuthenticated()) {
	    		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
		    	if(utilisateurCourant.getEstAdmin()) initializeForAllAlbums();
		    	else {
		    		initializeAccessibleAlbums(utilisateurCourant);
		    	}
	    	}else {
	    		initializeForPublicAlbums();
	    	}
    	}else{
    		if(type == null || type.trim().equals("")) {
    			CommonUtilisateurBean.hasAuthenticatedOrRedirect();
	    		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
		    	if(username.equals(utilisateurCourant.getUsername())) {
		    		initializeForOwnAlbums();
		    	}else {
		    		FacesContext fContext = FacesContext.getCurrentInstance();
		        	ExternalContext extContext = fContext.getExternalContext();
		        	try {
		    			extContext.redirect("index.xhtml");
		    		} catch (IOException e) {
		    			e.printStackTrace();
		    		}
		    	}
    		}else {
    			
    			Utilisateur otherUser = utilisateurDao.findByUsername(username);
    			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
    			if(otherUser != null && (otherUser.equals(utilisateurCourant) || utilisateurCourant.getEstAdmin()) ) {
    				if(type.equals("created")) {
    					initializeForOtherAlbums(otherUser);
        			}else if(type.equals("access")) {
        				initializeAccessibleAlbums(otherUser);
        			}
    			}else {
    				FacesContext fContext = FacesContext.getCurrentInstance();
    	        	ExternalContext extContext = fContext.getExternalContext();
    	        	try {
    	    			extContext.redirect("connexion.xhtml");
    	    		} catch (IOException e) {
    	    			e.printStackTrace();
    	    		}
    			}
		    	
    			
    			
    			
    			
    			
    		}
    		
    	}
    	
    	
    }
    
    private void initializeForOwnAlbums() {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
    	List<Album> allAlbumsList = albumDao.findAll();
    	if(allAlbumsList == null) return;
    	this.allAlbums = new ArrayList<Album>();
    	for (Album album : allAlbumsList) {
			if(album.getProprietaire().equals(utilisateurCourant)) {
				allAlbums.add(album);
			}
		}
		
	}
    
    
    private void initializeForOtherAlbums(Utilisateur otherUser) {
    	List<Album> allAlbumsList = albumDao.findAll();
    	this.allAlbums = new ArrayList<Album>();
    	if(allAlbumsList == null) return;
    	for (Album album : allAlbumsList) {
			if(album.getProprietaire().equals(otherUser)) {
				allAlbums.add(album);
			}
		}
	}



	private void initializeAccessibleAlbums(Utilisateur utilisateur) {
    	List<Album> allAlbumsList = albumDao.findAll();
    	if(allAlbumsList == null) return;
    	for (Album album : allAlbumsList) {
			if(userHasAccesToAlbum(utilisateur, album)) allAlbums.add(album);
		}
	}


	public void initializeForAllAlbums(){
    	if(CommonUtilisateurBean.isAdminOrRedirect()) {
    		this.allAlbums = albumDao.findAll();
    		if(this.allAlbums == null) this.allAlbums = new ArrayList<Album>();
    	}
    }
    
    public void initializeForPublicAlbums(){
    	this.allAlbums = albumDao.findAllPublicAcces();
    	if(this.allAlbums == null) this.allAlbums = new ArrayList<Album>();
    }
    
    public void initializeAlbumDetailsById(int id){
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
    	Album a = albumDao.findById(id);
    	if(userHasAccesToAlbumOrRedirect(utilisateurCourant, a)) {
    		this.albumDetails = a;
    		if(this.allAlbums == null) this.allAlbums = new ArrayList<Album>();
    		initStringMotsCles();
    	}
    }

    
    private void initStringMotsCles() {
    	System.out.println("mot cles : "+motsCles);
		this.motsCles = "";
		if(albumDetails == null) albumDetails = new Album();
		for (String mot : albumDetails.getMotsCles()) {
			this.motsCles += mot+" ";
		}
		
	}



	public void supprimerAlbumById(int id) {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
    	Album a = albumDao.findById(id);
    	if(userCanModifyAlbumOrRedirect(utilisateurCourant, a)) {
    		albumDao.remove(a.getId());
    		
    		String imageLocation = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\resources\\uploaded")+"\\album_images";
    		String oldFileName = a.getUri();
            File oldFile = new File(imageLocation+"\\"+oldFileName);
            oldFile.deleteOnExit();
            
    		FacesContext fContext = FacesContext.getCurrentInstance();
        	ExternalContext extContext = fContext.getExternalContext();
        	try {
    			extContext.redirect("index.xhtml");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	
    }
    
    
    public void supprimerAlbumBySession() {
    	if(this.albumDetails != null) {
    		supprimerAlbumById(this.albumDetails.getId());
    	}else {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Une erreur s'est produite lors de la suppression !", null));
    	}
    	
    }
    
    

	public List<Album> getAllAlbums() {
        return this.allAlbums;
    }
	
	public void setAllAlbums(List<Album> allAlbums) {
        this.allAlbums = allAlbums;
    }

//	public List<Album> getAllPublicAlbums() {
//        return this.allPublicAlbums;
//    }
//	
//	public void setAllPublicAlbums(List<Album> allPublicAlbums) {
//        this.allPublicAlbums = allPublicAlbums;
//    }
	
	public Album getAlbumDetails() {
        return this.albumDetails;
    }
	
	public void setAlbumDetails(Album albumDetails) {
        this.albumDetails = albumDetails;
    }
    
	public boolean getUserCanModify() {
		return userCanModify;
	}



	public void setUserCanModify(boolean userCanModify) {
		this.userCanModify = userCanModify;
	}



	public String getMotsCles() {
		return motsCles;
	}



	public void setMotsCles(String motsCles) {
		this.motsCles = motsCles;
	}



	public static boolean userCanModifyAlbumByUserAndAlbum(Utilisateur u, Album a ){
//    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
		if(u == null || a == null) return false;
		if(u.getEstAdmin()) return true;
		System.out.println("Album en questionoooooooooooooo "+a);
    	if(a.getProprietaire().equals(u)) return true;
    	return false;
    	
    }
	
	public boolean userCanModifyAlbumByUserSession(int albumId ){
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	try {
    		Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
        	
        	Album a = albumDao.findById(albumId);
        	return userCanModifyAlbumByUserAndAlbum(utilisateurCourant, a);
		} catch (Exception e) {
			return false;
		}
    	
    }
	
	
	public static boolean userCanModifyAlbumOrRedirect(Utilisateur u, Album a ){
		if(userCanModifyAlbumByUserAndAlbum(u, a) == false) {
    		FacesContext fContext = FacesContext.getCurrentInstance();
        	ExternalContext extContext = fContext.getExternalContext();
        	try {
    			extContext.redirect("index.xhtml");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	return false;
    	}
    	
    	return true;
    }
	
	public void initialiserAlbumDetailsOrRedirect(String username, int albumId) {
		Album a = albumDao.findById(albumId);
		Utilisateur u = utilisateurDao.findByUsername(username);
		userHasAccesToAlbumOrRedirect(u, a);
		this.albumDetails = a;
		this.userCanModify = userCanModifyAlbumByUserSession(this.albumDetails.getId());
		initStringMotsCles();
		
	}
	
	public void userHasAccesToAlbumByIdentifierOrRedirect(String username, int albumId) {
		Album a = albumDao.findById(albumId);
		Utilisateur u = utilisateurDao.findByUsername(username);
		userHasAccesToAlbumOrRedirect(u, a);
	}
	
    public static boolean userHasAccesToAlbum(Utilisateur u, Album a ){
    	if(a == null || u == null) return false;
    	if(a.getPrive() == false) return true;
    	if(userCanModifyAlbumByUserAndAlbum(u, a)) return true;
    	return a.getUtilisateursAutorises().contains(u);
    }
    
    public static boolean userHasAccesToAlbumOrRedirect(Utilisateur u, Album a ){
    	if(userHasAccesToAlbum(u, a) == false) {
    		FacesContext fContext = FacesContext.getCurrentInstance();
        	ExternalContext extContext = fContext.getExternalContext();
        	try {
    			extContext.redirect("index.xhtml");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	return false;
    	}
    	
    	return true;
    }
    
    
    public void goToAlbumModificationPage(int id){
    	FacesContext fContext = FacesContext.getCurrentInstance();
    	ExternalContext extContext = fContext.getExternalContext();
    	try {
			extContext.redirect("modifier_album.xhtml?albumId="+id);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    public void goToAlbumModificationPageBySession(){
    	if(this.albumDetails != null) {
    		goToAlbumModificationPage(this.albumDetails.getId());
    	}else {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Une erreur s'est produite lors de la redirection !", null));
    	}
    }
    

    
}