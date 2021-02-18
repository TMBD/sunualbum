package beans.utils;

import beans.persistent.Utilisateur;

public class UtilisateurAcces {
	
	Utilisateur utilisateur;
	Boolean acces;
	
	
	public UtilisateurAcces(Utilisateur utilisateur, Boolean acces) {
		super();
		this.utilisateur = utilisateur;
		this.acces = acces;
	}


	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}


	public Boolean getAcces() {
		return acces;
	}


	public void setAcces(Boolean acces) {
		this.acces = acces;
	}
	

}
