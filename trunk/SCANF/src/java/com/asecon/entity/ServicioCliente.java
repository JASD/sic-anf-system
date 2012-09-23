/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "servicio_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServicioCliente.findAll", query = "SELECT s FROM ServicioCliente s"),
    @NamedQuery(name = "ServicioCliente.findByIdTipoServicio", query = "SELECT s FROM ServicioCliente s WHERE s.servicioClientePK.idTipoServicio = :idTipoServicio"),
    @NamedQuery(name = "ServicioCliente.findByNumeroCliente", query = "SELECT s FROM ServicioCliente s WHERE s.servicioClientePK.numeroCliente = :numeroCliente"),
    @NamedQuery(name = "ServicioCliente.findByNumeroPeriodo", query = "SELECT s FROM ServicioCliente s WHERE s.servicioClientePK.numeroPeriodo = :numeroPeriodo"),
    @NamedQuery(name = "ServicioCliente.findByFechaServicioCliente", query = "SELECT s FROM ServicioCliente s WHERE s.fechaServicioCliente = :fechaServicioCliente"),
    @NamedQuery(name = "ServicioCliente.findByTerminosServicioCliente", query = "SELECT s FROM ServicioCliente s WHERE s.terminosServicioCliente = :terminosServicioCliente"),
    @NamedQuery(name = "ServicioCliente.findByPer", query = "SELECT s FROM ServicioCliente s WHERE s.servicioClientePK.numeroPeriodo = :periodo"),
    @NamedQuery(name = "ServicioCliente.findByServ", query = "SELECT s FROM ServicioCliente s WHERE s.servicioClientePK.idTipoServicio IN (SELECT ts.idTipoServicio FROM TipoServicio ts WHERE ts.idServicio = :servicio) AND s.servicioClientePK.numeroPeriodo = (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)"),
    @NamedQuery(name = "ServicioCliente.findByTipo", query = "SELECT s FROM ServicioCliente s WHERE s.tipoServicio = :tipoServicio AND s.servicioClientePK.numeroPeriodo = (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)"),
    @NamedQuery(name = "ServicioCliente.findByPerServ", query = "SELECT s FROM ServicioCliente s WHERE s.servicioClientePK.idTipoServicio IN (SELECT ts.idTipoServicio FROM TipoServicio ts WHERE ts.idServicio = :servicio) AND s.servicioClientePK.numeroPeriodo = :periodo"),
    @NamedQuery(name = "ServicioCliente.findByPerTipo", query = "SELECT s FROM ServicioCliente s WHERE s.tipoServicio = :tipoServicio AND s.servicioClientePK.numeroPeriodo = :periodo")})
public class ServicioCliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ServicioClientePK servicioClientePK;
    @Column(name = "FECHA_SERVICIO_CLIENTE")
    @Temporal(TemporalType.DATE)
    private Date fechaServicioCliente;
    @Size(max = 500)
    @Column(name = "TERMINOS_SERVICIO_CLIENTE")
    private String terminosServicioCliente;
    @JoinColumn(name = "NUMERO_CLIENTE", referencedColumnName = "NUMERO_CLIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "ID_TIPO_SERVICIO", referencedColumnName = "ID_TIPO_SERVICIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TipoServicio tipoServicio;
    @JoinColumn(name = "NUMERO_PERIODO", referencedColumnName = "NUMERO_PERIODO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Periodo periodo;

    public ServicioCliente() {
    }

    public ServicioCliente(ServicioClientePK servicioClientePK) {
        this.servicioClientePK = servicioClientePK;
    }

    public ServicioCliente(String idTipoServicio, long numeroCliente, long numeroPeriodo) {
        this.servicioClientePK = new ServicioClientePK(idTipoServicio, numeroCliente, numeroPeriodo);
    }

    public ServicioClientePK getServicioClientePK() {
        return servicioClientePK;
    }

    public void setServicioClientePK(ServicioClientePK servicioClientePK) {
        this.servicioClientePK = servicioClientePK;
    }

    public Date getFechaServicioCliente() {
        return fechaServicioCliente;
    }

    public void setFechaServicioCliente(Date fechaServicioCliente) {
        this.fechaServicioCliente = fechaServicioCliente;
    }

    public String getTerminosServicioCliente() {
        return terminosServicioCliente;
    }

    public void setTerminosServicioCliente(String terminosServicioCliente) {
        this.terminosServicioCliente = terminosServicioCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (servicioClientePK != null ? servicioClientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServicioCliente)) {
            return false;
        }
        ServicioCliente other = (ServicioCliente) object;
        if ((this.servicioClientePK == null && other.servicioClientePK != null) || (this.servicioClientePK != null && !this.servicioClientePK.equals(other.servicioClientePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asecon.entity.ServicioCliente[ servicioClientePK=" + servicioClientePK + " ]";
    }
}
