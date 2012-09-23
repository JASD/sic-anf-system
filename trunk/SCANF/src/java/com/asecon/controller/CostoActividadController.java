package com.asecon.controller;

import com.asecon.entity.CostoActividad;
import com.asecon.controller.util.JsfUtil;
import com.asecon.controller.util.PaginationHelper;
import com.asecon.facade.CostoActividadFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "costoActividadController")
@SessionScoped
public class CostoActividadController implements Serializable {

    private CostoActividad current;
    private DataModel items = null;
    @EJB
    private com.asecon.facade.CostoActividadFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public CostoActividadController() {
    }

    public CostoActividad getSelected() {
        if (current == null) {
            current = new CostoActividad();
            current.setCostoActividadPK(new com.asecon.entity.CostoActividadPK());
            selectedItemIndex = -1;
        }
        return current;
    }
    
     public DataModel getAllCurrent() {
        return (new ListDataModel(ejbFacade.findNoParameters("CostoActividad.findAllCurrent")));
    }

    public CostoActividadFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(CostoActividadFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }
    
    

    private CostoActividadFacade getFacade() {
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

    public String prepareView() {
        current = (CostoActividad) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new CostoActividad();
        current.setCostoActividadPK(new com.asecon.entity.CostoActividadPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CostoActividadCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (CostoActividad) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CostoActividadUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (CostoActividad) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CostoActividadDeleted"));
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

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = CostoActividad.class)
    public static class CostoActividadControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CostoActividadController controller = (CostoActividadController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "costoActividadController");
            return controller.ejbFacade.find(getKey(value));
        }

        com.asecon.entity.CostoActividadPK getKey(String value) {
            com.asecon.entity.CostoActividadPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.asecon.entity.CostoActividadPK();
            key.setNumeroPeriodo(Long.parseLong(values[0]));
            key.setIdActividad(values[1]);
            return key;
        }

        String getStringKey(com.asecon.entity.CostoActividadPK value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value.getNumeroPeriodo());
            sb.append(SEPARATOR);
            sb.append(value.getIdActividad());
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof CostoActividad) {
                CostoActividad o = (CostoActividad) object;
                return getStringKey(o.getCostoActividadPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + CostoActividadController.class.getName());
            }
        }
    }
}
