package beans.persistent;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Guest
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer	id;
	@Column(name = "nom", length = 25, nullable = false)
	String	nom;
	@Column(name = "dateInscription")
	@Temporal(TemporalType.DATE)
	Date	dateInscription;

	public Guest()
	{
		this.dateInscription = new Date(System.currentTimeMillis());
	}

	public Guest(String nom)
	{
		this.nom = nom;
		this.dateInscription = new Date(System.currentTimeMillis());
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getNom()
	{
		return nom;
	}

	public void setNom(String nom)
	{
		this.nom = nom;
	}

	public Date getDateInscription()
	{
		return dateInscription;
	}

	public void setDateInscription(Date dateInscription)
	{
		this.dateInscription = dateInscription;
	}

	@Override
	public String toString()
	{
		return "Guest [id=" + id + ", nom=" + nom + ", dateInscription=" + dateInscription + "]";
	}
}
