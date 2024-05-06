/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vcai.restclient;

import com.avbravo.jmoordbutils.DocumentUtil;
import com.jmoordb.core.model.Search;
import com.jmoordb.core.util.MessagesUtil;
import com.vcai.model.Analisis;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author avbravo
 */
@RegisterRestClient()
@Path("/analisis")
@ClientHeaderParam(name = "Authorization", value = "{lookupAuth}")
public interface AnalisisRestClient {
    // <editor-fold defaultstate="collapsed" desc="lookupAuth()">

    default String lookupAuth() {
        /**
         * *
         * Leer las configuraciones del archivo microprofile-config.properties
         */

        String secretKey = "SCox1jmWrkma$*opne2Amwz";

        Config config = ConfigProvider.getConfig();

        String userSecurity = config.getValue("userSecurity", String.class);

        // or
        ConfigValue passwordSecurity = config.getConfigValue("passwordSecurity");

        //        String userDecrypted = Encryptor.decrypt(userSecurity, secretKey,FacesUtil.nameOfClassAndMethod());ç
//String passwordDecrypted = Encryptor.decrypt(passwordSecurity.getValue(), secretKey,FacesUtil.nameOfClassAndMethod());
        String userDecrypted = userSecurity;
        String passwordDecrypted = passwordSecurity.getValue();
        return "Basic "
                + Base64.getEncoder().encodeToString((userDecrypted + ":" + passwordDecrypted).getBytes());
    }
// </editor-fold>
    
    
     // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
        @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
       public List<Analisis> findAll();
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Analisis findByIdanalisis">
    @GET
    @Path("{idanalisis}")
   
    public Analisis findByIdanalisis(
            @Parameter(description = "El idanalisis", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idanalisis") Long idanalisis) {

        return analisisRepository.findByPk(idanalisis).orElseThrow(
                () -> new WebApplicationException("No hay analisis con idanalisis " + idanalisis, Response.Status.NOT_FOUND));

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Analisis findByAnalisisname">

    @GET
    @RolesAllowed({"admin"})
    @Path("nhc")
    @Operation(summary = "Busca un analisis por el analisisnam", description = "Busqueda de analisis por idanalisis")
    @APIResponse(responseCode = "200", description = "El analisis")
    @APIResponse(responseCode = "404", description = "Cuando no existe el analisis")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El analisis", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Analisis.class)))

    public Analisis findByNhc(@Parameter(description = "El analisis", required = true, example = "1", schema = @Schema(type = SchemaType.STRING)) @QueryParam("analisis") final String analisis) {

        return analisisRepository.findByNhc(analisis).orElseThrow(
                () -> new WebApplicationException("No hay analisis con analisis " + analisis, Response.Status.NOT_FOUND));

    }
//// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="List<Analisis> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">
    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un analisis", description = "Busqueda de analisis por search")
    @APIResponse(responseCode = "200", description = "Analisis")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Analisis.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<Analisis> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<Analisis> suggestions = new ArrayList<>();
        try {

            Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
            suggestions = analisisRepository.lookup(search);

        } catch (Exception e) {

            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo analisis", description = "Inserta un nuevo analisis")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  analisis")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo analisis.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Analisis.class))) Analisis analisis) {

        Optional<Analisis> analisisOptional = analisisRepository.save(analisis);
        if (analisisOptional.isPresent()) {
//            saveHistory(analisis);
            return Response.status(201).entity(analisisOptional.get()).build();
        } else {
            return Response.status(400).entity("Error " + analisisRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo analisis", description = "Inserta un nuevo analisis")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  analisis")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo analisis.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Analisis.class))) Analisis analisis) {

        if (analisisRepository.update(analisis)) {
//            saveHistory(analisis);
            return Response.status(201).entity(analisis).build();
        } else {
            return Response.status(400).entity("Error " + analisisRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
    @RolesAllowed({"admin"})
    @Path("{idanalisis}")
    @Operation(summary = "Elimina un analisis por  idanalisis", description = "Elimina un analisis por su idanalisis")
    @APIResponse(responseCode = "200", description = "Cuando elimina el analisis")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento idanalisis", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idanalisis") Long idanalisis) {
        if (analisisRepository.deleteByPk(idanalisis) == 0L) {
            return Response.status(201).entity(Boolean.TRUE).build();
        } else {
            return Response.status(400).entity("Error " + analisisRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">
    @GET
    @Path("count")
    @RolesAllowed({"admin"})
    @Operation(summary = "Cuenta ", description = "Cuenta analisis")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Analisis.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        Long result = 0L;
        try {

            Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
            result = analisisRepository.count(search);

        } catch (Exception e) {

            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
//    // <editor-fold defaultstate="collapsed" desc="private void saveHistory(Analisis analisis)">
//    private void saveHistory(Analisis analisis) {
//        try {
//            History history = new History.Builder()
//                    .collection("analisis")
//                    .idcollection(analisis.getId().toString())
//                    .database("accreditation")
//                    .data(analisis.toString())
//                    .actionHistory(analisis.getActionHistory().get(analisis.getActionHistory().size() - 1))
//                    .build();
//            historyRepository.save(history);
//        } catch (Exception e) {
//            ConsoleUtil.error("saveHistory() " + e.getLocalizedMessage());
//        }
//    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" List<AnalisisView> likeByName(@QueryParam("name") String name)">
    @GET
    @Path("likebyname")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un analisis", description = "Busqueda de analisis usando like%")
    @APIResponse(responseCode = "200", description = "AnalisisView")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Analisis.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<Analisis> likeByName(@QueryParam("analisis") String analisis) {
        List<Analisis> suggestions = new ArrayList<>();
        try {

            suggestions = analisisRepository.likeByAnalisis(analisis);

        } catch (Exception e) {

            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    // </editor-fold>
}
