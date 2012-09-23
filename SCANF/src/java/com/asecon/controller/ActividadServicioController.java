package com.asecon.controller;

import com.asecon.entity.ActividadServicio;
import com.asecon.controller.util.JsfUtil;
import com.asecon.controller.util.PaginationHelper;
import com.asecon.entity.Actividad;
import com.asecon.entity.ActividadServicioPK;
import com.asecon.entity.CostoActividad;
import com.asecon.entity.CostoActividadPK;
import com.asecon.entity.Periodo;
import com.asecon.entity.Servicio;
import com.asecon.facade.ActividadFacade;
import com.asecon.facade.ActividadServicioFacade;

import com.asecon.facade.CostoActividadFacade;
import java.io.Serializable;
import java.math.BigDecimal;
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

@ManagedBean(name = "actividadServicioController")
@SessionScoped
public class ActividadServicioController implements Serializable {

    private ActividadServicio current;
    private ActividadServicio actividadServicio = new ActividadServicio();
    private DataModel items = null;
    private Periodo periodo;
    private Servicio servicio;
    private Actividad actividad;
    private boolean servicioNoNulo;
    private boolean ActividadNoNula;
    private CostoActividad costos = new CostoActividad();
    private List<CostoActividad> costos_con;
    
    @EJB
    private com.asecon.facade.ActividadServicioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ActividadServicioController() {
        costos_con = new ArrayList<CostoActividad>();
    }

    public ActividadServicioFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(ActividadServicioFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }
    
    

    public ActividadServicio getSelected() {
        if (current == null) {
            current = new ActividadServicio();
            current.setActividadServicioPK(new com.asecon.entity.ActividadServicioPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private ActividadServicioFacade getFacade() {
        return ejbFacade;
    }
    
    public void agregarInductorRecurso(){
        costos_con.add(costos);
        costos = new CostoActividad();
    
        
    }
    
    public void reset2(){
        costos = new CostoActividad();
        costos_con = new ArrayList<CostoActividad>();
    }

    public List<CostoActividad> getCostos_con() {
        return costos_con;
    }

    public void setCostos_con(List<CostoActividad> costos_con) {
        this.costos_con = costos_con;
    }
    
    

    public CostoActividad getCostos() {
        return costos;
    }

    public void setCostos(CostoActividad costos) {
        this.costos = costos;
    }
    
    
    
    public void calcularCostoActividad(ActionEvent event){
        
        Periodo actual = (Periodo) event.getComponent().getAttributes().get("periodo");
        CostoActividadFacade costo = (CostoActividadFacade) event.getComponent().getAttributes().get("costos");
       
        try{
        for(CostoActividad x: costos_con){
            
            Object[] parametros = new Object[4];
            parametros[0] = "idActividad";
            parametros[1] = x.getActividad().getIdActividad();
            parametros[2] = "numeroPeriodo";
            parametros[3] = actual.getNumeroPeriodo();
            
            List<ActividadServicio> activ_realizada = getFacade().find("ActividadServicio.findBySumIduc", parametros);
            BigDecimal total_inductor = new BigDecimal(0);
            
            for(ActividadServicio y: activ_realizada){
                
             total_inductor = total_inductor.add(y.getValorInductorActividad());
                
            }
            
            x.setTotalInductorActividad(total_inductor);
            
            x.calcularCis(actual.getTotalCisPeriodo());
            x.calcularCosto();
            x.setCostoActividadPK(new CostoActividadPK(actual.getNumeroPeriodo(), x.getActividad().getIdActividad()));
            costo.create(x);
            
        } JsfUtil.addSuccessMessage("Costos de Actividad Generados");
        }catch(Exception e){
        
            
        }
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

    public String prepareView() {
        current = (ActividadServicio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new ActividadServicio();
        current.setActividadServicioPK(new com.asecon.entity.ActividadServicioPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ActividadServicioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (ActividadServicio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ActividadServicioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (ActividadServicio) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ActividadServicioDeleted"));
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

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public void mostrarServicio(ActionEvent event) {
        servicio = (Servicio) event.getComponent().getAttributes().get("servicio");
        setServicioNoNulo(true);

    }

    public void mostrarActividad(ActionEvent event) {
        actividad = (Actividad) event.getComponent().getAttributes().get("actividad");
        recreateModel();
        setActividadNoNula(true);
    }

    public void reset() {
        recreateModel();
        setServicio(null);
        setActividad(null);
        setServicioNoNulo(false);
        setActividadNoNula(false);
        setPeriodo(null);
        actividadServicio = new ActividadServicio();
    }

    public void guardarActividad(ActionEvent event) {

        Periodo actual = (Periodo) event.getComponent().getAttributes().get("periodo");
        actividadServicio.setActividadServicioPK(new ActividadServicioPK(actual.getNumeroPeriodo(), actividadServicio.getActividad().getIdActividad(), actividadServicio.getServicio().getIdServicio()));

        try {
            getFacade().create(actividadServicio);
            actividadServicio = new ActividadServicio();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ServicioClienteCreated"));

        } catch (Exception e) {
            reset();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));

        }

    }

    public DataModel getItemsLibro() {
        if (items == null) {
            items = new ListDataModel(getFacade().find(getNamedQuery(), getParameters()));
        }
        return items;
    }

    public String getNamedQuery() {
        if (periodo == null) {
            if (actividad == null) {
                return "ActividadServicio.findByServ";
            } else {
                return "ActividadServicio.findByAct";
            }
        } else {
            if (actividad == null) {
                return "ActividadServicio.findByPerServ";
            } else {
                return "ActividadServicio.findByPerAct";
            }
        }

    }

    public Object[] getParameters() {
        if (periodo == null) {
            if (actividad == null) {
                Object[] parametros = new Object[2];
                parametros[0] = "servicio";
                parametros[1] = getServicio();
                return parametros;
            } else {
                Object[] parametros = new Object[2];
                parametros[0] = "actividad";
                parametros[1] = getActividad();
                return parametros;
            }
        } else {
            if (actividad == null) {
                Object[] parametros = new Object[4];
                parametros[0] = "servicio";
                parametros[1] = getServicio();
                parametros[2] = "periodo";
                parametros[3] = getPeriodo().getNumeroPeriodo();
                return parametros;
            } else {
                Object[] parametros = new Object[4];
                parametros[0] = "actividad";
                parametros[1] = getActividad();
                parametros[2] = "periodo";
                parametros[3] = getPeriodo().getNumeroPeriodo();
                return parametros;
            }

        }
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public boolean isActividadNoNula() {
        return ActividadNoNula;
    }

    public void setActividadNoNula(boolean ActividadNoNula) {
        this.ActividadNoNula = ActividadNoNula;
    }

    public boolean isServicioNoNulo() {
        return servicioNoNulo;
    }

    public void setServicioNoNulo(boolean servicioNoNulo) {
        this.servicioNoNulo = servicioNoNulo;
    }

    public boolean isPeriodoNoNulo() {
        return (periodo != null);
    }

    public ActividadServicio getActividadServicio() {
        return actividadServicio;
    }

    public void setActividadServicio(ActividadServicio actividadServicio) {
        this.actividadServicio = actividadServicio;
    }

    @FacesConverter(forClass = ActividadServicio.class)
    public static class ActividadServicioControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ActividadServicioController controller = (ActividadServicioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "actividadServicioController");
            return controller.ejbFacade.find(getKey(value));
        }

        com.asecon.entity.ActividadServicioPK getKey(String value) {
            com.asecon.entity.ActividadServicioPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.asecon.entity.ActividadServicioPK();
            key.setNumeroPeriodo(Long.parseLong(values[0]));
            key.setIdActividad(values[1]);
            key.setIdServicio(values[2]);
            return key;
        }

        String getStringKey(com.asecon.entity.ActividadServicioPK value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value.getNumeroPeriodo());
            sb.append(SEPARATOR);
            sb.append(value.getIdActividad());
            sb.append(SEPARATOR);
            sb.append(value.getIdServicio());
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ActividadServicio) {
                ActividadServicio o = (ActividadServicio) object;
                return getStringKey(o.getActividadServicioPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ActividadServicioController.class.getName());
            }
        }
    }
}
