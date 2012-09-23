package com.asecon.controller;

import com.asecon.entity.Transaccion;
import com.asecon.controller.util.JsfUtil;
import com.asecon.entity.Cuenta;
import com.asecon.entity.Partida;
import com.asecon.entity.Periodo;
import com.asecon.entity.SubCuenta;
import com.asecon.facade.TransaccionFacade;

import java.io.Serializable;
import java.util.ArrayList;
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

@ManagedBean(name = "transaccionController")
@SessionScoped
public class TransaccionController implements Serializable {

    private Transaccion current;
    private DataModel items = null;
    private Cuenta cuenta;
    private SubCuenta subcuenta;
    private Periodo periodo;
    private boolean cuentaNoNula;
    private boolean subcuentaNoNula;
    private Float debe;
    private Float haber;
    private Float saldo;
    private List<Transaccion> transacciones;
    private Partida partida = new Partida();
    private Transaccion transaccion = new Transaccion();
    @EJB
    private com.asecon.facade.TransaccionFacade ejbFacade;

    public TransaccionController() {
        transacciones = new ArrayList<Transaccion>();
    }

    private TransaccionFacade getFacade() {
        return ejbFacade;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public TransaccionFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(TransaccionFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }
    
    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }
    
    public void vistaPrevia() {
        Float debep = new Float(0);
        Float haberp = new Float(0);
        for (Transaccion x : transacciones) {
            try {
                x.verificarTransaccion();
                if (x.getDebe() != null) {
                    debep = debep + x.getDebe();
                }else{
                    x.setDebe(new Float(0));
                }
                if (x.getHaber() != null) {
                    haberp = haberp + x.getHaber();
                }
                else{
                    x.setHaber(new Float(0));
                }
            } catch (Exception ex) {
                JsfUtil.addErrorMessage(ex.toString());
            }
        }

        if (debep.compareTo(haberp) != 0) {
            try {
                throw new Exception("No se cumple Partida Doble");
            } catch (Exception ex) {
                JsfUtil.addErrorMessage(ex.toString());
            }
        }

        partida.setTotalDebe(debep);
        partida.setTotalHaber(haberp);
    }

    public void reset2() {
        transacciones = new ArrayList<Transaccion>();
        partida = new Partida();
        transaccion = new Transaccion();
    }

    public void agregarTransaccion() {
        transacciones.add(transaccion);
        transaccion = new Transaccion();

    }

    public void guardarPartida(ActionEvent event) {

        Periodo actual = (Periodo) event.getComponent().getAttributes().get("periodo");
        for (Transaccion x : transacciones) {
            x.setPeriodo(actual);
            x.setPartida(partida);
            try {
                getFacade().create(x);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PartidaCreated"));

            } catch (Exception e) {
                 reset2();
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));

            }
        }
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareCreate() {
        current = new Transaccion();
        current.setTransaccionPK(new com.asecon.entity.TransaccionPK());
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TransaccionCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TransaccionUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
   

    public void mostrarCuenta(ActionEvent event) {
        cuenta = (Cuenta) event.getComponent().getAttributes().get("cuenta");
        setCuentaNoNula(true);
        debe = new Float(0);
        haber = new Float(0);
        for (SubCuenta x : cuenta.getSubCuentaList()) {
            for (Transaccion y : x.getTransaccionList()) {
                debe = debe + y.getDebe();
                haber = haber + y.getHaber();
            }
        }
        saldo = cuenta.getSaldoCuenta();
    }

    public void mostrarSubCuenta(ActionEvent event) {
        subcuenta = (SubCuenta) event.getComponent().getAttributes().get("subcuenta");
        for (SubCuenta x : cuenta.getSubCuentaList()) {
            if (x.getCodigoSubcuenta().equals(subcuenta.getCodigoSubcuenta())) {
                items = null;
                setSubcuentaNoNula(true);
                setDebe(new Float(0));
                setHaber(new Float(0));
                for (Transaccion y : subcuenta.getTransaccionList()) {
                    debe = debe + y.getDebe();
                    haber = haber + y.getHaber();
                }
                saldo = subcuenta.getSaldoSubCuenta();
            }
        }
        if (!isSubcuentaNoNula()) {
            subcuenta = null;
            //mandar mensaje
        }
    }

    public void reset() {
        items = null;
        debe = null;
        haber = null;
        setCuenta(null);
        setSubcuenta(null);
        setCuentaNoNula(false);
        setSubcuentaNoNula(false);
        setPeriodo(null);
    }

    public DataModel getItems() {
        if (items == null) {
            items = new ListDataModel(getFacade().find(getNamedQuery(), getParameters()));
        }
        return items;
    }

    public String getNamedQuery() {
        if (periodo == null) {
            if (subcuenta == null) {
                return "Transaccion.findByCuenta";
            } else {
                return "Transaccion.findBySubCuenta";
            }
        }else{
            if (subcuenta == null) {
                return "Transaccion.findByPerCuenta";
            } else {
                return "Transaccion.findByPerSubCuenta";
            }
        }

    }

    public Object[] getParameters() {
        if (periodo == null) {
            if (subcuenta == null) {
                Object[] parametros = new Object[2];
                parametros[0] = "cuenta";
                parametros[1] = getCuenta();
                return parametros;
            } else {
                Object[] parametros = new Object[2];
                parametros[0] = "subCuenta";
                parametros[1] = getSubcuenta();
                return parametros;
            }
        } else {
            if (subcuenta == null) {
                Object[] parametros = new Object[4];
                parametros[0] = "cuenta";
                parametros[1] = getCuenta();
                parametros[2] = "periodo";
                parametros[3] = getPeriodo().getNumeroPeriodo();
                return parametros;
            } else {
                Object[] parametros = new Object[4];
                parametros[0] = "subCuenta";
                parametros[1] = getSubcuenta();
                parametros[2] = "periodo";
                parametros[3] = getPeriodo().getNumeroPeriodo();
                return parametros;
            }

        }
    }

    private void recreateModel() {
        items = null;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public SubCuenta getSubcuenta() {
        return subcuenta;
    }

    public void setSubcuenta(SubCuenta subcuenta) {
        this.subcuenta = subcuenta;
    }

    public boolean isCuentaNoNula() {
        return cuentaNoNula;
    }

    public boolean isSubcuentaNoNula() {
        return subcuentaNoNula;
    }

    public void setSubcuentaNoNula(boolean subcuentaNoNula) {
        this.subcuentaNoNula = subcuentaNoNula;
    }

    public void setCuentaNoNula(boolean cuentaNoNula) {
        this.cuentaNoNula = cuentaNoNula;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
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

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }
    
    public boolean isPeriodoNoNulo(){
        return (periodo != null);
    }

    @FacesConverter(forClass = Transaccion.class)
    public static class TransaccionControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TransaccionController controller = (TransaccionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "transaccionController");
            return controller.ejbFacade.find(getKey(value));
        }

        com.asecon.entity.TransaccionPK getKey(String value) {
            com.asecon.entity.TransaccionPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.asecon.entity.TransaccionPK();
            key.setCorrelativoPartida(Long.parseLong(values[0]));
            key.setNumeroPeriodo(Long.parseLong(values[1]));
            key.setCodigoSubcuenta(values[2]);
            return key;
        }

        String getStringKey(com.asecon.entity.TransaccionPK value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value.getCorrelativoPartida());
            sb.append(SEPARATOR);
            sb.append(value.getNumeroPeriodo());
            sb.append(SEPARATOR);
            sb.append(value.getCodigoSubcuenta());
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Transaccion) {
                Transaccion o = (Transaccion) object;
                return getStringKey(o.getTransaccionPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TransaccionController.class.getName());
            }
        }
    }
}
