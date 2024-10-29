package it.csi.sigas.sigasbl.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.sigas.sigasbl.request.home.RicercaConsumiRequest;
import it.csi.sigas.sigasbl.request.home.SearchSubjectPaymentFoRequest;
import it.csi.sigas.sigasbl.request.home.StorePaymentCartRequest;

@Path("/fopayment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IPaymentFoApi {

    @GET
    @Path("/searchYearsForFoUser")
    public Response searchYearsForFoUser();
    
    @GET
    @Path("/retrievePaidYears")
    public Response retrievePaidYears();    
    
	@GET
	@Path("/getUserAmountsForPrevYear")
	public Response getUserAmountsForPrevYear(@QueryParam("idAnag") String idAnag, @QueryParam("year") String year);
    
	@GET
	@Path("/getAllPiemonteCounties")
	public Response getAllPiemonteCounties();
    
    @POST
    @Path("/retrieveSubjectsForFoUser")
    public Response retrieveSubjectsForFoUser(RicercaConsumiRequest ricercaConsumiRequest);

    @POST
    @Path("/retrieveSubjectPaymentFo")
    public Response retrieveSubjectPaymentFo(SearchSubjectPaymentFoRequest ricercaSoggettiUtente);
    
    @POST
    @Path("/retrieveFoSubjectsForSearch")
    public Response retrieveFoSubjectsForSearch(RicercaConsumiRequest ricercaSoggettiUtente);
    
    @POST
    @Path("/retrieveFoPaymentSubjectsForSearch")
    public Response retrieveFoPaymentSubjectsForSearch(SearchSubjectPaymentFoRequest subject);

    @GET
    @Path("/retrieveFoPaymentSubjectDetail")
	public Response retrieveFoPaymentSubjectDetail(@QueryParam("idAnag") String idAnag, @QueryParam("year") String year);
    
    @GET
    @Path("/retrievePaymentTypes")
    public Response retrievePaymentTypes();

    @GET
    @Path("/retrievePaymentMethods")
    public Response retrievePaymentMethods();

    @POST
    @Path("/storePaymentInfoToAllCartItems")
    public Response savePaymentInfoToCartItems(StorePaymentCartRequest paymentInfo/*, List<StorePaymentCartRequest> cartItems*/);

    @POST
    @Path("/storePaymentCart")
    public Response storePaymentCart(StorePaymentCartRequest storePaymentCartRequest);
    
    @POST
    @Path("/insert-payment-cart")
    public Response insertPaymentCart(StorePaymentCartRequest storePaymentCartRequest);
    
    @POST
    @Path("/deletePaymentCartItem")
    public Response deletePaymentCartItem(StorePaymentCartRequest paymentInfo);
    
    @POST
    @Path("/startCartPayment")
	public Response startCartPayment(StorePaymentCartRequest storePaymentCartRequest);

    @POST
    @Path("/searchCartItems")
	public Response searchCartItems(SearchSubjectPaymentFoRequest subject);
    
    @POST
    @Path("/searchPaidCartItems")
    public Response searchPaidCartItems(SearchSubjectPaymentFoRequest request);

    @POST
    @Path("/getPaymentPagoPaRedirectInfo")
	public Response getPaymentPagoPaRedirectInfo(StorePaymentCartRequest storePaymentCartRequest);
	
    @GET
    @Path("/getPaymentCartRT")
	public Response getPaymentCartRT(@QueryParam("iuv") String iuv);
    
    @POST
    @Path("/savePaymentCart")
	public Response savePaymentCart(StorePaymentCartRequest storePaymentCartRequest);
    
    @POST
    @Path("avviso-pagamento/{iuv}/download")
    public Response downloadAvvisoPagamento(@PathParam("iuv") String iuv);
    
    @POST
    @Path("avviso-pagamento/")
    public Response generaAvvisoPagamento(StorePaymentCartRequest storePaymentCartRequest);
    
    @POST
    @Path("ricevuta-pagamento/{iuv}/download")
    public Response downloadRicevutaPagamento(@PathParam("iuv") String iuv);
    
    @GET
    @Path("ricevuta-pagamento/{iuv}")
    public Response previewRicevutaPagamento(@PathParam("iuv") String iuv);
    
    @POST
    @Path("/pago-pa-redirect-info")
	public Response getPaymentPagoPaRedirectUrl(StorePaymentCartRequest storePaymentCartRequest);
    
}
