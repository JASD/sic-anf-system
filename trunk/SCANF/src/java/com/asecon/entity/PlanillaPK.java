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
public class PlanillaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMERO_PERIODO")
    private long numeroPeriodo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ID_EMPLEADO")
    private String idEmpleado;

    public PlanillaPK() {
    }

    public PlanillaPK(long numeroPeriodo, String idEmpleado) {
        this.numeroPeriodo = numeroPeriodo;
        this.idEmpleado = idEmpleado;
    }

    public long getNumeroPeriodo() {
        return numeroPeriodo;
    }

    public void setNumeroPeriodo(long numeroPeriodo) {
        this.numeroPeriodo = numeroPeriodo;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) numeroPeriodo;
        hash += (idEmpleado != null ? idEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaPK)) {
            return false;
        }
        PlanillaPK other = (PlanillaPK) object;
        if (this.numeroPeriodo != other.numeroPeriodo) {
            return false;
        }
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asecon.entity.PlanillaPK[ numeroPeriodo=" + numeroPeriodo + ", idEmpleado=" + idEmpleado + " ]";
    }
    
}
