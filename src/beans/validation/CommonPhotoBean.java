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
import beans.persistent.Photo;
import beans.persistent.Utilisateur;
import dao.AlbumDao;
import dao.PhotoDao;
import dao.UtilisateurDao;


@ManagedBean
@ViewScoped
public class CommonPhotoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    AlbumDao albumDao;
    
    @EJB
    UtilisateurDao utilisateurDao;
    
    @EJB
    PhotoDao photoDao;
    
    private List<Photo> allPhotos;
    private Photo photoDetails = null;
    private boolean userCanModify = false;
    private String motsCles = "";


    public CommonPhotoBean() {
    	this.allPhotos = new ArrayList<Photo>();
    }
    
    
    
    public void initListPhotos(String albumId, String username, String type) {
    	if(albumId != null && albumId.trim().equals("") == false) {
    		initializePhotosForAlbum(albumId);
    	}else {
    		if(username == null || username.trim().equals("")) {
        		if(CommonUtilisateurBean.hasAuthenticated()) {
    	    		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    		    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
    		    	if(utilisateurCourant.getEstAdmin()) initializeForAllPhotos();
    		    	else {
    		    		initializeAccessiblePhotos(utilisateurCourant);
    		    	}
    	    	}else {
    	    		initializeForPublicPhotos();
    	    	}
        	}else{
        		if(type == null || type.trim().equals("")) {
        			CommonUtilisateurBean.hasAuthenticatedOrRedirect();
    	    		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    		    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
    		    	if(username.equals(utilisateurCourant.getUsername())) {
    		    		initializeForOwnPhotos();
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
        					initializeForOtherPhotos(otherUser);
            			}else if(type.equals("access")) {
            				initializeAccessiblePhotos(otherUser);
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
    	
    	
    	
    }
    
    private void initializePhotosForAlbum(String albumId) {
    	int id = 0;
    	try {
    		id = Integer.parseInt(albumId);
    		
		} catch (Exception e) {
			FacesContext fContext = FacesContext.getCurrentInstance();
        	ExternalContext extContext = fContext.getExternalContext();
        	try {
    			extContext.redirect("index.xhtml");
    		} catch (IOException ioE) {
    			ioE.printStackTrace();
    		}
		}
    	
    	this.allPhotos = new ArrayList<Photo>();
    	Album album = albumDao.findById(id);
    	if(album != null) {
    		for (Photo p : album.getPhotos()) {
				this.allPhotos.add(p);
			}
    	}else {
    		this.allPhotos = new ArrayList<Photo>();
    	}
    	
	}



	private void initializeForOwnPhotos() {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
    	List<Photo> allPhotosList = photoDao.findAll();
    	this.allPhotos = new ArrayList<Photo>();
    	for (Photo photo : allPhotosList) {
			if(photo.getAlbum().getProprietaire().equals(utilisateurCourant)) {
				allPhotos.add(photo);
			}
		}
	}
    
    
    private void initializeForOtherPhotos(Utilisateur otherUser) {
    	if(otherUser == null) return;
    	List<Photo> allPhotosList = photoDao.findAll();
    	this.allPhotos = new ArrayList<Photo>();
    	for (Photo photo : allPhotosList) {
			if(photo.getAlbum().getProprietaire().equals(otherUser)) {
				allPhotos.add(photo);
			}
		}
	}



	private void initializeAccessiblePhotos(Utilisateur utilisateur) {
		if(utilisateur == null) return;
		this.allPhotos = photoDao.findAllByUtilisateurAcces(utilisateur);
		if(this.allPhotos != null) {
			this.allPhotos.addAll(photoDao.findAllByProprietaire(utilisateur));
		}else {
			this.allPhotos = photoDao.findAllByProprietaire(utilisateur);
		}
		
	}


	public void initializeForAllPhotos(){
    	if(CommonUtilisateurBean.isAdminOrRedirect()) {
    		this.allPhotos = photoDao.findAll();
    	}
    }
    
    public void initializeForPublicPhotos(){
    	this.allPhotos = photoDao.findAllPublicAcces();
    }
    
    public void initializePhotoDetailsById(int id){
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
    	Photo p = photoDao.findById(id);
    	if(userHasAccesToPhotoOrRedirect(utilisateurCourant, p)) {
    		this.photoDetails = p;
    		initStringMotsCles();
    	}
    }

    
    private void initStringMotsCles() {
		this.motsCles = "";
		if(photoDetails == null) return;
		for (String mot : photoDetails.getMotsCles()) {
			this.motsCles += mot+" ";
		}
		
	}



	public void supprimerPhotoById(int id) {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
    	Photo p = photoDao.findById(id);
    	if(userCanModifyPhotoOrRedirect(utilisateurCourant, p)) {
    		photoDao.remove(p.getId());
    		String imageLocation = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\resources\\uploaded")+"\\photo_images";
    		String oldFileName = p.getUri();
            File oldFile = new File(imageLocation+"\\"+oldFileName);
            oldFile.deleteOnExit();
            
    		FacesContext fContext = FacesContext.getCurrentInstance();
        	ExternalContext extContext = fContext.getExternalContext();
        	try {
    			extContext.redirect("index_photos.xhtml");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    
    public void supprimerPhotoBySession() {
    	if(this.photoDetails != null) {
    		supprimerPhotoById(this.photoDetails.getId());
    	}else {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Une erreur s'est produite lors de la suppression !", null));
    	}
    	
    }
    
    

	public List<Photo> getAllPhotos() {
        return this.allPhotos;
    }
	
	public void setAllPhotos(List<Photo> allPhotos) {
        this.allPhotos = allPhotos;
    }


	public Photo getPhotoDetails() {
        return this.photoDetails;
    }
	
	public void setPhotoDetails(Photo photoDetails) {
        this.photoDetails = photoDetails;
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



	public static boolean userCanModifyPhotoByUserAndPhoto(Utilisateur u, Photo p ){
		if(u == null || p == null) return false;
		if(u.getEstAdmin()) return true;
    	if(p.getAlbum().getProprietaire().equals(u)) return true;
    	return false;
    }
	
	public boolean userCanModifyPhotoByUserSession(int photoId ){
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	try {
    		Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
        	
        	Photo p = photoDao.findById(photoId);
        	return userCanModifyPhotoByUserAndPhoto(utilisateurCourant, p);
		} catch (Exception e) {
			return false;
		}
    	
    }
	
	
	public static boolean userCanModifyPhotoOrRedirect(Utilisateur u, Photo p ){
		if(userCanModifyPhotoByUserAndPhoto(u, p) == false) {
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
	
	public void initialiserPhotoDetailsOrRedirect(String username, int photoId) {
		Photo p = photoDao.findById(photoId);
		Utilisateur u = utilisateurDao.findByUsername(username);
		userHasAccesToPhotoOrRedirect(u, p);
		this.photoDetails = p;
		this.userCanModify = userCanModifyPhotoByUserSession(this.photoDetails.getId());
		initStringMotsCles();
		
	}
	
	public void userHasAccesToPhotoByIdentifierOrRedirect(String username, int photoId) {
		Photo p = photoDao.findById(photoId);
		Utilisateur u = utilisateurDao.findByUsername(username);
		userHasAccesToPhotoOrRedirect(u, p);
	}
	
    public static boolean userHasAccesToPhoto(Utilisateur u, Photo p ){
    	if(p.getAlbum().getPrive() == false) return true;
    	if(userCanModifyPhotoByUserAndPhoto(u, p)) return true;
    	return p.getAlbum().getUtilisateursAutorises().contains(u);
    }
    
    public static boolean userHasAccesToPhotoOrRedirect(Utilisateur u, Photo p ){
    	if(userHasAccesToPhoto(u, p) == false) {
    		FacesContext fContext = FacesContext.getCurrentInstance();
        	ExternalContext extContext = fContext.getExternalContext();
        	try {
    			extContext.redirect("index_photos.xhtml");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	return false;
    	}
    	
    	return true;
    }
    
    
    public void goToPhotoModificationPage(int id){
    	FacesContext fContext = FacesContext.getCurrentInstance();
    	ExternalContext extContext = fContext.getExternalContext();
    	try {
			extContext.redirect("modifier_photo.xhtml?photoId="+id);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    public void goToPhotoModificationPageBySession(){
    	if(this.photoDetails != null) {
    		goToPhotoModificationPage(this.photoDetails.getId());
    	}else {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Une erreur s'est produite lors de la redirection !", null));
    	}
    }
    
}