package com.asecon.controller;

import com.asecon.entity.CostoServicio;
import com.asecon.controller.util.JsfUtil;
import com.asecon.controller.util.PaginationHelper;
import com.asecon.facade.CostoServicioFacade;

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

@ManagedBean(name = "costoServicioController")
@SessionScoped
public class CostoServicioController implements Serializable {

    private CostoServicio current;
    private DataModel items = null;
    @EJB
    private com.asecon.facade.CostoServicioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public CostoServicioController() {
    }

    public CostoServicioFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(CostoServicioFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }
    
    

    public CostoServicio getSelected() {
        if (current == null) {
            current = new CostoServicio();
            current.setCostoServicioPK(new com.asecon.entity.CostoServicioPK());
            selectedItemIndex = -1;
        }
        return current;
    }
    
    public DataModel getAllCurrent() {
        return (new ListDataModel(ejbFacade.findNoParameters("CostoServicio.findAllCurrent")));
    }

    private CostoServicioFacade getFacade() {
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
        current = (CostoServicio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new CostoServicio();
        current.setCostoServicioPK(new com.asecon.entity.CostoServicioPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CostoServicioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (CostoServicio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CostoServicioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (CostoServicio) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CostoServicioDeleted"));
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

    @FacesConverter(forClass = CostoServicio.class)
    public static class CostoServicioControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CostoServicioController controller = (CostoServicioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "costoServicioController");
            return controller.ejbFacade.find(getKey(value));
        }

        com.asecon.entity.CostoServicioPK getKey(String value) {
            com.asecon.entity.CostoServicioPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.asecon.entity.CostoServicioPK();
            key.setIdServicio(values[0]);
            key.setNumeroPeriodo(Long.parseLong(values[1]));
            return key;
        }

        String getStringKey(com.asecon.entity.CostoServicioPK value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value.getIdServicio());
            sb.append(SEPARATOR);
            sb.append(value.getNumeroPeriodo());
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof CostoServicio) {
                CostoServicio o = (CostoServicio) object;
                return getStringKey(o.getCostoServicioPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + CostoServicioController.class.getName());
            }
        }
    }
}
