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

	private static final String SELECT_ALL_GUEST_JPQL = "SELECT u FROM Utilisateur u";

	public void add(Utilisateur u)
	{
		em.persist(u);
		
	}

	public List<Utilisateur> lister()
	{
		return em.createQuery(SELECT_ALL_GUEST_JPQL, Utilisateur.class).getResultList();
	}
	
}
