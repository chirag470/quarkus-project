package com.fulfilment.application.monolith.stores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;
import org.jboss.logging.Logger;

@Path("store")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class StoreResource {

  @Inject LegacyStoreManagerGateway legacyStoreManagerGateway;

  @Inject StoreService storeService;

  private static final Logger LOGGER = Logger.getLogger(StoreResource.class.getName());

  @GET
  public List<Store> get() {
    return Store.listAll(Sort.by("name"));
  }

  @GET
  @Path("{id}")
  public Store getSingle(Long id) {
    Store entity = Store.findById(id);
    if (entity == null) {
      throw new WebApplicationException("Store with id of " + id + " does not exist.", 404);
    }
    return entity;
  }

  @POST
  public Response create(Store store) {

    storeService.create(store);
    legacyStoreManagerGateway.createStoreOnLegacySystem(store);

    return Response.ok(store).status(201).build();
  }

  @PUT
  @Path("{id}")
  public Store update(Long id, Store updatedStore) {

    Store savedStore = storeService.update(id, updatedStore);
    legacyStoreManagerGateway.updateStoreOnLegacySystem(savedStore);

    return savedStore;
  }

  @PATCH
  @Path("{id}")
  public Store patch(Long id, Store updatedStore) {

    Store savedStore = storeService.patch(id, updatedStore);
    legacyStoreManagerGateway.updateStoreOnLegacySystem(savedStore);

    return savedStore;
  }

  @DELETE
  @Path("{id}")
  public Response delete(Long id) {
    storeService.delete(id);
    return Response.status(204).build();
  }


}
