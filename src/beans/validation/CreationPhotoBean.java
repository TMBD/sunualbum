 package beans.validation;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import beans.persistent.Album;
import beans.persistent.Photo;
import beans.persistent.Utilisateur;
import dao.AlbumDao;
import dao.PhotoDao;


@ManagedBean
@ViewScoped
public class CreationPhotoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    AlbumDao albumDao;
    
    @EJB
    PhotoDao photoDao;
    
    private Album album;
    private Photo photo;
    private String chaineMotCle;
    
    @NotNull( message = "Veuillez selectionner l'image de la photo !" )
    private UploadedFile fichier;

    public CreationPhotoBean() {
        this.album = new Album();
        this.photo = new Photo();
    }
    
    public void initAlbum(int albumId) {
    	this.album = albumDao.findById(albumId);
    }

    public void save() throws IOException {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Utilisateur proprietaire = (Utilisateur)session.getAttribute("utilisateur");
    	if(proprietaire != null && this.album.getProprietaire().equals(proprietaire)) {
            String typeFichier = fichier.getContentType();
            

            if(typeFichier.contains("image")) {
            	
            	/////////////////////////////////////////////
            	// Prepare filename prefix and suffix for an unique filename in upload folder.
                String prefix = FilenameUtils.getBaseName(fichier.getName());
                String suffix = FilenameUtils.getExtension(fichier.getName());
                
                // Prepare file and outputstream.
                File file = null;
                OutputStream output = null;
                
                try {
                	String imageLocation = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\resources\\uploaded")+"\\photo_images";
                	
                	file = File.createTempFile(prefix + "_", "." + suffix, new File(imageLocation));
                	
                    output = new FileOutputStream(file);
                    IOUtils.copy(fichier.getInputStream(), output);
                    String fileName = file.getName();
                    
                    initialiserDateCreation();
                    initialiserMotsCles();
                    photo.setUri(fileName);
                    
                	photo.setAlbum(this.album);
                	

                	photoDao.add(photo);
                	
                    //SETTING THINGS FOR REDIRECTION
                    FacesContext fContext = FacesContext.getCurrentInstance();
                	ExternalContext extContext = fContext.getExternalContext();
                	try {
                		extContext.redirect("photo_details.xhtml?photoId="+this.photo.getId());
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
                    
                } catch (IOException e) {
                    if (file != null) file.delete();

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "Une erreur s'est produite lors de l'enregistrement de l'image.", null));
                    e.printStackTrace();
                } finally {
                    IOUtils.closeQuietly(output);
                }
            	
            	///////////////////////////
            	
            }else {
            	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Le fichier doit être une image !", null) ;
            	FacesContext.getCurrentInstance().addMessage( null, message );
            }

    	}else if(proprietaire == null){
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Vous devez etre authentifié pour pourvoir creer un album !", null) ;
        	FacesContext.getCurrentInstance().addMessage( null, message );
    	} else { //absence de session utilisateur et admin
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Vous ne pouvez creer que des photos de votre propre album !", null) ;
        	FacesContext.getCurrentInstance().addMessage( null, message );
    	}
    	
        
        
    }
    

    public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public Album getAlbum() {
        return album;
    }

    public void setAlbum( Album album ) {
        this.album = album;
    }
    
    public String getChaineMotCle() {
        return chaineMotCle;
    }

    public void setChaineMotCle( String chaineMotCle ) {
        this.chaineMotCle = chaineMotCle;
    }
    
    
    
    public UploadedFile getFichier() {
        return fichier;
    }

    public void setFichier( UploadedFile fichier ) {
        this.fichier = fichier;
    }
    
    private void initialiserDateCreation() {
        Timestamp date = new Timestamp( System.currentTimeMillis() );
        this.photo.setDateCreation( date );
    }
    
    void initialiserMotsCles() {
    	String[] splitedKeyWords = chaineMotCle.split(" ");
    	ArrayList<String> listMotsCles = new ArrayList<String>();
    	for (int i = 0; i < splitedKeyWords.length; i++) {
			listMotsCles.add(splitedKeyWords[i]);
		}
    	
    	photo.setMotsCles(listMotsCles);
    }
}