package ml.hro.moneymakers.client;




import org.glassfish.jersey.jackson.JacksonFeature;

import ml.hro.moneymakers.api.BalanceResponse;
import ml.hro.moneymakers.api.Credentials;
import ml.hro.moneymakers.api.WithdrawRequest;
import ml.hro.moneymakers.api.WithdrawResponse;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;


/**
 * Created by elvira on 24-03-17.
 */
public class MyClient
{
    private Client client;
    private static WebTarget target;


    public MyClient(int port)
    {
        client  = ClientBuilder.newClient().register(JacksonFeature.class);
        target  = client.target("http://145.24.222.224:" + port);
    }

    public static void main(String[] args)
    {	MyClient client = new MyClient(8025);
    	client.test();
    //client.set();
    }
    
    public void set(){
    	 
    	 //client.test();
    	 Credentials a = new Credentials();
    	 a.setPincode(1234);
    	
    	this.setCredentials(a, 486801160, "MLMOGE0002");
    }
    
    public void test(){
    	long pasnummer = 96359712692L;
    	int pincode = 1234;
    	int balans = -100;
    	String Pinclient = "Client-001";
    	try {
    		TimeUnit.SECONDS.sleep(1);
       
        
        System.out.println("Geef u pas: ");
        Credentials temp = this.checkCard(pasnummer);
        if(temp.isBlocked()){
        	System.out.println("Pas is geblokkeerd");
        	System.exit(0);
        }
        else{
        	System.out.println("Pas is niet geblokkeerd");
        }
        
        if(temp.isState()){
        	System.out.println("Pas is bekend in Database");
        }
        
        else{
        	System.out.println("Pas is niet bekend in Database");
        	System.exit(0);
        }
        System.out.println("Geef u pincode: ");
        
        temp.setPincode(pincode);
        
        System.out.println("Pincode wordt gecontroleerd");
			TimeUnit.SECONDS.sleep(1);
			 
        if(this.checkCredentials(temp, pasnummer).isState()){
        	 System.out.println("Pincode is geaccepteerd");
        }
        else{
        	 System.out.println("Foutieve pincode");
        	System.exit(0);
        }
        
			TimeUnit.SECONDS.sleep(1);
			System.out.println("Uw huidige saldo");
			 System.out.println(this.balance(pasnummer).getBalance());
			 System.out.println("Er wordt " + balans  + " euro van uw Rekening afgehaald");
			 
			 WithdrawRequest request = new WithdrawRequest();
			 request.setAmount(balans);
			 request.setATM(Pinclient);
			 TimeUnit.SECONDS.sleep(1);
			 
			 WithdrawResponse response = this.withdraw(request, pasnummer);
			 
			 if(response.isNoSaldo()){
				 System.out.println("Saldo ontoereikend");
				 System.exit(0);
			 }
			 else{
				 System.out.println("U heeft " + balans + " euro gepind");
			 }
			 
			 
			 TimeUnit.SECONDS.sleep(1);
			 System.out.println("Uw huidige saldo");
			 System.out.println(this.balance(pasnummer).getBalance());
    	} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public Credentials checkCredentials(Credentials credit, long pasnummer){
    	
    	Credentials a = target
                .path("/card/" + pasnummer + "/validate")
                .request(MediaType.APPLICATION_JSON)
                
                .post(Entity.entity(credit, MediaType.APPLICATION_JSON), Credentials.class);
                ;
    	return a;
    	
    }
    
public boolean setCredentials(Credentials credit, long pasnummer, String Rekeningnr){
    	
    	Boolean a = target
                .path("/card/" + pasnummer +  "/" +  Rekeningnr)
                .request(MediaType.APPLICATION_JSON)
                
                .post(Entity.entity(credit, MediaType.APPLICATION_JSON), boolean.class);
                ;
    	return a;
    	
    }
    
    public Credentials checkCard(long pasnummer){
    	 Credentials response = target
                 .path("/card/" + pasnummer  )
                 .request(MediaType.APPLICATION_JSON)
                 .get(Credentials.class);	

         return response;
    }
    
    public BalanceResponse balance(long pasnummer)
    {
        BalanceResponse response = target
                .path("/card/"+ pasnummer + "/balance/")
                .request(MediaType.APPLICATION_JSON)
                .get(BalanceResponse.class);

        return response;
    }
    
    public Credentials getToken(Credentials temp){
    	
    	
    	Credentials token = target
                .path("/token")
                .request(MediaType.APPLICATION_JSON).post(Entity.entity(temp, MediaType.APPLICATION_JSON), Credentials.class);
    	return token;
    }

    public WithdrawResponse withdraw(WithdrawRequest request, long pasnummer)
    {
        WithdrawResponse response = target
                .path("/card/"+ pasnummer + "/balance/")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON), WithdrawResponse.class);

        return response;
    }
}