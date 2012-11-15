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
public class SaldoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMERO_PERIODO")
    private long numeroPeriodo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "CODIGO_SUBCUENTA")
    private String codigoSubcuenta;

    public SaldoPK() {
    }

    public SaldoPK(long numeroPeriodo, String codigoSubcuenta) {
        this.numeroPeriodo = numeroPeriodo;
        this.codigoSubcuenta = codigoSubcuenta;
    }

    public long getNumeroPeriodo() {
        return numeroPeriodo;
    }

    public void setNumeroPeriodo(long numeroPeriodo) {
        this.numeroPeriodo = numeroPeriodo;
    }

    public String getCodigoSubcuenta() {
        return codigoSubcuenta;
    }

    public void setCodigoSubcuenta(String codigoSubcuenta) {
        this.codigoSubcuenta = codigoSubcuenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) numeroPeriodo;
        hash += (codigoSubcuenta != null ? codigoSubcuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaldoPK)) {
            return false;
        }
        SaldoPK other = (SaldoPK) object;
        if (this.numeroPeriodo != other.numeroPeriodo) {
            return false;
        }
        if ((this.codigoSubcuenta == null && other.codigoSubcuenta != null) || (this.codigoSubcuenta != null && !this.codigoSubcuenta.equals(other.codigoSubcuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asecon.entity.SaldoPK[ numeroPeriodo=" + numeroPeriodo + ", codigoSubcuenta=" + codigoSubcuenta + " ]";
    }
    
}
