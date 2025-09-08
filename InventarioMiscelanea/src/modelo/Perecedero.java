/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ericg
 */
@Entity
@Table(name = "perecedero")
@NamedQueries({
    @NamedQuery(name = "Perecedero.findAll", query = "SELECT p FROM Perecedero p"),
    @NamedQuery(name = "Perecedero.findByIdPerecedero", query = "SELECT p FROM Perecedero p WHERE p.idPerecedero = :idPerecedero"),
    @NamedQuery(name = "Perecedero.findByFechaCaducidad", query = "SELECT p FROM Perecedero p WHERE p.fechaCaducidad = :fechaCaducidad")})
public class Perecedero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_perecedero")
    private Integer idPerecedero;
    @Column(name = "fecha_caducidad")
    @Temporal(TemporalType.DATE)
    private Date fechaCaducidad;
    @JoinColumn(name = "producto_id", referencedColumnName = "id_producto")
    @ManyToOne
    private Producto productoId;

    public Perecedero() {
    }

    public Perecedero(Integer idPerecedero) {
        this.idPerecedero = idPerecedero;
    }

    public Integer getIdPerecedero() {
        return idPerecedero;
    }

    public void setIdPerecedero(Integer idPerecedero) {
        this.idPerecedero = idPerecedero;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Producto getProductoId() {
        return productoId;
    }

    public void setProductoId(Producto productoId) {
        this.productoId = productoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerecedero != null ? idPerecedero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Perecedero)) {
            return false;
        }
        Perecedero other = (Perecedero) object;
        if ((this.idPerecedero == null && other.idPerecedero != null) || (this.idPerecedero != null && !this.idPerecedero.equals(other.idPerecedero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Perecedero[ idPerecedero=" + idPerecedero + " ]";
    }
    
}
