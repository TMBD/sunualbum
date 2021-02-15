package utils.validator;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.custom.fileupload.UploadedFile;

@FacesValidator( value = "tailleMaxImageValidator" )
public class TailleMaxImageValidator implements Validator {

    private static final long   TAILLE_MAX_FICHIER = 5 * 1024 * 1024;                          // 1Mo
    private static final String MESSAGE_ERREUR     = "La taille maximale autorisée est de 5Mo";

    public void validate( FacesContext context, UIComponent component, Object value ) throws ValidatorException {
        if ( value != null && ( (UploadedFile) value ).getSize() > TAILLE_MAX_FICHIER ) {
            throw new ValidatorException( new FacesMessage( FacesMessage.SEVERITY_ERROR, MESSAGE_ERREUR, null ) );
        }
    }
}