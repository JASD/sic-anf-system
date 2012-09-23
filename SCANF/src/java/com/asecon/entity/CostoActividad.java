/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "costo_actividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CostoActividad.findAll", query = "SELECT c FROM CostoActividad c"),
    @NamedQuery(name = "CostoActividad.findAllCurrent", query = "SELECT c FROM CostoActividad c WHERE c.costoActividadPK.numeroPeriodo = (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)"),
    @NamedQuery(name = "CostoActividad.findByCostoServ", query = "SELECT c FROM CostoActividad c WHERE c.costoActividadPK.idActividad = :idActividad AND c.costoActividadPK.numeroPeriodo = :numeroPeriodo"),
    @NamedQuery(name = "CostoActividad.findByNumeroPeriodo", query = "SELECT c FROM CostoActividad c WHERE c.costoActividadPK.numeroPeriodo = :numeroPeriodo"),
    @NamedQuery(name = "CostoActividad.findByIdActividad", query = "SELECT c FROM CostoActividad c WHERE c.costoActividadPK.idActividad = :idActividad"),
    @NamedQuery(name = "CostoActividad.findByValorInductorRecurso", query = "SELECT c FROM CostoActividad c WHERE c.valorInductorRecurso = :valorInductorRecurso"),
    @NamedQuery(name = "CostoActividad.findByCisActividad", query = "SELECT c FROM CostoActividad c WHERE c.cisActividad = :cisActividad"),
    @NamedQuery(name = "CostoActividad.findByTotalInductorActividad", query = "SELECT c FROM CostoActividad c WHERE c.totalInductorActividad = :totalInductorActividad"),
    @NamedQuery(name = "CostoActividad.findByCostoActividad", query = "SELECT c FROM CostoActividad c WHERE c.costoActividad = :costoActividad")})
public class CostoActividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CostoActividadPK costoActividadPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR_INDUCTOR_RECURSO")
    private BigDecimal valorInductorRecurso;
    @Column(name = "CIS_ACTIVIDAD")
    private Float cisActividad;
    @Column(name = "TOTAL_INDUCTOR_ACTIVIDAD")
    private BigDecimal totalInductorActividad;
    @Column(name = "COSTO_ACTIVIDAD")
    private Float costoActividad;
    @JoinColumn(name = "NUMERO_PERIODO", referencedColumnName = "NUMERO_PERIODO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Periodo periodo;
    @JoinColumn(name = "ID_ACTIVIDAD", referencedColumnName = "ID_ACTIVIDAD", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Actividad actividad;

    public CostoActividad() {
    }

    public CostoActividad(CostoActividadPK costoActividadPK) {
        this.costoActividadPK = costoActividadPK;
    }

    public CostoActividad(long numeroPeriodo, String idActividad) {
        this.costoActividadPK = new CostoActividadPK(numeroPeriodo, idActividad);
    }

    public CostoActividadPK getCostoActividadPK() {
        return costoActividadPK;
    }

    public void setCostoActividadPK(CostoActividadPK costoActividadPK) {
        this.costoActividadPK = costoActividadPK;
    }

    public BigDecimal getValorInductorRecurso() {
        return valorInductorRecurso;
    }

    public void setValorInductorRecurso(BigDecimal valorInductorRecurso) {
        this.valorInductorRecurso = valorInductorRecurso;
    }

    public Float getCisActividad() {
        return cisActividad;
    }

    public void setCisActividad(Float cisActividad) {
        this.cisActividad = cisActividad;
    }

    public BigDecimal getTotalInductorActividad() {
        return totalInductorActividad;
    }

    public void setTotalInductorActividad(BigDecimal totalInductorActividad) {
        this.totalInductorActividad = totalInductorActividad;
    }

    public Float getCostoActividad() {
        return costoActividad;
    }

    public void setCostoActividad(Float costoActividad) {
        this.costoActividad = costoActividad;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }
    
    public void calcularCosto(){
    
        this.costoActividad = this.cisActividad / this.totalInductorActividad.floatValue();
    }
    
    public void calcularCis(Float totalCis){
        this.cisActividad = totalCis * this.valorInductorRecurso.floatValue();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (costoActividadPK != null ? costoActividadPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CostoActividad)) {
            return false;
        }
        CostoActividad other = (CostoActividad) object;
        if ((this.costoActividadPK == null && other.costoActividadPK != null) || (this.costoActividadPK != null && !this.costoActividadPK.equals(other.costoActividadPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asecon.entity.CostoActividad[ costoActividadPK=" + costoActividadPK + " ]";
    }
    
}
