//package dao;
//
//import java.util.List;
//
//import javax.ejb.Stateless;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import beans.persistent.Admin;
//
//
//@Stateless
//public class AdminDao {
//
//
//	@PersistenceContext(unitName = "sunualbum_pu")
//	private EntityManager em;
//
//	private static final String SELECT_ALL_ADMINS_JPQL = "SELECT a FROM Admin a";
//	private static final String SELECT_ALL_ADMINS_BY_MAIL_JPQL = "SELECT a FROM Admin a WHERE a.email LIKE :email";
//	private static final String SELECT_ALL_ADMINS_BY_USERNAME_JPQL = "SELECT a FROM Admin a WHERE a.username LIKE ";
//	
//
//	public void add(Admin a)
//	{
//		em.persist(a);
//	}
//
//	public List<Admin> findAll()
//	{
//		return em.createQuery(SELECT_ALL_ADMINS_JPQL, Admin.class).getResultList();
//	}
//
//	public List<Admin> findByMail(String email) {
//		try {
//			return em.createNamedQuery("adminByEmail").setParameter("email", email).getResultList();
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	
//	public Admin findByUsername(String username) {
//		try {
//			return (Admin) em.createNamedQuery("adminByUsername").setParameter("username", username).getSingleResult();
//		} catch (Exception e) {
//			return null;
//		}
//	}
//	
//	public boolean exist(String username) {
//		if(findByUsername(username) != null) return true;
//		return false;
//	}
//	
//	public boolean exist(String username, String password) {
//		Admin a = findByUsername(username);
//		if(a != null && a.getPassword().equals(password)) return true;
//		return false;
//	}
//	
//	
//	 
//}