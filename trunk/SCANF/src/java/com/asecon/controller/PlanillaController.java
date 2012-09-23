package com.asecon.controller;

import com.asecon.entity.Planilla;
import com.asecon.controller.util.JsfUtil;
import com.asecon.controller.util.PaginationHelper;
import com.asecon.entity.Periodo;
import com.asecon.facade.PlanillaFacade;

import java.io.Serializable;
import java.math.BigDecimal;
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

@ManagedBean(name = "planillaController")
@SessionScoped
public class PlanillaController implements Serializable {

    private Planilla current;
    private DataModel items = null;
    private Periodo periodo;
    private Float salarioBase;
    private BigDecimal numeroHorasExtra;
    private Float horasExtra;
    private Float bonificaciones;
    private Float afp;
    private Float isss;
    private Float salarioNeto;
    @EJB
    private com.asecon.facade.PlanillaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public PlanillaController() {
    }

    public Planilla getSelected() {
        if (current == null) {
            current = new Planilla();
            current.setPlanillaPK(new com.asecon.entity.PlanillaPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    public PlanillaFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(PlanillaFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    
    private PlanillaFacade getFacade() {
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
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, "Planilla.findAllCurrent"));
                }
            };
        }
        return pagination;
    }

    public DataModel itemsLibro() { 
        if (periodo == null) {
            List<Planilla> planilla = getFacade().findNoParameters("Planilla.findAllCurrent");
            setAfp(new Float(0));
            setHorasExtra(new Float(0));
            setIsss(new Float(0));
            setNumeroHorasExtra(new BigDecimal(0));
            setSalarioBase(new Float(0));
            setSalarioNeto(new Float(0));
            setBonificaciones(new Float(0));
          
            
            for(Planilla x: planilla){
                afp = afp + x.getAfp();
                horasExtra = horasExtra + x.getHorasExtra();
                numeroHorasExtra = numeroHorasExtra.add(x.getNumeroHorasExtra());
                bonificaciones = bonificaciones + x.getBonificaciones();
                isss = isss + x.getIsss();
                salarioBase = salarioBase + x.getSalarioBase();
                salarioNeto = salarioNeto + x.getSalarioNeto();
            }
            return (new ListDataModel(planilla));
        } else {
            Object[] parameters = new Object[2];
            parameters[0] = "numeroPeriodo";
            parameters[1] = getPeriodo().getNumeroPeriodo();
            
            List<Planilla> planilla = getFacade().find("Planilla.findByNumeroPeriodo", parameters);
            setAfp(new Float(0));
            setHorasExtra(new Float(0));
            setIsss(new Float(0));
            setNumeroHorasExtra(new BigDecimal(0));
            setSalarioBase(new Float(0));
            setSalarioNeto(new Float(0));
            
            for(Planilla x: planilla){
                afp = afp + x.getAfp();
                horasExtra = horasExtra + x.getHorasExtra();
                numeroHorasExtra = numeroHorasExtra.add(x.getNumeroHorasExtra());
                isss = isss + x.getIsss();
                salarioBase = salarioBase + x.getSalarioBase();
                salarioNeto = salarioNeto + x.getSalarioNeto();
            }
            return (new ListDataModel(planilla));
        }
    }
    
    public void mostrar(){
        
    }

    public void reset() {
        setPeriodo(null);
    }

    public boolean isPeriodoNoNulo() {
        return (periodo != null);
    }

    public void prepareList() {
        recreateModel();
       
    }

    public void prepareView() {
        current = (Planilla) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
       
    }

    public void prepareCreate() {
        current = new Planilla();
        current.setPlanillaPK(new com.asecon.entity.PlanillaPK());
        selectedItemIndex = -1;
       
    }

    public void create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PlanillaCreated"));
           
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            
        }
    }

    public void prepareEdit() {
        current = (Planilla) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        
    }

    public void update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PlanillaUpdated"));
            
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            
        }
    }

    public void  destroy() {
        current = (Planilla) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PlanillaDeleted"));
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

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Float getAfp() {
        return afp;
    }

    public void setAfp(Float afp) {
        this.afp = afp;
    }

    public Float getHorasExtra() {
        return horasExtra;
    }

    public void setHorasExtra(Float horasExtra) {
        this.horasExtra = horasExtra;
    }

    public Float getIsss() {
        return isss;
    }

    public void setIsss(Float isss) {
        this.isss = isss;
    }

    public BigDecimal getNumeroHorasExtra() {
        return numeroHorasExtra;
    }

    public void setNumeroHorasExtra(BigDecimal numeroHorasExtra) {
        this.numeroHorasExtra = numeroHorasExtra;
    }

    public Float getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(Float salarioBase) {
        this.salarioBase = salarioBase;
    }

    public Float getSalarioNeto() {
        return salarioNeto;
    }

    public void setSalarioNeto(Float salarioNeto) {
        this.salarioNeto = salarioNeto;
    }

    public Float getBonificaciones() {
        return bonificaciones;
    }

    public void setBonificaciones(Float bonificaciones) {
        this.bonificaciones = bonificaciones;
    }

    @FacesConverter(forClass = Planilla.class)
    public static class PlanillaControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PlanillaController controller = (PlanillaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "planillaController");
            return controller.ejbFacade.find(getKey(value));
        }

        com.asecon.entity.PlanillaPK getKey(String value) {
            com.asecon.entity.PlanillaPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.asecon.entity.PlanillaPK();
            key.setNumeroPeriodo(Long.parseLong(values[0]));
            key.setIdEmpleado(values[1]);
            return key;
        }

        String getStringKey(com.asecon.entity.PlanillaPK value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value.getNumeroPeriodo());
            sb.append(SEPARATOR);
            sb.append(value.getIdEmpleado());
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Planilla) {
                Planilla o = (Planilla) object;
                return getStringKey(o.getPlanillaPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + PlanillaController.class.getName());
            }
        }
    }
}
