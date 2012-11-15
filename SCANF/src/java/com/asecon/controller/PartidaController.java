package com.asecon.controller;

import com.asecon.entity.Partida;
import com.asecon.controller.util.JsfUtil;
import com.asecon.controller.util.PaginationHelper;
import com.asecon.entity.Cuenta;
import com.asecon.entity.Empleado;
import com.asecon.entity.Periodo;
import com.asecon.entity.Planilla;
import com.asecon.entity.SubCuenta;
import com.asecon.entity.Transaccion;
import com.asecon.entity.TransaccionPK;
import com.asecon.facade.CuentaFacade;
import com.asecon.facade.EmpleadoFacade;
import com.asecon.facade.PartidaFacade;

import com.asecon.facade.PeriodoFacade;
import com.asecon.facade.PlanillaFacade;
import com.asecon.facade.SubCuentaFacade;
import com.asecon.facade.TransaccionFacade;
import com.asecon.filter.PartidaFilter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "partidaController")
@SessionScoped
public class PartidaController implements Serializable {
    
    private Partida current;
    private PartidaFilter filtro;
    private DataModel items = null;
    private boolean isFiltering;
    private boolean agrego;
    private List<Transaccion> transacciones;
    private Partida partida = new Partida();
    private Transaccion transaccion = new Transaccion();
    private Float total_ingresos;
    private Float total_gastos;
    private Periodo actual;
    @EJB
    private com.asecon.facade.PartidaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    public PartidaController() {
        transacciones = new ArrayList<Transaccion>();
    }
    
    public Partida getSelected() {
        if (current == null) {
            current = new Partida();
            selectedItemIndex = -1;
        }
        return current;
    }
    
    private PartidaFacade getFacade() {
        return ejbFacade;
    }

    /*public PaginationHelper getPagination() {
    if (pagination == null) {
    pagination = new PaginationHelper(10) {
    
    @Override
    public int getItemsCount() {
    return getFacade().count();
    }
    
    @Override
    public DataModel createPageDataModel() {
    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
    }
    };
    }
    return pagination;
    }*/
    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }
                
                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, "Partida.findAllCurrent"));
                }
            };
        }
        return pagination;
    }
    
    public PaginationHelper getFilterPagination() throws Exception {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                
                private String namedQuery = filtro.getNamedQuery();
                private Object[] parameters = filtro.getParameters();
                
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }
                
                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, namedQuery, parameters));
                }
            };
        }
        return pagination;
        
    }
    
    public String prepareList() {
        recreateModel();
        return "List";
    }
    
    public void prepareView() {
        current = (Partida) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    }
    
    public void prepareFind() {
        filtro = new PartidaFilter();
    }
    
    public void find() {
        recreateModel();
        pagination = null;
        setIsFiltering(true);
        try {
            items = getFilterPagination().createPageDataModel();
        } catch (Exception ex) {
            unfind();
            //No se ingresaron datos
        }
        //Guardar SQL
    }
    
    public void unfind() {
        recreateModel();
        pagination = null;
        setIsFiltering(false);
        //Anular SQL

    }
    
    public String prepareCreate() {
        current = new Partida();
        selectedItemIndex = -1;
        return "Create";
    }
    
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PartidaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public String prepareEdit() {
        current = (Partida) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }
    
    public void vistaPrevia() {
        Float debe = new Float(0);
        Float haber = new Float(0);
        agrego = true;
        for (Transaccion x : transacciones) {
            try {
                
                x.verificarTransaccion();
                if (x.getDebe() != null) {
                    debe = debe + x.getDebe();
                } else {
                    x.setDebe(new Float(0));
                }
                if (x.getHaber() != null) {
                    haber = haber + x.getHaber();
                } else {
                    x.setHaber(new Float(0));
                }
            } catch (Exception ex) {
                agrego = false;
                JsfUtil.addErrorMessage(ex.toString());
            }
        }
        
        if (debe.compareTo(haber) != 0 || ((debe.compareTo(new Float(0)) <= 0) && (haber.compareTo(new Float(0)) <= 0))) {
            try {
                
                throw new Exception("No se cumple Partida Doble");
            } catch (Exception ex) {
                agrego = false;
                JsfUtil.addErrorMessage("No se cumple Partida Doble");
            }
        }
        
        if (agrego) {
            partida.setTotalDebe(debe);
            partida.setTotalHaber(haber);
            setAgrego(true);
            JsfUtil.addSuccessMessage("Partida Bien Formada");
        }
    }
    
    public void reset() {
        transacciones = new ArrayList<Transaccion>();
        partida = new Partida();
        transaccion = new Transaccion();
        setAgrego(false);
    }
    
    public void agregarTransaccion() {
        transacciones.add(transaccion);
        transaccion = new Transaccion();
        
    }
    
    public void guardarPartida(ActionEvent event) {
        
        actual = (Periodo) event.getComponent().getAttributes().get("periodo");
        TransaccionFacade transaccionfa = (TransaccionFacade) event.getComponent().getAttributes().get("transafacade");
        CuentaFacade cuentafa = (CuentaFacade) event.getComponent().getAttributes().get("cuentafacade");
        SubCuentaFacade subfa = (SubCuentaFacade) event.getComponent().getAttributes().get("subfacade");
        try {
            getFacade().create(partida);
            for (Transaccion x : transacciones) {
                x.setTransaccionPK(new TransaccionPK(partida.getCorrelativoPartida(), actual.getNumeroPeriodo(), x.getSubCuenta().getCodigoSubcuenta()));
                x.ajustarSaldoCuenta();
                cuentafa.edit(x.getSubCuenta().getCodigoCuenta());
                subfa.edit(x.getSubCuenta());
                transaccionfa.create(x);
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PartidaCreated"));
            reset();
        } catch (Exception e) {
            reset();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            
        }
    }
    
    public void fechar(ActionEvent event){
        
        PeriodoFacade perfa = (PeriodoFacade) event.getComponent().getAttributes().get("perfacade");
        actual = (Periodo) event.getComponent().getAttributes().get("periodo");
    
        Calendar fechaHoy = Calendar.getInstance();
        Date hoy = fechaHoy.getTime();
        actual.setFechaFinPeriodo(hoy);
        perfa.edit(actual);
        JsfUtil.addSuccessMessage("Listo");
    }
    
    public void preCierre(ActionEvent event) {
        
        
        PeriodoFacade perfa = (PeriodoFacade) event.getComponent().getAttributes().get("perfacade");
        actual = (Periodo) event.getComponent().getAttributes().get("periodo");
        TransaccionFacade transaccionfa = (TransaccionFacade) event.getComponent().getAttributes().get("transafacade");
        CuentaFacade cuentafa = (CuentaFacade) event.getComponent().getAttributes().get("cuentafacade");
        SubCuentaFacade subfa = (SubCuentaFacade) event.getComponent().getAttributes().get("subfacade");
        
        List<Cuenta> gastos = cuentafa.findNoParameters("Cuenta.findGastos");
        List<Cuenta> ingresos = cuentafa.findNoParameters("Cuenta.findIngresos");
        
        Calendar fechaHoy = Calendar.getInstance();
        Date hoy = fechaHoy.getTime();
        total_gastos = new Float(0);
        total_ingresos = new Float(0);
        Float diferencia = new Float(0);
        Partida cierre = new Partida();
        cierre.setDescripcionPartida("Partida de Cierre Periodo");
        cierre.setFechaPartida(hoy);
        getFacade().create(cierre);

        //abonar todos los gastos
        for (Cuenta gasto : gastos) {
            
            for (SubCuenta x : gasto.getSubCuentaList()) {
                
                if (x.getSaldoSubCuenta().compareTo(new Float(0)) > 0) {
                    
                    total_gastos = total_gastos + x.getSaldoSubCuenta();
                    Transaccion y = new Transaccion(cierre.getCorrelativoPartida(), actual.getNumeroPeriodo(), x.getCodigoSubcuenta());
                    y.setHaber(x.getSaldoSubCuenta());
                    y.setDebe(new Float(0));
                    x.setSaldoSubCuenta(new Float(0));
                    transaccionfa.create(y);
                    subfa.edit(x);
                }
                
            }
            
            gasto.setSaldoCuenta(new Float(0));
            cuentafa.edit(gasto);
        }


        //cargar todos los ingresos
        for (Cuenta ingreso : ingresos) {
            
            
            for (SubCuenta x : ingreso.getSubCuentaList()) {
                
                if (x.getSaldoSubCuenta().compareTo(new Float(0)) > 0) {
                    total_ingresos = total_ingresos + x.getSaldoSubCuenta();
                    Transaccion y = new Transaccion(cierre.getCorrelativoPartida(), actual.getNumeroPeriodo(), x.getCodigoSubcuenta());
                    y.setHaber(new Float(0));
                    y.setDebe(x.getSaldoSubCuenta());
                    x.setSaldoSubCuenta(new Float(0));
                    transaccionfa.create(y);
                    subfa.edit(x);
                }
                
            }
            
            ingreso.setSaldoCuenta(new Float(0));
            cuentafa.edit(ingreso);
        }

        //obtener diferencia
        diferencia = total_ingresos - total_gastos;


        //Hubo Pérdida
        if (diferencia.compareTo(new Float(0)) < 0) {
            Transaccion z = new Transaccion(cierre.getCorrelativoPartida(), actual.getNumeroPeriodo(), "30600");
            z.setDebe(total_gastos - total_ingresos);
            z.setHaber(new Float(0));
            transaccionfa.create(z);
            cierre.setTotalDebe(total_gastos);
            cierre.setTotalHaber(total_gastos);
            getFacade().edit(cierre);
            SubCuenta perdida = subfa.find("30600");
            perdida.sumarASaldo(total_gastos - total_ingresos);
            perdida.getCodigoCuenta().sumarASaldo(total_gastos - total_ingresos);
            subfa.edit(perdida);
            cuentafa.edit(perdida.getCodigoCuenta());
            actual.setPerdidasPeriodo(total_gastos - total_ingresos);
           /* actual.setUtilidadNetaPeriodo(new Float(0));
            actual.setTotalCisPeriodo(total_gastos);
            actual.setTotalCostoPeriodo(total_gastos);
            actual.setTotalIngresosPeriodo(total_ingresos);*/
            actual.setFechaFinPeriodo(hoy);
            perfa.edit(actual);
        } else {
            //Hubo Ganancia
            Transaccion z = new Transaccion(cierre.getCorrelativoPartida(), actual.getNumeroPeriodo(), "30700");
            z.setHaber(total_ingresos - total_gastos);
            z.setDebe(new Float(0));
            transaccionfa.create(z);
            cierre.setTotalDebe(total_ingresos);
            cierre.setTotalHaber(total_ingresos);
            getFacade().edit(cierre);
            SubCuenta ganancia = subfa.find("30700");
            ganancia.sumarASaldo(total_ingresos - total_gastos);
            ganancia.getCodigoCuenta().sumarASaldo(total_ingresos - total_gastos);
            subfa.edit(ganancia);
            cuentafa.edit(ganancia.getCodigoCuenta());
            actual.setPerdidasPeriodo(new Float(0));
           /* actual.setUtilidadNetaPeriodo(total_ingresos - total_gastos);
            actual.setTotalCisPeriodo(total_gastos);
            actual.setTotalCostoPeriodo(total_gastos);
            actual.setTotalIngresosPeriodo(total_ingresos);
            actual.setFechaFinPeriodo(hoy);*/
            perfa.edit(actual);
        }
        //actual.setFechaFinPeriodo(hoy);

        //Consolidacion IVA

        SubCuenta iva_credito = subfa.find("10401");
        SubCuenta iva_debito = subfa.find("20400");
        if (iva_credito.getSaldoSubCuenta().compareTo(iva_debito.getSaldoSubCuenta()) < 0) {
            
            if (iva_credito.getSaldoSubCuenta().compareTo(new Float(0)) > 0) {

                //credito menor a debito
                //Se crea partida
                Partida consolida = new Partida();
                consolida.setFechaPartida(hoy);
                consolida.setDescripcionPartida("Consolidación de IVA del Período");


                //Terminar partida
                consolida.setTotalDebe(iva_credito.getSaldoSubCuenta());
                consolida.setTotalHaber(iva_credito.getSaldoSubCuenta());
                getFacade().create(consolida);

                //Se abona credito
                Transaccion ivadebe = new Transaccion(consolida.getCorrelativoPartida(), actual.getNumeroPeriodo(), iva_credito.getCodigoSubcuenta());
                ivadebe.setDebe(new Float(0));
                ivadebe.setHaber(iva_credito.getSaldoSubCuenta());
                transaccionfa.create(ivadebe);


                //Se carga debito
                Transaccion ivadebe2 = new Transaccion(consolida.getCorrelativoPartida(), actual.getNumeroPeriodo(), iva_debito.getCodigoSubcuenta());
                ivadebe2.setHaber(new Float(0));
                ivadebe2.setDebe(iva_credito.getSaldoSubCuenta());
                transaccionfa.create(ivadebe2);


                // Se Actualizan Saldos
                iva_debito.restarASaldo(iva_credito.getSaldoSubCuenta());
                iva_debito.getCodigoCuenta().restarASaldo(iva_credito.getSaldoSubCuenta());
                subfa.edit(iva_debito);
                cuentafa.edit(iva_debito.getCodigoCuenta());
                
                iva_credito.setSaldoSubCuenta(new Float(0));
                subfa.edit(iva_credito);
                iva_credito.getCodigoCuenta().setSaldoCuenta(new Float(0));
                cuentafa.edit(iva_credito.getCodigoCuenta());
            }
            
        } else {
            
            if (iva_debito.getSaldoSubCuenta().compareTo(new Float(0)) > 0) {
                //debito menor a credito
                //Se crea partida
                Partida consolida = new Partida();
                consolida.setFechaPartida(hoy);
                consolida.setDescripcionPartida("Consolidación de IVA del Período");


                //Terminar partida
                consolida.setTotalDebe(iva_debito.getSaldoSubCuenta());
                consolida.setTotalHaber(iva_debito.getSaldoSubCuenta());
                getFacade().create(consolida);

                //Se carga debito
                Transaccion ivadebe = new Transaccion(consolida.getCorrelativoPartida(), actual.getNumeroPeriodo(), iva_debito.getCodigoSubcuenta());
                ivadebe.setDebe(iva_debito.getSaldoSubCuenta());
                ivadebe.setHaber(new Float(0));
                transaccionfa.create(ivadebe);


                //Se abona credito
                Transaccion ivadebe2 = new Transaccion(consolida.getCorrelativoPartida(), actual.getNumeroPeriodo(), iva_credito.getCodigoSubcuenta());
                ivadebe2.setHaber(iva_debito.getSaldoSubCuenta());
                ivadebe2.setDebe(new Float(0));
                transaccionfa.create(ivadebe2);

                //Se Actualizan Saldos
                iva_credito.restarASaldo(iva_debito.getSaldoSubCuenta());
                iva_credito.getCodigoCuenta().restarASaldo(iva_debito.getSaldoSubCuenta());
                subfa.edit(iva_credito);
                cuentafa.edit(iva_credito.getCodigoCuenta());
                
                iva_debito.setSaldoSubCuenta(new Float(0));
                subfa.edit(iva_debito);
                iva_debito.getCodigoCuenta().setSaldoCuenta(new Float(0));
                cuentafa.edit(iva_debito.getCodigoCuenta());
                
            }
            
        }
        
        JsfUtil.addSuccessMessage("Generación Exitosa");
    }
    
    public void cierrePeriodo(ActionEvent event) {
        actual = (Periodo) event.getComponent().getAttributes().get("periodo");
        PlanillaFacade planilla = (PlanillaFacade) event.getComponent().getAttributes().get("planilla");
        EmpleadoFacade empleados = (EmpleadoFacade) event.getComponent().getAttributes().get("empleados");
        PeriodoFacade perfa = (PeriodoFacade) event.getComponent().getAttributes().get("perfacade");
        TransaccionFacade transaccionfa = (TransaccionFacade) event.getComponent().getAttributes().get("transafacade");
        CuentaFacade cuentafa = (CuentaFacade) event.getComponent().getAttributes().get("cuentafacade");
        SubCuentaFacade subfa = (SubCuentaFacade) event.getComponent().getAttributes().get("subfacade");
        Calendar fechaHoy = Calendar.getInstance();
        Date hoy = fechaHoy.getTime();


        //Traspaso de Utilidad / Perdida del Periodo
        Partida x = new Partida();
        x.setFechaPartida(hoy);
        x.setDescripcionPartida("Traspaso de las Utilidades/Perdidas del ejercicio");
        getFacade().create(x);
        if (actual.getPerdidasPeriodo().compareTo(new Float(0)) == 0) {

            //Kitar  Ganancias del Periodo
            Transaccion p = new Transaccion(x.getCorrelativoPartida(), actual.getNumeroPeriodo(), "30700");
            //p.setDebe(actual.getTotalIngresosPeriodo() - actual.getTotalCostoPeriodo());
            p.setHaber(new Float(0));
            transaccionfa.create(p);

            //Actualizar Saldo
            SubCuenta sub = subfa.find("30700");
            sub.setSaldoSubCuenta(new Float(0));
            subfa.edit(sub);
            sub.getCodigoCuenta().setSaldoCuenta(new Float(0));
            cuentafa.edit(sub.getCodigoCuenta());

            //Ingresar a Ganancias anteriores
            Transaccion h = new Transaccion(x.getCorrelativoPartida(), actual.getNumeroPeriodo(), "30800");
           // h.setHaber(actual.getTotalIngresosPeriodo() - actual.getTotalCostoPeriodo());
            h.setDebe(new Float(0));
            transaccionfa.create(h);
           
           


            //Actualizar Saldos
            SubCuenta y = subfa.find("30800");
            //y.sumarASaldo(actual.getTotalIngresosPeriodo() - actual.getTotalCostoPeriodo());
            //y.getCodigoCuenta().sumarASaldo(actual.getTotalIngresosPeriodo() - actual.getTotalCostoPeriodo());
            subfa.edit(y);
            cuentafa.edit(y.getCodigoCuenta());
            //Actualizar Partida
            //x.setTotalDebe(actual.getTotalIngresosPeriodo() - actual.getTotalCostoPeriodo());
            //x.setTotalHaber(actual.getTotalIngresosPeriodo() - actual.getTotalCostoPeriodo());
            getFacade().edit(x);
            
        } else {

            //Kitar Perdidas del Periodo
            Transaccion p = new Transaccion(x.getCorrelativoPartida(), actual.getNumeroPeriodo(), "30600");
            p.setDebe(new Float(0));
            //p.setHaber(actual.getTotalCostoPeriodo() - actual.getTotalIngresosPeriodo());
            transaccionfa.create(p);
            SubCuenta sub = subfa.find("30600");

            //Se actualizan saldos
            sub.setSaldoSubCuenta(new Float(0));
            subfa.edit(sub);
            sub.getCodigoCuenta().setSaldoCuenta(new Float(0));
            cuentafa.edit(sub.getCodigoCuenta());


            //Ingresar a Perdidas Anteriores
            Transaccion h = new Transaccion(x.getCorrelativoPartida(), actual.getNumeroPeriodo(), "30300");
            //h.setDebe(actual.getTotalCostoPeriodo() - actual.getTotalIngresosPeriodo());
            h.setHaber(new Float(0));

            //SubCuenta y = new SubCuenta("30300");
            SubCuenta y = subfa.find("30300");
            //y.sumarASaldo(actual.getTotalCostoPeriodo() - actual.getTotalIngresosPeriodo());
            //y.getCodigoCuenta().sumarASaldo(actual.getTotalCostoPeriodo() - actual.getTotalIngresosPeriodo());
            subfa.edit(y);
            cuentafa.edit(y.getCodigoCuenta());

            //Actualizar Partida
            //x.setTotalDebe(actual.getTotalCostoPeriodo() - actual.getTotalIngresosPeriodo());
            //x.setTotalHaber(actual.getTotalCostoPeriodo() - actual.getTotalIngresosPeriodo());
            getFacade().edit(x);
        }


        //Calculando Total Activo

        List<Cuenta> activo_deudor = cuentafa.findNoParameters("Cuenta.findActivoDeudor");
        List<Cuenta> activo_acreedora = cuentafa.findNoParameters("Cuenta.findActivoAcreedor");
        Float actd = new Float(0);
        Float acta = new Float(0);
        
        for (Cuenta deudora : activo_deudor) {
            
            actd = actd + deudora.getSaldoCuenta();
        }
        
        for (Cuenta deudora : activo_acreedora) {
            
            acta = acta + deudora.getSaldoCuenta();
        }
        
        //actual.setTotalActivoPeriodo(actd - acta);

        //Calculando total Pasivo

        List<Cuenta> pasivo_deudor = cuentafa.findNoParameters("Cuenta.findAllPasivoAcreedor");
        
        Float pasa = new Float(0);
        
        for (Cuenta pasivo : pasivo_deudor) {
            
            pasa = pasa + pasivo.getSaldoCuenta();
        }
        
        //actual.setTotalPasivoPeriodo(pasa);



        //Calculando Total Capital

        List<Cuenta> capital_deudor = cuentafa.findNoParameters("Cuenta.findAllCapitalDeudor");
        List<Cuenta> capital_acreedor = cuentafa.findNoParameters("Cuenta.findAllCapitalAcreedor");
        Float capd = new Float(0);
        Float capa = new Float(0);
        
        for (Cuenta capdeu : capital_deudor) {
            
            capd = capd + capdeu.getSaldoCuenta();
        }
        
        for (Cuenta capacre : capital_acreedor) {
            
            capa = capa + capacre.getSaldoCuenta();
        }
        
        //actual.setTotalCapitalPeriodo(capa - capd);

        //Finalizando Periodo

        actual.setEstadoPeriodo(false);
        //actual.setFechaFinPeriodo(hoy);
        perfa.edit(actual);

        //Creando Nuevo Periodo

        Periodo nuevo = new Periodo();
        nuevo.setEstadoPeriodo(true);
        nuevo.setFechaInicioPeriodo(hoy);
        perfa.create(nuevo);


        //Creando Nueva Planilla para el Periodo
        
        List<Empleado> empleado = empleados.findNoParameters("Empleado.findByAllActivos");
        
        for (Empleado emple : empleado) {
            
            Planilla nueva = new Planilla(nuevo.getNumeroPeriodo(), emple.getIdEmpleado());
            nueva.setAfp(new Float(0));
            nueva.setBonificaciones(new Float(0));
            nueva.setHorasExtra(new Float(0));
            nueva.setIsss(new Float(0));
            nueva.setSalarioBase(new Float(0));
            nueva.setSalarioNeto(new Float(0));
            nueva.setNumeroHorasExtra(BigDecimal.ZERO);
            planilla.create(nueva);
        }
        
        JsfUtil.addSuccessMessage("Cierre Exitoso");
        
        
    }
    
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PartidaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public String destroy() {
        current = (Partida) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        return "List";
    }
    
    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }
    
    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PartidaDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    
    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }
    
    public DataModel getItems() {
        if (items == null && !isFiltering) {
            items = getPagination().createPageDataModel();
        }
        if (items == null && isFiltering) {
            try {
                items = getFilterPagination().createPageDataModel();
            } catch (Exception ex) {
                //No deberia caer nunca aki
            }
        }
        return items;
    }
    
    private void recreateModel() {
        items = null;
    }
    
    public void next() {
        getPagination().nextPage();
        recreateModel();
    }
    
    public void previous() {
        getPagination().previousPage();
        recreateModel();
    }
    
    public PartidaFilter getFiltro() {
        return filtro;
    }
    
    public void setFiltro(PartidaFilter filtro) {
        this.filtro = filtro;
    }
    
    public boolean isIsFiltering() {
        return isFiltering;
    }
    
    public List<Transaccion> getTransacciones() {
        return transacciones;
    }
    
    public Transaccion getTransaccion() {
        return transaccion;
    }
    
    public Partida getPartida() {
        return partida;
    }
    
    public void setPartida(Partida partida) {
        this.partida = partida;
    }
    
    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }
    
    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }
    
    public void setIsFiltering(boolean isFiltering) {
        this.isFiltering = isFiltering;
    }
    
    public boolean isAgrego() {
        return agrego;
    }
    
    public void setAgrego(boolean agrego) {
        this.agrego = agrego;
    }
    
    public Periodo getActual() {
        return actual;
    }
    
    public void setActual(Periodo actual) {
        this.actual = actual;
    }
    
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }
    
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
    
    @FacesConverter(forClass = Partida.class)
    public static class PartidaControllerConverter implements Converter {
        
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PartidaController controller = (PartidaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "partidaController");
            return controller.ejbFacade.find(getKey(value));
        }
        
        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }
        
        String getStringKey(java.lang.Long value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }
        
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Partida) {
                Partida o = (Partida) object;
                return getStringKey(o.getCorrelativoPartida());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + PartidaController.class.getName());
            }
        }
    }
}
