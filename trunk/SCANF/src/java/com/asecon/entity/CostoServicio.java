/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "costo_servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CostoServicio.findAll", query = "SELECT c FROM CostoServicio c"),
    @NamedQuery(name = "CostoServicio.findAllCurrent", query = "SELECT c FROM CostoServicio c WHERE c.costoServicioPK.numeroPeriodo = (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)"),
    @NamedQuery(name = "CostoServicio.findByIdServicio", query = "SELECT c FROM CostoServicio c WHERE c.costoServicioPK.idServicio = :idServicio"),
    @NamedQuery(name = "CostoServicio.findByNumeroPeriodo", query = "SELECT c FROM CostoServicio c WHERE c.costoServicioPK.numeroPeriodo = :numeroPeriodo"),
    @NamedQuery(name = "CostoServicio.findByCostoTotalServicio", query = "SELECT c FROM CostoServicio c WHERE c.costoTotalServicio = :costoTotalServicio"),
    @NamedQuery(name = "CostoServicio.findByNumeroRepeticionesServicio", query = "SELECT c FROM CostoServicio c WHERE c.numeroRepeticionesServicio = :numeroRepeticionesServicio"),
    @NamedQuery(name = "CostoServicio.findByCostoServicio", query = "SELECT c FROM CostoServicio c WHERE c.costoServicio = :costoServicio")})
public class CostoServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CostoServicioPK costoServicioPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "COSTO_TOTAL_SERVICIO")
    private Float costoTotalServicio;
    @Column(name = "NUMERO_REPETICIONES_SERVICIO")
    private Integer numeroRepeticionesServicio;
    @Column(name = "COSTO_SERVICIO")
    private Float costoServicio;
    @JoinColumn(name = "ID_SERVICIO", referencedColumnName = "ID_SERVICIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Servicio servicio;
    @JoinColumn(name = "NUMERO_PERIODO", referencedColumnName = "NUMERO_PERIODO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Periodo periodo;

    public CostoServicio() {
    }
    
    public void CalcularCosto(){
    
        this.costoServicio = this.costoTotalServicio / this.numeroRepeticionesServicio.floatValue();
    }

    public CostoServicio(CostoServicioPK costoServicioPK) {
        this.costoServicioPK = costoServicioPK;
    }

    public CostoServicio(String idServicio, long numeroPeriodo) {
        this.costoServicioPK = new CostoServicioPK(idServicio, numeroPeriodo);
    }

    public CostoServicioPK getCostoServicioPK() {
        return costoServicioPK;
    }

    public void setCostoServicioPK(CostoServicioPK costoServicioPK) {
        this.costoServicioPK = costoServicioPK;
    }

    public Float getCostoTotalServicio() {
        return costoTotalServicio;
    }

    public void setCostoTotalServicio(Float costoTotalServicio) {
        this.costoTotalServicio = costoTotalServicio;
    }

    public Integer getNumeroRepeticionesServicio() {
        return numeroRepeticionesServicio;
    }

    public void setNumeroRepeticionesServicio(Integer numeroRepeticionesServicio) {
        this.numeroRepeticionesServicio = numeroRepeticionesServicio;
    }

    public Float getCostoServicio() {
        return costoServicio;
    }

    public void setCostoServicio(Float costoServicio) {
        this.costoServicio = costoServicio;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (costoServicioPK != null ? costoServicioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CostoServicio)) {
            return false;
        }
        CostoServicio other = (CostoServicio) object;
        if ((this.costoServicioPK == null && other.costoServicioPK != null) || (this.costoServicioPK != null && !this.costoServicioPK.equals(other.costoServicioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asecon.entity.CostoServicio[ costoServicioPK=" + costoServicioPK + " ]";
    }
    
}
