package Geek_connect.Geek_inc.server;

import Geek_connect.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by thomas on 4-4-2017.
 */

@Path("/")
public class BankEndPoint
{
    public static final Logger logger = LoggerFactory.getLogger(BankEndPoint.class);
    /*
     * Mogelijk Http methodes
     * GET      - get something from the server
     * POST     - create something new on the server
     * PUT      - update something on the server
     * DELETE   - remover something from the server
     */

    @GET
    @Path("/balance/{rekeningNr}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
    public BalanceResponse getSaldo(@PathParam("rekeningNr") String rekeningNummer)
    {
        Database db = Server.getDatabse();
        logger.trace("In the BankEndpoint::getSaldo()");
        BalanceResponse response = new BalanceResponse();
        response.setBalance(db.getBalance(rekeningNummer));
        response.setRekeningNummer(rekeningNummer);
        return response;
    }

    @POST
    @Path("/withdraw")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
    public WithdrawResponse withdraw(WithdrawRequest request)
    {
        logger.trace("In de BankEndpoint::withdraw");

        WithdrawResponse response = new WithdrawResponse();

        Database db = Server.getDatabse();
        if (db.withdraw(request.getIBAN(), request.getAmount()))
        {
            response.setResponse("Dank u voor pinnen.");
            response.setNewSaldo(db.getBalance(request.getIBAN()));
            response.setTransactionNumber(12345); //zelf waardes voor invullen!!!!! nu FOUT
            logger.info("Pinnen is gelukt, niuwe saldo is: " + response.getNewSaldo() + "euro.");
            return response;
        }
        else
        {
            response.setResponse("Saldo ontoereikend.");
            response.setNewSaldo(db.getBalance(request.getIBAN()));
            logger.info("Pinnen is niet gelukt. " + response.getResponse());
            throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(response).build());
        }
    }
}