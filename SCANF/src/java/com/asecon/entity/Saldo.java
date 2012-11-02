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
    @NamedQuery(name = "Saldo.findByCodigoCuenta", query = "SELECT s FROM Saldo s WHERE s.saldoPK.codigoCuenta = :codigoCuenta"),
    @NamedQuery(name = "Saldo.findBySaldoFinalSubCuenta", query = "SELECT s FROM Saldo s WHERE s.saldoFinalSubcuenta = :saldoFinalSubcuenta")})
public class Saldo implements Serializable {
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALDO_FINAL_SUBCUENTA")
    private Float saldoFinalSubcuenta;
    @JoinColumn(name = "CODIGO_SUBCUENTA", referencedColumnName = "CODIGO_SUBCUENTA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SubCuenta subCuenta;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SaldoPK saldoPK;
    @JoinColumn(name = "NUMERO_PERIODO", referencedColumnName = "NUMERO_PERIODO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Periodo periodo;
    @JoinColumn(name = "CODIGO_CUENTA", referencedColumnName = "CODIGO_CUENTA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cuenta cuenta;

    public Saldo() {
    }

    public Saldo(SaldoPK saldoPK) {
        this.saldoPK = saldoPK;
    }

    public Saldo(long numeroPeriodo, String codigoCuenta) {
        this.saldoPK = new SaldoPK(numeroPeriodo, codigoCuenta);
    }

    public SaldoPK getSaldoPK() {
        return saldoPK;
    }

    public void setSaldoPK(SaldoPK saldoPK) {
        this.saldoPK = saldoPK;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
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

    public Float getSaldoFinalSubcuenta() {
        return saldoFinalSubcuenta;
    }

    public void setSaldoFinalSubcuenta(Float saldoFinalSubcuenta) {
        this.saldoFinalSubcuenta = saldoFinalSubcuenta;
    }

    public SubCuenta getSubCuenta() {
        return subCuenta;
    }

    public void setSubCuenta(SubCuenta subCuenta) {
        this.subCuenta = subCuenta;
    }
    
}
