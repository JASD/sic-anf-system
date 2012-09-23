/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByAllActivos", query = "SELECT e FROM Empleado e WHERE e.estadoEmpleado = true"),
    @NamedQuery(name = "Empleado.findByIdEmpleado", query = "SELECT e FROM Empleado e WHERE e.idEmpleado = :idEmpleado"),
    @NamedQuery(name = "Empleado.findByPrimerNombreEmpleado", query = "SELECT e FROM Empleado e WHERE e.primerNombreEmpleado = :primerNombreEmpleado"),
    @NamedQuery(name = "Empleado.findBySegundoNombreEmpleado", query = "SELECT e FROM Empleado e WHERE e.segundoNombreEmpleado = :segundoNombreEmpleado"),
    @NamedQuery(name = "Empleado.findByPrimerApellidoEmpleado", query = "SELECT e FROM Empleado e WHERE e.primerApellidoEmpleado = :primerApellidoEmpleado"),
    @NamedQuery(name = "Empleado.findBySegundoApellidoEmpleado", query = "SELECT e FROM Empleado e WHERE e.segundoApellidoEmpleado = :segundoApellidoEmpleado"),
    @NamedQuery(name = "Empleado.findByFechaContratacionEmpleado", query = "SELECT e FROM Empleado e WHERE e.fechaContratacionEmpleado = :fechaContratacionEmpleado"),
    @NamedQuery(name = "Empleado.findByTelefonoEmpleado", query = "SELECT e FROM Empleado e WHERE e.telefonoEmpleado = :telefonoEmpleado"),
    @NamedQuery(name = "Empleado.findByEmailEmpleado", query = "SELECT e FROM Empleado e WHERE e.emailEmpleado = :emailEmpleado"),
    @NamedQuery(name = "Empleado.findByEstadoEmpleado", query = "SELECT e FROM Empleado e WHERE e.estadoEmpleado = :estadoEmpleado")})
public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ID_EMPLEADO")
    private String idEmpleado;
    @Size(max = 50)
    @Column(name = "PRIMER_NOMBRE_EMPLEADO")
    private String primerNombreEmpleado;
    @Size(max = 50)
    @Column(name = "SEGUNDO_NOMBRE_EMPLEADO")
    private String segundoNombreEmpleado;
    @Size(max = 50)
    @Column(name = "PRIMER_APELLIDO_EMPLEADO")
    private String primerApellidoEmpleado;
    @Size(max = 50)
    @Column(name = "SEGUNDO_APELLIDO_EMPLEADO")
    private String segundoApellidoEmpleado;
    @Column(name = "FECHA_CONTRATACION_EMPLEADO")
    @Temporal(TemporalType.DATE)
    private Date fechaContratacionEmpleado;
    @Size(max = 9)
    @Column(name = "TELEFONO_EMPLEADO")
    private String telefonoEmpleado;
    @Size(max = 50)
    @Column(name = "EMAIL_EMPLEADO")
    private String emailEmpleado;
    @Column(name = "ESTADO_EMPLEADO")
    private Boolean estadoEmpleado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private List<Planilla> planillaList;

    public Empleado() {
    }

    public Empleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getPrimerNombreEmpleado() {
        return primerNombreEmpleado;
    }

    public void setPrimerNombreEmpleado(String primerNombreEmpleado) {
        this.primerNombreEmpleado = primerNombreEmpleado;
    }

    public String getSegundoNombreEmpleado() {
        return segundoNombreEmpleado;
    }

    public void setSegundoNombreEmpleado(String segundoNombreEmpleado) {
        this.segundoNombreEmpleado = segundoNombreEmpleado;
    }

    public String getPrimerApellidoEmpleado() {
        return primerApellidoEmpleado;
    }

    public void setPrimerApellidoEmpleado(String primerApellidoEmpleado) {
        this.primerApellidoEmpleado = primerApellidoEmpleado;
    }

    public String getSegundoApellidoEmpleado() {
        return segundoApellidoEmpleado;
    }

    public void setSegundoApellidoEmpleado(String segundoApellidoEmpleado) {
        this.segundoApellidoEmpleado = segundoApellidoEmpleado;
    }

    public Date getFechaContratacionEmpleado() {
        return fechaContratacionEmpleado;
    }

    public void setFechaContratacionEmpleado(Date fechaContratacionEmpleado) {
        this.fechaContratacionEmpleado = fechaContratacionEmpleado;
    }

    public String getTelefonoEmpleado() {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(String telefonoEmpleado) {
        this.telefonoEmpleado = telefonoEmpleado;
    }

    public String getEmailEmpleado() {
        return emailEmpleado;
    }

    public void setEmailEmpleado(String emailEmpleado) {
        this.emailEmpleado = emailEmpleado;
    }

    public Boolean getEstadoEmpleado() {
        return estadoEmpleado;
    }

    public void setEstadoEmpleado(Boolean estadoEmpleado) {
        this.estadoEmpleado = estadoEmpleado;
    }

    @XmlTransient
    public List<Planilla> getPlanillaList() {
        return planillaList;
    }

    public void setPlanillaList(List<Planilla> planillaList) {
        this.planillaList = planillaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleado != null ? idEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asecon.entity.Empleado[ idEmpleado=" + idEmpleado + " ]";
    }
    
}
