/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "cuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c"),
    @NamedQuery(name = "Cuenta.findActivoDeudor", query = "SELECT c FROM Cuenta c WHERE c.rubroCuenta = 'ACTIVO' and c.tipoCuenta = 'DEUDORA'"),
    @NamedQuery(name = "Cuenta.findActivoAcreedor", query = "SELECT c FROM Cuenta c WHERE c.rubroCuenta = 'ACTIVO' and c.tipoCuenta = 'ACREEDORA'"),
    @NamedQuery(name = "Cuenta.findAllPasivoAcreedor", query = "SELECT c FROM Cuenta c WHERE c.rubroCuenta = 'PASIVO' and c.tipoCuenta = 'ACREEDORA'"),
    @NamedQuery(name = "Cuenta.findAllCapitalDeudor", query = "SELECT c FROM Cuenta c WHERE c.rubroCuenta = 'PATRIMONIO' and c.tipoCuenta = 'DEUDORA'"),
    @NamedQuery(name = "Cuenta.findAllCapitalAcreedor", query = "SELECT c FROM Cuenta c WHERE c.rubroCuenta = 'PATRIMONIO' and c.tipoCuenta = 'ACREEDORA'"),
    @NamedQuery(name = "Cuenta.findGastos", query = "SELECT c FROM Cuenta c WHERE c.rubroCuenta = 'RESULTADOS'"),
    @NamedQuery(name = "Cuenta.findIngresos", query = "SELECT c FROM Cuenta c WHERE c.rubroCuenta = 'RESULTADOS VENTAS Y PRODUCTOS'"),
    @NamedQuery(name = "Cuenta.findByCodigoCuenta", query = "SELECT c FROM Cuenta c WHERE c.codigoCuenta = :codigoCuenta"),
    @NamedQuery(name = "Cuenta.findByNombreCuenta", query = "SELECT c FROM Cuenta c WHERE c.nombreCuenta = :nombreCuenta"),
    @NamedQuery(name = "Cuenta.findByRubroCuenta", query = "SELECT c FROM Cuenta c WHERE c.rubroCuenta = :rubroCuenta"),
    @NamedQuery(name = "Cuenta.findBySubrubroCuenta", query = "SELECT c FROM Cuenta c WHERE c.subrubroCuenta = :subrubroCuenta"),
    @NamedQuery(name = "Cuenta.findByTipoCuenta", query = "SELECT c FROM Cuenta c WHERE c.tipoCuenta = :tipoCuenta"),
    @NamedQuery(name = "Cuenta.findByDescripcionCuenta", query = "SELECT c FROM Cuenta c WHERE c.descripcionCuenta = :descripcionCuenta"),
    @NamedQuery(name = "Cuenta.findBySaldoCuenta", query = "SELECT c FROM Cuenta c WHERE c.saldoCuenta = :saldoCuenta"),
    @NamedQuery(name = "Cuenta.findBySaldoFinal", query = "SELECT c FROM Cuenta c WHERE c.rubroCuenta = :rubroCuenta AND c.codigoCuenta IN (SELECT DISTINCT sb.codigoCuenta.codigoCuenta FROM SubCuenta sb WHERE sb.codigoSubcuenta IN(SELECT s.saldoPK.codigoSubcuenta FROM Saldo s WHERE s.saldoPK.numeroPeriodo = :numeroPeriodo))"),
    @NamedQuery(name = "Cuenta.findBySaldoFinalResultados", query = "SELECT c FROM Cuenta c WHERE c.rubroCuenta = 'RESULTADOS' AND c.codigoCuenta IN (SELECT DISTINCT sb.codigoCuenta.codigoCuenta FROM SubCuenta sb WHERE sb.codigoSubcuenta IN(SELECT s.saldoPK.codigoSubcuenta FROM Saldo s WHERE s.saldoPK.numeroPeriodo = :numeroPeriodo)) ORDER BY c.tipoCuenta, c.codigoCuenta"),
    @NamedQuery(name = "Cuenta.findBySaldoFinalBG", query = "SELECT c FROM Cuenta c WHERE c.rubroCuenta IN ('ACTIVO', 'PASIVO', 'PATRIMONIO') AND c.codigoCuenta IN (SELECT DISTINCT sb.codigoCuenta.codigoCuenta FROM SubCuenta sb WHERE sb.codigoSubcuenta IN(SELECT s.saldoPK.codigoSubcuenta FROM Saldo s WHERE s.saldoPK.numeroPeriodo = :numeroPeriodo))"),
    @NamedQuery(name = "Cuenta.findBySaldoFinalFE", query = "SELECT c FROM Cuenta c WHERE c.subrubroCuenta IN ('CIRCULANTE', 'NO CIRCULANTE', 'CORTO PLAZO') AND c.codigoCuenta IN (SELECT DISTINCT sb.codigoCuenta.codigoCuenta FROM SubCuenta sb WHERE sb.codigoSubcuenta IN(SELECT s.saldoPK.codigoSubcuenta FROM Saldo s WHERE s.saldoPK.numeroPeriodo = :numeroPeriodo)) ORDER BY c.subrubroCuenta")})
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "CODIGO_CUENTA")
    private String codigoCuenta;
    @Size(max = 50)
    @Column(name = "NOMBRE_CUENTA")
    private String nombreCuenta;
    @Size(max = 50)
    @Column(name = "RUBRO_CUENTA")
    private String rubroCuenta;
    @Size(max = 50)
    @Column(name = "SUBRUBRO_CUENTA")
    private String subrubroCuenta;
    @Size(max = 50)
    @Column(name = "TIPO_CUENTA")
    private String tipoCuenta;
    @Size(max = 150)
    @Column(name = "DESCRIPCION_CUENTA")
    private String descripcionCuenta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALDO_CUENTA")
    private Float saldoCuenta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoCuenta")
    private List<SubCuenta> subCuentaList;
    @Transient
    private List<Saldo> saldosSubcuenta;
    @Transient
    private Float porcentajeParticipacion;
    @Transient
    private Double saldoFinalCuenta;
    @Transient
    private Double saldoAnteriorCuenta;
    @Transient
    private Double porcentajeIncremento;
    @Transient
    private Double diferenciaSaldos;
    @Transient
    private Double fuente;
    @Transient
    private Double uso;
    @Transient
    private boolean aumento;
    @Transient
    private boolean esTema;

    public Cuenta() {
    }

    public Cuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public String getRubroCuenta() {
        return rubroCuenta;
    }

    public void setRubroCuenta(String rubroCuenta) {
        this.rubroCuenta = rubroCuenta;
    }

    public String getSubrubroCuenta() {
        return subrubroCuenta;
    }

    public void setSubrubroCuenta(String subrubroCuenta) {
        this.subrubroCuenta = subrubroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getDescripcionCuenta() {
        return descripcionCuenta;
    }

    public void setDescripcionCuenta(String descripcionCuenta) {
        this.descripcionCuenta = descripcionCuenta;
    }

    public Float getSaldoCuenta() {
        return saldoCuenta;
    }

    public void setSaldoCuenta(Float saldoCuenta) {
        this.saldoCuenta = saldoCuenta;
    }
    
     public void sumarASaldo(Float x){
        this.saldoCuenta = this.saldoCuenta + x;
    }
    
    public void restarASaldo(Float x){
        
        this.saldoCuenta = this.saldoCuenta - x;
    
    }

    public Float getPorcentajeParticipacion() {
        return porcentajeParticipacion;
    }

    public void setPorcentajeParticipacion(Float porcentajeParticipacion) {
        this.porcentajeParticipacion = porcentajeParticipacion;
    }

    public List<Saldo> getSaldosSubcuenta() {
        return saldosSubcuenta;
    }

    public void setSaldosSubcuenta(List<Saldo> saldosSucuenta) {
        this.saldosSubcuenta = saldosSucuenta;
    }

    public Double getSaldoFinalCuenta() {
        return saldoFinalCuenta;
    }

    public void setSaldoFinalCuenta(Double saldoFinalCuenta) {
        this.saldoFinalCuenta = saldoFinalCuenta;
    }

    public Double getPorcentajeIncremento() {
        return porcentajeIncremento;
    }

    public void setPorcentajeIncremento(Double porcentajeIncremento) {
        this.porcentajeIncremento = porcentajeIncremento;
    }

    public Double getSaldoAnteriorCuenta() {
        return saldoAnteriorCuenta;
    }

    public void setSaldoAnteriorCuenta(Double saldoAnteriorCuenta) {
        this.saldoAnteriorCuenta = saldoAnteriorCuenta;
    }

    public Double getDiferenciaSaldos() {
        return diferenciaSaldos;
    }

    public void setDiferenciaSaldos(Double diferenciaSaldos) {
        this.diferenciaSaldos = diferenciaSaldos;
    }

    public Double getFuente() {
        return fuente;
    }

    public void setFuente(Double fuente) {
        this.fuente = fuente;
    }

    public Double getUso() {
        return uso;
    }

    public void setUso(Double uso) {
        this.uso = uso;
    }

    public boolean isAumento() {
        return aumento;
    }

    public void setAumento(boolean aumento) {
        this.aumento = aumento;
    }

    public boolean isEsTema() {
        return esTema;
    }

    public void setEsTema(boolean esTema) {
        this.esTema = esTema;
    }
    
    @XmlTransient
    public List<SubCuenta> getSubCuentaList() {
        return subCuentaList;
    }

    public void setSubCuentaList(List<SubCuenta> subCuentaList) {
        this.subCuentaList = subCuentaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoCuenta != null ? codigoCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.codigoCuenta == null && other.codigoCuenta != null) || (this.codigoCuenta != null && !this.codigoCuenta.equals(other.codigoCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return codigoCuenta + " " + nombreCuenta;
    }

}
