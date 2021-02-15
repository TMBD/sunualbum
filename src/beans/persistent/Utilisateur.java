package beans.persistent;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proprietaire")
	Set<Album> album;
	
	@ManyToMany(fetch = FetchType.LAZY)
	Set<Album> albumsAutorises;

	public Utilisateur() {
		super();
	}

	public Utilisateur(Integer id, String prenom, String nom, String mail, String username, String password,
			Date registeredDate) {
		super(id, prenom, nom, mail, username, password, registeredDate);
	}

	public Utilisateur(String prenom, String nom, String mail, String username, String password, Date registeredDate) {
		super(prenom, nom, mail, username, password, registeredDate);
		// TODO Auto-generated constructor stub
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((album == null) ? 0 : album.hashCode());
		result = prime * result + ((albumsAutorises == null) ? 0 : albumsAutorises.hashCode());
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
		if (album == null) {
			if (other.album != null)
				return false;
		} else if (!album.equals(other.album))
			return false;
		if (albumsAutorises == null) {
			if (other.albumsAutorises != null)
				return false;
		} else if (!albumsAutorises.equals(other.albumsAutorises))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Utilisateur [album=" + album + ", albumsAutorises=" + albumsAutorises + ", toString()=" + super.toString()+ "]";
	}

	
	
	
	
	
}
