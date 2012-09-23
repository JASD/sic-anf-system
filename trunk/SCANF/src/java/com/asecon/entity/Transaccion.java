/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "transaccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaccion.findAll", query = "SELECT t FROM Transaccion t"),
    @NamedQuery(name = "Transaccion.findByCorrelativoPartida", query = "SELECT t FROM Transaccion t WHERE t.transaccionPK.correlativoPartida = :correlativoPartida"),
    @NamedQuery(name = "Transaccion.findByNumeroPeriodo", query = "SELECT t FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :numeroPeriodo"),
    @NamedQuery(name = "Transaccion.findByCodigoSubcuenta", query = "SELECT t FROM Transaccion t WHERE t.transaccionPK.codigoSubcuenta = :codigoSubcuenta"),
    @NamedQuery(name = "Transaccion.findByDebe", query = "SELECT t FROM Transaccion t WHERE t.debe = :debe"),
    @NamedQuery(name = "Transaccion.findByHaber", query = "SELECT t FROM Transaccion t WHERE t.haber = :haber"),
    @NamedQuery(name = "Transaccion.findAllCurrent", query = "SELECT t FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)"),
    @NamedQuery(name = "Transaccion.findByCuenta", query = "SELECT t FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true) AND t.transaccionPK.codigoSubcuenta IN (SELECT sc.codigoSubcuenta FROM SubCuenta sc WHERE sc.codigoCuenta = :cuenta)"),
    @NamedQuery(name = "Transaccion.findBySubCuenta", query = "SELECT t FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true) AND t.subCuenta = :subCuenta"),
    @NamedQuery(name = "Transaccion.findByPerCuenta", query = "SELECT t FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo AND t.transaccionPK.codigoSubcuenta IN (SELECT sc.codigoSubcuenta FROM SubCuenta sc WHERE sc.codigoCuenta = :cuenta)"),
    @NamedQuery(name = "Transaccion.findByPerSubCuenta", query = "SELECT t FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo AND t.subCuenta = :subCuenta")})
public class Transaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransaccionPK transaccionPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DEBE")
    private Float debe;
    @Column(name = "HABER")
    private Float haber;
    @JoinColumn(name = "CODIGO_SUBCUENTA", referencedColumnName = "CODIGO_SUBCUENTA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SubCuenta subCuenta;
    @JoinColumn(name = "NUMERO_PERIODO", referencedColumnName = "NUMERO_PERIODO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Periodo periodo;
    @JoinColumn(name = "CORRELATIVO_PARTIDA", referencedColumnName = "CORRELATIVO_PARTIDA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partida partida;

    public Transaccion() {
    }

    public void verificarTransaccion() throws Exception {

        if (subCuenta.getCodigoCuenta().getTipoCuenta().equals("DEUDORA")) {

            if (haber != null) {

                if (subCuenta.getSaldoSubCuenta().compareTo(haber) < 0) {
                    throw new Exception("El Saldo de" + subCuenta.getNombreSubcuenta()
                            + "es insuficiente para la Transacción");
                }
            }

        } else {

            if (debe != null) {
                
                if (subCuenta.getSaldoSubCuenta().compareTo(debe) < 0) {
                    throw new Exception("El Saldo de" + subCuenta.getNombreSubcuenta()
                            + "es insuficiente para la Transacción");
                }
            }

        }
    }
    
    public void ajustarSaldoCuenta() {

        if (subCuenta.getCodigoCuenta().getTipoCuenta().equals("DEUDORA")) {

            if (haber != null) {
                subCuenta.restarASaldo(haber);
                subCuenta.getCodigoCuenta().restarASaldo(haber);

            }
            if (debe != null) {
                subCuenta.sumarASaldo(debe);
                subCuenta.getCodigoCuenta().sumarASaldo(debe);

            }
        } else {

            if (haber != null) {
                subCuenta.sumarASaldo(haber);
                subCuenta.getCodigoCuenta().sumarASaldo(haber);

            }
            if (debe != null) {
                subCuenta.restarASaldo(debe);
                subCuenta.getCodigoCuenta().restarASaldo(debe);
            }
        }    
    }

    public Transaccion(TransaccionPK transaccionPK) {
        this.transaccionPK = transaccionPK;
    }

    public Transaccion(long correlativoPartida, long numeroPeriodo, String codigoSubcuenta) {
        this.transaccionPK = new TransaccionPK(correlativoPartida, numeroPeriodo, codigoSubcuenta);
    }

    public TransaccionPK getTransaccionPK() {
        return transaccionPK;
    }

    public void setTransaccionPK(TransaccionPK transaccionPK) {
        this.transaccionPK = transaccionPK;
    }

    public Float getDebe() {
        return debe;
    }

    public void setDebe(Float debe) {
        this.debe = debe;
    }

    public Float getHaber() {
        return haber;
    }

    public void setHaber(Float haber) {
        this.haber = haber;
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

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transaccionPK != null ? transaccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaccion)) {
            return false;
        }
        Transaccion other = (Transaccion) object;
        if ((this.transaccionPK == null && other.transaccionPK != null) || (this.transaccionPK != null && !this.transaccionPK.equals(other.transaccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asecon.entity.Transaccion[ transaccionPK=" + transaccionPK + " ]";
    }
}
