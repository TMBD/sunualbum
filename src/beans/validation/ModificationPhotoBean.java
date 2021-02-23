package beans.validation;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import beans.persistent.Photo;
import beans.persistent.Utilisateur;
import dao.PhotoDao;


@ManagedBean
@ViewScoped
public class ModificationPhotoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EJB
    PhotoDao photoDao;
    
    private Photo           photo;
    private String chaineMotCle;
    
    private UploadedFile fichier = null;

    public ModificationPhotoBean() {
        this.photo = new Photo();
    }

    
    public void init(int photoId){
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
    	Photo p = photoDao.findById(photoId);
    	CommonPhotoBean.userCanModifyPhotoOrRedirect(utilisateurCourant, p);
    	this.photo = p;
    	String resultatMotsCle = "";
    	for (String mot : p.getMotsCles()) {
			resultatMotsCle += " " + mot;
		}
    	
    	this.chaineMotCle = resultatMotsCle;
    }
    
    
    public void save() throws IOException {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Utilisateur utilisateurCourant = (Utilisateur)session.getAttribute("utilisateur");
    	CommonPhotoBean.userCanModifyPhotoOrRedirect(utilisateurCourant, this.photo);
    	
    	if(utilisateurCourant != null) {
            
            if(fichier != null) {
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
                        
                        String oldFileName = this.photo.getUri();
                        File oldFile = new File(imageLocation+"\\"+oldFileName);
                        oldFile.deleteOnExit();
                        
                        
                        this.photo.setUri(fileName);
                        
                    } catch (IOException e) {
                        if (file != null) file.delete();

                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "Une erreur s'est produite lors de l'enregistrement de l'image.", null));

                        
                        e.printStackTrace();
                    } finally {
                        IOUtils.closeQuietly(output);
                    }
                   
                        
                }else {
                	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Le fichier doit être une image !", null) ;
                	FacesContext.getCurrentInstance().addMessage( null, message );
                }
            }
            
            
            initialiserMotsCles();
            initialiserDateModification();
        	photoDao.update(this.photo);
        	
            //SETTING THINGS FOR REDIRECTION
            FacesContext fContext = FacesContext.getCurrentInstance();
        	ExternalContext extContext = fContext.getExternalContext();
        	try {
        		extContext.redirect("photo_details.xhtml?photoId="+this.photo.getId());
			} catch (IOException e) {
				e.printStackTrace();
			}
            
    	}else { //absence de session utilisateur et admin
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Vous devez etre authentifié pour pourvoir modifier une photo", null) ;
        	FacesContext.getCurrentInstance().addMessage( null, message );
    	}
        
    }
    

    public Photo getPhoto() {
        return this.photo;
    }

    public void setPhoto( Photo photo ) {
        this.photo = photo;
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
    
    private void initialiserDateModification() {
        Timestamp date = new Timestamp( System.currentTimeMillis() );
        this.photo.setDateMiseAJour( date );
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
