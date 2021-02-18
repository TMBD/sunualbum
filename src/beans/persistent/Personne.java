package beans.persistent;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Personne implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull( message = "Veuillez saisir un prénom" )
	@Size( min = 2, message = "Le prénom doit contenir au moins 2 caractères" )
	@Size( max=50, message = "Le prénom doit contenir au maximun 50 caractères" )
	@Column(nullable = false)
	private String prenom;
	
	@NotNull( message = "Veuillez saisir un nom" )
	@Size( min = 2, message = "Le nom doit contenir au moins 2 caractères" )
	@Size( max=50, message = "Le nom doit contenir au maximun 50 caractères" )
	@Column(nullable = false)
	private String nom;
	
	@NotNull( message = "Veuillez saisir une adresse mail" )
	@Pattern( regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)", message = "Merci de saisir une adresse mail valide" )
	@Size( max=50, message = "L'adresse mail doit contenir au maximun 50 caractères" )
	@Column(nullable = false)
	private String mail;
	
	@NotNull( message = "Veuillez saisir un nom d'utilisateur" )
	@Size( min = 3, message = "Le nom d'utilisateur doit contenir au moins 3 caractères" )
	@Size( max=50, message = "Le nom d'utilisateur doit contenir au maximun 50 caractères" )
	@Column(unique = true, nullable = false)
	private String username;
	
	@NotNull( message = "Veuillez saisir un mot de passe" )
	@Size( min = 4, message = "Le mot de passe doit contenir au moins 4 caractères" )
	@Size( max=50, message = "Le mot de passe doit contenir au maximun 50 caractères" )
	@Column(nullable = false)
	private String password;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date registeredDate;
	
	
	public Personne(){

	}


	public Personne(Integer id, String prenom, String nom, String mail, String username, String password,
			Date registeredDate) {
		super();
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.mail = mail;
		this.username = username;
		this.password = password;
		this.registeredDate = registeredDate;
	}
	
	
	public Personne(String prenom, String nom, String mail, String username, String password,
			Date registeredDate) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.mail = mail;
		this.username = username;
		this.password = password;
		this.registeredDate = registeredDate;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Date getRegisteredDate() {
		return registeredDate;
	}


	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((registeredDate == null) ? 0 : registeredDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personne other = (Personne) obj;
		if (registeredDate == null) {
			if (other.registeredDate != null)
				return false;
		} else if (!registeredDate.equals(other.registeredDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Personne [id=" + id + ", prenom=" + prenom + ", nom=" + nom + ", mail=" + mail + ", username="
				+ username + ", password=" + password + ", RegisteredDate=" + registeredDate + "]";
	}
	
	
	
	
}
