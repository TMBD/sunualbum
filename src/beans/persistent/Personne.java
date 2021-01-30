package beans.persistent;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Personne {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String prenom;
	private String nom;
	private String mail;
	private String userName;
	private String password;
	@Temporal(TemporalType.DATE)
	private Date RegisteredDate;
	
	
	public Personne(){

	}


	public Personne(Integer id, String prenom, String nom, String mail, String userName, String password,
			Date registeredDate) {
		super();
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.mail = mail;
		this.userName = userName;
		this.password = password;
		RegisteredDate = registeredDate;
	}
	
	
	public Personne(String prenom, String nom, String mail, String userName, String password,
			Date registeredDate) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.mail = mail;
		this.userName = userName;
		this.password = password;
		RegisteredDate = registeredDate;
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


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Date getRegisteredDate() {
		return RegisteredDate;
	}


	public void setRegisteredDate(Date registeredDate) {
		RegisteredDate = registeredDate;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((RegisteredDate == null) ? 0 : RegisteredDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		if (RegisteredDate == null) {
			if (other.RegisteredDate != null)
				return false;
		} else if (!RegisteredDate.equals(other.RegisteredDate))
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
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Personne [id=" + id + ", prenom=" + prenom + ", nom=" + nom + ", mail=" + mail + ", userName="
				+ userName + ", password=" + password + ", RegisteredDate=" + RegisteredDate + "]";
	}
	
	
	
	
}
