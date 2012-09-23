package com.asecon.controller;

import com.asecon.entity.ServicioCliente;
import com.asecon.controller.util.JsfUtil;
import com.asecon.controller.util.PaginationHelper;
import com.asecon.entity.Periodo;
import com.asecon.entity.Servicio;
import com.asecon.entity.ServicioClientePK;
import com.asecon.entity.TipoServicio;
import com.asecon.facade.ServicioClienteFacade;

import java.io.Serializable;
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

@ManagedBean(name = "servicioClienteController")
@SessionScoped
public class ServicioClienteController implements Serializable {

    private ServicioCliente current;
    private DataModel items = null;
    private Servicio servicio;
    private TipoServicio tipo;
    private Periodo periodo;
    private ServicioCliente servicioCliente = new ServicioCliente();
    private boolean isFiltering;
    private boolean servicioNoNulo;
    private boolean tipoNoNulo;
    @EJB
    private com.asecon.facade.ServicioClienteFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ServicioClienteController() {
    }

    public ServicioCliente getSelected() {
        if (current == null) {
            current = new ServicioCliente();
            current.setServicioClientePK(new com.asecon.entity.ServicioClientePK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private ServicioClienteFacade getFacade() {
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
        current = (ServicioCliente) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    }

    public String prepareCreate() {
        current = new ServicioCliente();
        current.setServicioClientePK(new com.asecon.entity.ServicioClientePK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ServicioClienteCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (ServicioCliente) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ServicioClienteUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (ServicioCliente) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ServicioClienteDeleted"));
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

    public void next() throws Exception {
        getPagination().nextPage();
        recreateModel();

    }

    public void previous() throws Exception {
        getPagination().previousPage();
        recreateModel();

    }

    public void mostrarServicio(ActionEvent event) {
        servicio = (Servicio) event.getComponent().getAttributes().get("servicio");
        setServicioNoNulo(true);
        
    }

    public void mostrarTipoServicio(ActionEvent event) {
        tipo = (TipoServicio) event.getComponent().getAttributes().get("tiposervicio");
        for (TipoServicio x : servicio.getTipoServicioList()) {

            if (x.getIdTipoServicio().equals(tipo.getIdTipoServicio())) {
                recreateModel();
                setTipoNoNulo(true);
            }
        }
        if (!isTipoNoNulo()) {
            tipo = null;
        }
    }

    public void reset() {
        recreateModel();
        setServicio(null);
        setTipo(null);
        setServicioNoNulo(false);
        setTipoNoNulo(false);
        setPeriodo(null);
        setServicioCliente(new ServicioCliente());
    }
    
    public DataModel getItemsLibro() {
        if (items == null) {
            items = new ListDataModel(getFacade().find(getNamedQuery(), getParameters()));
        }
        return items;
    }
    
   public String getNamedQuery() {
        if (periodo == null) {
            if (tipo == null) {
                return "ServicioCliente.findByServ";
            } else {
                return "ServicioCliente.findByTipo";
            }
        }else{
            if (tipo == null) {
                return "ServicioCliente.findByPerServ";
            } else {
                return "ServicioCliente.findByPerTipo";
            }
        }

    }
   
   public void guardarServicio(ActionEvent event){
   
       Periodo actual = (Periodo) event.getComponent().getAttributes().get("periodo");
       servicioCliente.setServicioClientePK(new ServicioClientePK(servicioCliente.getTipoServicio().getIdTipoServicio(), servicioCliente.getCliente().getNumeroCliente(), actual.getNumeroPeriodo()));

        try {
            getFacade().create(servicioCliente);
            servicioCliente = new ServicioCliente();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ServicioClienteCreated"));
            
        } catch (Exception e) {
            reset();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));

        }
       
   }

    public Object[] getParameters() {
        if (periodo == null) {
            if (tipo == null) {
                Object[] parametros = new Object[2];
                parametros[0] = "servicio";
                parametros[1] = getServicio();
                return parametros;
            } else {
                Object[] parametros = new Object[2];
                parametros[0] = "tipoServicio";
                parametros[1] = getTipo();
                return parametros;
            }
        } else {
            if (tipo == null) {
                Object[] parametros = new Object[4];
                parametros[0] = "servicio";
                parametros[1] = getServicio();
                parametros[2] = "periodo";
                parametros[3] = getPeriodo().getNumeroPeriodo();
                return parametros;
            } else {
                Object[] parametros = new Object[4];
                parametros[0] = "tipoServicio";
                parametros[1] = getTipo();
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

    public ServicioCliente getServicioCliente() {
        return servicioCliente;
    }

    public void setServicioCliente(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }


    public boolean isIsFiltering() {
        return isFiltering;
    }

    public void setIsFiltering(boolean isFiltering) {
        this.isFiltering = isFiltering;
    }

    public boolean isServicioNoNulo() {
        return servicioNoNulo;
    }

    public void setServicioNoNulo(boolean servicioNoNulo) {
        this.servicioNoNulo = servicioNoNulo;
    }

    public boolean isTipoNoNulo() {
        return tipoNoNulo;
    }

    public void setTipoNoNulo(boolean tipoNoNulo) {
        this.tipoNoNulo = tipoNoNulo;
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

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }
    
    public boolean isPeriodoNoNulo(){
        return (periodo != null);
    }

    @FacesConverter(forClass = ServicioCliente.class)
    public static class ServicioClienteControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ServicioClienteController controller = (ServicioClienteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "servicioClienteController");
            return controller.ejbFacade.find(getKey(value));
        }

        com.asecon.entity.ServicioClientePK getKey(String value) {
            com.asecon.entity.ServicioClientePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.asecon.entity.ServicioClientePK();
            key.setIdTipoServicio(values[0]);
            key.setNumeroCliente(Long.parseLong(values[1]));
            key.setNumeroPeriodo(Long.parseLong(values[2]));
            return key;
        }

        String getStringKey(com.asecon.entity.ServicioClientePK value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value.getIdTipoServicio());
            sb.append(SEPARATOR);
            sb.append(value.getNumeroCliente());
            sb.append(SEPARATOR);
            sb.append(value.getNumeroPeriodo());
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ServicioCliente) {
                ServicioCliente o = (ServicioCliente) object;
                return getStringKey(o.getServicioClientePK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ServicioClienteController.class.getName());
            }
        }
    }
}
