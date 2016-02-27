/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Materia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Carrera;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author jsnar
 */
public class CarreraJpaController implements Serializable {

    public CarreraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public CarreraJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Taller1JavaWebPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carrera carrera) throws PreexistingEntityException, Exception {
        if (carrera.getMateriaList() == null) {
            carrera.setMateriaList(new ArrayList<Materia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Materia> attachedMateriaList = new ArrayList<Materia>();
            for (Materia materiaListMateriaToAttach : carrera.getMateriaList()) {
                materiaListMateriaToAttach = em.getReference(materiaListMateriaToAttach.getClass(), materiaListMateriaToAttach.getNumeromateria());
                attachedMateriaList.add(materiaListMateriaToAttach);
            }
            carrera.setMateriaList(attachedMateriaList);
            em.persist(carrera);
            for (Materia materiaListMateria : carrera.getMateriaList()) {
                Carrera oldNumerocarreraOfMateriaListMateria = materiaListMateria.getNumerocarrera();
                materiaListMateria.setNumerocarrera(carrera);
                materiaListMateria = em.merge(materiaListMateria);
                if (oldNumerocarreraOfMateriaListMateria != null) {
                    oldNumerocarreraOfMateriaListMateria.getMateriaList().remove(materiaListMateria);
                    oldNumerocarreraOfMateriaListMateria = em.merge(oldNumerocarreraOfMateriaListMateria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCarrera(carrera.getNumerocarrera()) != null) {
                throw new PreexistingEntityException("Carrera " + carrera + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carrera carrera) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera persistentCarrera = em.find(Carrera.class, carrera.getNumerocarrera());
            List<Materia> materiaListOld = persistentCarrera.getMateriaList();
            List<Materia> materiaListNew = carrera.getMateriaList();
            List<Materia> attachedMateriaListNew = new ArrayList<Materia>();
            for (Materia materiaListNewMateriaToAttach : materiaListNew) {
                materiaListNewMateriaToAttach = em.getReference(materiaListNewMateriaToAttach.getClass(), materiaListNewMateriaToAttach.getNumeromateria());
                attachedMateriaListNew.add(materiaListNewMateriaToAttach);
            }
            materiaListNew = attachedMateriaListNew;
            carrera.setMateriaList(materiaListNew);
            carrera = em.merge(carrera);
            for (Materia materiaListOldMateria : materiaListOld) {
                if (!materiaListNew.contains(materiaListOldMateria)) {
                    materiaListOldMateria.setNumerocarrera(null);
                    materiaListOldMateria = em.merge(materiaListOldMateria);
                }
            }
            for (Materia materiaListNewMateria : materiaListNew) {
                if (!materiaListOld.contains(materiaListNewMateria)) {
                    Carrera oldNumerocarreraOfMateriaListNewMateria = materiaListNewMateria.getNumerocarrera();
                    materiaListNewMateria.setNumerocarrera(carrera);
                    materiaListNewMateria = em.merge(materiaListNewMateria);
                    if (oldNumerocarreraOfMateriaListNewMateria != null && !oldNumerocarreraOfMateriaListNewMateria.equals(carrera)) {
                        oldNumerocarreraOfMateriaListNewMateria.getMateriaList().remove(materiaListNewMateria);
                        oldNumerocarreraOfMateriaListNewMateria = em.merge(oldNumerocarreraOfMateriaListNewMateria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = carrera.getNumerocarrera();
                if (findCarrera(id) == null) {
                    throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera carrera;
            try {
                carrera = em.getReference(Carrera.class, id);
                carrera.getNumerocarrera();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.", enfe);
            }
            List<Materia> materiaList = carrera.getMateriaList();
            for (Materia materiaListMateria : materiaList) {
                materiaListMateria.setNumerocarrera(null);
                materiaListMateria = em.merge(materiaListMateria);
            }
            em.remove(carrera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carrera> findCarreraEntities() {
        return findCarreraEntities(true, -1, -1);
    }

    public List<Carrera> findCarreraEntities(int maxResults, int firstResult) {
        return findCarreraEntities(false, maxResults, firstResult);
    }

    private List<Carrera> findCarreraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carrera.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Carrera findCarrera(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carrera.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarreraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carrera> rt = cq.from(Carrera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
