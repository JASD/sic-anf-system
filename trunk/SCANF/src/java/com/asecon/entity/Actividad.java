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
@Table(name = "actividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actividad.findAll", query = "SELECT a FROM Actividad a"),
    @NamedQuery(name = "Actividad.findByIdActividad", query = "SELECT a FROM Actividad a WHERE a.idActividad = :idActividad"),
    @NamedQuery(name = "Actividad.findByNombreActividad", query = "SELECT a FROM Actividad a WHERE a.nombreActividad = :nombreActividad"),
    @NamedQuery(name = "Actividad.findByDescripcionActividad", query = "SELECT a FROM Actividad a WHERE a.descripcionActividad = :descripcionActividad")})
public class Actividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ID_ACTIVIDAD")
    private String idActividad;
    @Size(max = 50)
    @Column(name = "NOMBRE_ACTIVIDAD")
    private String nombreActividad;
    @Size(max = 250)
    @Column(name = "DESCRIPCION_ACTIVIDAD")
    private String descripcionActividad;
    @JoinColumn(name = "ID_INDUCTOR_ACTIVIDAD", referencedColumnName = "ID_INDUCTOR_ACTIVIDAD")
    @ManyToOne(optional = false)
    private InductorActividad idInductorActividad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actividad")
    private List<ActividadServicio> actividadServicioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actividad")
    private List<CostoActividad> costoActividadList;

    public Actividad() {
    }

    public Actividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getDescripcionActividad() {
        return descripcionActividad;
    }

    public void setDescripcionActividad(String descripcionActividad) {
        this.descripcionActividad = descripcionActividad;
    }

    public InductorActividad getIdInductorActividad() {
        return idInductorActividad;
    }

    public void setIdInductorActividad(InductorActividad idInductorActividad) {
        this.idInductorActividad = idInductorActividad;
    }

    @XmlTransient
    public List<ActividadServicio> getActividadServicioList() {
        return actividadServicioList;
    }

    public void setActividadServicioList(List<ActividadServicio> actividadServicioList) {
        this.actividadServicioList = actividadServicioList;
    }

    @XmlTransient
    public List<CostoActividad> getCostoActividadList() {
        return costoActividadList;
    }

    public void setCostoActividadList(List<CostoActividad> costoActividadList) {
        this.costoActividadList = costoActividadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActividad != null ? idActividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actividad)) {
            return false;
        }
        Actividad other = (Actividad) object;
        if ((this.idActividad == null && other.idActividad != null) || (this.idActividad != null && !this.idActividad.equals(other.idActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreActividad;
    }
}
