/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Perecedero;
import modelo.Producto;

/**
 *
 * @author ericg
 */
public class PerecederoJpaController implements Serializable {

    public PerecederoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Perecedero perecedero) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto productoId = perecedero.getProductoId();
            if (productoId != null) {
                productoId = em.getReference(productoId.getClass(), productoId.getIdProducto());
                perecedero.setProductoId(productoId);
            }
            em.persist(perecedero);
            if (productoId != null) {
                productoId.getPerecederoList().add(perecedero);
                productoId = em.merge(productoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Perecedero perecedero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Perecedero persistentPerecedero = em.find(Perecedero.class, perecedero.getIdPerecedero());
            Producto productoIdOld = persistentPerecedero.getProductoId();
            Producto productoIdNew = perecedero.getProductoId();
            if (productoIdNew != null) {
                productoIdNew = em.getReference(productoIdNew.getClass(), productoIdNew.getIdProducto());
                perecedero.setProductoId(productoIdNew);
            }
            perecedero = em.merge(perecedero);
            if (productoIdOld != null && !productoIdOld.equals(productoIdNew)) {
                productoIdOld.getPerecederoList().remove(perecedero);
                productoIdOld = em.merge(productoIdOld);
            }
            if (productoIdNew != null && !productoIdNew.equals(productoIdOld)) {
                productoIdNew.getPerecederoList().add(perecedero);
                productoIdNew = em.merge(productoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = perecedero.getIdPerecedero();
                if (findPerecedero(id) == null) {
                    throw new NonexistentEntityException("The perecedero with id " + id + " no longer exists.");
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
            Perecedero perecedero;
            try {
                perecedero = em.getReference(Perecedero.class, id);
                perecedero.getIdPerecedero();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The perecedero with id " + id + " no longer exists.", enfe);
            }
            Producto productoId = perecedero.getProductoId();
            if (productoId != null) {
                productoId.getPerecederoList().remove(perecedero);
                productoId = em.merge(productoId);
            }
            em.remove(perecedero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Perecedero> findPerecederoEntities() {
        return findPerecederoEntities(true, -1, -1);
    }

    public List<Perecedero> findPerecederoEntities(int maxResults, int firstResult) {
        return findPerecederoEntities(false, maxResults, firstResult);
    }

    private List<Perecedero> findPerecederoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Perecedero.class));
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

    public Perecedero findPerecedero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Perecedero.class, id);
        } finally {
            em.close();
        }
    }

    public int getPerecederoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Perecedero> rt = cq.from(Perecedero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
