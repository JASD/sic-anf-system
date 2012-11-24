package com.asecon.controller.util;

import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

public class JsfUtil {
    
    public static final double COEFICIENTE_A_VENTAS = 4039.2966071429;
    public static final double COEFICIENTE_B_VENTAS = -16198399.5923216;
    public static final double COEFICIENTE_C_VENTAS = 16239845208.7302;
    public static final double COEFICIENTE_A_COSTOS = 2499.87267857141;
    public static final double COEFICIENTE_B_COSTOS = -10025382.0609642;
    public static final double COEFICIENTE_C_COSTOS = 10051451160.4182;
    public static final double COEFICIENTE_A_GASTOS = 3288.67946428571;
    public static final double COEFICIENTE_B_GASTOS = -13203524.7331785;
    public static final double COEFICIENTE_C_GASTOS = 13252519737.8008;

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static SelectItem[] getSelectItems(List<?> entities) {
        int size = entities.size();
        SelectItem[] items;
        int i = 0;
        if (size == 0) {
            items = new SelectItem[size + 1];
            items[0] = new SelectItem("", "---");
        } else {
            items = new SelectItem[size];
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage("ERROR", facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("Información", facesMsg);
    }
    
    public static void addWarningMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg);
        FacesContext.getCurrentInstance().addMessage("Advertencia", facesMsg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    public static void redirect(String url) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().redirect(url);
    }
}