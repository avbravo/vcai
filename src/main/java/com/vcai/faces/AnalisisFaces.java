/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vcai.faces;

import com.avbravo.jmoordbutils.FacesUtil;
import com.avbravo.jmoordbutils.JmoordbCoreXHTMLUtil;
import com.avbravo.jmoordbutils.paginator.IPaginator;
import com.avbravo.jmoordbutils.paginator.Paginator;
import com.jmoordb.core.model.Pagination;
import com.jmoordb.core.model.Sorted;
import static com.mongodb.client.model.Filters.ne;
import com.vcai.faces.services.AnalisisFacesServices;
import com.vcai.faces.services.FacesServices;
import com.vcai.model.Analisis;
import com.vcai.services.AnalisisServices;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Provider;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CloseEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author avbravo
 */
@Named
@ViewScoped
public class AnalisisFaces implements Serializable, JmoordbCoreXHTMLUtil, IPaginator, AnalisisFacesServices , FacesServices{

    private static final long serialVersionUID = 1L;

    // <editor-fold defaultstate="collapsed" desc="LazyDataModel">
    private LazyDataModel<Analisis> analisisLazyDataModel;
      private DataTable dataTable;
    Integer totalRecords = 0;
    private Boolean isRowPageSmall = Boolean.TRUE;
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="paginator ">
    Paginator paginator = new Paginator();
    Paginator paginatorOld = new Paginator();

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Microprofile Config">
    @Inject
    private Config config;

    // Row
    @Inject
    @ConfigProperty(name = "rowPage", defaultValue = "0")
    private Provider<Integer> rowPage;
    @Inject
    @ConfigProperty(name = "rowPageSmall", defaultValue = "0")
    private Provider<Integer> rowPageSmall;

    @Inject
    @ConfigProperty(name = "rowPageDialog", defaultValue = "0")
    private Provider<Integer> rowPageDialog;

    @Inject
    @ConfigProperty(name = "pathBaseLinuxAddUserHomeStoreInCollections", defaultValue = "false")
    private Provider<Boolean> pathBaseLinuxAddUserHomeStoreInCollections;

    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Services">
    @Inject
    AnalisisServices analisisServices;
// </editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="methods()">
        private Analisis analisisSelected = new Analisis();
// </editor-fold>
    
   // <editor-fold defaultstate="collapsed" desc="selected For Dialog()">
    public Analisis getAnalisisSelected() {
        if (analisisSelected == null) {
            analisisSelected = new Analisis();
        }
        return analisisSelected;
    }

   

    // </editor-fold>
    
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

            findAll();

            this.analisisLazyDataModel = new LazyDataModel<Analisis>() {
                @Override
                public List<Analisis> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

                    switch (paginator.getName()) {
                        case "findAll":

                            totalRecords = analisisServices.count(paginator.getFilter(),
                                    paginator.getSort(), 0, 0).intValue();
                            break;

                    }

                    List<Paginator> list = new ArrayList<>();
                    if (!isRowPageSmall) {

                        /**
                         * Utiliza rowPage
                         */
                        list = processLazyDataModel(paginator, paginatorOld, offset, rowPage.get(), totalRecords, sortBy);

                    } else {

                        /**
                         * Utiliza rowPageWithOverlayPanel para el OverlayPanel
                         */
                        list = processLazyDataModel(paginator, paginatorOld, offset, rowPageSmall.get(), totalRecords, sortBy);

                    }
//

                    paginator = list.get(0);
                    paginatorOld = list.get(1);
                    Pagination pagination = new Pagination();
                    if (!isRowPageSmall) {

                        paginator.setNumberOfPage(numberOfPages(totalRecords, rowPage.get()));
                        pagination = new Pagination(paginator.getPage(), rowPage.get());
                    } else {

                        paginator.setNumberOfPage(numberOfPages(totalRecords, rowPageSmall.get()));
                        pagination = new Pagination(paginator.getPage(), rowPageSmall.get());
                    }

                    List<Analisis> result = new ArrayList<>();
                    switch ((paginator.getName())) {
                        case "findAll":

                            result = analisisServices.lookup(paginator.getFilter(),
                                    paginator.getSort(), paginator.getPage(), rowPageSmall.get());

                            break;
                        default:

                    }

                    analisisLazyDataModel.setRowCount(totalRecords);

                    PrimeFaces.current().executeScript("setDataTableWithPageStart()");
                    PrimeFaces.current().executeScript("widgetVardataTable.getPaginator().setPage(0);");
                    analisisList = result;

                    return result;
                }

                @Override
                public int count(Map<String, FilterMeta> map) {

                    return totalRecords;

                }

                @Override
                public String getRowKey(Analisis object) {
                    if (object == null || object.getIdanalisis() == null) {
                        return "";
                    }
                    return object.getIdanalisis().toString();
                }

                @Override
                public Analisis getRowData(String rowKey) {
                    for (Analisis t : analisisList) {
                        if (t != null) {
                            if (t.getIdanalisis().equals(rowKey)) {
                                return t;
                            }
                        }
                    }
                    return null;
                }
            };
    
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }

    // <editor-fold defaultstate="collapsed" desc="String findAll()">
    public String findAll() {
        try {

            Bson filter = ne("nhc", "");

            Document sort = new Document("idanalisis", 1);

            paginator
                    = new Paginator.Builder()
                            .page(1)
                            .filter(filter)
                            .sort(sort)
                            .sorted(new Sorted(new Document("idanalisis", -1)))
                            .title("Todos")
                            .name("findAll")
                            .build();

            /**
             * Limpiar los elementos
             */
            setFirstPageDataTable();
        } catch (Exception e) {
            // FacesUtil.errorMessage(FacesUtil.nameOfMethod() + "() : " + e.getLocalizedMessage());
        }

        return "";
    }
// </editor-fold>

    
    // <editor-fold defaultstate="collapsed" desc="setFirstPageDataTable()">
    public void setFirstPageDataTable() {

        if (dataTable != null) {
            dataTable.setFirst(0);
        }
    }
    // </editor-fold>
    
    @Override
    public void preDestroy() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String refresh() {
       try {

            analisisList = new ArrayList<>();
            findAll();
            PrimeFaces.current().ajax().update("form");
            PrimeFaces.current().ajax().update("dataTable");

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }

    @Override
    public void handleCloseDialogRefresh(CloseEvent event) {
        refresh();
    }
}
