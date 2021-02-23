package beans.persistent;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
public class Photo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	Album album;
	
	@NotNull( message = "Veuillez saisir un titre" )
	@Size( min = 2, message = "Le titre doit contenir au moins 2 caractères" )
	@Size( max=50, message = "Le titre doit contenir au maximun 50 caractères" )
	@Column(nullable = false)
	private String titre;
	
	@NotNull( message = "Veuillez donner une description de votre photo" )
	@Size( min = 3, message = "La description doit contenir au moins 3 caractères" )
	@Size( max=200, message = "La description doit contenir au maximun 200 caractères" )
	@Column(nullable = false)
	private String description;
	
	@NotNull( message = "Veuillez saisir la hauteur de l'image en pixel" )
	@Min(value = 0L, message = "La hauteur doit être positive")
	@Max(value = Integer.MAX_VALUE, message = "La valeur maximale autorisé est "+Integer.MAX_VALUE)
	@Column(nullable = false)
	private Integer hauteur;
	
	@NotNull( message = "Veuillez saisir la largeur de l'image en pixel" )
	@Min(value = 0L, message = "La largeur doit être positive")
	@Max(value = Integer.MAX_VALUE, message = "La largeur maximale autorisé est "+Integer.MAX_VALUE)
	@Column(nullable = false)
	private Integer largeur;

	@Column( nullable = false)
	private String uri;

	@ElementCollection 
	@Column(nullable = true)
	private List<String> motsCles;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dateCreation;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date dateMiseAJour;

	public Photo(Integer id, Album album,
			@NotNull(message = "Veuillez saisir un titre") @Size(min = 2, message = "Le titre doit contenir au moins 2 caractères") @Size(max = 50, message = "Le titre doit contenir au maximun 50 caractères") String titre,
			@NotNull(message = "Veuillez donner une description de votre photo") @Size(min = 3, message = "La description doit contenir au moins 3 caractères") @Size(max = 50, message = "La description doit contenir au maximun 50 caractères") String description,
			@NotNull(message = "Veuillez saisir la hauteur de l'image en pixel") @Size(min = 1, message = "La hauteur doit être suppérieure à 0") @Size(max = 2147483647, message = "La hauteur doit être inférieur à 2147483647") Integer hauteur,
			@NotNull(message = "Veuillez saisir la largeur de l'image en pixel") @Size(min = 1, message = "La largeur doit être suppérieure à 0") @Size(max = 2147483647, message = "La largeur doit être inférieur à 2147483647") Integer largeur,
			String uri, List<String> motsCles, Date dateCreation, Date dateMiseAJour) {
		super();
		this.id = id;
		this.album = album;
		this.titre = titre;
		this.description = description;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.uri = uri;
		this.motsCles = motsCles;
		this.dateCreation = dateCreation;
		this.dateMiseAJour = dateMiseAJour;
	}

	public Photo(Album album,
			@NotNull(message = "Veuillez saisir un titre") @Size(min = 2, message = "Le titre doit contenir au moins 2 caractères") @Size(max = 50, message = "Le titre doit contenir au maximun 50 caractères") String titre,
			@NotNull(message = "Veuillez donner une description de votre photo") @Size(min = 3, message = "La description doit contenir au moins 3 caractères") @Size(max = 50, message = "La description doit contenir au maximun 50 caractères") String description,
			@NotNull(message = "Veuillez saisir la hauteur de l'image en pixel") @Size(min = 1, message = "La hauteur doit être suppérieure à 0") @Size(max = 2147483647, message = "La hauteur doit être inférieur à 2147483647") Integer hauteur,
			@NotNull(message = "Veuillez saisir la largeur de l'image en pixel") @Size(min = 1, message = "La largeur doit être suppérieure à 0") @Size(max = 2147483647, message = "La largeur doit être inférieur à 2147483647") Integer largeur,
			String uri, List<String> motsCles, Date dateCreation, Date dateMiseAJour) {
		super();
		this.album = album;
		this.titre = titre;
		this.description = description;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.uri = uri;
		this.motsCles = motsCles;
		this.dateCreation = dateCreation;
		this.dateMiseAJour = dateMiseAJour;
	}

	public Photo(
			@NotNull(message = "Veuillez saisir un titre") @Size(min = 2, message = "Le titre doit contenir au moins 2 caractères") @Size(max = 50, message = "Le titre doit contenir au maximun 50 caractères") String titre,
			@NotNull(message = "Veuillez donner une description de votre photo") @Size(min = 3, message = "La description doit contenir au moins 3 caractères") @Size(max = 50, message = "La description doit contenir au maximun 50 caractères") String description,
			@NotNull(message = "Veuillez saisir la hauteur de l'image en pixel") @Size(min = 1, message = "La hauteur doit être suppérieure à 0") @Size(max = 2147483647, message = "La hauteur doit être inférieur à 2147483647") Integer hauteur,
			@NotNull(message = "Veuillez saisir la largeur de l'image en pixel") @Size(min = 1, message = "La largeur doit être suppérieure à 0") @Size(max = 2147483647, message = "La largeur doit être inférieur à 2147483647") Integer largeur,
			String uri, List<String> motsCles, Date dateCreation, Date dateMiseAJour) {
		super();
		this.titre = titre;
		this.description = description;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.uri = uri;
		this.motsCles = motsCles;
		this.dateCreation = dateCreation;
		this.dateMiseAJour = dateMiseAJour;
	}
	
	public Photo(
			@NotNull(message = "Veuillez saisir un titre") @Size(min = 2, message = "Le titre doit contenir au moins 2 caractères") @Size(max = 50, message = "Le titre doit contenir au maximun 50 caractères") String titre,
			@NotNull(message = "Veuillez donner une description de votre photo") @Size(min = 3, message = "La description doit contenir au moins 3 caractères") @Size(max = 50, message = "La description doit contenir au maximun 50 caractères") String description,
			@NotNull(message = "Veuillez saisir la hauteur de l'image en pixel") @Size(min = 1, message = "La hauteur doit être suppérieure à 0") @Size(max = 2147483647, message = "La hauteur doit être inférieur à 2147483647") Integer hauteur,
			@NotNull(message = "Veuillez saisir la largeur de l'image en pixel") @Size(min = 1, message = "La largeur doit être suppérieure à 0") @Size(max = 2147483647, message = "La largeur doit être inférieur à 2147483647") Integer largeur,
			List<String> motsCles, Date dateCreation, Date dateMiseAJour) {
		super();
		this.titre = titre;
		this.description = description;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.motsCles = motsCles;
		this.dateCreation = dateCreation;
		this.dateMiseAJour = dateMiseAJour;
	}

	public Photo() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
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

	public Integer getHauteur() {
		return hauteur;
	}

	public void setHauteur(Integer hauteur) {
		this.hauteur = hauteur;
	}

	public Integer getLargeur() {
		return largeur;
	}

	public void setLargeur(Integer largeur) {
		this.largeur = largeur;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCreation == null) ? 0 : dateCreation.hashCode());
		result = prime * result + ((dateMiseAJour == null) ? 0 : dateMiseAJour.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((hauteur == null) ? 0 : hauteur.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((largeur == null) ? 0 : largeur.hashCode());
		result = prime * result + ((motsCles == null) ? 0 : motsCles.hashCode());
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
		Photo other = (Photo) obj;
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
		if (hauteur == null) {
			if (other.hauteur != null)
				return false;
		} else if (!hauteur.equals(other.hauteur))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (largeur == null) {
			if (other.largeur != null)
				return false;
		} else if (!largeur.equals(other.largeur))
			return false;
		if (motsCles == null) {
			if (other.motsCles != null)
				return false;
		} else if (!motsCles.equals(other.motsCles))
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
		return "Photo [id=" + id + ", album=" + album + ", titre=" + titre + ", description=" + description
				+ ", hauteur=" + hauteur + ", largeur=" + largeur + ", uri=" + uri + ", motsCles=" + motsCles
				+ ", dateCreation=" + dateCreation + ", dateMiseAJour=" + dateMiseAJour + "]";
	}

	
	
	
	

}
