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
public class ServicioClientePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ID_TIPO_SERVICIO")
    private String idTipoServicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMERO_CLIENTE")
    private long numeroCliente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMERO_PERIODO")
    private long numeroPeriodo;

    public ServicioClientePK() {
    }

    public ServicioClientePK(String idTipoServicio, long numeroCliente, long numeroPeriodo) {
        this.idTipoServicio = idTipoServicio;
        this.numeroCliente = numeroCliente;
        this.numeroPeriodo = numeroPeriodo;
    }

    public String getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(String idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public long getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(long numeroCliente) {
        this.numeroCliente = numeroCliente;
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
        hash += (idTipoServicio != null ? idTipoServicio.hashCode() : 0);
        hash += (int) numeroCliente;
        hash += (int) numeroPeriodo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServicioClientePK)) {
            return false;
        }
        ServicioClientePK other = (ServicioClientePK) object;
        if ((this.idTipoServicio == null && other.idTipoServicio != null) || (this.idTipoServicio != null && !this.idTipoServicio.equals(other.idTipoServicio))) {
            return false;
        }
        if (this.numeroCliente != other.numeroCliente) {
            return false;
        }
        if (this.numeroPeriodo != other.numeroPeriodo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asecon.entity.ServicioClientePK[ idTipoServicio=" + idTipoServicio + ", numeroCliente=" + numeroCliente + ", numeroPeriodo=" + numeroPeriodo + " ]";
    }
    
}
