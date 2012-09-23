/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.filter;

import com.asecon.entity.Documento;
import com.asecon.entity.Periodo;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Antonio
 */
public class PartidaFilter {

    private Periodo periodo;
    private Long partidaInicial;
    private Long partidaFinal;
    private Date fechaInicial;
    private Date fechaFinal;
    private BigInteger numeroDocumento;
    private Documento idDocumento;
    private String descripcion;
    private Object[] parametros;
    private int cuentaFiltros = 0;
    private boolean coincidir;
    private String namedQuery;

    public PartidaFilter() {
    }

    public String getNamedQuery() throws Exception {

        if (coincidir) {
            namedQuery = "PartidaAND.findBy";
        } else {
            namedQuery = "PartidaOR.findBy";
        }

        if (periodoNoNulo()) {
            namedQuery = namedQuery.concat("Per");
            cuentaFiltros++;
        }

        if (partidasNoNulas()) {
            namedQuery = namedQuery.concat("Part");
            cuentaFiltros = cuentaFiltros + 2;

        }
        if (fechasNoNulas()) {
            namedQuery = namedQuery.concat("Fechas");
            cuentaFiltros = cuentaFiltros + 2;
        }
        if (idNoNulo()) {
            namedQuery = namedQuery.concat("IdDoc");
            cuentaFiltros++;
        }
        if (numeroNoNulo()) {
            namedQuery = namedQuery.concat("NumD");
            cuentaFiltros++;
        }
        if (descripcionNoNula()) {
            namedQuery = namedQuery.concat("Desc");
            cuentaFiltros++;
        }
        if (namedQuery.equals("PartidaAND.findBy") || namedQuery.equals("PartidaOR.findBy")) {
            throw new Exception("No se Ingresaron Datos");
        }
        return namedQuery;
    }

    public Object[] getParameters() {
        parametros = new Object[cuentaFiltros * 2];
        int i = 0;

        if (periodoNoNulo()) {
            parametros[i++] = "periodo";
            parametros[i++] = getPeriodo().getNumeroPeriodo();
        }
        if (partidasNoNulas()) {

            parametros[i++] = "partidaInicial";
            parametros[i++] = getPartidaInicial();
            parametros[i++] = "partidaFinal";
            parametros[i++] = getPartidaFinal();
        }
        if (fechasNoNulas()) {
            parametros[i++] = "fechaInicial";
            parametros[i++] = getFechaInicial();
            parametros[i++] = "fechaFinal";
            parametros[i++] = getFechaFinal();
        }
        if (idNoNulo()) {
            parametros[i++] = "idDocumento";
            parametros[i++] = getIdDocumento();

        }
        if (numeroNoNulo()) {
            parametros[i++] = "numeroDoc";
            parametros[i++] = getNumeroDocumento();
        }
        if (descripcionNoNula()) {
            parametros[i++] = "descripcion";
            parametros[i++] = "%" + getDescripcion() + "%";
        }
        return parametros;
    }

    private boolean periodoNoNulo() {
        return (periodo != null);
    }

    private boolean partidasNoNulas() {
        return (partidaInicial != null) && (partidaFinal != null);
    }

    private boolean fechasNoNulas() {
        return (fechaInicial != null) && (fechaFinal != null);
    }

    private boolean numeroNoNulo() {

        return (numeroDocumento != null);
    }

    private boolean idNoNulo() {
        return (idDocumento != null);
    }

    private boolean descripcionNoNula() {
        if (descripcion == null) {
            return false;
        } else {
            return (!descripcion.isEmpty());
        }

    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Documento getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Documento idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Long getPartidaFinal() {
        return partidaFinal;
    }

    public void setPartidaFinal(Long partidaFinal) {
        this.partidaFinal = partidaFinal;
    }

    public Long getPartidaInicial() {
        return partidaInicial;
    }

    public void setPartidaInicial(Long partidaInicial) {
        this.partidaInicial = partidaInicial;
    }

    public BigInteger getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(BigInteger numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public boolean isCoincidir() {
        return coincidir;
    }

    public void setCoincidir(boolean coincidir) {
        this.coincidir = coincidir;
    }
}
