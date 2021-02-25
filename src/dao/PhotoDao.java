package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import beans.persistent.Album;
import beans.persistent.Photo;
import beans.persistent.Utilisateur;


@Stateless
public class PhotoDao {

	@EJB
	AlbumDao albumDao;
	

	@PersistenceContext(unitName = "sunualbum_pu")
	private EntityManager em;

	private static final String SELECT_ALL_PHOTOS_JPQL = "SELECT p FROM Photo p";
	private static final String SELECT_PHOTO_BY_ID_JPQL = "SELECT p FROM Photo p WHERE p.id=";
	private static final String SELECT_PHOTO_BY_ALBUM_ID_JPQL = "SELECT p FROM Photo p WHERE p.album.id=";
	private static final String UPDATE_PHOTO_JPQL = "UPDATE Photo p SET p = :photo WHERE p.id LIKE :id";
	

	public void add(Photo p){
		em.persist(p);
	}

	public List<Photo> findAll(){
		return em.createQuery(SELECT_ALL_PHOTOS_JPQL, Photo.class).getResultList();
	}
	
	public List<Photo> findAllPublicAcces(){
		List<Album> allAlbums = albumDao.findAll();
		List<Photo> publicPhotos = new ArrayList<Photo>();
		
		for (Album album : allAlbums) {
			if(album.getPrive() == false) {
				Set<Photo> albumPhotos = album.getPhotos();
				for (Photo photo : albumPhotos) {
					publicPhotos.add(photo);
				}
			}
		}
		if(publicPhotos.isEmpty()) return null;
		else return publicPhotos;
	}
	
	public Photo findById(int id) {
		try {
			return em.createQuery(SELECT_PHOTO_BY_ID_JPQL+id, Photo.class).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public List<Photo> findAllByUtilisateurAcces(Utilisateur u){
		List<Album> allAlbums = albumDao.findAll();
		List<Photo> accessiblePhotos = new ArrayList<Photo>();
		
		for (Album album : allAlbums) {
			if(album.getUtilisateursAutorises().contains(u)) {
				Set<Photo> albumPhotos = album.getPhotos();
				for (Photo photo : albumPhotos) {
					accessiblePhotos.add(photo);
				}
			}
		}
		if(accessiblePhotos.isEmpty()) return null;
		else return accessiblePhotos;
	}
	
	
	public List<Photo> findAllByProprietaire(Utilisateur u){
		List<Album> allAlbums = albumDao.findAll();
		List<Photo> proprietairePhotos = new ArrayList<Photo>();
		
		for (Album album : allAlbums) {
			if(album.getProprietaire().equals(u)) {
				Set<Photo> albumPhotos = album.getPhotos();
				for (Photo photo : albumPhotos) {
					proprietairePhotos.add(photo);
				}
			}
		}
		if(proprietairePhotos.isEmpty()) return null;
		else return proprietairePhotos;
	}

	public void update(Photo photo) {
		em.merge(photo);
	}

	public void remove(Integer id) {
		em.remove(findById(id));
	}

	public List<Photo> findByAlbumId(int id) {
		List<Photo> allPhoto = findAll();
		List<Photo> resultPhoto = new ArrayList<Photo>();
		for (Photo photo : allPhoto) {
			if(photo.getAlbum().getId() == id) {
				resultPhoto.add(photo);
			}
		}
		return resultPhoto;
	}

	

	 
}
