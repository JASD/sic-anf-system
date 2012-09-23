/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "partida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partida.findAll", query = "SELECT p FROM Partida p"),
    @NamedQuery(name = "Partida.findByCorrelativoPartida", query = "SELECT p FROM Partida p WHERE p.correlativoPartida = :correlativoPartida"),
    @NamedQuery(name = "Partida.findByFechaPartida", query = "SELECT p FROM Partida p WHERE p.fechaPartida = :fechaPartida"),
    @NamedQuery(name = "Partida.findByNumeroDocumento", query = "SELECT p FROM Partida p WHERE p.numeroDocumento = :numeroDocumento"),
    @NamedQuery(name = "Partida.findByDescripcionPartida", query = "SELECT p FROM Partida p WHERE p.descripcionPartida = :descripcionPartida"),
    @NamedQuery(name = "Partida.findAllCurrent", query = "SELECT DISTINCT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true))"),//Filtros para el Periodo Actual con coincidencia total
    @NamedQuery(name = "PartidaAND.findByPart", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal"),
    @NamedQuery(name = "PartidaAND.findByPartFechas", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal"),
    @NamedQuery(name = "PartidaAND.findByFechas", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal"),
    @NamedQuery(name = "PartidaAND.findByPartFechasIdDoc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento"),
    @NamedQuery(name = "PartidaAND.findByFechasIdDoc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento"),
    @NamedQuery(name = "PartidaAND.findByPartIdDoc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.idDocumento = :idDocumento"),
    @NamedQuery(name = "PartidaAND.findByIdDoc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.idDocumento = :idDocumento"),
    @NamedQuery(name = "PartidaAND.findByPartFechasIdDocNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento AND p.numeroDocumento = :numeroDoc"),
    @NamedQuery(name = "PartidaAND.findByFechasIdDocNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento AND p.numeroDocumento = :numeroDoc"),
    @NamedQuery(name = "PartidaAND.findByFechasNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.numeroDocumento = :numeroDoc"),
    @NamedQuery(name = "PartidaAND.findByPartIdDocNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.idDocumento = :idDocumento AND p.numeroDocumento = :numeroDoc"),
    @NamedQuery(name = "PartidaAND.findByIdDocNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.idDocumento = :idDocumento AND p.numeroDocumento = :numeroDoc"),
    @NamedQuery(name = "PartidaAND.findByPartNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.numeroDocumento = :numeroDoc"),
    @NamedQuery(name = "PartidaAND.findByNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.numeroDocumento = :numeroDoc"),
    @NamedQuery(name = "PartidaAND.findByPartFechasIdDocNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento AND p.numeroDocumento = :numeroDoc AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPartIdDocNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.idDocumento = :idDocumento AND p.numeroDocumento = :numeroDoc AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPartNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.numeroDocumento = :numeroDoc AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPartFechasIdDocDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPartIdDocDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPartFechasDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPartDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByFechasIdDocNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento AND p.numeroDocumento = :numeroDoc AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByFechasIdDocDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByFechasDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByIdDocNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.idDocumento = :idDocumento AND p.numeroDocumento = :numeroDoc AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByIdDocDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND p.idDocumento = :idDocumento AND p.descripcionPartida LIKE :descripcion"), //Fin Filtros del Periodo Actual
    @NamedQuery(name = "PartidaOR.findByPartFechas", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal)"),
    @NamedQuery(name = "PartidaOR.findByFechas", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal)"),
    @NamedQuery(name = "PartidaOR.findByPartFechasIdDoc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento)"),
    @NamedQuery(name = "PartidaOR.findByFechasIdDoc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento)"),
    @NamedQuery(name = "PartidaOR.findByPartIdDoc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.idDocumento = :idDocumento)"),
    @NamedQuery(name = "PartidaOR.findByIdDoc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.idDocumento = :idDocumento)"),
    @NamedQuery(name = "PartidaOR.findByPartFechasIdDocNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento OR p.numeroDocumento = :numeroDoc)"),
    @NamedQuery(name = "PartidaOR.findByFechasIdDocNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento OR p.numeroDocumento = :numeroDoc)"),
    @NamedQuery(name = "PartidaOR.findByFechasNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.numeroDocumento = :numeroDoc)"),
    @NamedQuery(name = "PartidaOR.findByPartIdDocNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.idDocumento = :idDocumento OR p.numeroDocumento = :numeroDoc)"),
    @NamedQuery(name = "PartidaOR.findByIdDocNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.idDocumento = :idDocumento OR p.numeroDocumento = :numeroDoc)"),
    @NamedQuery(name = "PartidaOR.findByPartNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.numeroDocumento = :numeroDoc)"),
    @NamedQuery(name = "PartidaOR.findByNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.numeroDocumento = :numeroDoc)"),
    @NamedQuery(name = "PartidaOR.findByPartFechasIdDocNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento OR p.numeroDocumento = :numeroDoc OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPartIdDocNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.idDocumento = :idDocumento OR p.numeroDocumento = :numeroDoc OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPartNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.numeroDocumento = :numeroDoc OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPartFechasIdDocDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPartIdDocDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPartFechasDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPartDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByFechasIdDocNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento OR p.numeroDocumento = :numeroDoc OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByFechasIdDocDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByFechasDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByIdDocNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.idDocumento = :idDocumento OR p.numeroDocumento = :numeroDoc OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByIdDocDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo IN (SELECT h.numeroPeriodo FROM Periodo h WHERE h.estadoPeriodo = true)) AND (p.idDocumento = :idDocumento OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaAND.findByPer", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo)"),// Con Periodo
    @NamedQuery(name = "PartidaOR.findByPer", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo)"),
    @NamedQuery(name = "PartidaAND.findByPerPart", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal"),
    @NamedQuery(name = "PartidaAND.findByPerPartFechas", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal"),
    @NamedQuery(name = "PartidaAND.findByPerFechas", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal"),
    @NamedQuery(name = "PartidaAND.findByPerPartFechasIdDoc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento"),
    @NamedQuery(name = "PartidaAND.findByPerFechasIdDoc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento"),
    @NamedQuery(name = "PartidaAND.findByPerPartIdDoc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.idDocumento = :idDocumento"),
    @NamedQuery(name = "PartidaAND.findByPerIdDoc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.idDocumento = :idDocumento"),
    @NamedQuery(name = "PartidaAND.findByPerPartFechasIdDocNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento AND p.numeroDocumento = :numeroDoc"),
    @NamedQuery(name = "PartidaAND.findByPerFechasIdDocNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento AND p.numeroDocumento = :numeroDoc"),
    @NamedQuery(name = "PartidaAND.findByPerFechasNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.numeroDocumento = :numeroDoc"),
    @NamedQuery(name = "PartidaAND.findByPerPartIdDocNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.idDocumento = :idDocumento AND p.numeroDocumento = :numeroDoc"),
    @NamedQuery(name = "PartidaAND.findByPerIdDocNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.idDocumento = :idDocumento AND p.numeroDocumento = :numeroDoc"),
    @NamedQuery(name = "PartidaAND.findByPerPartNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.numeroDocumento = :numeroDoc"),
    @NamedQuery(name = "PartidaAND.findByPerNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.numeroDocumento = :numeroDoc"),
    @NamedQuery(name = "PartidaAND.findByPerPartFechasIdDocNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento AND p.numeroDocumento = :numeroDoc AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPerPartIdDocNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.idDocumento = :idDocumento AND p.numeroDocumento = :numeroDoc AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPerPartNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.numeroDocumento = :numeroDoc AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPerPartFechasIdDocDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPerPartIdDocDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPerPartFechasDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPerPartDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPerDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPerFechasIdDocNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento AND p.numeroDocumento = :numeroDoc AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPerFechasIdDocDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.idDocumento = :idDocumento AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPerFechasDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPerIdDocNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.idDocumento = :idDocumento AND p.numeroDocumento = :numeroDoc AND p.descripcionPartida LIKE :descripcion"),
    @NamedQuery(name = "PartidaAND.findByPerIdDocDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND p.idDocumento = :idDocumento AND p.descripcionPartida LIKE :descripcion"), //Fin Filtros del Periodo Actual
    @NamedQuery(name = "PartidaOR.findByPerPartFechas", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal)"),
    @NamedQuery(name = "PartidaOR.findByPerFechas", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal)"),
    @NamedQuery(name = "PartidaOR.findByPerPartFechasIdDoc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento)"),
    @NamedQuery(name = "PartidaOR.findByPerFechasIdDoc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento)"),
    @NamedQuery(name = "PartidaOR.findByPerPartIdDoc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.idDocumento = :idDocumento)"),
    @NamedQuery(name = "PartidaOR.findByPerIdDoc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.idDocumento = :idDocumento)"),
    @NamedQuery(name = "PartidaOR.findByPerPartFechasIdDocNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento OR p.numeroDocumento = :numeroDoc)"),
    @NamedQuery(name = "PartidaOR.findByPerFechasIdDocNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento OR p.numeroDocumento = :numeroDoc)"),
    @NamedQuery(name = "PartidaOR.findByPerFechasNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.numeroDocumento = :numeroDoc)"),
    @NamedQuery(name = "PartidaOR.findByPerPartIdDocNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.idDocumento = :idDocumento OR p.numeroDocumento = :numeroDoc)"),
    @NamedQuery(name = "PartidaOR.findByPerIdDocNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.idDocumento = :idDocumento OR p.numeroDocumento = :numeroDoc)"),
    @NamedQuery(name = "PartidaOR.findByPerPartNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.numeroDocumento = :numeroDoc)"),
    @NamedQuery(name = "PartidaOR.findByPerNumD", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.numeroDocumento = :numeroDoc)"),
    @NamedQuery(name = "PartidaOR.findByPerPartFechasIdDocNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento OR p.numeroDocumento = :numeroDoc OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPerPartIdDocNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.idDocumento = :idDocumento OR p.numeroDocumento = :numeroDoc OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPerPartNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.numeroDocumento = :numeroDoc OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPerPartFechasIdDocDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPerPartIdDocDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPerPartFechasDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPerPartDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.correlativoPartida BETWEEN :partidaInicial AND :partidaFinal OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPerDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPerFechasIdDocNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento OR p.numeroDocumento = :numeroDoc OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPerFechasIdDocDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.idDocumento = :idDocumento OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPerFechasDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.fechaPartida BETWEEN :fechaInicial AND :fechaFinal OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPerIdDocNumDDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.idDocumento = :idDocumento OR p.numeroDocumento = :numeroDoc OR p.descripcionPartida LIKE :descripcion)"),
    @NamedQuery(name = "PartidaOR.findByPerIdDocDesc", query = "SELECT p FROM Partida p WHERE p.correlativoPartida IN (SELECT t.transaccionPK.correlativoPartida FROM Transaccion t WHERE t.transaccionPK.numeroPeriodo = :periodo) AND (p.idDocumento = :idDocumento OR p.descripcionPartida LIKE :descripcion)")})
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CORRELATIVO_PARTIDA", insertable=false, nullable=false, updatable=true)
    private Long correlativoPartida;
    @Column(name = "NUMERO_DOCUMENTO")
    private BigInteger numeroDocumento;
    @Size(max = 150)
    @Column(name = "DESCRIPCION_PARTIDA")
    private String descripcionPartida;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partida")
    private List<Transaccion> transaccionList;
    @Column(name = "FECHA_PARTIDA")
    @Temporal(TemporalType.DATE)
    private Date fechaPartida;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTAL_DEBE")
    private Float totalDebe;
    @Column(name = "TOTAL_HABER")
    private Float totalHaber;
    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
    @ManyToOne
    private Documento idDocumento;

    public Partida() {
    }

    public Partida(Long correlativoPartida) {
        this.correlativoPartida = correlativoPartida;
    }

    public Long getCorrelativoPartida() {
        return correlativoPartida;
    }

    public void setCorrelativoPartida(Long correlativoPartida) {
        this.correlativoPartida = correlativoPartida;
    }

    public Date getFechaPartida() {
        return fechaPartida;
    }

    public void setFechaPartida(Date fechaPartida) {
        this.fechaPartida = fechaPartida;
    }

    public BigInteger getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(BigInteger numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getDescripcionPartida() {
        return descripcionPartida;
    }

    public void setDescripcionPartida(String descripcionPartida) {
        this.descripcionPartida = descripcionPartida;
    }

    @XmlTransient
    public List<Transaccion> getTransaccionList() {
        return transaccionList;
    }

    public void setTransaccionList(List<Transaccion> transaccionList) {
        this.transaccionList = transaccionList;
    }

    public Documento getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Documento idDocumento) {
        this.idDocumento = idDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (correlativoPartida != null ? correlativoPartida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partida)) {
            return false;
        }
        Partida other = (Partida) object;
        if ((this.correlativoPartida == null && other.correlativoPartida != null) || (this.correlativoPartida != null && !this.correlativoPartida.equals(other.correlativoPartida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.asecon.entity.Partida[ correlativoPartida=" + correlativoPartida + " ]";
    }

    public Float getTotalDebe() {
        return totalDebe;
    }

    public void setTotalDebe(Float totalDebe) {
        this.totalDebe = totalDebe;
    }

    public Float getTotalHaber() {
        return totalHaber;
    }

    public void setTotalHaber(Float totalHaber) {
        this.totalHaber = totalHaber;
    }
}
