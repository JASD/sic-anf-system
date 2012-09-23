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
    @NamedQuery(name = "Cuenta.findBySaldoCuenta", query = "SELECT c FROM Cuenta c WHERE c.saldoCuenta = :saldoCuenta")})
public class Cuenta implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuenta")
    private List<Saldo> saldoList;
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

    @XmlTransient
    public List<Saldo> getSaldoList() {
        return saldoList;
    }

    public void setSaldoList(List<Saldo> saldoList) {
        this.saldoList = saldoList;
    }
    
}
