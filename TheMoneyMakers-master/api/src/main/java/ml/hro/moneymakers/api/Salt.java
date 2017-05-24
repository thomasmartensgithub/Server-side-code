/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ml.hro.moneymakers.api;

/**
 *
 * @author Gebruiker
 */
public class Salt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       String passnummer = "001";
       String RFID = "123456789";
       String hash = (passnummer+RFID);
       SaltMethode x = new SaltMethode();
       String a = x.hash(hash);
       System.out.println(a);
       String token = a;
       boolean check = x.authenticate(hash,token);
       System.out.println("check "+check);
    }
    
}
