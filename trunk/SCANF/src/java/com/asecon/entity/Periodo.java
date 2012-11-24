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
    @NamedQuery(name = "Periodo.findByUtilidadNetaPeriodo", query = "SELECT p FROM Periodo p WHERE p.utilidadNetaPeriodo = :utilidadNetaPeriodo"),
    @NamedQuery(name = "Periodo.findByTotalActivoPeriodo", query = "SELECT p FROM Periodo p WHERE p.totalActivoPeriodo = :totalActivoPeriodo"),
    @NamedQuery(name = "Periodo.findByTotalPasivoPeriodo", query = "SELECT p FROM Periodo p WHERE p.totalPasivoPeriodo = :totalPasivoPeriodo"),
    @NamedQuery(name = "Periodo.findByTotalCapitalPeriodo", query = "SELECT p FROM Periodo p WHERE p.totalCapitalPeriodo = :totalCapitalPeriodo"),
    @NamedQuery(name = "Periodo.findByTotalCostoPeriodo", query = "SELECT p FROM Periodo p WHERE p.totalCostoPeriodo = :totalCostoPeriodo"),
    @NamedQuery(name = "Periodo.findByTotalIngresosPeriodo", query = "SELECT p FROM Periodo p WHERE p.totalIngresosPeriodo = :totalIngresosPeriodo"),
    @NamedQuery(name = "Periodo.findByTotalCisPeriodo", query = "SELECT p FROM Periodo p WHERE p.totalCisPeriodo = :totalCisPeriodo"),
    @NamedQuery(name = "Periodo.findNoCurrentAndFirst", query = "SELECT p FROM Periodo p WHERE p.estadoPeriodo = false AND p.numeroPeriodo > 1")})
public class Periodo implements Serializable {

    @Column(name = "FECHA_INICIO_PERIODO")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioPeriodo;
    @Column(name = "FECHA_FIN_PERIODO")
    @Temporal(TemporalType.DATE)
    private Date fechaFinPeriodo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTAL_ACTIVO_PERIODO")
    private Double totalActivoPeriodo;
    @Column(name = "TOTAL_PASIVO_PERIODO")
    private Double totalPasivoPeriodo;
    @Column(name = "TOTAL_CAPITAL_PERIODO")
    private Double totalCapitalPeriodo;
    @Column(name = "TOTAL_INGRESOS_PERIODO")
    private Double totalIngresosPeriodo;
    @Column(name = "TOTAL_CIS_PERIODO")
    private Double totalCisPeriodo;
    @Column(name = "UTILIDAD_BRUTA_PERIODO")
    private Double utilidadBrutaPeriodo;
    @Column(name = "UTILIDAD_OPERACION_PERIODO")
    private Double utilidadOperacionPeriodo;
    @Column(name = "UTILIDAD_AI_PERIODO")
    private Double utilidadAiPeriodo;
    @Column(name = "UTILIDAD_NETA_PERIODO")
    private Double utilidadNetaPeriodo;
    @Column(name = "TOTAL_COSTO_PERIODO")
    private Double totalCostoPeriodo;
    @Column(name = "IMPUESTOS_PERIODO")
    private Double impuestosPeriodo;
    @Column(name = "ACTIVO_CIRCULANTE_PERIODO")
    private Double activoCirculantePeriodo;
    @Column(name = "PASIVO_CIRCULANTE_PERIODO")
    private Double pasivoCirculantePeriodo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
    private List<Saldo> saldoList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NUMERO_PERIODO", insertable = false, nullable = false, updatable = true)
    private Long numeroPeriodo;
    @Column(name = "ESTADO_PERIODO")
    private Boolean estadoPeriodo;
    //@Column(name = "PERDIDAS_PERIODO")
    @Transient
    private Float perdidasPeriodo;
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
    @Transient
    private Double porcentajeAumentoRubro;
    @Transient
    private Double diferenciaRubro;
    @Transient
    private Double totalFuente;
    @Transient
    private Double totalUso;
    @Transient
    private Double efectivoInicio;
    @Transient
    private Double efectivoFinal;
    @Transient
    private Double difEfectivo;
    @Transient
    private Double cambioPorcUPAPeriodo;
    @Transient
    private Double cambioPorcEBITPeriodo;
    @Transient
    private Double gafPeriodo;
    
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

    public Double getUtilidadNetaPeriodo() {
        return utilidadNetaPeriodo;
    }

    public void setUtilidadNetaPeriodo(Double utilidadNetaPeriodo) {
        this.utilidadNetaPeriodo = utilidadNetaPeriodo;
    }

    public Double getUtilidadAIPeriodo() {
        return utilidadAiPeriodo;
    }

    public void setUtilidadAIPeriodo(Double utilidadAIPeriodo) {
        this.utilidadAiPeriodo = utilidadAIPeriodo;
    }

    public Double getUtilidadBrutaPeriodo() {
        return utilidadBrutaPeriodo;
    }

    public void setUtilidadBrutaPeriodo(Double utilidadBrutaPeriodo) {
        this.utilidadBrutaPeriodo = utilidadBrutaPeriodo;
    }

    public Double getUtilidadOperacionPeriodo() {
        return utilidadOperacionPeriodo;
    }

    public void setUtilidadOperacionPeriodo(Double utilidadOperacionPeriodo) {
        this.utilidadOperacionPeriodo = utilidadOperacionPeriodo;
    }

    public Double getTotalActivoPeriodo() {
        return totalActivoPeriodo;
    }

    public void setTotalActivoPeriodo(Double totalActivoPeriodo) {
        this.totalActivoPeriodo = totalActivoPeriodo;
    }

    public Double getTotalPasivoPeriodo() {
        return totalPasivoPeriodo;
    }

    public void setTotalPasivoPeriodo(Double totalPasivoPeriodo) {
        this.totalPasivoPeriodo = totalPasivoPeriodo;
    }

    public Double getTotalCapitalPeriodo() {
        return totalCapitalPeriodo;
    }

    public void setTotalCapitalPeriodo(Double totalCapitalPeriodo) {
        this.totalCapitalPeriodo = totalCapitalPeriodo;
    }

    public Double getTotalCostoPeriodo() {
        return totalCostoPeriodo;
    }

    public void setTotalCostoPeriodo(Double totalCostoPeriodo) {
        this.totalCostoPeriodo = totalCostoPeriodo;
    }

    public Double getTotalIngresosPeriodo() {
        return totalIngresosPeriodo;
    }

    public void setTotalIngresosPeriodo(Double totalIngresosPeriodo) {
        this.totalIngresosPeriodo = totalIngresosPeriodo;
    }

    public Double getTotalCisPeriodo() {
        return totalCisPeriodo;
    }

    public void setTotalCisPeriodo(Double totalCisPeriodo) {
        this.totalCisPeriodo = totalCisPeriodo;
    }

    public Double getImpuestosPeriodo() {
        return impuestosPeriodo;
    }

    public void setImpuestosPeriodo(Double impuestosPeriodo) {
        this.impuestosPeriodo = impuestosPeriodo;
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
        SimpleDateFormat formato = new SimpleDateFormat("yyyy");
        return formato.format(fechaFinPeriodo);
    }

    @XmlTransient
    public List<Saldo> getSaldoList() {
        return saldoList;
    }

    public void setSaldoList(List<Saldo> saldoList) {
        this.saldoList = saldoList;
    }

    public Double getUtilidadAiPeriodo() {
        return utilidadAiPeriodo;
    }

    public void setUtilidadAiPeriodo(Double utilidadAiPeriodo) {
        this.utilidadAiPeriodo = utilidadAiPeriodo;
    }

    public Double getActivoCirculantePeriodo() {
        return activoCirculantePeriodo;
    }

    public void setActivoCirculantePeriodo(Double activoCirculantePeriodo) {
        this.activoCirculantePeriodo = activoCirculantePeriodo;
    }

    public Double getPasivoCirculantePeriodo() {
        return pasivoCirculantePeriodo;
    }

    public void setPasivoCirculantePeriodo(Double pasivoCirculantePeriodo) {
        this.pasivoCirculantePeriodo = pasivoCirculantePeriodo;
    }

    public Double getPorcentajeAumentoRubro() {
        return porcentajeAumentoRubro;
    }

    public void setPorcentajeAumentoRubro(Double porcentajeAumentoRubro) {
        this.porcentajeAumentoRubro = porcentajeAumentoRubro;
    }

    public Double getDiferenciaRubro() {
        return diferenciaRubro;
    }

    public void setDiferenciaRubro(Double diferenciaRubro) {
        this.diferenciaRubro = diferenciaRubro;
    }

    public Double getTotalFuente() {
        return totalFuente;
    }

    public void setTotalFuente(Double totalFuente) {
        this.totalFuente = totalFuente;
    }

    public Double getTotalUso() {
        return totalUso;
    }

    public void setTotalUso(Double totalUso) {
        this.totalUso = totalUso;
    }

    public Double getDifEfectivo() {
        return difEfectivo;
    }

    public void setDifEfectivo(Double difEfectivo) {
        this.difEfectivo = difEfectivo;
    }

    public Double getEfectivoFinal() {
        return efectivoFinal;
    }

    public void setEfectivoFinal(Double efectivoFinal) {
        this.efectivoFinal = efectivoFinal;
    }

    public Double getEfectivoInicio() {
        return efectivoInicio;
    }

    public void setEfectivoInicio(Double efectivoInicio) {
        this.efectivoInicio = efectivoInicio;
    }

    public Double getCambioPorcEBITPeriodo() {
        return cambioPorcEBITPeriodo;
    }

    public void setCambioPorcEBITPeriodo(Double cambioPorcEBITPeriodo) {
        this.cambioPorcEBITPeriodo = cambioPorcEBITPeriodo;
    }

    public Double getCambioPorcUPAPeriodo() {
        return cambioPorcUPAPeriodo;
    }

    public void setCambioPorcUPAPeriodo(Double cambioPorcUPAPeriodo) {
        this.cambioPorcUPAPeriodo = cambioPorcUPAPeriodo;
    }

    public Double getGafPeriodo() {
        return gafPeriodo;
    }

    public void setGafPeriodo(Double gafPeriodo) {
        this.gafPeriodo = gafPeriodo;
    }
}
