/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "tipo_servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoServicio.findAll", query = "SELECT t FROM TipoServicio t"),
    @NamedQuery(name = "TipoServicio.findByIdTipoServicio", query = "SELECT t FROM TipoServicio t WHERE t.idTipoServicio = :idTipoServicio"),
    @NamedQuery(name = "TipoServicio.findByNombreTipoServicio", query = "SELECT t FROM TipoServicio t WHERE t.nombreTipoServicio = :nombreTipoServicio"),
    @NamedQuery(name = "TipoServicio.findByDescripcionTipoServicio", query = "SELECT t FROM TipoServicio t WHERE t.descripcionTipoServicio = :descripcionTipoServicio")})
public class TipoServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ID_TIPO_SERVICIO")
    private String idTipoServicio;
    @Size(max = 100)
    @Column(name = "NOMBRE_TIPO_SERVICIO")
    private String nombreTipoServicio;
    @Size(max = 250)
    @Column(name = "DESCRIPCION_TIPO_SERVICIO")
    private String descripcionTipoServicio;
    @JoinColumn(name = "ID_SERVICIO", referencedColumnName = "ID_SERVICIO")
    @ManyToOne(optional = false)
    private Servicio idServicio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoServicio")
    private List<ServicioCliente> servicioClienteList;

    public TipoServicio() {
    }

    public TipoServicio(String idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public String getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(String idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public String getNombreTipoServicio() {
        return nombreTipoServicio;
    }

    public void setNombreTipoServicio(String nombreTipoServicio) {
        this.nombreTipoServicio = nombreTipoServicio;
    }

    public String getDescripcionTipoServicio() {
        return descripcionTipoServicio;
    }

    public void setDescripcionTipoServicio(String descripcionTipoServicio) {
        this.descripcionTipoServicio = descripcionTipoServicio;
    }

    public Servicio getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Servicio idServicio) {
        this.idServicio = idServicio;
    }

    @XmlTransient
    public List<ServicioCliente> getServicioClienteList() {
        return servicioClienteList;
    }

    public void setServicioClienteList(List<ServicioCliente> servicioClienteList) {
        this.servicioClienteList = servicioClienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoServicio != null ? idTipoServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoServicio)) {
            return false;
        }
        TipoServicio other = (TipoServicio) object;
        if ((this.idTipoServicio == null && other.idTipoServicio != null) || (this.idTipoServicio != null && !this.idTipoServicio.equals(other.idTipoServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreTipoServicio;
    }
    
}
