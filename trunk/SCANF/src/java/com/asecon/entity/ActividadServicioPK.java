/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Antonio
 */
@Embeddable
public class ActividadServicioPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMERO_PERIODO")
    private long numeroPeriodo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ID_ACTIVIDAD")
    private String idActividad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ID_SERVICIO")
    private String idServicio;

    public ActividadServicioPK() {
    }

    public ActividadServicioPK(long numeroPeriodo, String idActividad, String idServicio) {
        this.numeroPeriodo = numeroPeriodo;
        this.idActividad = idActividad;
        this.idServicio = idServicio;
    }

    public long getNumeroPeriodo() {
        return numeroPeriodo;
    }

    public void setNumeroPeriodo(long numeroPeriodo) {
        this.numeroPeriodo = numeroPeriodo;
    }

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) numeroPeriodo;
        hash += (idActividad != null ? idActividad.hashCode() : 0);
        hash += (idServicio != null ? idServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadServicioPK)) {
            return false;
        }
        ActividadServicioPK other = (ActividadServicioPK) object;
        if (this.numeroPeriodo != other.numeroPeriodo) {
            return false;
        }
        if ((this.idActividad == null && other.idActividad != null) || (this.idActividad != null && !this.idActividad.equals(other.idActividad))) {
            return false;
        }
        if ((this.idServicio == null && other.idServicio != null) || (this.idServicio != null && !this.idServicio.equals(other.idServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asecon.entity.ActividadServicioPK[ numeroPeriodo=" + numeroPeriodo + ", idActividad=" + idActividad + ", idServicio=" + idServicio + " ]";
    }
    
}
