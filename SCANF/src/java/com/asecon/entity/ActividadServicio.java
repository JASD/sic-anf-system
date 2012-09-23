/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "actividad_servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadServicio.findAll", query = "SELECT a FROM ActividadServicio a"),
    @NamedQuery(name = "ActividadServicio.findByNumeroPeriodo", query = "SELECT a FROM ActividadServicio a WHERE a.actividadServicioPK.numeroPeriodo = :numeroPeriodo"),
    @NamedQuery(name = "ActividadServicio.findByIdActividad", query = "SELECT a FROM ActividadServicio a WHERE a.actividadServicioPK.idActividad = :idActividad"),
    @NamedQuery(name = "ActividadServicio.findBySumIduc", query = "SELECT a FROM ActividadServicio a WHERE a.actividadServicioPK.idActividad = :idActividad AND a.actividadServicioPK.numeroPeriodo = :numeroPeriodo"),
    @NamedQuery(name = "ActividadServicio.findByCostoServ", query = "SELECT a FROM ActividadServicio a WHERE a.actividadServicioPK.idServicio = :idServicio AND a.actividadServicioPK.numeroPeriodo = :numeroPeriodo"),
    @NamedQuery(name = "ActividadServicio.findByIdServicio", query = "SELECT a FROM ActividadServicio a WHERE a.actividadServicioPK.idServicio = :idServicio"),
    @NamedQuery(name = "ActividadServicio.findByFechaActividadServicio", query = "SELECT a FROM ActividadServicio a WHERE a.fechaActividadServicio = :fechaActividadServicio"),
    @NamedQuery(name = "ActividadServicio.findByValorInductorActividad", query = "SELECT a FROM ActividadServicio a WHERE a.valorInductorActividad = :valorInductorActividad"),
    @NamedQuery(name = "ActividadServicio.findByNumeroRepeticionesActividad", query = "SELECT a FROM ActividadServicio a WHERE a.numeroRepeticionesActividad = :numeroRepeticionesActividad"),
    @NamedQuery(name = "ActividadServicio.findByDescipcionActividadServicio", query = "SELECT a FROM ActividadServicio a WHERE a.descipcionActividadServicio = :descipcionActividadServicio"),
    @NamedQuery(name = "ActividadServicio.findByPer", query = "SELECT a FROM ActividadServicio a WHERE a.actividadServicioPK.numeroPeriodo = :periodo"),
    @NamedQuery(name = "ActividadServicio.findByServ", query = "SELECT a FROM ActividadServicio a WHERE a.actividadServicioPK.numeroPeriodo = (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true) AND a.servicio = :servicio"),
    @NamedQuery(name = "ActividadServicio.findByAct", query = "SELECT a FROM ActividadServicio a WHERE a.actividadServicioPK.numeroPeriodo = (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true) AND a.actividad = :actividad"),
    @NamedQuery(name = "ActividadServicio.findByPerServ", query = "SELECT a FROM ActividadServicio a WHERE a.actividadServicioPK.numeroPeriodo = :periodo AND a.servicio = :servicio"),
    @NamedQuery(name = "ActividadServicio.findByPerAct", query = "SELECT a FROM ActividadServicio a WHERE a.actividadServicioPK.numeroPeriodo = :periodo AND a.actividad = :actividad")})
public class ActividadServicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ActividadServicioPK actividadServicioPK;
    @Column(name = "FECHA_ACTIVIDAD_SERVICIO")
    @Temporal(TemporalType.DATE)
    private Date fechaActividadServicio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR_INDUCTOR_ACTIVIDAD")
    private BigDecimal valorInductorActividad;
    @Column(name = "NUMERO_REPETICIONES_ACTIVIDAD")
    private BigDecimal numeroRepeticionesActividad;
    @Size(max = 250)
    @Column(name = "DESCIPCION_ACTIVIDAD_SERVICIO")
    private String descipcionActividadServicio;
    @JoinColumn(name = "ID_ACTIVIDAD", referencedColumnName = "ID_ACTIVIDAD", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Actividad actividad;
    @JoinColumn(name = "NUMERO_PERIODO", referencedColumnName = "NUMERO_PERIODO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Periodo periodo;
    @JoinColumn(name = "ID_SERVICIO", referencedColumnName = "ID_SERVICIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Servicio servicio;

    public ActividadServicio() {
    }

    public ActividadServicio(ActividadServicioPK actividadServicioPK) {
        this.actividadServicioPK = actividadServicioPK;
    }

    public ActividadServicio(long numeroPeriodo, String idActividad, String idServicio) {
        this.actividadServicioPK = new ActividadServicioPK(numeroPeriodo, idActividad, idServicio);
    }

    public ActividadServicioPK getActividadServicioPK() {
        return actividadServicioPK;
    }

    public void setActividadServicioPK(ActividadServicioPK actividadServicioPK) {
        this.actividadServicioPK = actividadServicioPK;
    }

    public Date getFechaActividadServicio() {
        return fechaActividadServicio;
    }

    public void setFechaActividadServicio(Date fechaActividadServicio) {
        this.fechaActividadServicio = fechaActividadServicio;
    }

    public BigDecimal getValorInductorActividad() {
        return valorInductorActividad;
    }

    public void setValorInductorActividad(BigDecimal valorInductorActividad) {
        this.valorInductorActividad = valorInductorActividad;
    }

    public BigDecimal getNumeroRepeticionesActividad() {
        return numeroRepeticionesActividad;
    }

    public void setNumeroRepeticionesActividad(BigDecimal numeroRepeticionesActividad) {
        this.numeroRepeticionesActividad = numeroRepeticionesActividad;
    }

    public String getDescipcionActividadServicio() {
        return descipcionActividadServicio;
    }

    public void setDescipcionActividadServicio(String descipcionActividadServicio) {
        this.descipcionActividadServicio = descipcionActividadServicio;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actividadServicioPK != null ? actividadServicioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadServicio)) {
            return false;
        }
        ActividadServicio other = (ActividadServicio) object;
        if ((this.actividadServicioPK == null && other.actividadServicioPK != null) || (this.actividadServicioPK != null && !this.actividadServicioPK.equals(other.actividadServicioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asecon.entity.ActividadServicio[ actividadServicioPK=" + actividadServicioPK + " ]";
    }
}
