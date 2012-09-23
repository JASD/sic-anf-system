/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "periodo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Periodo.findAll", query = "SELECT p FROM Periodo p"),
    @NamedQuery(name = "Periodo.findCurrent", query = "SELECT p FROM Periodo p WHERE p.estadoPeriodo = true"),
    @NamedQuery(name = "Periodo.findNoCurrent", query = "SELECT p FROM Periodo p WHERE p.estadoPeriodo = false"),
    @NamedQuery(name = "Periodo.findByNumeroPeriodo", query = "SELECT p FROM Periodo p WHERE p.numeroPeriodo = :numeroPeriodo"),
    @NamedQuery(name = "Periodo.findByFechaInicioPeriodo", query = "SELECT p FROM Periodo p WHERE p.fechaInicioPeriodo = :fechaInicioPeriodo"),
    @NamedQuery(name = "Periodo.findByFechaFinPeriodo", query = "SELECT p FROM Periodo p WHERE p.fechaFinPeriodo = :fechaFinPeriodo"),
    @NamedQuery(name = "Periodo.findByEstadoPeriodo", query = "SELECT p FROM Periodo p WHERE p.estadoPeriodo = :estadoPeriodo"),
    @NamedQuery(name = "Periodo.findByUtilidadesPeriodo", query = "SELECT p FROM Periodo p WHERE p.utilidadesPeriodo = :utilidadesPeriodo"),
    @NamedQuery(name = "Periodo.findByTotalActivoPeriodo", query = "SELECT p FROM Periodo p WHERE p.totalActivoPeriodo = :totalActivoPeriodo"),
    @NamedQuery(name = "Periodo.findByTotalPasivoPeriodo", query = "SELECT p FROM Periodo p WHERE p.totalPasivoPeriodo = :totalPasivoPeriodo"),
    @NamedQuery(name = "Periodo.findByTotalCapitalPeriodo", query = "SELECT p FROM Periodo p WHERE p.totalCapitalPeriodo = :totalCapitalPeriodo"),
    @NamedQuery(name = "Periodo.findByTotalGastosPeriodo", query = "SELECT p FROM Periodo p WHERE p.totalGastosPeriodo = :totalGastosPeriodo"),
    @NamedQuery(name = "Periodo.findByTotalIngresosPeriodo", query = "SELECT p FROM Periodo p WHERE p.totalIngresosPeriodo = :totalIngresosPeriodo"),
    @NamedQuery(name = "Periodo.findByTotalCisPeriodo", query = "SELECT p FROM Periodo p WHERE p.totalCisPeriodo = :totalCisPeriodo")})
public class Periodo implements Serializable {
    @Column(name = "FECHA_INICIO_PERIODO")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioPeriodo;
    @Column(name = "FECHA_FIN_PERIODO")
    @Temporal(TemporalType.DATE)
    private Date fechaFinPeriodo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
    private List<Saldo> saldoList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NUMERO_PERIODO", insertable=false, nullable=false, updatable=true)
    private Long numeroPeriodo;
    @Column(name = "ESTADO_PERIODO")
    private Boolean estadoPeriodo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "UTILIDADES_PERIODO")
    private Float utilidadesPeriodo;
    @Column(name = "PERDIDAS_PERIODO")
    private Float perdidasPeriodo;
    @Column(name = "TOTAL_ACTIVO_PERIODO")
    private Float totalActivoPeriodo;
    @Column(name = "TOTAL_PASIVO_PERIODO")
    private Float totalPasivoPeriodo;
    @Column(name = "TOTAL_CAPITAL_PERIODO")
    private Float totalCapitalPeriodo;
    @Column(name = "TOTAL_GASTOS_PERIODO")
    private Float totalGastosPeriodo;
    @Column(name = "TOTAL_INGRESOS_PERIODO")
    private Float totalIngresosPeriodo;
    @Column(name = "TOTAL_CIS_PERIODO")
    private Float totalCisPeriodo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
    private List<Planilla> planillaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
    private List<CostoServicio> costoServicioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
    private List<Transaccion> transaccionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
    private List<ActividadServicio> actividadServicioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
    private List<CostoActividad> costoActividadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
    private List<ServicioCliente> servicioClienteList;

    public Periodo() {
    }

    public Periodo(Long numeroPeriodo) {
        this.numeroPeriodo = numeroPeriodo;
    }

    public Long getNumeroPeriodo() {
        return numeroPeriodo;
    }

    public void setNumeroPeriodo(Long numeroPeriodo) {
        this.numeroPeriodo = numeroPeriodo;
    }

    public Date getFechaInicioPeriodo() {
        return fechaInicioPeriodo;
    }

    public void setFechaInicioPeriodo(Date fechaInicioPeriodo) {
        this.fechaInicioPeriodo = fechaInicioPeriodo;
    }

    public Date getFechaFinPeriodo() {
        return fechaFinPeriodo;
    }

    public void setFechaFinPeriodo(Date fechaFinPeriodo) {
        this.fechaFinPeriodo = fechaFinPeriodo;
    }

    public Boolean getEstadoPeriodo() {
        return estadoPeriodo;
    }

    public void setEstadoPeriodo(Boolean estadoPeriodo) {
        this.estadoPeriodo = estadoPeriodo;
    }

    public Float getUtilidadesPeriodo() {
        return utilidadesPeriodo;
    }

    public void setUtilidadesPeriodo(Float utilidadesPeriodo) {
        this.utilidadesPeriodo = utilidadesPeriodo;
    }

    public Float getTotalActivoPeriodo() {
        return totalActivoPeriodo;
    }

    public void setTotalActivoPeriodo(Float totalActivoPeriodo) {
        this.totalActivoPeriodo = totalActivoPeriodo;
    }

    public Float getTotalPasivoPeriodo() {
        return totalPasivoPeriodo;
    }

    public void setTotalPasivoPeriodo(Float totalPasivoPeriodo) {
        this.totalPasivoPeriodo = totalPasivoPeriodo;
    }

    public Float getTotalCapitalPeriodo() {
        return totalCapitalPeriodo;
    }

    public void setTotalCapitalPeriodo(Float totalCapitalPeriodo) {
        this.totalCapitalPeriodo = totalCapitalPeriodo;
    }

    public Float getTotalGastosPeriodo() {
        return totalGastosPeriodo;
    }

    public void setTotalGastosPeriodo(Float totalGastosPeriodo) {
        this.totalGastosPeriodo = totalGastosPeriodo;
    }

    public Float getTotalIngresosPeriodo() {
        return totalIngresosPeriodo;
    }

    public void setTotalIngresosPeriodo(Float totalIngresosPeriodo) {
        this.totalIngresosPeriodo = totalIngresosPeriodo;
    }

    public Float getTotalCisPeriodo() {
        return totalCisPeriodo;
    }

    public void setTotalCisPeriodo(Float totalCisPeriodo) {
        this.totalCisPeriodo = totalCisPeriodo;
    }

    @XmlTransient
    public List<Planilla> getPlanillaList() {
        return planillaList;
    }

    public void setPlanillaList(List<Planilla> planillaList) {
        this.planillaList = planillaList;
    }

    @XmlTransient
    public List<CostoServicio> getCostoServicioList() {
        return costoServicioList;
    }

    public void setCostoServicioList(List<CostoServicio> costoServicioList) {
        this.costoServicioList = costoServicioList;
    }

    public Float getPerdidasPeriodo() {
        return perdidasPeriodo;
    }

    public void setPerdidasPeriodo(Float perdidasPeriodo) {
        this.perdidasPeriodo = perdidasPeriodo;
    }
    

    @XmlTransient
    public List<Transaccion> getTransaccionList() {
        return transaccionList;
    }

    public void setTransaccionList(List<Transaccion> transaccionList) {
        this.transaccionList = transaccionList;
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
        hash += (numeroPeriodo != null ? numeroPeriodo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Periodo)) {
            return false;
        }
        Periodo other = (Periodo) object;
        if ((this.numeroPeriodo == null && other.numeroPeriodo != null) || (this.numeroPeriodo != null && !this.numeroPeriodo.equals(other.numeroPeriodo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return "Del: " + formato.format(fechaInicioPeriodo) + " al: " + formato.format(fechaFinPeriodo);
    }

    @XmlTransient
    public List<Saldo> getSaldoList() {
        return saldoList;
    }

    public void setSaldoList(List<Saldo> saldoList) {
        this.saldoList = saldoList;
    }
    
}
