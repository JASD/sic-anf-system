/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "planilla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Planilla.findAll", query = "SELECT p FROM Planilla p"),
    @NamedQuery(name = "Planilla.findAllCurrent", query = "SELECT p FROM Planilla p WHERE p.planillaPK.numeroPeriodo = (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)"),
    @NamedQuery(name = "Planilla.findByNumeroPeriodo", query = "SELECT p FROM Planilla p WHERE p.planillaPK.numeroPeriodo = :numeroPeriodo"),
    @NamedQuery(name = "Planilla.findByIdEmpleado", query = "SELECT p FROM Planilla p WHERE p.planillaPK.idEmpleado = :idEmpleado"),
    @NamedQuery(name = "Planilla.findByNumeroPlanilla", query = "SELECT p FROM Planilla p WHERE p.numeroPlanilla = :numeroPlanilla"),
    @NamedQuery(name = "Planilla.findBySalarioBase", query = "SELECT p FROM Planilla p WHERE p.salarioBase = :salarioBase"),
    @NamedQuery(name = "Planilla.findByNumeroHorasExtra", query = "SELECT p FROM Planilla p WHERE p.numeroHorasExtra = :numeroHorasExtra"),
    @NamedQuery(name = "Planilla.findByHorasExtra", query = "SELECT p FROM Planilla p WHERE p.horasExtra = :horasExtra"),
    @NamedQuery(name = "Planilla.findByBonificaciones", query = "SELECT p FROM Planilla p WHERE p.bonificaciones = :bonificaciones"),
    @NamedQuery(name = "Planilla.findByAfp", query = "SELECT p FROM Planilla p WHERE p.afp = :afp"),
    @NamedQuery(name = "Planilla.findByIsss", query = "SELECT p FROM Planilla p WHERE p.isss = :isss"),
    @NamedQuery(name = "Planilla.findBySalarioNeto", query = "SELECT p FROM Planilla p WHERE p.salarioNeto = :salarioNeto")})
public class Planilla implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanillaPK planillaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMERO_PLANILLA")
    private long numeroPlanilla;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALARIO_BASE")
    private Float salarioBase;
    @Column(name = "NUMERO_HORAS_EXTRA")
    private BigDecimal numeroHorasExtra;
    @Column(name = "HORAS_EXTRA")
    private Float horasExtra;
    @Column(name = "BONIFICACIONES")
    private Float bonificaciones;
    @Column(name = "AFP")
    private Float afp;
    @Column(name = "ISSS")
    private Float isss;
    @Column(name = "SALARIO_NETO")
    private Float salarioNeto;
    @JoinColumn(name = "NUMERO_PERIODO", referencedColumnName = "NUMERO_PERIODO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Periodo periodo;
    @JoinColumn(name = "ID_EMPLEADO", referencedColumnName = "ID_EMPLEADO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empleado empleado;

    public Planilla() {
    }

    public Planilla(PlanillaPK planillaPK) {
        this.planillaPK = planillaPK;
    }

    public Planilla(PlanillaPK planillaPK, long numeroPlanilla) {
        this.planillaPK = planillaPK;
        this.numeroPlanilla = numeroPlanilla;
    }

    public Planilla(long numeroPeriodo, String idEmpleado) {
        this.planillaPK = new PlanillaPK(numeroPeriodo, idEmpleado);
    }

    public PlanillaPK getPlanillaPK() {
        return planillaPK;
    }

    public void setPlanillaPK(PlanillaPK planillaPK) {
        this.planillaPK = planillaPK;
    }

    public long getNumeroPlanilla() {
        return numeroPlanilla;
    }

    public void setNumeroPlanilla(long numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }

    public Float getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(Float salarioBase) {
        this.salarioBase = salarioBase;
    }

    public BigDecimal getNumeroHorasExtra() {
        return numeroHorasExtra;
    }

    public void setNumeroHorasExtra(BigDecimal numeroHorasExtra) {
        this.numeroHorasExtra = numeroHorasExtra;
    }

    public Float getHorasExtra() {
        return horasExtra;
    }

    public void setHorasExtra(Float horasExtra) {
        this.horasExtra = horasExtra;
    }

    public Float getBonificaciones() {
        return bonificaciones;
    }

    public void setBonificaciones(Float bonificaciones) {
        this.bonificaciones = bonificaciones;
    }

    public Float getAfp() {
        return afp;
    }

    public void setAfp(Float afp) {
        this.afp = afp;
    }

    public Float getIsss() {
        return isss;
    }

    public void setIsss(Float isss) {
        this.isss = isss;
    }

    public Float getSalarioNeto() {
        return salarioNeto;
    }

    public void setSalarioNeto(Float salarioNeto) {
        this.salarioNeto = salarioNeto;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planillaPK != null ? planillaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planilla)) {
            return false;
        }
        Planilla other = (Planilla) object;
        if ((this.planillaPK == null && other.planillaPK != null) || (this.planillaPK != null && !this.planillaPK.equals(other.planillaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asecon.entity.Planilla[ planillaPK=" + planillaPK + " ]";
    }
    
}
