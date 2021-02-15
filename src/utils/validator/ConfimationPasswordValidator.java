package utils.validator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.application.FacesMessage;


@FacesValidator( value = "confimationPasswordValidator" )
public class ConfimationPasswordValidator implements Validator {


    private static final String CHAMP_MOT_DE_PASSE       = "passwordComponent";
    private static final String MOTS_DE_PASSE_DIFFERENTS = "Le mot de passe et la confirmation doivent être identiques.";

    @Override
    public void validate( FacesContext context, UIComponent component, Object value ) throws ValidatorException {
        /*
         * Récupération de l'attribut mot de passe parmi la liste des attributs
         * du composant confirmation
         */
        UIInput composantMotDePasse = (UIInput) component.getAttributes().get( CHAMP_MOT_DE_PASSE );
        /*
         * Récupération de la valeur du champ, c'est-à-dire le mot de passe
         * saisi
         */
        String motDePasse = (String) composantMotDePasse.getValue();
        /* Récupération de la valeur du champ confirmation */
        String confirmation = (String) value;

        if ( confirmation != null && !confirmation.equals( motDePasse ) ) {
            /*
             * Envoi d'une exception contenant une erreur de validation JSF
             * initialisée avec le message destiné à l'utilisateur, si les mots
             * de passe sont différents
             */
            throw new ValidatorException(new FacesMessage( FacesMessage.SEVERITY_ERROR, MOTS_DE_PASSE_DIFFERENTS, null ) );
        }
    }

}
