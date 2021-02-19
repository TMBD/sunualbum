package beans.persistent;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@NamedQuery(
	name="utilisateurByEmail", 
	query="SELECT u FROM Utilisateur u WHERE u.mail LIKE :mail"
)

@NamedQuery(
	name="utilisateurByUsername", 
	query="SELECT u FROM Utilisateur u WHERE u.username LIKE :username"
)

@Entity
public class Utilisateur extends Personne implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotNull( message = "Veuillez indiquer s'il s'agit d'un administrateur ou non !" )
	@Column(nullable = false)
	private Boolean estAdmin = false;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proprietaire")
	Set<Album> album;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "utilisateursAutorises")
	Set<Album> albumsAutorises;

	public Utilisateur() {
		super();
	}

	public Utilisateur(Integer id, String prenom, String nom, String mail, String username, String password,
			Date registeredDate, Boolean estAdmin) {
		super(id, prenom, nom, mail, username, password, registeredDate);
		this.estAdmin = estAdmin;
	}
	
	public Utilisateur(String prenom, String nom, String mail, String username, String password, Date registeredDate, Boolean estAdmin) {
		super(prenom, nom, mail, username, password, registeredDate);
		this.estAdmin = estAdmin;
	}

	public Utilisateur(String prenom, String nom, String mail, String username, String password, Date registeredDate) {
		super(prenom, nom, mail, username, password, registeredDate);
	}

	public Utilisateur(Set<Album> album, Set<Album> albumsAutorises, Boolean estAdmin) {
		super();
		this.album = album;
		this.albumsAutorises = albumsAutorises;
		this.estAdmin = estAdmin;
	}
	
	public Utilisateur(Set<Album> album, Set<Album> albumsAutorises) {
		super();
		this.album = album;
		this.albumsAutorises = albumsAutorises;
	}

	public Set<Album> getAlbum() {
		return album;
	}

	public void setAlbum(Set<Album> album) {
		this.album = album;
	}

	public Set<Album> getAlbumsAutorises() {
		return albumsAutorises;
	}

	public void setAlbumsAutorises(Set<Album> albumsAutorises) {
		this.albumsAutorises = albumsAutorises;
	}
	
	public Boolean getEstAdmin() {
		return estAdmin;
	}

	public void setEstAdmin(Boolean estAdmin) {
		this.estAdmin = estAdmin;
	}
	
	public static boolean exist(String username, Set<Utilisateur> utilisateurs) {
		if(utilisateurs == null) return false;
		for (Utilisateur utilisateur : utilisateurs) {
			if(utilisateur.getUsername().equals(username)) return true;
		}
		return false;
	}
	
	public static boolean exist(Integer id, Set<Utilisateur> utilisateurs) {
		for (Utilisateur utilisateur : utilisateurs) {
			if(utilisateur.getId() == id) return true;
		}
		return false;
	}
	
	public static Utilisateur getUtilisateurFromSet(String username, Set<Utilisateur> utilisateurs) {
		for (Utilisateur utilisateur : utilisateurs) {
			if(utilisateur.getUsername().equals(username)) return utilisateur;
		}
		return null;
	}
	
	public static Utilisateur getUtilisateurFromSet(String username, List<Utilisateur> utilisateurs) {
		for (Utilisateur utilisateur : utilisateurs) {
			if(utilisateur.getUsername().equals(username)) {
				return utilisateur;
			} 
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((estAdmin == null) ? 0 : estAdmin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilisateur other = (Utilisateur) obj;
		if (estAdmin == null) {
			if (other.estAdmin != null)
				return false;
		} else if (!estAdmin.equals(other.estAdmin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Utilisateur [estAdmin=" + estAdmin + ", nom=" + super.getNom() + ", prenom=" + super.getPrenom() + ", username=" + super.getUsername() +"]";
	}

	
	
	
	
	
}
