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
@Table(name = "servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servicio.findAll", query = "SELECT s FROM Servicio s"),
    @NamedQuery(name = "Servicio.findByIdServicio", query = "SELECT s FROM Servicio s WHERE s.idServicio = :idServicio"),
    @NamedQuery(name = "Servicio.findByNombreServicio", query = "SELECT s FROM Servicio s WHERE s.nombreServicio = :nombreServicio"),
    @NamedQuery(name = "Servicio.findByDescripcionServicio", query = "SELECT s FROM Servicio s WHERE s.descripcionServicio = :descripcionServicio")})
public class Servicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ID_SERVICIO")
    private String idServicio;
    @Size(max = 50)
    @Column(name = "NOMBRE_SERVICIO")
    private String nombreServicio;
    @Size(max = 250)
    @Column(name = "DESCRIPCION_SERVICIO")
    private String descripcionServicio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servicio")
    private List<CostoServicio> costoServicioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servicio")
    private List<ActividadServicio> actividadServicioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idServicio")
    private List<TipoServicio> tipoServicioList;

    public Servicio() {
    }

    public Servicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    @XmlTransient
    public List<CostoServicio> getCostoServicioList() {
        return costoServicioList;
    }

    public void setCostoServicioList(List<CostoServicio> costoServicioList) {
        this.costoServicioList = costoServicioList;
    }

    @XmlTransient
    public List<ActividadServicio> getActividadServicioList() {
        return actividadServicioList;
    }

    public void setActividadServicioList(List<ActividadServicio> actividadServicioList) {
        this.actividadServicioList = actividadServicioList;
    }

    @XmlTransient
    public List<TipoServicio> getTipoServicioList() {
        return tipoServicioList;
    }

    public void setTipoServicioList(List<TipoServicio> tipoServicioList) {
        this.tipoServicioList = tipoServicioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idServicio != null ? idServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicio)) {
            return false;
        }
        Servicio other = (Servicio) object;
        if ((this.idServicio == null && other.idServicio != null) || (this.idServicio != null && !this.idServicio.equals(other.idServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreServicio;
    }
    
}
