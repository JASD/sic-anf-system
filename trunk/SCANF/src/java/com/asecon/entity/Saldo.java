/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "saldo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Saldo.findAll", query = "SELECT s FROM Saldo s"),
    @NamedQuery(name = "Saldo.findByNumeroPeriodo", query = "SELECT s FROM Saldo s WHERE s.saldoPK.numeroPeriodo = :numeroPeriodo"),
    @NamedQuery(name = "Saldo.findByCodigoSubcuenta", query = "SELECT s FROM Saldo s WHERE s.saldoPK.codigoSubcuenta = :codigoSubcuenta"),
    @NamedQuery(name = "Saldo.findBySaldoFinalSubcuenta", query = "SELECT s FROM Saldo s WHERE s.saldoFinalSubcuenta = :saldoFinalSubcuenta"),
    @NamedQuery(name = "Saldo.findBySaldoFinalSubcuenta", query = "SELECT s FROM Saldo s WHERE s.saldoFinalSubcuenta = :saldoFinalSubcuenta"),
    @NamedQuery(name = "Saldo.findByCuenta", query = "SELECT s FROM Saldo s WHERE s.saldoPK.numeroPeriodo = :numeroPeriodo AND s.saldoPK.codigoSubcuenta IN (SELECT sb.codigoSubcuenta FROM SubCuenta sb WHERE sb.codigoCuenta = :codigoCuenta)")})
public class Saldo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SaldoPK saldoPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALDO_FINAL_SUBCUENTA")
    private Double saldoFinalSubcuenta;
    @JoinColumn(name = "CODIGO_SUBCUENTA", referencedColumnName = "CODIGO_SUBCUENTA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SubCuenta subCuenta;
    @JoinColumn(name = "NUMERO_PERIODO", referencedColumnName = "NUMERO_PERIODO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Periodo periodo;
    @Transient
    private Float porcentajeParticipacion;

    public Saldo() {
    }

    public Saldo(SaldoPK saldoPK) {
        this.saldoPK = saldoPK;
    }

    public Saldo(long numeroPeriodo, String codigoSubcuenta) {
        this.saldoPK = new SaldoPK(numeroPeriodo, codigoSubcuenta);
    }

    public SaldoPK getSaldoPK() {
        return saldoPK;
    }

    public void setSaldoPK(SaldoPK saldoPK) {
        this.saldoPK = saldoPK;
    }

    public Double getSaldoFinalSubcuenta() {
        return saldoFinalSubcuenta;
    }

    public void setSaldoFinalSubcuenta(Double saldoFinalSubcuenta) {
        this.saldoFinalSubcuenta = saldoFinalSubcuenta;
    }

    public SubCuenta getSubCuenta() {
        return subCuenta;
    }

    public void setSubCuenta(SubCuenta subCuenta) {
        this.subCuenta = subCuenta;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Float getPorcentajeParticipacion() {
        return porcentajeParticipacion;
    }

    public void setPorcentajeParticipacion(Float porcentajeParticipacion) {
        this.porcentajeParticipacion = porcentajeParticipacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (saldoPK != null ? saldoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Saldo)) {
            return false;
        }
        Saldo other = (Saldo) object;
        if ((this.saldoPK == null && other.saldoPK != null) || (this.saldoPK != null && !this.saldoPK.equals(other.saldoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asecon.entity.Saldo[ saldoPK=" + saldoPK + " ]";
    }
    
}
