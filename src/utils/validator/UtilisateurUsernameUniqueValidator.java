package utils.validator;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.DAOException;
import dao.UtilisateurDao;

@ManagedBean
@RequestScoped
public class UtilisateurUsernameUniqueValidator implements Validator{

	private static final String USERNAME_EXIST = "Ce nom d'utilisateur existe d�j�";


    @EJB
    private UtilisateurDao      utilisateurDao;

    @Override
    public void validate( FacesContext context, UIComponent component, Object value ) throws ValidatorException {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	Boolean forUpdate = (Boolean)session.getAttribute("forUpdate");
    	if(forUpdate == null || forUpdate == false) {
    		
    		String username = (String) value;
            try {
                if (utilisateurDao.exist(username)) {
                    /*
                     * Si un nom d'utlisateur est retourn�e, alors on envoie une exception
                     * propre � JSF, qu'on initialise avec un FacesMessage de
                     * gravit� "Erreur" et contenant le message d'explication. Le
                     * framework va alors g�rer lui-m�me cette exception et s'en
                     * servir pour afficher le message d'erreur � l'utilisateur.
                     */
                    throw new ValidatorException( new FacesMessage( FacesMessage.SEVERITY_ERROR, USERNAME_EXIST, null ) );
                }
            } catch ( DAOException e ) {
                /*
                 * En cas d'erreur impr�vue �manant de la BDD, on pr�pare un message
                 * d'erreur contenant l'exception retourn�e, pour l'afficher �
                 * l'utilisateur ensuite.
                 */
                FacesMessage message = new FacesMessage( FacesMessage.SEVERITY_ERROR, e.getMessage(), null );
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.addMessage( component.getClientId( facesContext ), message );
            }
    		
    	}
        
    }

}
