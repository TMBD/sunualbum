package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import beans.persistent.Album;


@Stateless
public class AlbumDao {


	@PersistenceContext(unitName = "sunualbum_pu")
	private EntityManager em;

	private static final String SELECT_ALL_ALBUMS_JPQL = "SELECT a FROM Album a";
	//private static final String SELECT_ALL_ADMINS_BY_MAIL_JPQL = "SELECT a FROM Album a WHERE a.email LIKE :email";
	//private static final String SELECT_ALL_ADMINS_BY_USERNAME_JPQL = "SELECT a FROM Admin a WHERE a.username LIKE ";
	

	public void add(Album a)
	{
		em.persist(a);
	}

	public List<Album> findAll()
	{
		return em.createQuery(SELECT_ALL_ALBUMS_JPQL, Album.class).getResultList();
	}

//	public List<Album> findByMail(String email) {
//		try {
//			return em.createNamedQuery("adminByEmail").setParameter("email", email).getResultList();
//		} catch (Exception e) {
//			return null;
//		}
//	}

	
//	public Album findByUsername(String username) {
//		try {
//			return (Album) em.createNamedQuery("adminByUsername").setParameter("username", username).getSingleResult();
//		} catch (Exception e) {
//			return null;
//		}
//	}
	

	 
}