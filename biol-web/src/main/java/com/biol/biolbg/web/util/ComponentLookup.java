package com.biol.biolbg.web.util;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("ComponentLookup")
@RequestScoped
public class ComponentLookup implements Serializable {

	private static final long serialVersionUID = 1L;

    private Map<String, Map<String, UIComponent>> componentMap;

    private Map<String, Map<String, UIComponent>> getPageMaps() {
        if (componentMap == null) {
            componentMap = new HashMap<String, Map<String, UIComponent>>();
        }
        return componentMap;
    }

    private Map<String, UIComponent> getViewMap(String viewId) {
        Map<String, Map<String, UIComponent>> pageMaps = getPageMaps();
        Map<String, UIComponent> viewMap = pageMaps.get(viewId);
        if (viewMap == null) {
            viewMap = new HashMap<String, UIComponent>();
            pageMaps.put(viewId, viewMap);
        }
        return viewMap;
    }

    /** Returns map for components */
    public Map<String, UIComponent> getComponents() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot view = context.getViewRoot();
        String viewId = view.getViewId();
        return getViewMap(viewId);
    }

    private Map<String, String> clientIdMap;

    /** Returns map for getting clientIds */
    public Map<String, String> getClientIds() {
        if (clientIdMap == null) {
            clientIdMap = new AbstractMap<String, String>() {
                @Override
                public String get(Object key) {
                    return getClientId(key.toString());
                }
                @Override
                public Set<java.util.Map.Entry<String, String>> entrySet() {
                    throw new UnsupportedOperationException();
                }
            };
        }
        return clientIdMap;
    }

    private String getClientId(String id) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, UIComponent> components = getComponents();
        if (components == null) {
        	return "";
        }
        UIComponent component = components.get(id);
        if (component == null) {
        	return "";
        }
        return component.getClientId(context);
    }

}
