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
}
