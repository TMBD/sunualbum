package beans.persistent;


import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class Album {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	Utilisateur proprietaire;
	
	@ManyToMany(fetch = FetchType.LAZY)
	Set<Utilisateur> utilisateursAutorises;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "album")
	Set<Photo> photos;
	
	@NotNull( message = "Veuillez saisir un theme" )
	@Size( min = 2, message = "Le theme doit contenir au moins 2 caractères" )
	@Size( max=50, message = "Le theme doit contenir au maximun 50 caractères" )
	@Column(nullable = false)
	private String theme;
	
	@NotNull( message = "Veuillez designer si votre album est privé ou non" )
	@Column(nullable = false)
	private Boolean prive = false;
	
	@NotNull( message = "Veuillez saisir un titre" )
	@Size( min = 2, message = "Le titre doit contenir au moins 2 caractères" )
	@Size( max=50, message = "Le titre doit contenir au maximun 50 caractères" )
	@Column(nullable = false)
	private String titre;
	
	@NotNull( message = "Veuillez donner une description de votre album" )
	@Size( min = 3, message = "La description doit contenir au moins 3 caractères" )
	@Size( max=200, message = "La description doit contenir au maximun 200 caractères" )
	@Column(nullable = false)
	private String description;

	@ElementCollection 
	@Column(nullable = true)
	private List<String> motsCles;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dateCreation;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date dateMiseAJour;
	
	@Column(unique = true, nullable = false)
	private String uri;
	
	
	public Album() {
		super();
	}


	public Album(Integer id, Utilisateur proprietaire, Set<Utilisateur> utilisateursAutorises, Set<Photo> photos,
			@NotNull(message = "Veuillez saisir un theme") @Size(min = 2, message = "Le theme doit contenir au moins 2 caractères") @Size(max = 50, message = "Le theme doit contenir au maximun 50 caractères") String theme,
			@NotNull(message = "Veuillez designer si votre album est privé ou non") Boolean prive,
			@NotNull(message = "Veuillez saisir un titre") @Size(min = 2, message = "Le titre doit contenir au moins 2 caractères") @Size(max = 50, message = "Le titre doit contenir au maximun 50 caractères") String titre,
			@NotNull(message = "Veuillez donner une description de votre album") @Size(min = 3, message = "La description doit contenir au moins 3 caractères") @Size(max = 50, message = "La description doit contenir au maximun 50 caractères") String description,
			List<String> motsCles, Date dateCreation, Date dateMiseAJour, String uri) {
		super();
		this.id = id;
		this.proprietaire = proprietaire;
		this.utilisateursAutorises = utilisateursAutorises;
		this.photos = photos;
		this.theme = theme;
		this.prive = prive;
		this.titre = titre;
		this.description = description;
		this.motsCles = motsCles;
		this.dateCreation = dateCreation;
		this.dateMiseAJour = dateMiseAJour;
		this.uri = uri;
	}


	public Album(
			@NotNull(message = "Veuillez saisir un theme") @Size(min = 2, message = "Le theme doit contenir au moins 2 caractères") @Size(max = 50, message = "Le theme doit contenir au maximun 50 caractères") String theme,
			@NotNull(message = "Veuillez designer si votre album est privé ou non") Boolean prive,
			@NotNull(message = "Veuillez saisir un titre") @Size(min = 2, message = "Le titre doit contenir au moins 2 caractères") @Size(max = 50, message = "Le titre doit contenir au maximun 50 caractères") String titre,
			@NotNull(message = "Veuillez donner une description de votre album") @Size(min = 3, message = "La description doit contenir au moins 3 caractères") @Size(max = 50, message = "La description doit contenir au maximun 50 caractères") String description,
			List<String> motsCles, Date dateCreation, Date dateMiseAJour, String uri) {
		super();
		this.theme = theme;
		this.prive = prive;
		this.titre = titre;
		this.description = description;
		this.motsCles = motsCles;
		this.dateCreation = dateCreation;
		this.dateMiseAJour = dateMiseAJour;
		this.uri = uri;
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Utilisateur getProprietaire() {
		return proprietaire;
	}


	public void setProprietaire(Utilisateur proprietaire) {
		this.proprietaire = proprietaire;
	}


	public Set<Utilisateur> getUtilisateursAutorises() {
		return utilisateursAutorises;
	}


	public void setUtilisateursAutorises(Set<Utilisateur> utilisateursAutorises) {
		this.utilisateursAutorises = utilisateursAutorises;
	}


	public Set<Photo> getPhotos() {
		return photos;
	}


	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}


	public String getTheme() {
		return theme;
	}


	public void setTheme(String theme) {
		this.theme = theme;
	}


	public Boolean getPrive() {
		return prive;
	}


	public void setPrive(Boolean prive) {
		this.prive = prive;
	}


	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public List<String> getMotsCles() {
		return motsCles;
	}


	public void setMotsCles(List<String> motsCles) {
		this.motsCles = motsCles;
	}


	public Date getDateCreation() {
		return dateCreation;
	}


	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	public Date getDateMiseAJour() {
		return dateMiseAJour;
	}


	public void setDateMiseAJour(Date dateMiseAJour) {
		this.dateMiseAJour = dateMiseAJour;
	}


	public String getUri() {
		return uri;
	}


	public void setUri(String uri) {
		this.uri = uri;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCreation == null) ? 0 : dateCreation.hashCode());
		result = prime * result + ((dateMiseAJour == null) ? 0 : dateMiseAJour.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((motsCles == null) ? 0 : motsCles.hashCode());
		result = prime * result + ((prive == null) ? 0 : prive.hashCode());
		result = prime * result + ((theme == null) ? 0 : theme.hashCode());
		result = prime * result + ((titre == null) ? 0 : titre.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		Album other = (Album) obj;
		if (dateCreation == null) {
			if (other.dateCreation != null)
				return false;
		} else if (!dateCreation.equals(other.dateCreation))
			return false;
		if (dateMiseAJour == null) {
			if (other.dateMiseAJour != null)
				return false;
		} else if (!dateMiseAJour.equals(other.dateMiseAJour))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (motsCles == null) {
			if (other.motsCles != null)
				return false;
		} else if (!motsCles.equals(other.motsCles))
			return false;
		if (prive == null) {
			if (other.prive != null)
				return false;
		} else if (!prive.equals(other.prive))
			return false;
		if (theme == null) {
			if (other.theme != null)
				return false;
		} else if (!theme.equals(other.theme))
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Album [id=" + id + ", proprietaire=" + proprietaire + ", utilisateursAutorises=" + utilisateursAutorises
			    + ", theme=" + theme + ", prive=" + prive + ", titre=" + titre + ", description="
				+ description + ", motsCles=" + motsCles + ", dateCreation=" + dateCreation + ", dateMiseAJour="
				+ dateMiseAJour + ", uri=" + uri + "]";
	}
	
	
	
	
}
