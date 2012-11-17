/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.controller;

import com.asecon.entity.Cuenta;
import com.asecon.entity.Periodo;
import com.asecon.entity.Saldo;
import com.scanf.pojo.Dupont;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Antonio
 */
@ManagedBean(name = "financieroController")
@SessionScoped
public class FinancieroController implements Serializable {

    @EJB
    private com.asecon.facade.CuentaFacade cuentaFacade;
    @EJB
    private com.asecon.facade.SaldoFacade saldoFacade;
    @EJB
    private com.asecon.facade.PeriodoFacade periodoFacade;
    private List<Cuenta> cuentas;
    private Periodo periodoSelected;
    private Periodo periodoAnterior;
    private Double totalPorcentaje;
    private TreeNode dupont;
    private CartesianChartModel chartModel;
    private String legendPosition;

    public FinancieroController() {
        chartModel = new CartesianChartModel();
        ChartSeries x = new ChartSeries();
        x.setLabel("X");
        ChartSeries y = new ChartSeries();
        y.setLabel("Y");
        x.set("0", 0);
        y.set("0", 0);

        chartModel.addSeries(x);
        chartModel.addSeries(y);
        legendPosition = "nw";

    }

    public void analizarVerticalActivos() {
        Object[] parameters = {"rubroCuenta", "ACTIVO", "numeroPeriodo", periodoSelected.getNumeroPeriodo()};
        cuentas = cuentaFacade.getResultList("Cuenta.findBySaldoFinal", parameters);
        Double total = new Double(0);
        //Asignaciones y calculo de total activos
        for (Cuenta c : cuentas) {
            parameters = new Object[]{"numeroPeriodo", periodoSelected.getNumeroPeriodo(), "codigoCuenta", c};
            List<Saldo> saldos = saldoFacade.getResultList("Saldo.findByCuenta", parameters);
            c.setSaldosSubcuenta(saldos);
            Double totalCuenta = new Double(0);
            for (Saldo s : saldos) {
                totalCuenta = totalCuenta + s.getSaldoFinalSubcuenta();
            }
            if (c.getTipoCuenta().equals("DEUDORA")) {
                c.setSaldoFinalCuenta(totalCuenta);
                total = total + totalCuenta;
            } else {
                c.setSaldoFinalCuenta(-totalCuenta);
            }
        }
        //Calculo % de participación
        totalPorcentaje = new Double(0);
        for (Cuenta c : cuentas) {
            Double porc = total / periodoSelected.getTotalActivoPeriodo() * Double.valueOf(100);
            Float porcCuenta;
            porcCuenta = new Float(c.getSaldoFinalCuenta() / total * porc);
            c.setPorcentajeParticipacion(porcCuenta);
            totalPorcentaje = totalPorcentaje + porcCuenta;
            for (Saldo s : c.getSaldosSubcuenta()) {
                Float porcSubCuenta = new Float(s.getSaldoFinalSubcuenta() / total * porc);
                s.setPorcentajeParticipacion(porcSubCuenta);
            }
        }
        //maxPorcentaje = new Double(100);
    }

    public void analizarVerticalPasivos() {
        Object[] parameters = {"rubroCuenta", "PASIVO", "numeroPeriodo", periodoSelected.getNumeroPeriodo()};
        cuentas = cuentaFacade.getResultList("Cuenta.findBySaldoFinal", parameters);
        Double total = new Double(0);
        //Asignaciones y calculo de total pasivos
        for (Cuenta c : cuentas) {
            parameters = new Object[]{"numeroPeriodo", periodoSelected.getNumeroPeriodo(), "codigoCuenta", c};
            List<Saldo> saldos = saldoFacade.getResultList("Saldo.findByCuenta", parameters);
            c.setSaldosSubcuenta(saldos);
            Double totalCuenta = new Double(0);
            for (Saldo s : saldos) {
                totalCuenta = totalCuenta + s.getSaldoFinalSubcuenta();
            }
            if (c.getTipoCuenta().equals("ACREEDORA")) {
                c.setSaldoFinalCuenta(totalCuenta);
                total = total + totalCuenta;
            } else {
                c.setSaldoFinalCuenta(-totalCuenta);
            }
        }
        //Calculo % de participación
        totalPorcentaje = new Double(0);
        for (Cuenta c : cuentas) {
            Double porc = total / (periodoSelected.getTotalPasivoPeriodo()
                    + periodoSelected.getTotalCapitalPeriodo()) * Double.valueOf(100);
            Float porcCuenta;
            porcCuenta = new Float(c.getSaldoFinalCuenta() / total * porc);
            c.setPorcentajeParticipacion(porcCuenta);
            totalPorcentaje = totalPorcentaje + porcCuenta;
            for (Saldo s : c.getSaldosSubcuenta()) {
                Float porcSubCuenta = new Float(s.getSaldoFinalSubcuenta() / total * porc);
                s.setPorcentajeParticipacion(porcSubCuenta);
            }
        }
    }

    public void analizarVerticalCapital() {
        Object[] parameters = {"rubroCuenta", "PATRIMONIO", "numeroPeriodo", periodoSelected.getNumeroPeriodo()};
        cuentas = cuentaFacade.getResultList("Cuenta.findBySaldoFinal", parameters);
        Double total = new Double(0);
        //Asignaciones y calculo de total capital
        for (Cuenta c : cuentas) {
            parameters = new Object[]{"numeroPeriodo", periodoSelected.getNumeroPeriodo(), "codigoCuenta", c};
            List<Saldo> saldos = saldoFacade.getResultList("Saldo.findByCuenta", parameters);
            c.setSaldosSubcuenta(saldos);
            Double totalCuenta = new Double(0);
            for (Saldo s : saldos) {
                totalCuenta = totalCuenta + s.getSaldoFinalSubcuenta();
            }
            if (c.getTipoCuenta().equals("ACREEDORA")) {
                c.setSaldoFinalCuenta(totalCuenta);
                total = total + totalCuenta;
            } else {
                c.setSaldoFinalCuenta(-totalCuenta);
            }
        }
        //Calculo % de participación
        totalPorcentaje = new Double(0);
        for (Cuenta c : cuentas) {
            Double porc = total / (periodoSelected.getTotalPasivoPeriodo()
                    + periodoSelected.getTotalCapitalPeriodo()) * Double.valueOf(100);
            Float porcCuenta;
            porcCuenta = new Float(c.getSaldoFinalCuenta() / total * porc);
            c.setPorcentajeParticipacion(porcCuenta);
            totalPorcentaje = totalPorcentaje + porcCuenta;
            for (Saldo s : c.getSaldosSubcuenta()) {
                Float porcSubCuenta = new Float(s.getSaldoFinalSubcuenta() / total * porc);
                s.setPorcentajeParticipacion(porcSubCuenta);
            }
        }
    }

    public void analizarVerticalResultados() {

        Object[] parameters = {"numeroPeriodo", periodoSelected.getNumeroPeriodo()};
        cuentas = cuentaFacade.getResultList("Cuenta.findBySaldoFinalResultados", parameters);
        Double total = periodoSelected.getUtilidadBrutaPeriodo();
        //Asignaciones y calculo de total activos
        for (Cuenta c : cuentas) {
            parameters = new Object[]{"numeroPeriodo", periodoSelected.getNumeroPeriodo(), "codigoCuenta", c};
            List<Saldo> saldos = saldoFacade.getResultList("Saldo.findByCuenta", parameters);
            c.setSaldosSubcuenta(saldos);
            Double totalCuenta = new Double(0);
            for (Saldo s : saldos) {
                totalCuenta = totalCuenta + s.getSaldoFinalSubcuenta();
            }
            if (c.getTipoCuenta().equals("ACREEDORA")) {
                c.setSaldoFinalCuenta(totalCuenta);
                //total = total + totalCuenta;
            } else {
                c.setSaldoFinalCuenta(-totalCuenta);
            }
        }
        //Calculo % de participación
        totalPorcentaje = new Double(0);
        for (Cuenta c : cuentas) {
            Double porc = Double.valueOf(100);
            Float porcCuenta;
            porcCuenta = new Float(c.getSaldoFinalCuenta() / total * porc);
            c.setPorcentajeParticipacion(porcCuenta);
            totalPorcentaje = totalPorcentaje + porcCuenta;
            for (Saldo s : c.getSaldosSubcuenta()) {
                Float porcSubCuenta = new Float(s.getSaldoFinalSubcuenta() / total * porc);
                s.setPorcentajeParticipacion(porcSubCuenta);
            }
        }

    }

    public void analizarDupont() {
        dupont = new DefaultTreeNode(null, null);
        Dupont utilidad = new Dupont("Utilidad Neta", periodoSelected.getUtilidadNetaPeriodo());
        TreeNode utilidadNode = new DefaultTreeNode(utilidad, null);
        Dupont ventas = new Dupont("Ventas", periodoSelected.getTotalIngresosPeriodo());
        TreeNode ventasNode = new DefaultTreeNode(ventas, null);
        TreeNode ventasNode2 = new DefaultTreeNode(ventas, null);
        Dupont activos = new Dupont("Total Activos", periodoSelected.getTotalActivoPeriodo());
        TreeNode activosNode = new DefaultTreeNode(activos, null);
        Dupont margen = new Dupont("Margen de Utilidad", utilidad.getValor() / ventas.getValor());
        TreeNode margenNode = new DefaultTreeNode(margen, null);
        Dupont rotacion = new Dupont("Rotación de Activos", ventas.getValor() / activos.getValor());
        TreeNode rotacionNode = new DefaultTreeNode(rotacion, null);
        Dupont deudas = new Dupont("Total Deudas", periodoSelected.getTotalPasivoPeriodo());
        TreeNode deudasNode = new DefaultTreeNode(deudas, null);
        TreeNode activosNode2 = new DefaultTreeNode(activos, null);
        Dupont rendimiento = new Dupont("Rendimiento s/ Activos", margen.getValor() * rotacion.getValor());
        TreeNode rendimientoNode = new DefaultTreeNode(rendimiento, null);
        Dupont plan = new Dupont("Plan de Financiamiento", Double.valueOf(1) - (deudas.getValor() / activos.getValor()));
        TreeNode planNode = new DefaultTreeNode(plan, null);
        Dupont rendimientoCap = new Dupont("Rendimiento s/ Capital Contable", rendimiento.getValor() / plan.getValor());
        TreeNode rendCapNode = new DefaultTreeNode(rendimientoCap, dupont);

        //Uniendo Hojas
        rendimientoNode.setParent(rendCapNode);
        margenNode.setParent(rendimientoNode);
        utilidadNode.setParent(margenNode);
        ventasNode.setParent(margenNode);

        rotacionNode.setParent(rendimientoNode);
        ventasNode2.setParent(rotacionNode);
        activosNode.setParent(rotacionNode);

        planNode.setParent(rendCapNode);
        deudasNode.setParent(planNode);
        activosNode2.setParent(planNode);

    }

    public void graficarActivoVrsPasivo() {

        List<Periodo> periodos = periodoFacade.findNoParameters("Periodo.findNoCurrent");

        //indiceDeuda = new CartesianChartModel();
        chartModel.clear();

        LineChartSeries pasivos = new LineChartSeries();
        pasivos.setLabel("Pasivos");
        LineChartSeries activos = new LineChartSeries();
        activos.setLabel("Activos");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        for (Periodo p : periodos) {
            String year = yearFormat.format(p.getFechaFinPeriodo());
            pasivos.set(year, p.getTotalPasivoPeriodo());
            activos.set(year, p.getTotalActivoPeriodo());
        }
        chartModel.addSeries(pasivos);
        chartModel.addSeries(activos);
        legendPosition = "nw";

    }

    public void graficarIndiceEndeuda() {

        List<Periodo> periodos = periodoFacade.findNoParameters("Periodo.findNoCurrent");
        chartModel.clear();

        LineChartSeries indice = new LineChartSeries();
        indice.setLabel("Índice Endeudamiento");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        for (Periodo p : periodos) {
            String year = yearFormat.format(p.getFechaFinPeriodo());
            indice.set(year, p.getTotalPasivoPeriodo() / p.getTotalActivoPeriodo());
        }
        chartModel.addSeries(indice);
        legendPosition = "ne";

    }

    public void graficarUtilidadVrsVentas() {

        List<Periodo> periodos = periodoFacade.findNoParameters("Periodo.findNoCurrent");
        chartModel.clear();

        ChartSeries utilidad = new ChartSeries();
        utilidad.setLabel("Utilidad");
        ChartSeries ventas = new ChartSeries();
        ventas.setLabel("Ventas");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        for (Periodo p : periodos) {
            String year = yearFormat.format(p.getFechaFinPeriodo());
            utilidad.set(year, p.getUtilidadNetaPeriodo());
            ventas.set(year, p.getTotalIngresosPeriodo());
        }
        chartModel.addSeries(utilidad);
        chartModel.addSeries(ventas);
        legendPosition = "nw";

    }

    public void graficarMargenUtilidad() {

        List<Periodo> periodos = periodoFacade.findNoParameters("Periodo.findNoCurrent");
        chartModel.clear();

        LineChartSeries margen = new LineChartSeries();
        margen.setLabel("Margen de Utilidad");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        for (Periodo p : periodos) {
            String year = yearFormat.format(p.getFechaFinPeriodo());
            margen.set(year, p.getUtilidadNetaPeriodo() / p.getTotalIngresosPeriodo());
        }
        chartModel.addSeries(margen);
        legendPosition = "nw";

    }

    public void graficarValoresCirculantes() {

        List<Periodo> periodos = periodoFacade.findNoParameters("Periodo.findNoCurrent");
        chartModel.clear();

        ChartSeries actCirculante = new ChartSeries();
        actCirculante.setLabel("Activo Circulante");
        ChartSeries pasCirculante = new ChartSeries();
        pasCirculante.setLabel("PasivoCirculante");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        for (Periodo p : periodos) {
            String year = yearFormat.format(p.getFechaFinPeriodo());
            actCirculante.set(year, p.getActivoCirculantePeriodo());
            pasCirculante.set(year, p.getPasivoCirculantePeriodo());
        }
        chartModel.addSeries(pasCirculante);
        chartModel.addSeries(actCirculante);
        legendPosition = "e";

    }

    public void graficarRazonCirculante() {

        List<Periodo> periodos = periodoFacade.findNoParameters("Periodo.findNoCurrent");
        chartModel.clear();

        LineChartSeries razon = new LineChartSeries();
        razon.setLabel("Razón Circulante");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        for (Periodo p : periodos) {
            String year = yearFormat.format(p.getFechaFinPeriodo());
            razon.set(year, p.getActivoCirculantePeriodo() / p.getPasivoCirculantePeriodo());
        }
        chartModel.addSeries(razon);
        legendPosition = "se";

    }

    public void analizarHorizontalActivos() {
        Long anterior = periodoSelected.getNumeroPeriodo() - Long.valueOf(1);
        periodoAnterior = periodoFacade.find(anterior);
        Object[] parameters = {"rubroCuenta", "ACTIVO", "numeroPeriodo",
            periodoSelected.getNumeroPeriodo()};
        cuentas = cuentaFacade.getResultList("Cuenta.findBySaldoFinal", parameters);
        parameters = new Object[]{"rubroCuenta", "ACTIVO", "numeroPeriodo",
            periodoAnterior.getNumeroPeriodo()};
        List<Cuenta> cuentasPasadas = cuentaFacade.getResultList("Cuenta.findBySaldoFinal", parameters);

        for (Cuenta c : cuentas) {
            parameters = new Object[]{"numeroPeriodo", periodoSelected.getNumeroPeriodo(), "codigoCuenta", c};
            List<Saldo> saldos = saldoFacade.getResultList("Saldo.findByCuenta", parameters);
            Double totalCuenta = new Double(0);
            for (Saldo s : saldos) {
                totalCuenta = totalCuenta + s.getSaldoFinalSubcuenta();
            }

            c.setSaldoFinalCuenta(totalCuenta);
        }
        //Asignaciones y calculo de total activos cuentas pasadas
        for (Cuenta c : cuentasPasadas) {
            parameters = new Object[]{"numeroPeriodo", periodoAnterior.getNumeroPeriodo(), "codigoCuenta", c};
            List<Saldo> saldos = saldoFacade.getResultList("Saldo.findByCuenta", parameters);
            Double totalCuenta = new Double(0);
            for (Saldo s : saldos) {
                totalCuenta = totalCuenta + s.getSaldoFinalSubcuenta();
            }

            c.setSaldoFinalCuenta(totalCuenta);
        }

        //Calculo Diferencia y porcentaje
        for (int i = 0; i < cuentas.size(); i++) {
            Cuenta c = cuentas.get(i);
            Double saldoAnt = cuentasPasadas.get(i).getSaldoFinalCuenta();
            Double dif = c.getSaldoFinalCuenta() - saldoAnt;
            Double porc = dif / saldoAnt * Double.valueOf(100);
            c.setSaldoAnteriorCuenta(saldoAnt);
            c.setDiferenciaSaldos(dif);
            c.setPorcentajeIncremento(porc);
        }
        Double saldoAnt = periodoAnterior.getTotalActivoPeriodo();
        Double dif = periodoSelected.getTotalActivoPeriodo() - saldoAnt;
        periodoSelected.setDiferenciaRubro(dif);
        periodoSelected.setPorcentajeAumentoRubro(dif / saldoAnt * Double.valueOf(100));
    }

    public void analizarHorizontalPasivos() {
        Long anterior = periodoSelected.getNumeroPeriodo() - Long.valueOf(1);
        periodoAnterior = periodoFacade.find(anterior);
        Object[] parameters = {"rubroCuenta", "PASIVO", "numeroPeriodo",
            periodoSelected.getNumeroPeriodo()};
        cuentas = cuentaFacade.getResultList("Cuenta.findBySaldoFinal", parameters);
        parameters = new Object[]{"rubroCuenta", "PASIVO", "numeroPeriodo",
            periodoAnterior.getNumeroPeriodo()};
        List<Cuenta> cuentasPasadas = cuentaFacade.getResultList("Cuenta.findBySaldoFinal", parameters);

        for (Cuenta c : cuentas) {
            parameters = new Object[]{"numeroPeriodo", periodoSelected.getNumeroPeriodo(), "codigoCuenta", c};
            List<Saldo> saldos = saldoFacade.getResultList("Saldo.findByCuenta", parameters);
            Double totalCuenta = new Double(0);
            for (Saldo s : saldos) {
                totalCuenta = totalCuenta + s.getSaldoFinalSubcuenta();
            }

            c.setSaldoFinalCuenta(totalCuenta);
        }
        //Asignaciones y calculo de total activos cuentas pasadas
        for (Cuenta c : cuentasPasadas) {
            parameters = new Object[]{"numeroPeriodo", periodoAnterior.getNumeroPeriodo(), "codigoCuenta", c};
            List<Saldo> saldos = saldoFacade.getResultList("Saldo.findByCuenta", parameters);
            Double totalCuenta = new Double(0);
            for (Saldo s : saldos) {
                totalCuenta = totalCuenta + s.getSaldoFinalSubcuenta();
            }

            c.setSaldoFinalCuenta(totalCuenta);
        }

        //Calculo Diferencia y porcentaje
        for (int i = 0; i < cuentas.size(); i++) {
            Cuenta c = cuentas.get(i);
            Double saldoAnt = cuentasPasadas.get(i).getSaldoFinalCuenta();
            Double dif = c.getSaldoFinalCuenta() - saldoAnt;
            Double porc = dif / saldoAnt * Double.valueOf(100);
            c.setSaldoAnteriorCuenta(saldoAnt);
            c.setDiferenciaSaldos(dif);
            c.setPorcentajeIncremento(porc);
        }
        Double saldoAnt = periodoAnterior.getTotalPasivoPeriodo();
        Double dif = periodoSelected.getTotalPasivoPeriodo() - saldoAnt;
        periodoSelected.setDiferenciaRubro(dif);
        periodoSelected.setPorcentajeAumentoRubro(dif / saldoAnt * Double.valueOf(100));
    }

    public void analizarHorizontalCapital() {
        Long anterior = periodoSelected.getNumeroPeriodo() - Long.valueOf(1);
        periodoAnterior = periodoFacade.find(anterior);
        Object[] parameters = {"rubroCuenta", "PATRIMONIO", "numeroPeriodo",
            periodoSelected.getNumeroPeriodo()};
        cuentas = cuentaFacade.getResultList("Cuenta.findBySaldoFinal", parameters);
        parameters = new Object[]{"rubroCuenta", "PATRIMONIO", "numeroPeriodo",
            periodoAnterior.getNumeroPeriodo()};
        List<Cuenta> cuentasPasadas = cuentaFacade.getResultList("Cuenta.findBySaldoFinal", parameters);

        for (Cuenta c : cuentas) {
            parameters = new Object[]{"numeroPeriodo", periodoSelected.getNumeroPeriodo(), "codigoCuenta", c};
            List<Saldo> saldos = saldoFacade.getResultList("Saldo.findByCuenta", parameters);
            Double totalCuenta = new Double(0);
            for (Saldo s : saldos) {
                totalCuenta = totalCuenta + s.getSaldoFinalSubcuenta();
            }

            c.setSaldoFinalCuenta(totalCuenta);
        }
        //Asignaciones y calculo de total activos cuentas pasadas
        for (Cuenta c : cuentasPasadas) {
            parameters = new Object[]{"numeroPeriodo", periodoAnterior.getNumeroPeriodo(), "codigoCuenta", c};
            List<Saldo> saldos = saldoFacade.getResultList("Saldo.findByCuenta", parameters);
            Double totalCuenta = new Double(0);
            for (Saldo s : saldos) {
                totalCuenta = totalCuenta + s.getSaldoFinalSubcuenta();
            }

            c.setSaldoFinalCuenta(totalCuenta);
        }

        //Calculo Diferencia y porcentaje
        for (int i = 0; i < cuentas.size(); i++) {
            Cuenta c = cuentas.get(i);
            Double saldoAnt = cuentasPasadas.get(i).getSaldoFinalCuenta();
            Double dif = c.getSaldoFinalCuenta() - saldoAnt;
            Double porc = dif / saldoAnt * Double.valueOf(100);
            c.setSaldoAnteriorCuenta(saldoAnt);
            c.setDiferenciaSaldos(dif);
            c.setPorcentajeIncremento(porc);
        }
        Double saldoAnt = periodoAnterior.getTotalCapitalPeriodo();
        Double dif = periodoSelected.getTotalCapitalPeriodo() - saldoAnt;
        periodoSelected.setDiferenciaRubro(dif);
        periodoSelected.setPorcentajeAumentoRubro(dif / saldoAnt * Double.valueOf(100));
    }

    public void analizarHorizontalResultados() {
        Long anterior = periodoSelected.getNumeroPeriodo() - Long.valueOf(1);

        periodoAnterior = periodoFacade.find(anterior);
        Object[] parameters = {"numeroPeriodo", periodoSelected.getNumeroPeriodo()};
        cuentas = cuentaFacade.getResultList("Cuenta.findBySaldoFinalResultados", parameters);
        parameters = new Object[]{"numeroPeriodo", periodoAnterior.getNumeroPeriodo()};
        List<Cuenta> cuentasPasadas = cuentaFacade.getResultList("Cuenta.findBySaldoFinalResultados", parameters);

        for (Cuenta c : cuentas) {
            parameters = new Object[]{"numeroPeriodo", periodoSelected.getNumeroPeriodo(), "codigoCuenta", c};
            List<Saldo> saldos = saldoFacade.getResultList("Saldo.findByCuenta", parameters);
            Double totalCuenta = new Double(0);
            for (Saldo s : saldos) {
                totalCuenta = totalCuenta + s.getSaldoFinalSubcuenta();
            }

            c.setSaldoFinalCuenta(totalCuenta);
        }
        //Asignaciones y calculo de total activos cuentas pasadas
        for (Cuenta c : cuentasPasadas) {
            parameters = new Object[]{"numeroPeriodo", periodoAnterior.getNumeroPeriodo(), "codigoCuenta", c};
            List<Saldo> saldos = saldoFacade.getResultList("Saldo.findByCuenta", parameters);
            Double totalCuenta = new Double(0);
            for (Saldo s : saldos) {
                totalCuenta = totalCuenta + s.getSaldoFinalSubcuenta();
            }

            c.setSaldoFinalCuenta(totalCuenta);
        }

        //Calculo Diferencia y porcentaje
        for (int i = 0; i < cuentas.size(); i++) {
            Cuenta c = cuentas.get(i);
            Double saldoAnt = cuentasPasadas.get(i).getSaldoFinalCuenta();
            Double dif = c.getSaldoFinalCuenta() - saldoAnt;
            Double porc = dif / saldoAnt * Double.valueOf(100);
            c.setSaldoAnteriorCuenta(saldoAnt);
            c.setDiferenciaSaldos(dif);
            c.setPorcentajeIncremento(porc);
        }
        Double saldoAnt = periodoAnterior.getUtilidadNetaPeriodo();
        Double dif = periodoSelected.getUtilidadNetaPeriodo() - saldoAnt;
        periodoSelected.setDiferenciaRubro(dif);
        periodoSelected.setPorcentajeAumentoRubro(dif / saldoAnt * Double.valueOf(100));
    }

    public void generarFuentesYUsos() {
        Long anterior = periodoSelected.getNumeroPeriodo() - Long.valueOf(1);
        periodoAnterior = periodoFacade.find(anterior);
        Object[] parameters = {"numeroPeriodo", periodoSelected.getNumeroPeriodo()};
        cuentas = cuentaFacade.getResultList("Cuenta.findBySaldoFinalBG", parameters);
        parameters = new Object[]{"numeroPeriodo", periodoAnterior.getNumeroPeriodo()};
        List<Cuenta> cuentasPasadas = cuentaFacade.getResultList("Cuenta.findBySaldoFinalBG", parameters);
        for (Cuenta c : cuentas) {
            parameters = new Object[]{"numeroPeriodo", periodoSelected.getNumeroPeriodo(), "codigoCuenta", c};
            List<Saldo> saldos = saldoFacade.getResultList("Saldo.findByCuenta", parameters);
            Double totalCuenta = new Double(0);
            for (Saldo s : saldos) {
                totalCuenta = totalCuenta + s.getSaldoFinalSubcuenta();
            }
            c.setSaldoFinalCuenta(totalCuenta);
        }
        //Asignaciones y calculo de total cuentas pasadas
        for (Cuenta c : cuentasPasadas) {
            parameters = new Object[]{"numeroPeriodo", periodoAnterior.getNumeroPeriodo(), "codigoCuenta", c};
            List<Saldo> saldos = saldoFacade.getResultList("Saldo.findByCuenta", parameters);
            Double totalCuenta = new Double(0);
            for (Saldo s : saldos) {
                totalCuenta = totalCuenta + s.getSaldoFinalSubcuenta();
            }
            
            c.setSaldoFinalCuenta(totalCuenta);
        }
        Double totalFuente = Double.valueOf(0);
        Double totalUso = Double.valueOf(0);
        //Calculo Diferencia y si es fuente o uso
        for (int i = 0; i < cuentas.size(); i++) {
            Cuenta c = cuentas.get(i);
            Double saldoAnt = cuentasPasadas.get(i).getSaldoFinalCuenta();
            Double dif = c.getSaldoFinalCuenta() - saldoAnt;
            boolean aumento = (dif >= 0) ? true : false;
            c.setAumento(aumento);
            if (c.getTipoCuenta().equals("DEUDORA")) {
                if (aumento) {
                    c.setUso(dif);
                    c.setFuente(Double.valueOf(0));
                    totalUso = totalUso + dif;
                } else {
                    dif = -dif;
                    c.setUso(Double.valueOf(0));
                    c.setFuente(dif);
                    totalFuente = totalFuente + dif;
                }
            } else {
                if (aumento) {
                    c.setUso(Double.valueOf(0));
                    c.setFuente(dif);
                    totalFuente = totalFuente + dif;
                } else {
                    dif = -dif;
                    c.setUso(dif);
                    c.setFuente(Double.valueOf(0));
                    totalUso = totalUso + dif;
                }
            }
        }
        periodoSelected.setTotalUso(totalUso);
        periodoSelected.setTotalFuente(totalFuente);
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public Periodo getPeriodoSelected() {
        return periodoSelected;
    }

    public void setPeriodoSelected(Periodo periodoSelected) {
        this.periodoSelected = periodoSelected;
    }

    public Double getTotalPorcentaje() {
        return totalPorcentaje;
    }

    public void setTotalPorcentaje(Double totalPorcentaje) {
        this.totalPorcentaje = totalPorcentaje;
    }

    public TreeNode getDupont() {
        return dupont;
    }

    public void setDupont(TreeNode dupont) {
        this.dupont = dupont;
    }

    public CartesianChartModel getChartModel() {
        return chartModel;
    }

    public void setChartModel(CartesianChartModel chartModel) {
        this.chartModel = chartModel;
    }

    public String getLegendPosition() {
        return legendPosition;
    }

    public void setLegendPosition(String legendPosition) {
        this.legendPosition = legendPosition;
    }

    public Periodo getPeriodoAnterior() {
        return periodoAnterior;
    }

    public void setPeriodoAnterior(Periodo periodoAnterior) {
        this.periodoAnterior = periodoAnterior;
    }
}
