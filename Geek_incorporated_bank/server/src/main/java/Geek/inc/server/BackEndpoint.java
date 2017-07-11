package Geek.inc.server;

import Geek.inc.api.BalanceResponse;
import Geek.inc.api.Credentials;
import Geek.inc.api.WithdrawRequest;
import Geek.inc.api.WithdrawResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.security.Key;





@Path("/")
public class BackEndpoint {
private static final Logger logger = LoggerFactory.getLogger(BackEndpoint.class);

@POST		/// was POST
@Path("card/{rekeningNr}/validate" )
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON )
public Credentials checkCredentials(@PathParam("rekeningNr") long rekeningNr, Credentials credit)
{
    
	
    Database db = Server.getDatabase();
   
    credit.setState(db.checkCredentials(credit, rekeningNr));
    return credit;	
    	
}

@POST		// was POST
@Path("card/{pasnr}/{rekeningNr}/" )
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON )
public boolean setCredentials(@PathParam("rekeningNr") String rekeningNr, @PathParam("pasnr") long pasnr, Credentials credit)
{
   

    Database db = Server.getDatabase();
   
    
    return db.makeCredentials(credit,  pasnr, rekeningNr);	
}
    
@GET
@Path("card/{rekeningNr}/")
@Produces(MediaType.APPLICATION_JSON )
public Credentials checkCard(@PathParam("rekeningNr") long rekeningNr){
	
	Database db = Server.getDatabase();
	
		Credentials temp = new Credentials();
		temp = db.checkCard(rekeningNr);
	return temp;
}


	@POST		// POST
	@Path("token")
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

	@POST	//POST
    @Path("checktoken")
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
	@Path("card/{rekeningNr}/balance/")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public BalanceResponse getSaldo(@PathParam("rekeningNr") long rekeningNr){
		
		Database db = Server.getDatabase();
		logger.trace("In the BankEndpoint::getSaldo()");
		BalanceResponse temp = new BalanceResponse();
		temp.setBalance(db.getBalance(String.valueOf(rekeningNr)));
		
		return temp;
	}
	
	
	
	@POST
    @Path("card/{rekeningNr}/balance/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WithdrawResponse withdraw(@PathParam("rekeningNr")  String rekeningNr, WithdrawRequest request)
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
