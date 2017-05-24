package ml.hro.moneymakers.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Kluis {
	

	   int tien;
	   int twintig;
	   int vijftig;
	
	private Kluis(){
		
	}
	
	public static void main(String[] args){
		Kluis a = new Kluis();
		a.write( "10\n20\n30\n");
		a.read();
		
		System.out.print("HALLO");
	}
	private void read(){
		   try{
		    FileInputStream fstream = new FileInputStream("geld.txt");
		          DataInputStream in = new DataInputStream(fstream);
		          BufferedReader br = new BufferedReader(new InputStreamReader(in));
		          String strLine;
		          String[] a = new String[3];
		          for(int i = 0; (strLine = br.readLine()) != null; i++)   {
		 
			  System.out.println(strLine);
			  a[i] = strLine;
		  }
		        	  
		   
		          
		   in.close();
		   }catch (Exception e){
		     System.out.println("Error: " + e.getMessage());
		   }
		   
		   tien = Integer.parseInt( a[0]);
		   twintig = Integer.parseInt( a[1]);
		   vijftig = Integer.parseInt( a[2]);
		}
private void write(String strLine){
	try{
	    FileOutputStream fstream = new FileOutputStream("geld.txt");
	          DataOutputStream in = new DataOutputStream(fstream);
	        
	          BufferedWriter br = new BufferedWriter(new OutputStreamWriter(in));
	          
	          br.write(strLine);
	          br.close();
	        	  
	   }catch (Exception e){
	     System.out.println("Error: " + e.getMessage());
	   }
	}
		  
}

	

