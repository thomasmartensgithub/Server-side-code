package ml.hro.moneymakers.server;

import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import ml.hro.moneymakers.api.BalanceResponse;
import ml.hro.moneymakers.api.Credentials;
import ml.hro.moneymakers.api.WithdrawRequest;
import ml.hro.moneymakers.api.WithdrawResponse;

import java.security.Key;

//import ml.hro.moneymakers.api.*;



@Path("/")
public class BackEndpoint {
private static final Logger logger = LoggerFactory.getLogger(BackEndpoint.class);

@POST
@Path("/card/{rekeningNr}/validate" )
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON )
public Credentials checkCredentials(@PathParam("rekeningNr") long rekeningNr, Credentials credit)
{
    
	
    Database db = Server.getDatabase();
   
    credit.setState(db.checkCredentials(credit, rekeningNr));
    return credit;	
    	
}

@POST
@Path("/card/{pasnr}/{rekeningNr}/" )
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON )
public boolean setCredentials(@PathParam("rekeningNr") String rekeningNr, @PathParam("pasnr") long pasnr, Credentials credit)
{
   

    Database db = Server.getDatabase();
   
    
    return db.makeCredentials(credit,  pasnr, rekeningNr);	
}
    
@GET
@Path("/card/{rekeningNr}/")
@Produces(MediaType.APPLICATION_JSON )
public Credentials checkCard(@PathParam("rekeningNr") long rekeningNr){
	
	Database db = Server.getDatabase();
	
		Credentials temp = new Credentials();
		temp = db.checkCard(rekeningNr);
	return temp;
}


	@POST
	@Path("/token")
	@Produces(MediaType.APPLICATION_JSON)
	public Credentials getToken(Credentials credentials){
	logger.trace("In the get::getSaldo()");
	

	 
	Key key = MacProvider.generateKey();
	 
	String jwt = 
	    Jwts.builder()
	        .setSubject(credentials.getClient() )
	        .signWith(SignatureAlgorithm.HS256,key)
	        .compact();
	
	credentials.setToken(jwt);
	return credentials;
}

	@POST
    @Path("/checktoken")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean checktoken(Credentials credentials)
    {	Key key = MacProvider.generateKey();
        logger.trace("In de BankEndpoint::checktoken");
        if(Jwts.parser().setSigningKey(key).parseClaimsJws(credentials.getToken()).getBody().getSubject().equals(credentials.getClient()))
        {
        	return true;
        }
        else return false;
    }


	@GET
	@Path("/card/{rekeningNr}/balance/")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public BalanceResponse getSaldo(@PathParam("rekeningNr") long rekeningNr){
		
		Database db = Server.getDatabase();
		logger.trace("In the BankEndpoint::getSaldo()");
		BalanceResponse temp = new BalanceResponse();
		temp.setBalance(db.getBalance(rekeningNr));
		
		return temp;
	}
	
	
	
	@POST
    @Path("/card/{rekeningNr}/balance/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
    public WithdrawResponse withdraw(@PathParam("rekeningNr")  long rekeningNr, WithdrawRequest request)
    {
        logger.trace("In de BankEndpoint::withdraw");

        
        Database db = Server.getDatabase();
        if (db.withdraw(rekeningNr, request.getAmount()))
        {	
        	
        	WithdrawResponse temp = new WithdrawResponse();
        	
        	temp.setNoSaldo(false);
        	temp.setTransactionNr(db.getTransacties() +1);
        	temp.setATM(request.getATM());
        	temp.setAmount(request.getAmount());
        	
        	db.setTransacties(temp, rekeningNr);
            return temp;
        }
        else
        {
        	WithdrawResponse temp = new WithdrawResponse();
        
        	temp.setNoSaldo(true);
        	temp.setTransactionNr(db.getTransacties() + 1);
        	temp.setATM(request.getATM());
        	temp.setAmount(request.getAmount());
            
        	db.setTransacties(temp, rekeningNr);
           return temp;
           }
    }


}
