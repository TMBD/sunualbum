package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import beans.persistent.Utilisateur;


@Stateless
public class UtilisateurDao {


	@PersistenceContext(unitName = "sunualbum_pu")
	private EntityManager em;

	private static final String SELECT_ALL_UTILISATEURS_JPQL = "SELECT u FROM Utilisateur u";
	private static final String SELECT_ALL_UTLISATEURS_BY_MAIL_JPQL = "SELECT u FROM Utilisateur u WHERE u.email LIKE :email";
	private static final String SELECT_ALL_UTLISATEURS_BY_USERNAME_JPQL = "SELECT u FROM Utilisateur u WHERE u.username LIKE ";

	public void add(Utilisateur u)
	{
		em.persist(u);
	}

	public List<Utilisateur> findAll()
	{
		return em.createQuery(SELECT_ALL_UTILISATEURS_JPQL, Utilisateur.class).getResultList();
	}

	public List<Utilisateur> findByMail(String email) {
		try {
			return em.createNamedQuery("utilisateurByEmail").setParameter("email", email).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	
	public Utilisateur findByUsername(String username) {
		try {
			return (Utilisateur) em.createNamedQuery("utilisateurByUsername").setParameter("username", username).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean exist(String username) {
		if(findByUsername(username) != null) return true;
		return false;
	}
	
	public boolean exist(String username, String password) {
		Utilisateur u = findByUsername(username);
		if(u != null && u.getPassword().equals(password)) return true;
		return false;
	}
	
	public boolean estAdmin(String username) {
		Utilisateur u = findByUsername(username);
		if(u != null && u.getEstAdmin()) return true;
		return false;
	}
	
	public boolean estAdmin(String username, String password) {
		Utilisateur u = findByUsername(username);
		if(u != null && u.getPassword().equals(password) && u.getEstAdmin()) return true;
		return false;
	}

	public void delete(String username) {
		em.remove(findByUsername(username));
	}

	public void update(Utilisateur utilisateur) {
		em.merge(utilisateur);
		
	}
	
	
	 
}
