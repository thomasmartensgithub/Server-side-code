package Geek.inc.client;

import java.io.*;

public class Bank_Geek {
	

	   int tien;
	   int twintig;
	   int vijftig;
	
	private Bank_Geek(){
		
	}
	
	public static void main(String[] args){
		Bank_Geek a = new Bank_Geek();
		a.write( "10\n20\n30\n");
		a.read();
		
		System.out.print("HALLO bij Geek Incorporated bank");
	}
	private void read() {
		String[] a = new String[0];
		try {
			FileInputStream fstream = null;
			try {
				fstream = new FileInputStream("geld.txt");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			a = new String[3];
			try {
				for (int i = 0; (strLine = br.readLine()) != null; i++) {

                    System.out.println(strLine);
                    a[i] = strLine;
                }
			} catch (IOException e) {
				e.printStackTrace();
			}


			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		tien = Integer.parseInt(a[0]);
		twintig = Integer.parseInt(a[1]);
		vijftig = Integer.parseInt(a[2]);
	}
private void write(String strLine){
	try{
	    FileOutputStream fstream = new FileOutputStream("geld.txt");
	          DataOutputStream in = new DataOutputStream(fstream);
	        
	          BufferedWriter br = new BufferedWriter(new OutputStreamWriter(in));
	          
	          br.write(strLine);
	          br.close();
	        	  
	   } catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
		  
}

	

