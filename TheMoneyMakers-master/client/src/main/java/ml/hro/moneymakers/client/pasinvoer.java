package ml.hro.moneymakers.client;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import ml.hro.moneymakers.api.Credentials;
import ml.hro.moneymakers.api.WithdrawRequest;
import ml.hro.moneymakers.api.WithdrawResponse;

import java.util.Enumeration;
import java.util.concurrent.TimeUnit;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPasswordField;

import javax.swing.JTextField;
import javax.swing.Timer;

public class pasinvoer implements SerialPortEventListener {
	MyClient client;
	String stars;
	gui gui;
	Credentials temp;
    SerialPort serialPort;
    String pinclient;
    int bedrag;
    long pasnummer = 0l;
    String inputLine;
    
    static JButton Agreed, End;
    static JLabel CompanyName;
    static JLabel Center;
    static JTextField passwordField;
    static JPasswordField password;
    Timer timer;
    boolean foutepin = false;
    
    
    /**
     * The port we're normally going to use.
     */
    private static final String PORT_NAMES[] = {
        
        "COM4", // Windows
    };
    /**
     * A BufferedReader which will be fed by a InputStreamReader converting the
     * bytes into characters making the displayed results codepage independent
     */
    private BufferedReader input;
    /**
     * The output stream to the port
     */
    
    /**
     * Milliseconds to block while waiting for port open
     */
    private static final int TIME_OUT = 2000;
    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 9600;
   
    private String oldpassword;
    
    private int screen;
    private String thePassword;
    
    
    public pasinvoer(){
        client = new MyClient(8025);
        gui = new gui();
        inputLine = "";
        bedrag =0;
        pinclient = "Client-001";
        thePassword = "";
       
    }

    public void initialize() {

        CommPortIdentifier portId = null;
        Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
          

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * This should be called when you stop using the port. This will prevent
     * port locking on platforms like Linux.
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    @Override
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
            	timer.restart();
                inputLine = input.readLine();
                System.out.println(inputLine);
                
                if(inputLine.length() < 2){
                	
                	if(screen ==4 && Character.isDigit(inputLine.charAt(0)) && thePassword.length() < 5){
                		thePassword = (thePassword + inputLine);
                    	switchCase();
                	}
                	else if(Character.isDigit(inputLine.charAt(0)) && screen == 50){
                    		checkPincode();
                    		System.out.println("check pincode");
                    	}
                    	else{
                    		checkButton();
                    		System.out.println("check button");
                    	}
                    }
                	  
                else{
                	if (screen == 100) {
                
                		checkPas();
                		System.out.println("check pas");
                	}
                
                }
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }
    
    public void checkButton() throws Exception{
    	switch (inputLine) {

        case "Y": 
               
             
               if (thePassword.length() == 4 && screen == 50) {
                   
                   temp = new Credentials();
                   temp.setPincode(Integer.parseInt(thePassword));
                   
                   
                  
                   System.out.println("Pincode wordt gecontroleerd");
       				TimeUnit.SECONDS.sleep(1);
       			 
               if(client.checkCredentials(temp, pasnummer).isState()){
               	 System.out.println("Pincode is geaccepteerd");
               	 screen = 1;
                   switchCase();
               	
               }
               else{
               	 System.out.println("Foutieve pincode");
               	 //Foutieve pincode scherm, dan moet pas weer gecontroleerd worden of er een pincode mag worden ingevoerd. Daarna weer opnieuw naar pincode scherm.
               	 thePassword = "";
               	 foutepin = true;
               	 switchCase();
               }
               
               
               
               }
               else if(screen == 3){
            	   bedrag = 70;
            	   screen = 6;
               }
               else  if (screen == 4) {
                   /////////////////pin een x bedrag de waarden moet nog ingevoerd worden//////////////////////////////////////////////////////////
                   
                  bedrag = Integer.parseInt(thePassword);
                  if(bedrag % 5 == 0 && bedrag <= 1000 && bedrag > 0 ){
                  screen = 5;
                  
                  
                  }else{
                	  thePassword = "";
                	  
                  }
                  
                  switchCase();
                   ///////////////ALEX: hier moet een pin bedrag uitkomen//////////////////////////////////////////////////////////////
               }
              
               else if (screen == 7) {
                   //geldt word gepinned, druk op y om naar einde screen te gaan ///////////////////////////////////////////////////////////////
                   
                   
                   
                 
               }
               else  if (screen == 2) {
                   //saldo scherm, druk op y om terug tegaan naar het menu//////////////////////////////////////////////////////////////////////
                   screen = 1;
                   switchCase();
               }
               
               break;

           
        
        case "A": 
 
               if (screen == 1) {
                   ///////////// ga naar pin x bedrag scherm/////////////////////////////////////////////////////////////////////////////////

                   screen = 4;
                   switchCase();
               }
               else if (screen == 6) {
                   ////////////// ik wil een bon, ga naar print money scherm//////////////////////////////////////////////////////////////////////
                   
                   System.out.println("Er wordt " + bedrag + " euro van uw Rekening afgehaald");
        			 
     			 	WithdrawRequest request = new WithdrawRequest();
     			 request.setAmount(bedrag);
     			 request.setATM(pinclient);
     			 TimeUnit.SECONDS.sleep(1);
     			 
     			 WithdrawResponse response = client.withdraw(request, pasnummer);
     			 
     			 if(response.isNoSaldo()){
     				 System.out.println("Saldo ontoereikend");
     				 System.exit(0);
     				 screen = 11;
     				switchCase();
     			 }
     			 else{
     				 System.out.println("U heeft " + bedrag + " euro gepind");
     				 
     				screen = 8;
     				switchCase();
     			 }
     			 
     			PrinterDymo400 Printer = new PrinterDymo400();
     			
     	        Printer.print("Maaslandje", pasnummer + "",bedrag);
     			 
     			 
     	       switchCase();
                   
               }
               else if (screen == 5) {
                   //////////////ik weet het zeker, ga naar wilt u een bon scherm///////////////////////////////////////////////////////////////
            	   screen = 6;
            	   switchCase();
            	   
                  
      			 
                   
               }
               else if (screen == 3) {
                   //////////////ik wil 20 euro, ga naar weet u het zeker scherm//////////////////////////////////////////////////////////////////////
                   screen = 5;
                   bedrag = 20;
                   switchCase();
                   
               }
               
               break;

        case "B": 
               
               if (screen == 3) {
                   //////////////ik wil 50 euro, ga naar weet u het zeker scherm//////////////////////////////////////////////////////////////////////
                   screen = 5;
                   bedrag = 50;
                   switchCase();
                   
               }else if (screen == 1) {
                   //////////////ik wil een vast bedrag pinnen , ga naar het weet u zeker sherm////////////////////////////////////////////////////////
                   screen = 3;
                   switchCase();
               }
               else if (screen == 5) {
                   //////////////weet u het zeker scherm, nee ik weet het niet zeker terug naar main scherm//////////////////////////////////////////
                   screen = 1;
                   switchCase();
               }
               
               else if(screen == 6){
            	   System.out.println("Er wordt " + bedrag + " euro van uw Rekening afgehaald");
        			 
     			 	WithdrawRequest request = new WithdrawRequest();
     			 request.setAmount(bedrag);
     			 request.setATM(pinclient);
     			 TimeUnit.SECONDS.sleep(1);
     			 
     			 WithdrawResponse response = client.withdraw(request, pasnummer);
     			 
     			 if(response.isNoSaldo()){
     				 System.out.println("Saldo ontoereikend");
     				 
     				 screen = 11;
     			 }
     			 else{
     				 System.out.println("U heeft " + bedrag + " euro gepind");
     				 screen = 8;
     				
     			 }
     			 
     			 
     			 
                  switchCase();
               }
               

               break;
        case "C": 
               if (screen == 3) {
                   //////////////ik wil 100 euro pinnen, ga naar de weet u zeker scherm //////////////////////////////////////////////////////////////
                   screen = 5;
                   bedrag = 100;
                   switchCase();
                       
               }
               
               else if (screen == 1) {
                   ////////////// ik wil saldo zien, ga naar saldo scherm////////////////////////////////////////////////////////////////////////////////
                   screen = 2;
                   System.out.println("saldoscherm");
                   switchCase();
                   //ALEX: saldo moet in dit scherm komen
               }
               
               
               break;
        case "N": 
        	
        		if(screen <6 || screen == 50 ){
        			reset();
        		}
               // code om te anuleren ////////////////////////////////////////////////////////////////////////////////////////////////////////
               
                   //////////////ga terug naar show pas scherm//////////////////////////////////////////////////////////////////////////////////////////////////
                   
                   
                   
               
               break;
        case "D": 
               if (screen == 50 || screen == 4) {
                   //////////////remove het wachtwoord ald je op delete clicked//////////////////////////////////////////////////////////////////////
                   thePassword = "";
                   switchCase();
               }
               else if (screen == 3) {
                   
                   screen = 5;
                   bedrag = 250;
                   switchCase();    
                   
                   
               }

          
        
        
    	break;
    	}
    	
    }
    
    public void reset(){
    
    	screen = 100;
    	timer.stop();
    	thePassword = "";
        bedrag = 0;
        pasnummer = 0l;
        temp = null;
        switchCase();
    }
    
    public void checkPas(){
    	
            System.out.print("i saw a card");
            pasnummer = Long.parseLong(inputLine, 10);
            
            temp = client.checkCard(pasnummer);
            
            if(temp.isState()){
            	if(temp.isBlocked()){
            		
            		System.out.println("Pas is geblokkeerd");
                	screen = 10;
                	switchCase();
            	}
            	else{
            		System.out.println("Pas is bekend in Database");
                    thePassword = "";
                    screen = 50;
                    
                    switchCase();
            	}
            }
            
            else{
            	  System.out.println( "Pas bestaat niet" );
            	  screen = 12;
              	switchCase();
            	
            }
         
           
           
    	
    	
    	
            
    }
    
    public void startTimer(){
    	ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reset();

                System.out.println("Reading SMTP Info.");
            }
        };
        timer = new Timer(30000 ,taskPerformer);
        timer.setRepeats(false);
        
        

       
    }
    
    public void checkPincode(){
    	
                oldpassword = thePassword;

                if (thePassword.length() < 4 && (screen == 50 || screen == 4)) {
                	
                    thePassword = (oldpassword + inputLine);
                    
                   switchCase();
                  //  Gui_scan.passwordField.setText(thePassword);
                }

    }

    public void switchCase() {
    	System.out.println("screen" + screen);
        switch (screen) {
        
        
        
        case 100:
        	

        	gui.update("Plaats U pinpas op de scanner", "", false, "", false, "", false, "", false, "");
        	gui.accepteerOff(false);
            gui.endOff(false);
        	break;
        	
        case 50:  
        		
        		
        		
        		if(!thePassword.equals("")){
        			stars = thePassword.replaceAll(".", "*");
                	foutepin = false;
            		gui.update( "Uw pincode: " + stars, "D: Correctie",  false, "", false, "", false, "", false, "");
            		if(thePassword.length() != 4){
            			gui.accepteerOff(false);
            		}
        		}
            	
        		else
            	{	if(foutepin){
            		gui.update("U pincode is onjuist", "Voer U pincode opnieuw in", false, "", false, "", false, "", false, "");
            		gui.accepteerOff(false);
        		}
            	else{
            		gui.update("Voer U pincode in", "", false, "", false, "", false, "", false, "");
            	}
					
            		  
            		  
            	}
                
        		
        		break;
        
            case 1: 
            	thePassword = "";
                System.out.println("you logged in");
                
                
                gui.update( "Keuzemenu", "Maak hier Uw keuze", true, "Willekeurig bedrag", true, "Vast bedrag", true, "Saldo", false, "test");
                gui.accepteerOff(false);
              	
              	
                break;
            
            case 2: 

                gui.update( "Saldo: €" + client.balance(pasnummer).getBalance(), "", false, "", false, "", false, "", false, "");

                break;
            
            case 3: 

                gui.update( "Kies een bedrag.", "", true, "€20,-", true, "€50,-", true, "€100,-", true, "€250,-");
                gui.accepteerOff(false);
                gui.snelpinnenOn(true);
                break;
            
            case 4: 
            	if(!thePassword.equals(""))
            	{
            		gui.update("uw bedrag: €"+thePassword, "Press D to delete", false, "", false, "", false, "", false, "");
            	}
              
            	else
            	{
            		  gui.update("Bedrag kleiner dan 1000 en veelvoud van 5", "", false, "", false, "", false, "", false, "");
            	}
                break;
           
            case 5: 

                gui.update("Weet u het zeker?", "", true, "Ja", true, "Nee", false, "", false, "");
                gui.accepteerOff(false);
               
                break;
            
            case 6: 

                gui.update("Wilt u een bon?", "", true, "Ja", true, "Nee", false, "", false, "");
                gui.accepteerOff(false);
                gui.endOff(false);
                break;
            
            case 7: 

                gui.update( "Uw geld wordt nu uitgevoerd.", "", false, "", false, "", false, "", false, "");
                gui.accepteerOff(false);
                gui.endOff(false);
                break;
            
            case 8: 

                gui.update("Bedankt voor het pinnen en nog een fijne dag.", "", false, "", false, "", false, "", false, "");
                gui.accepteerOff(false);
                gui.endOff(false);
                try {
    				TimeUnit.SECONDS.sleep(4);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
                
                reset();
                break;
            
           
            
            case 10: 
                gui.update( "Uw pinpas is geblokkeerd", "", false, "", false, "", false, "", false, "");
                gui.accepteerOff(false);
                gui.endOff(false);
                try {
    				TimeUnit.SECONDS.sleep(4);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
                reset();
                break;
            
            case 11: 
                gui.update("Er staat te weinig saldo op deze kaart", "", false, "", false, "", false, "", false, "");
                gui.accepteerOff(false);
                gui.endOff(false);
                try {
    				TimeUnit.SECONDS.sleep(4);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
                reset();
                break;
                
            case 12: 
                gui.update( "Deze pas is niet bekend bij ons", "", false, "", false, "", false, "", false, "");
                gui.accepteerOff(false);
                gui.endOff(false);
                try {
    				TimeUnit.SECONDS.sleep(4);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
                reset();
                break;
            
            default: 
                	System.out.println("geen case");
                	break;
        }

    }

    public void start() {
        this.initialize();
        startTimer();
        screen = 100;
        gui.setExtendedState(JFrame.MAXIMIZED_BOTH); 
      	gui.setUndecorated(true);
        gui.start("The Money Generators");
       
        
        switchCase();
        
        System.out.println("Started now");

    }
}
