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
import modelo.Carrera;
import modelo.Docente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Materia;
import modelo.Matricula;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author jsnar
 */
public class MateriaJpaController implements Serializable {

    public MateriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public MateriaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Taller1JavaWebPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Materia materia) throws PreexistingEntityException, Exception {
        if (materia.getDocenteList() == null) {
            materia.setDocenteList(new ArrayList<Docente>());
        }
        if (materia.getMatriculaList() == null) {
            materia.setMatriculaList(new ArrayList<Matricula>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera numerocarrera = materia.getNumerocarrera();
            if (numerocarrera != null) {
                numerocarrera = em.getReference(numerocarrera.getClass(), numerocarrera.getNumerocarrera());
                materia.setNumerocarrera(numerocarrera);
            }
            List<Docente> attachedDocenteList = new ArrayList<Docente>();
            for (Docente docenteListDocenteToAttach : materia.getDocenteList()) {
                docenteListDocenteToAttach = em.getReference(docenteListDocenteToAttach.getClass(), docenteListDocenteToAttach.getDocumentodocente());
                attachedDocenteList.add(docenteListDocenteToAttach);
            }
            materia.setDocenteList(attachedDocenteList);
            List<Matricula> attachedMatriculaList = new ArrayList<Matricula>();
            for (Matricula matriculaListMatriculaToAttach : materia.getMatriculaList()) {
                matriculaListMatriculaToAttach = em.getReference(matriculaListMatriculaToAttach.getClass(), matriculaListMatriculaToAttach.getMatriculaPK());
                attachedMatriculaList.add(matriculaListMatriculaToAttach);
            }
            materia.setMatriculaList(attachedMatriculaList);
            em.persist(materia);
            if (numerocarrera != null) {
                numerocarrera.getMateriaList().add(materia);
                numerocarrera = em.merge(numerocarrera);
            }
            for (Docente docenteListDocente : materia.getDocenteList()) {
                docenteListDocente.getMateriaList().add(materia);
                docenteListDocente = em.merge(docenteListDocente);
            }
            for (Matricula matriculaListMatricula : materia.getMatriculaList()) {
                Materia oldMateriaOfMatriculaListMatricula = matriculaListMatricula.getMateria();
                matriculaListMatricula.setMateria(materia);
                matriculaListMatricula = em.merge(matriculaListMatricula);
                if (oldMateriaOfMatriculaListMatricula != null) {
                    oldMateriaOfMatriculaListMatricula.getMatriculaList().remove(matriculaListMatricula);
                    oldMateriaOfMatriculaListMatricula = em.merge(oldMateriaOfMatriculaListMatricula);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMateria(materia.getNumeromateria()) != null) {
                throw new PreexistingEntityException("Materia " + materia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Materia materia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materia persistentMateria = em.find(Materia.class, materia.getNumeromateria());
            Carrera numerocarreraOld = persistentMateria.getNumerocarrera();
            Carrera numerocarreraNew = materia.getNumerocarrera();
            List<Docente> docenteListOld = persistentMateria.getDocenteList();
            List<Docente> docenteListNew = materia.getDocenteList();
            List<Matricula> matriculaListOld = persistentMateria.getMatriculaList();
            List<Matricula> matriculaListNew = materia.getMatriculaList();
            List<String> illegalOrphanMessages = null;
            for (Matricula matriculaListOldMatricula : matriculaListOld) {
                if (!matriculaListNew.contains(matriculaListOldMatricula)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Matricula " + matriculaListOldMatricula + " since its materia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (numerocarreraNew != null) {
                numerocarreraNew = em.getReference(numerocarreraNew.getClass(), numerocarreraNew.getNumerocarrera());
                materia.setNumerocarrera(numerocarreraNew);
            }
            List<Docente> attachedDocenteListNew = new ArrayList<Docente>();
            for (Docente docenteListNewDocenteToAttach : docenteListNew) {
                docenteListNewDocenteToAttach = em.getReference(docenteListNewDocenteToAttach.getClass(), docenteListNewDocenteToAttach.getDocumentodocente());
                attachedDocenteListNew.add(docenteListNewDocenteToAttach);
            }
            docenteListNew = attachedDocenteListNew;
            materia.setDocenteList(docenteListNew);
            List<Matricula> attachedMatriculaListNew = new ArrayList<Matricula>();
            for (Matricula matriculaListNewMatriculaToAttach : matriculaListNew) {
                matriculaListNewMatriculaToAttach = em.getReference(matriculaListNewMatriculaToAttach.getClass(), matriculaListNewMatriculaToAttach.getMatriculaPK());
                attachedMatriculaListNew.add(matriculaListNewMatriculaToAttach);
            }
            matriculaListNew = attachedMatriculaListNew;
            materia.setMatriculaList(matriculaListNew);
            materia = em.merge(materia);
            if (numerocarreraOld != null && !numerocarreraOld.equals(numerocarreraNew)) {
                numerocarreraOld.getMateriaList().remove(materia);
                numerocarreraOld = em.merge(numerocarreraOld);
            }
            if (numerocarreraNew != null && !numerocarreraNew.equals(numerocarreraOld)) {
                numerocarreraNew.getMateriaList().add(materia);
                numerocarreraNew = em.merge(numerocarreraNew);
            }
            for (Docente docenteListOldDocente : docenteListOld) {
                if (!docenteListNew.contains(docenteListOldDocente)) {
                    docenteListOldDocente.getMateriaList().remove(materia);
                    docenteListOldDocente = em.merge(docenteListOldDocente);
                }
            }
            for (Docente docenteListNewDocente : docenteListNew) {
                if (!docenteListOld.contains(docenteListNewDocente)) {
                    docenteListNewDocente.getMateriaList().add(materia);
                    docenteListNewDocente = em.merge(docenteListNewDocente);
                }
            }
            for (Matricula matriculaListNewMatricula : matriculaListNew) {
                if (!matriculaListOld.contains(matriculaListNewMatricula)) {
                    Materia oldMateriaOfMatriculaListNewMatricula = matriculaListNewMatricula.getMateria();
                    matriculaListNewMatricula.setMateria(materia);
                    matriculaListNewMatricula = em.merge(matriculaListNewMatricula);
                    if (oldMateriaOfMatriculaListNewMatricula != null && !oldMateriaOfMatriculaListNewMatricula.equals(materia)) {
                        oldMateriaOfMatriculaListNewMatricula.getMatriculaList().remove(matriculaListNewMatricula);
                        oldMateriaOfMatriculaListNewMatricula = em.merge(oldMateriaOfMatriculaListNewMatricula);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = materia.getNumeromateria();
                if (findMateria(id) == null) {
                    throw new NonexistentEntityException("The materia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materia materia;
            try {
                materia = em.getReference(Materia.class, id);
                materia.getNumeromateria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The materia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Matricula> matriculaListOrphanCheck = materia.getMatriculaList();
            for (Matricula matriculaListOrphanCheckMatricula : matriculaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("No se puede eliminar la materia debido a que algún estudinate la tiene matriculada.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Carrera numerocarrera = materia.getNumerocarrera();
            if (numerocarrera != null) {
                numerocarrera.getMateriaList().remove(materia);
                numerocarrera = em.merge(numerocarrera);
            }
            List<Docente> docenteList = materia.getDocenteList();
            for (Docente docenteListDocente : docenteList) {
                docenteListDocente.getMateriaList().remove(materia);
                docenteListDocente = em.merge(docenteListDocente);
            }
            em.remove(materia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Materia> findMateriaEntities() {
        return findMateriaEntities(true, -1, -1);
    }

    public List<Materia> findMateriaEntities(int maxResults, int firstResult) {
        return findMateriaEntities(false, maxResults, firstResult);
    }

    private List<Materia> findMateriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Materia.class));
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

    public Materia findMateria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Materia.class, id);
        } finally {
            em.close();
        }
    }

    public int getMateriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Materia> rt = cq.from(Materia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
