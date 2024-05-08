/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vcai.faces;

import com.avbravo.jmoordbutils.FacesUtil;
import com.vcai.model.Analisis;
import com.vcai.restclient.AnalisisRestClient;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author avbravo
 */
@Named
@ViewScoped
public class AnalisisFaces implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    AnalisisRestClient analisisRestClient;

    List<Analisis> analisisList = new ArrayList<>();

    public List<Analisis> getAnalisisList() {
        return analisisList;
    }

    public void setAnalisisList(List<Analisis> analisisList) {
        this.analisisList = analisisList;
    }

    @PostConstruct
    public void init() {
        try {
            analisisList = analisisRestClient.findAll();
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }
}
