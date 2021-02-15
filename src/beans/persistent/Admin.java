package beans.persistent;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;


@NamedQuery(
		name="adminByEmail", 
		query="SELECT a FROM Admin a WHERE a.mail LIKE :mail"
	)

	@NamedQuery(
		name="adminByUsername", 
		query="SELECT a FROM Admin a WHERE a.username LIKE :username"
	)

@Entity
public class Admin extends Utilisateur implements Serializable{
	private static final long serialVersionUID = 1L;

	public Admin() {
		super();
	}

	public Admin(Integer id, String prenom, String nom, String mail, String username, String password,
			Date registeredDate) {
		super(id, prenom, nom, mail, username, password, registeredDate);
	}

	public Admin(Set<Album> album, Set<Album> albumsAutorises) {
		super(album, albumsAutorises);
	}

	public Admin(String prenom, String nom, String mail, String username, String password, Date registeredDate) {
		super(prenom, nom, mail, username, password, registeredDate);
	}
	
	
}
