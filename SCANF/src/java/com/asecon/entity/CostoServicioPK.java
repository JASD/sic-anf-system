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
public class CostoServicioPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ID_SERVICIO")
    private String idServicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMERO_PERIODO")
    private long numeroPeriodo;

    public CostoServicioPK() {
    }

    public CostoServicioPK(String idServicio, long numeroPeriodo) {
        this.idServicio = idServicio;
        this.numeroPeriodo = numeroPeriodo;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public long getNumeroPeriodo() {
        return numeroPeriodo;
    }

    public void setNumeroPeriodo(long numeroPeriodo) {
        this.numeroPeriodo = numeroPeriodo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idServicio != null ? idServicio.hashCode() : 0);
        hash += (int) numeroPeriodo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CostoServicioPK)) {
            return false;
        }
        CostoServicioPK other = (CostoServicioPK) object;
        if ((this.idServicio == null && other.idServicio != null) || (this.idServicio != null && !this.idServicio.equals(other.idServicio))) {
            return false;
        }
        if (this.numeroPeriodo != other.numeroPeriodo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asecon.entity.CostoServicioPK[ idServicio=" + idServicio + ", numeroPeriodo=" + numeroPeriodo + " ]";
    }
    
}
