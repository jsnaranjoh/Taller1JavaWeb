/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Estudiante;
import modelo.Materia;
import modelo.Matricula;
import modelo.MatriculaPK;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author jsnar
 */
public class MatriculaJpaController implements Serializable {

    public MatriculaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public MatriculaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Taller1JavaWebPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Matricula matricula) throws PreexistingEntityException, Exception {
        if (matricula.getMatriculaPK() == null) {
            matricula.setMatriculaPK(new MatriculaPK());
        }
        matricula.getMatriculaPK().setNumeromateria(matricula.getMateria().getNumeromateria());
        matricula.getMatriculaPK().setDocumentoestudiante(matricula.getEstudiante().getDocumentoestudiante());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante estudiante = matricula.getEstudiante();
            if (estudiante != null) {
                estudiante = em.getReference(estudiante.getClass(), estudiante.getDocumentoestudiante());
                matricula.setEstudiante(estudiante);
            }
            Materia materia = matricula.getMateria();
            if (materia != null) {
                materia = em.getReference(materia.getClass(), materia.getNumeromateria());
                matricula.setMateria(materia);
            }
            em.persist(matricula);
            if (estudiante != null) {
                estudiante.getMatriculaList().add(matricula);
                estudiante = em.merge(estudiante);
            }
            if (materia != null) {
                materia.getMatriculaList().add(matricula);
                materia = em.merge(materia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMatricula(matricula.getMatriculaPK()) != null) {
                throw new PreexistingEntityException("Matricula " + matricula + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Matricula matricula) throws NonexistentEntityException, Exception {
        matricula.getMatriculaPK().setNumeromateria(matricula.getMateria().getNumeromateria());
        matricula.getMatriculaPK().setDocumentoestudiante(matricula.getEstudiante().getDocumentoestudiante());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matricula persistentMatricula = em.find(Matricula.class, matricula.getMatriculaPK());
            Estudiante estudianteOld = persistentMatricula.getEstudiante();
            Estudiante estudianteNew = matricula.getEstudiante();
            Materia materiaOld = persistentMatricula.getMateria();
            Materia materiaNew = matricula.getMateria();
            if (estudianteNew != null) {
                estudianteNew = em.getReference(estudianteNew.getClass(), estudianteNew.getDocumentoestudiante());
                matricula.setEstudiante(estudianteNew);
            }
            if (materiaNew != null) {
                materiaNew = em.getReference(materiaNew.getClass(), materiaNew.getNumeromateria());
                matricula.setMateria(materiaNew);
            }
            matricula = em.merge(matricula);
            if (estudianteOld != null && !estudianteOld.equals(estudianteNew)) {
                estudianteOld.getMatriculaList().remove(matricula);
                estudianteOld = em.merge(estudianteOld);
            }
            if (estudianteNew != null && !estudianteNew.equals(estudianteOld)) {
                estudianteNew.getMatriculaList().add(matricula);
                estudianteNew = em.merge(estudianteNew);
            }
            if (materiaOld != null && !materiaOld.equals(materiaNew)) {
                materiaOld.getMatriculaList().remove(matricula);
                materiaOld = em.merge(materiaOld);
            }
            if (materiaNew != null && !materiaNew.equals(materiaOld)) {
                materiaNew.getMatriculaList().add(matricula);
                materiaNew = em.merge(materiaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MatriculaPK id = matricula.getMatriculaPK();
                if (findMatricula(id) == null) {
                    throw new NonexistentEntityException("The matricula with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MatriculaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matricula matricula;
            try {
                matricula = em.getReference(Matricula.class, id);
                matricula.getMatriculaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The matricula with id " + id + " no longer exists.", enfe);
            }
            Estudiante estudiante = matricula.getEstudiante();
            if (estudiante != null) {
                estudiante.getMatriculaList().remove(matricula);
                estudiante = em.merge(estudiante);
            }
            Materia materia = matricula.getMateria();
            if (materia != null) {
                materia.getMatriculaList().remove(matricula);
                materia = em.merge(materia);
            }
            em.remove(matricula);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Matricula> findMatriculaEntities() {
        return findMatriculaEntities(true, -1, -1);
    }

    public List<Matricula> findMatriculaEntities(int maxResults, int firstResult) {
        return findMatriculaEntities(false, maxResults, firstResult);
    }

    private List<Matricula> findMatriculaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Matricula.class));
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

    public Matricula findMatricula(MatriculaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Matricula.class, id);
        } finally {
            em.close();
        }
    }

    public int getMatriculaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Matricula> rt = cq.from(Matricula.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
