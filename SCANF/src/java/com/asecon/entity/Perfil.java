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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "perfil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Perfil.findAll", query = "SELECT p FROM Perfil p"),
    @NamedQuery(name = "Perfil.findByIdPerfil", query = "SELECT p FROM Perfil p WHERE p.idPerfil = :idPerfil"),
    @NamedQuery(name = "Perfil.findByNombrePerfil", query = "SELECT p FROM Perfil p WHERE p.nombrePerfil = :nombrePerfil"),
    @NamedQuery(name = "Perfil.findByPermisoRegistroContable", query = "SELECT p FROM Perfil p WHERE p.permisoRegistroContable = :permisoRegistroContable"),
    @NamedQuery(name = "Perfil.findByPermisoRegistroCosto", query = "SELECT p FROM Perfil p WHERE p.permisoRegistroCosto = :permisoRegistroCosto"),
    @NamedQuery(name = "Perfil.findByPermisoLibros", query = "SELECT p FROM Perfil p WHERE p.permisoLibros = :permisoLibros"),
    @NamedQuery(name = "Perfil.findByPermisoEstadosFinancieros", query = "SELECT p FROM Perfil p WHERE p.permisoEstadosFinancieros = :permisoEstadosFinancieros"),
    @NamedQuery(name = "Perfil.findByPermisoMantenimiento", query = "SELECT p FROM Perfil p WHERE p.permisoMantenimiento = :permisoMantenimiento"),
    @NamedQuery(name = "Perfil.findByPermisoAdministracion", query = "SELECT p FROM Perfil p WHERE p.permisoAdministracion = :permisoAdministracion")})
public class Perfil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERFIL", insertable = false, nullable = false, updatable = true)
    private Integer idPerfil;
    @Size(max = 15)
    @Column(name = "NOMBRE_PERFIL")
    private String nombrePerfil;
    @Column(name = "PERMISO_REGISTRO_CONTABLE")
    private Boolean permisoRegistroContable;
    @Column(name = "PERMISO_REGISTRO_COSTO")
    private Boolean permisoRegistroCosto;
    @Column(name = "PERMISO_LIBROS")
    private Boolean permisoLibros;
    @Column(name = "PERMISO_ESTADOS_FINANCIEROS")
    private Boolean permisoEstadosFinancieros;
    @Column(name = "PERMISO_MANTENIMIENTO")
    private Boolean permisoMantenimiento;
    @Column(name = "PERMISO_ADMINISTRACION")
    private Boolean permisoAdministracion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPerfil")
    private List<Usuario> usuarioList;

    public Perfil() {
    }

    public Perfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public Boolean getPermisoRegistroContable() {
        return permisoRegistroContable;
    }

    public void setPermisoRegistroContable(Boolean permisoRegistroContable) {
        this.permisoRegistroContable = permisoRegistroContable;
    }

    public Boolean getPermisoRegistroCosto() {
        return permisoRegistroCosto;
    }

    public void setPermisoRegistroCosto(Boolean permisoRegistroCosto) {
        this.permisoRegistroCosto = permisoRegistroCosto;
    }

    public Boolean getPermisoLibros() {
        return permisoLibros;
    }

    public void setPermisoLibros(Boolean permisoLibros) {
        this.permisoLibros = permisoLibros;
    }

    public Boolean getPermisoEstadosFinancieros() {
        return permisoEstadosFinancieros;
    }

    public void setPermisoEstadosFinancieros(Boolean permisoEstadosFinancieros) {
        this.permisoEstadosFinancieros = permisoEstadosFinancieros;
    }

    public Boolean getPermisoMantenimiento() {
        return permisoMantenimiento;
    }

    public void setPermisoMantenimiento(Boolean permisoMantenimiento) {
        this.permisoMantenimiento = permisoMantenimiento;
    }

    public Boolean getPermisoAdministracion() {
        return permisoAdministracion;
    }

    public void setPermisoAdministracion(Boolean permisoAdministracion) {
        this.permisoAdministracion = permisoAdministracion;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerfil != null ? idPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Perfil)) {
            return false;
        }
        Perfil other = (Perfil) object;
        if ((this.idPerfil == null && other.idPerfil != null) || (this.idPerfil != null && !this.idPerfil.equals(other.idPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombrePerfil;
    }
}
