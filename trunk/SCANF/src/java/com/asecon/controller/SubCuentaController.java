package com.asecon.controller;

import com.asecon.entity.SubCuenta;
import com.asecon.controller.util.JsfUtil;
import com.asecon.controller.util.PaginationHelper;
import com.asecon.facade.SubCuentaFacade;

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
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "subCuentaController")
@SessionScoped
public class SubCuentaController implements Serializable {

    private SubCuenta current;
    private DataModel items = null;
    private SubCuenta subCuenta;
    @EJB
    private com.asecon.facade.SubCuentaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public SubCuentaController() {
    }

    public SubCuenta getSelected() {
        if (current == null) {
            current = new SubCuenta();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SubCuentaFacade getFacade() {
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

    public void prepareList() {
        recreateModel();

    }

    public void prepareView() {
        current = (SubCuenta) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();

    }

    public void prepareCreate() {
        current = new SubCuenta();
        selectedItemIndex = -1;

    }

    public void create() {
        try {
            getFacade().create(current);
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SubCuentaCreated"));

        } catch (Exception e) {
            recreateModel();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));

        }
    }

    public void prepareEdit() {
        current = (SubCuenta) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();

    }

    public void update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SubCuentaUpdated"));

        } catch (Exception e) {
            recreateModel();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));

        }
    }

    public void destroy() {
        current = (SubCuenta) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SubCuentaDeleted"));
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

    public List<SubCuenta> autocomplete(String query) {
        List<SubCuenta> sugerencias = new ArrayList<SubCuenta>();
        for (SubCuenta x : ejbFacade.findAll()) {
            if (x.getNombreSubcuenta().startsWith(query.toUpperCase())
                    || x.getCodigoSubcuenta().startsWith(query)) {
                sugerencias.add(x);
            }
        }
        return sugerencias;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SubCuenta getSubCuenta() {
        return subCuenta;
    }

    public void setSubCuenta(SubCuenta subCuenta) {
        this.subCuenta = subCuenta;
    }

    public SubCuentaFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(SubCuentaFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }
    
    

    @FacesConverter(forClass = SubCuenta.class)
    public static class SubCuentaControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SubCuentaController controller = (SubCuentaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "subCuentaController");
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
            if (object instanceof SubCuenta) {
                SubCuenta o = (SubCuenta) object;
                return getStringKey(o.getCodigoSubcuenta());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SubCuentaController.class.getName());
            }
        }
    }
}
