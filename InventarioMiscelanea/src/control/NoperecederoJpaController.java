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
import modelo.Noperecedero;
import modelo.Producto;

/**
 *
 * @author ericg
 */
public class NoperecederoJpaController implements Serializable {

    public NoperecederoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Noperecedero noperecedero) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto productoId = noperecedero.getProductoId();
            if (productoId != null) {
                productoId = em.getReference(productoId.getClass(), productoId.getIdProducto());
                noperecedero.setProductoId(productoId);
            }
            em.persist(noperecedero);
            if (productoId != null) {
                productoId.getNoperecederoList().add(noperecedero);
                productoId = em.merge(productoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Noperecedero noperecedero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Noperecedero persistentNoperecedero = em.find(Noperecedero.class, noperecedero.getId());
            Producto productoIdOld = persistentNoperecedero.getProductoId();
            Producto productoIdNew = noperecedero.getProductoId();
            if (productoIdNew != null) {
                productoIdNew = em.getReference(productoIdNew.getClass(), productoIdNew.getIdProducto());
                noperecedero.setProductoId(productoIdNew);
            }
            noperecedero = em.merge(noperecedero);
            if (productoIdOld != null && !productoIdOld.equals(productoIdNew)) {
                productoIdOld.getNoperecederoList().remove(noperecedero);
                productoIdOld = em.merge(productoIdOld);
            }
            if (productoIdNew != null && !productoIdNew.equals(productoIdOld)) {
                productoIdNew.getNoperecederoList().add(noperecedero);
                productoIdNew = em.merge(productoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = noperecedero.getId();
                if (findNoperecedero(id) == null) {
                    throw new NonexistentEntityException("The noperecedero with id " + id + " no longer exists.");
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
            Noperecedero noperecedero;
            try {
                noperecedero = em.getReference(Noperecedero.class, id);
                noperecedero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The noperecedero with id " + id + " no longer exists.", enfe);
            }
            Producto productoId = noperecedero.getProductoId();
            if (productoId != null) {
                productoId.getNoperecederoList().remove(noperecedero);
                productoId = em.merge(productoId);
            }
            em.remove(noperecedero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Noperecedero> findNoperecederoEntities() {
        return findNoperecederoEntities(true, -1, -1);
    }

    public List<Noperecedero> findNoperecederoEntities(int maxResults, int firstResult) {
        return findNoperecederoEntities(false, maxResults, firstResult);
    }

    private List<Noperecedero> findNoperecederoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Noperecedero.class));
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

    public Noperecedero findNoperecedero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Noperecedero.class, id);
        } finally {
            em.close();
        }
    }

    public int getNoperecederoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Noperecedero> rt = cq.from(Noperecedero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
