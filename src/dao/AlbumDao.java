package dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import beans.persistent.Album;
import beans.persistent.Utilisateur;


@Stateless
public class AlbumDao {


	@PersistenceContext(unitName = "sunualbum_pu")
	private EntityManager em;

	private static final String SELECT_ALL_ALBUMS_JPQL = "SELECT a FROM Album a";
	private static final String SELECT_ALL_ALBUMS_PUBLIC_ACCES_JPQL = "SELECT a FROM Album a WHERE a.prive=false";
	private static final String SELECT_ALBUM_BY_ID_JPQL = "SELECT a FROM Album a WHERE a.id=";
	//private static final String SELECT_ALL_ADMINS_BY_MAIL_JPQL = "SELECT a FROM Album a WHERE a.email LIKE :email";
	//private static final String SELECT_ALL_ADMINS_BY_USERNAME_JPQL = "SELECT a FROM Admin a WHERE a.username LIKE ";
	private static final String UPDATE_ALBUM_JPQL = "UPDATE Album a SET a = :album WHERE a.id LIKE :id";
	

	public void add(Album a){
		em.persist(a);
	}

	public List<Album> findAll(){
		return em.createQuery(SELECT_ALL_ALBUMS_JPQL, Album.class).getResultList();
	}
	
	public List<Album> findAllPublicAcces(){
		return em.createQuery(SELECT_ALL_ALBUMS_PUBLIC_ACCES_JPQL, Album.class).getResultList();
	}
	
	public Album findById(int id) {
		try {
			return em.createQuery(SELECT_ALBUM_BY_ID_JPQL+id, Album.class).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public List<Album> findAllByUtilisateurAcces(Utilisateur u){
		List<Album> allAlbum = em.createQuery(SELECT_ALL_ALBUMS_JPQL, Album.class).getResultList();
		
		List<Album> albumsResult = new ArrayList<Album>();
		for (Album album : allAlbum) {
			if(album.getUtilisateursAutorises().contains(u)) {
				albumsResult.add(album);
			}
		}
		
		if(albumsResult.isEmpty()) return null;
		return albumsResult;
	}

	public void update(Album album) {
		em.merge(album);
	}

	public void remove(Integer id) {
		em.remove(findById(id));
	}


	 
}