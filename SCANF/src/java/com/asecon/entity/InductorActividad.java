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
@Table(name = "inductor_actividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InductorActividad.findAll", query = "SELECT i FROM InductorActividad i"),
    @NamedQuery(name = "InductorActividad.findByIdInductorActividad", query = "SELECT i FROM InductorActividad i WHERE i.idInductorActividad = :idInductorActividad"),
    @NamedQuery(name = "InductorActividad.findByNombreInductorActividad", query = "SELECT i FROM InductorActividad i WHERE i.nombreInductorActividad = :nombreInductorActividad"),
    @NamedQuery(name = "InductorActividad.findByDescripcionInductorActividad", query = "SELECT i FROM InductorActividad i WHERE i.descripcionInductorActividad = :descripcionInductorActividad")})
public class InductorActividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ID_INDUCTOR_ACTIVIDAD")
    private String idInductorActividad;
    @Size(max = 50)
    @Column(name = "NOMBRE_INDUCTOR_ACTIVIDAD")
    private String nombreInductorActividad;
    @Size(max = 250)
    @Column(name = "DESCRIPCION_INDUCTOR_ACTIVIDAD")
    private String descripcionInductorActividad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInductorActividad")
    private List<Actividad> actividadList;

    public InductorActividad() {
    }

    public InductorActividad(String idInductorActividad) {
        this.idInductorActividad = idInductorActividad;
    }

    public String getIdInductorActividad() {
        return idInductorActividad;
    }

    public void setIdInductorActividad(String idInductorActividad) {
        this.idInductorActividad = idInductorActividad;
    }

    public String getNombreInductorActividad() {
        return nombreInductorActividad;
    }

    public void setNombreInductorActividad(String nombreInductorActividad) {
        this.nombreInductorActividad = nombreInductorActividad;
    }

    public String getDescripcionInductorActividad() {
        return descripcionInductorActividad;
    }

    public void setDescripcionInductorActividad(String descripcionInductorActividad) {
        this.descripcionInductorActividad = descripcionInductorActividad;
    }

    @XmlTransient
    public List<Actividad> getActividadList() {
        return actividadList;
    }

    public void setActividadList(List<Actividad> actividadList) {
        this.actividadList = actividadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInductorActividad != null ? idInductorActividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InductorActividad)) {
            return false;
        }
        InductorActividad other = (InductorActividad) object;
        if ((this.idInductorActividad == null && other.idInductorActividad != null) || (this.idInductorActividad != null && !this.idInductorActividad.equals(other.idInductorActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreInductorActividad;
    }
    
}
