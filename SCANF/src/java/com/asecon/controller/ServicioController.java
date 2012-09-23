package com.asecon.controller;

import com.asecon.entity.Servicio;
import com.asecon.controller.util.JsfUtil;
import com.asecon.controller.util.PaginationHelper;
import com.asecon.entity.ActividadServicio;
import com.asecon.entity.CostoActividad;
import com.asecon.entity.CostoServicio;
import com.asecon.entity.Periodo;
import com.asecon.entity.TipoServicio;
import com.asecon.facade.ActividadServicioFacade;
import com.asecon.facade.CostoActividadFacade;
import com.asecon.facade.CostoServicioFacade;
import com.asecon.facade.ServicioFacade;

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

@ManagedBean(name = "servicioController")
@SessionScoped
public class ServicioController implements Serializable {

    private Servicio current;
    private DataModel items = null;
    private Servicio servicio;
    private TipoServicio tipo;
    @EJB
    private com.asecon.facade.ServicioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ServicioController() {
    }

    public Servicio getSelected() {
        if (current == null) {
            current = new Servicio();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ServicioFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
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
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public void prepareView() {
        current = (Servicio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();

    }

    public void prepareCreate() {
        current = new Servicio();
        selectedItemIndex = -1;

    }

    public void calcularCostoServicio(ActionEvent event) {

        Periodo actual = (Periodo) event.getComponent().getAttributes().get("periodo");
        CostoServicioFacade faca = (CostoServicioFacade) event.getComponent().getAttributes().get("costo");
        ActividadServicioFacade facde = (ActividadServicioFacade) event.getComponent().getAttributes().get("act_serv");
        CostoActividadFacade facde2 = (CostoActividadFacade) event.getComponent().getAttributes().get("costo_act");
        List<Servicio> servicios = getFacade().findAll();

        try {
            for (Servicio x : servicios) {

                Object[] parametros = new Object[4];
                parametros[0] = "idServicio";
                parametros[1] = x.getIdServicio();
                parametros[2] = "numeroPeriodo";
                parametros[3] = actual.getNumeroPeriodo();

                List<ActividadServicio> activ_realizada = facde.find("ActividadServicio.findByCostoServ", parametros);
                Float costo_total = new Float(0);
                Integer i = 0;
                for (ActividadServicio y : activ_realizada) {

                    Object[] parametros2 = new Object[4];
                    parametros2[0] = "idActividad";
                    parametros2[1] = y.getActividad().getIdActividad();
                    parametros2[2] = "numeroPeriodo";
                    parametros2[3] = actual.getNumeroPeriodo();
                    CostoActividad costo_actividad = facde2.getSingleResult("CostoActividad.findByCostoServ", parametros2);
                    costo_total = costo_total + costo_actividad.getCostoActividad() * y.getNumeroRepeticionesActividad().floatValue();
                    i = i + 1;
                }

                CostoServicio costo_servicio = new CostoServicio(x.getIdServicio(), actual.getNumeroPeriodo());
                costo_servicio.setCostoTotalServicio(costo_total);
                costo_servicio.setNumeroRepeticionesServicio(i);
                costo_servicio.CalcularCosto();

                faca.create(costo_servicio);


            }
            JsfUtil.addSuccessMessage("Costos de Servicio Generados");

        } catch (Exception e) {
        }



    }

    public void create() {
        try {
            getFacade().create(current);
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ServicioCreated"));

        } catch (Exception e) {
            recreateModel();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));

        }
    }

    public void prepareEdit() {
        current = (Servicio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();

    }

    public void update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ServicioUpdated"));

        } catch (Exception e) {
            recreateModel();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));

        }
    }

    public void destroy() {
        current = (Servicio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();

    }

    public void destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
        } else {
            // all items were removed - go back to list
            recreateModel();

        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ServicioDeleted"));
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
        if (items == null) {
            items = getPagination().createPageDataModel();
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

    public List<Servicio> autocomplete(String query) {
        List<Servicio> sugerencias = new ArrayList<Servicio>();
        for (Servicio x : ejbFacade.findAll()) {
            if (x.getNombreServicio().contains(query.toUpperCase())) {
                sugerencias.add(x);
            }
        }
        return sugerencias;
    }

    public List<TipoServicio> tipoAutocomplete(String query) {
        List<TipoServicio> sugerencias = new ArrayList<TipoServicio>();
        for (TipoServicio x : servicio.getTipoServicioList()) {
            if (x.getNombreTipoServicio().contains(query.toUpperCase())) {
                sugerencias.add(x);
            }
        }
        return sugerencias;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public TipoServicio getTipo() {
        return tipo;
    }

    public void setTipo(TipoServicio tipo) {
        this.tipo = tipo;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SelectItem[] getItemsSelect() {
        return JsfUtil.getSelectItems(ejbFacade.findAll());
    }

    @FacesConverter(forClass = Servicio.class)
    public static class ServicioControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ServicioController controller = (ServicioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "servicioController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Servicio) {
                Servicio o = (Servicio) object;
                return getStringKey(o.getIdServicio());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ServicioController.class.getName());
            }
        }
    }
}
