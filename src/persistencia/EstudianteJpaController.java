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
import modelo.Matricula;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Estudiante;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author jsnar
 */
public class EstudianteJpaController implements Serializable {

    public EstudianteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public EstudianteJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Taller1JavaWebPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudiante estudiante) throws PreexistingEntityException, Exception {
        if (estudiante.getMatriculaList() == null) {
            estudiante.setMatriculaList(new ArrayList<Matricula>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Matricula> attachedMatriculaList = new ArrayList<Matricula>();
            for (Matricula matriculaListMatriculaToAttach : estudiante.getMatriculaList()) {
                matriculaListMatriculaToAttach = em.getReference(matriculaListMatriculaToAttach.getClass(), matriculaListMatriculaToAttach.getMatriculaPK());
                attachedMatriculaList.add(matriculaListMatriculaToAttach);
            }
            estudiante.setMatriculaList(attachedMatriculaList);
            em.persist(estudiante);
            for (Matricula matriculaListMatricula : estudiante.getMatriculaList()) {
                Estudiante oldEstudianteOfMatriculaListMatricula = matriculaListMatricula.getEstudiante();
                matriculaListMatricula.setEstudiante(estudiante);
                matriculaListMatricula = em.merge(matriculaListMatricula);
                if (oldEstudianteOfMatriculaListMatricula != null) {
                    oldEstudianteOfMatriculaListMatricula.getMatriculaList().remove(matriculaListMatricula);
                    oldEstudianteOfMatriculaListMatricula = em.merge(oldEstudianteOfMatriculaListMatricula);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstudiante(estudiante.getDocumentoestudiante()) != null) {
                throw new PreexistingEntityException("Estudiante " + estudiante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudiante estudiante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante persistentEstudiante = em.find(Estudiante.class, estudiante.getDocumentoestudiante());
            List<Matricula> matriculaListOld = persistentEstudiante.getMatriculaList();
            List<Matricula> matriculaListNew = estudiante.getMatriculaList();
            List<String> illegalOrphanMessages = null;
            for (Matricula matriculaListOldMatricula : matriculaListOld) {
                if (!matriculaListNew.contains(matriculaListOldMatricula)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Matricula " + matriculaListOldMatricula + " since its estudiante field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Matricula> attachedMatriculaListNew = new ArrayList<Matricula>();
            for (Matricula matriculaListNewMatriculaToAttach : matriculaListNew) {
                matriculaListNewMatriculaToAttach = em.getReference(matriculaListNewMatriculaToAttach.getClass(), matriculaListNewMatriculaToAttach.getMatriculaPK());
                attachedMatriculaListNew.add(matriculaListNewMatriculaToAttach);
            }
            matriculaListNew = attachedMatriculaListNew;
            estudiante.setMatriculaList(matriculaListNew);
            estudiante = em.merge(estudiante);
            for (Matricula matriculaListNewMatricula : matriculaListNew) {
                if (!matriculaListOld.contains(matriculaListNewMatricula)) {
                    Estudiante oldEstudianteOfMatriculaListNewMatricula = matriculaListNewMatricula.getEstudiante();
                    matriculaListNewMatricula.setEstudiante(estudiante);
                    matriculaListNewMatricula = em.merge(matriculaListNewMatricula);
                    if (oldEstudianteOfMatriculaListNewMatricula != null && !oldEstudianteOfMatriculaListNewMatricula.equals(estudiante)) {
                        oldEstudianteOfMatriculaListNewMatricula.getMatriculaList().remove(matriculaListNewMatricula);
                        oldEstudianteOfMatriculaListNewMatricula = em.merge(oldEstudianteOfMatriculaListNewMatricula);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = estudiante.getDocumentoestudiante();
                if (findEstudiante(id) == null) {
                    throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante estudiante;
            try {
                estudiante = em.getReference(Estudiante.class, id);
                estudiante.getDocumentoestudiante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Matricula> matriculaListOrphanCheck = estudiante.getMatriculaList();
            for (Matricula matriculaListOrphanCheckMatricula : matriculaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("No se puede eliminar el estudiante debido a que ha realizado alguna matricula.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estudiante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estudiante> findEstudianteEntities() {
        return findEstudianteEntities(true, -1, -1);
    }

    public List<Estudiante> findEstudianteEntities(int maxResults, int firstResult) {
        return findEstudianteEntities(false, maxResults, firstResult);
    }

    private List<Estudiante> findEstudianteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudiante.class));
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

    public Estudiante findEstudiante(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudiante.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudianteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudiante> rt = cq.from(Estudiante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
