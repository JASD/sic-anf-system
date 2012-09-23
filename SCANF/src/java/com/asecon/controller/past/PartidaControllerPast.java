package com.asecon.controller.past;

import com.asecon.entity.Partida;
import com.asecon.controller.util.PaginationHelper;
import com.asecon.entity.Periodo;
import com.asecon.entity.Transaccion;
import com.asecon.facade.PartidaFacade;

import com.asecon.filter.PartidaFilter;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

@ManagedBean(name = "partidaControllerPast")
@SessionScoped
public class PartidaControllerPast implements Serializable {

    private Partida current;
    private PartidaFilter filtro;
    private Periodo periodo;
    private DataModel items = null;
    private Float debe;
    private Float haber;
    private String cuurentQuery;
    private boolean isFiltering;
    @EJB
    private com.asecon.facade.PartidaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public PartidaControllerPast() {
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
    /*
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
    }*/
    public PaginationHelper getFilterPagination() throws Exception {
        if (pagination == null && isFiltering) {
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

    public void prepareView() {
        current = (Partida) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        debe = new Float(0);
        haber = new Float(0);

        for (Transaccion x : current.getTransaccionList()) {
            debe = debe + x.getDebe();
            haber = haber + x.getHaber();
        }
    }

    public void prepareFind() {
        filtro = new PartidaFilter();
    }

    public void find() {
        recreateModel();
        pagination = null;
        setIsFiltering(true);
        filtro.setPeriodo(periodo);
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
        setPeriodo(null);
        //Anular SQL

    }

    public DataModel getItems() {
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
        try {
            getFilterPagination().nextPage();
        } catch (Exception ex) {
            //no deberia
        }
        recreateModel();
    }

    public void previous() {
        try {
            getFilterPagination().previousPage();
        } catch (Exception ex) {
            //no deberia
        }
        recreateModel();
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

    public PartidaFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(PartidaFilter filtro) {
        this.filtro = filtro;
    }

    public boolean isIsFiltering() {
        return isFiltering;
    }

    public void setIsFiltering(boolean isFiltering) {
        this.isFiltering = isFiltering;
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
}
