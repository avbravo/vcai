/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vcai.restclient;

import com.avbravo.jmoordbutils.DocumentUtil;
import com.jmoordb.core.model.Search;
import com.jmoordb.core.util.MessagesUtil;
import com.vcai.model.Resultadocultivo;
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
@Path("/resultadocultivo")
@ClientHeaderParam(name = "Authorization", value = "{lookupAuth}")
public interface ResultadocultivoRestClient {
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
       public List<Resultadocultivo> findAll();
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Resultadocultivo findByIdresultadocultivo">
    @GET
    @Path("{idresultadocultivo}")
   
    public Resultadocultivo findByIdresultadocultivo(
            @Parameter(description = "El idresultadocultivo", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idresultadocultivo") Long idresultadocultivo) ;

     
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Resultadocultivo findByResultadocultivo">

    @GET
   @Path("resultadocultivo")
    public Resultadocultivo findByResultadocultivo(@Parameter(description = "El resultadocultivo", required = true, example = "1", schema = @Schema(type = SchemaType.STRING)) @QueryParam("resultadocultivo") final String resultadocultivo);
//// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="List<Resultadocultivo> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">
    @GET
    @Path("lookup")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<Resultadocultivo> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size);
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
     public Response save(
            @RequestBody(description = "Crea un nuevo resultadocultivo.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resultadocultivo.class))) Resultadocultivo resultadocultivo) ;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
 
    public Response update(
            @RequestBody(description = "Crea un nuevo resultadocultivo.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resultadocultivo.class))) Resultadocultivo resultadocultivo) ;
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
 
    @Path("{idresultadocultivo}")
 
    public Response delete(
            @Parameter(description = "El elemento idresultadocultivo", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idresultadocultivo") Long idresultadocultivo) ;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">
    @GET
    @Path("count")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) ;

    // </editor-fold>

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" List<ResultadocultivoView> likeByName(@QueryParam("name") String name)">
    @GET
    @Path("likebyname")

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<Resultadocultivo> likeByName(@QueryParam("resultadocultivo") String resultadocultivo) ;

    // </editor-fold>
}
