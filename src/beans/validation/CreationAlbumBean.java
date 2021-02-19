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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import beans.persistent.Album;
import beans.persistent.Utilisateur;
import dao.AlbumDao;
import utils.PropertiesUtil;


@ManagedBean
@RequestScoped
public class CreationAlbumBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    AlbumDao albumDao;
    
    private Album           album;
    private String chaineMotCle;
    
    @NotNull( message = "Merci de sélectionner un fichier à envoyer" )
    private UploadedFile fichier;

    // Initialisation du bean fichier
    public CreationAlbumBean() {
        album = new Album();
    }

    public void save() throws IOException {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Utilisateur proprietaire = (Utilisateur)session.getAttribute("utilisateur");
    	if(proprietaire == null) proprietaire = (Utilisateur)session.getAttribute("admin"); 
    	
    	if(proprietaire != null) {
    		String nomFichier = FilenameUtils.getName( fichier.getName() );
            String tailleFichier = FileUtils.byteCountToDisplaySize( fichier.getSize() );
            String typeFichier = fichier.getContentType();
            byte[] contenuFichier = fichier.getBytes();
            

            if(typeFichier.contains("image")) {
            	
            	/////////////////////////////////////////////
            	// Prepare filename prefix and suffix for an unique filename in upload folder.
                String prefix = FilenameUtils.getBaseName(fichier.getName());
                String suffix = FilenameUtils.getExtension(fichier.getName());
                
                // Prepare file and outputstream.
                File file = null;
                OutputStream output = null;
                
                try {
                    // Create file with unique name in upload folder and write to it.
                	PropertiesUtil configProperties = new PropertiesUtil("C:\\Users\\thier\\Documents\\gitclones\\dgi_workspace\\jee\\sunualbum\\build\\classes\\utils\\config.properties");
                	//String imageLocation = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\resources\\uploaded")+"\\album_images";
                	String imageLocation = configProperties.getValue("location.album");
                	System.out.println(imageLocation);
                	file = File.createTempFile(prefix + "_", "." + suffix, new File(imageLocation));
                    output = new FileOutputStream(file);
                    IOUtils.copy(fichier.getInputStream(), output);
                    String fileName = file.getName();
                    
                    initialiserDateCreation();
                    initialiserMotsCles();
                    album.setUri(fileName);
                	album.setProprietaire(proprietaire);
                	
                	session.setAttribute("newAlbum", album);

                	albumDao.add(album);
                	
                    //SETTING THINGS FOR REDIRECTION
                    FacesContext fContext = FacesContext.getCurrentInstance();
                	ExternalContext extContext = fContext.getExternalContext();
                	
                    if(album.getPrive()) {
                    	try {
            				extContext.redirect("choix_acces_album.xhtml");
            			} catch (IOException e) {
            				e.printStackTrace();
            			}
                    }else {
                    	try {
            				extContext.redirect("index.xhtml");
            			} catch (IOException e) {
            				e.printStackTrace();
            			}
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

    	}else { //absence de session utilisateur et admin
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Vous devez etre authentifié pour pourvoir creer un album", null) ;
        	FacesContext.getCurrentInstance().addMessage( null, message );
    	}
    	
        
        
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
        album.setDateCreation( date );
    }
    
    void initialiserMotsCles() {
    	String[] splitedKeyWords = chaineMotCle.split(" ");
    	ArrayList<String> listMotsCles = new ArrayList<String>();
    	for (int i = 0; i < splitedKeyWords.length; i++) {
			listMotsCles.add(splitedKeyWords[i]);
		}
    	
    	album.setMotsCles(listMotsCles);
    }
}