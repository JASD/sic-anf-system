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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "sub_cuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubCuenta.findAll", query = "SELECT s FROM SubCuenta s"),
    @NamedQuery(name = "SubCuenta.findByCodigoSubcuenta", query = "SELECT s FROM SubCuenta s WHERE s.codigoSubcuenta = :codigoSubcuenta"),
    @NamedQuery(name = "SubCuenta.findByNombreSubcuenta", query = "SELECT s FROM SubCuenta s WHERE s.nombreSubcuenta = :nombreSubcuenta"),
    @NamedQuery(name = "SubCuenta.findByDescripcionSubcuenta", query = "SELECT s FROM SubCuenta s WHERE s.descripcionSubcuenta = :descripcionSubcuenta")})
public class SubCuenta implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subCuenta")
    private List<Saldo> saldoList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "CODIGO_SUBCUENTA")
    private String codigoSubcuenta;
    @Size(max = 50)
    @Column(name = "NOMBRE_SUBCUENTA")
    private String nombreSubcuenta;
    @Size(max = 150)
    @Column(name = "DESCRIPCION_SUBCUENTA")
    private String descripcionSubcuenta;
    @Column(name = "SALDO_SUBCUENTA")
    private Float saldoSubCuenta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subCuenta")
    private List<Transaccion> transaccionList;
    @JoinColumn(name = "CODIGO_CUENTA", referencedColumnName = "CODIGO_CUENTA")
    @ManyToOne(optional = false)
    private Cuenta codigoCuenta;

    public SubCuenta() {
    }
    
   

    public SubCuenta(String codigoSubcuenta) {
        this.codigoSubcuenta = codigoSubcuenta;
    }

    public String getCodigoSubcuenta() {
        return codigoSubcuenta;
    }

    public void setCodigoSubcuenta(String codigoSubcuenta) {
        this.codigoSubcuenta = codigoSubcuenta;
    }

    public String getNombreSubcuenta() {
        return nombreSubcuenta;
    }

    public void setNombreSubcuenta(String nombreSubcuenta) {
        this.nombreSubcuenta = nombreSubcuenta;
    }

    public String getDescripcionSubcuenta() {
        return descripcionSubcuenta;
    }

    public void setDescripcionSubcuenta(String descripcionSubcuenta) {
        this.descripcionSubcuenta = descripcionSubcuenta;
    }

    @XmlTransient
    public List<Transaccion> getTransaccionList() {
        return transaccionList;
    }

    public void setTransaccionList(List<Transaccion> transaccionList) {
        this.transaccionList = transaccionList;
    }

    public Cuenta getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(Cuenta codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public Float getSaldoSubCuenta() {
        return saldoSubCuenta;
    }
    
    public void sumarASaldo(Float x){
        this.saldoSubCuenta = this.saldoSubCuenta + x;
    }
    
    public void restarASaldo(Float x){
        
        this.saldoSubCuenta = this.saldoSubCuenta - x;
    
    }

    public void setSaldoSubCuenta(Float saldoSubCuenta) {
        this.saldoSubCuenta = saldoSubCuenta;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoSubcuenta != null ? codigoSubcuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubCuenta)) {
            return false;
        }
        SubCuenta other = (SubCuenta) object;
        if ((this.codigoSubcuenta == null && other.codigoSubcuenta != null) || (this.codigoSubcuenta != null && !this.codigoSubcuenta.equals(other.codigoSubcuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return codigoSubcuenta + " " + nombreSubcuenta;
    }

    @XmlTransient
    public List<Saldo> getSaldoList() {
        return saldoList;
    }

    public void setSaldoList(List<Saldo> saldoList) {
        this.saldoList = saldoList;
    }
    
}
