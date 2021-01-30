package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import beans.persistent.Guest;

@Stateless
public class GuestDao
{
	@PersistenceContext(unitName = "sunualbum_pu")
	private EntityManager em;

	private static final String SELECT_ALL_GUEST_JPQL = "SELECT g FROM Guest g";

	public void ajouter(Guest guest)
	{
		em.persist(guest);
		
		//em.flush();
	}

	public List<Guest> lister()
	{
		System.out.println("Dans la fonction lister... ");
		return em.createQuery(SELECT_ALL_GUEST_JPQL, Guest.class).getResultList();
	}
}
