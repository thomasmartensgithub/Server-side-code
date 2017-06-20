package Geek.inc.server;

import Geek.inc.api.Credentials;
import Geek.inc.api.SaltMethode;
import Geek.inc.api.WithdrawResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;


public class DatabaseImpl implements Database {
	
	private static final Logger logger= LoggerFactory.getLogger(DatabaseImpl.class);
	private Connection con;
	private String host = "jdbc:mysql://localhost:3306/Geek_incorporated";
	private String uName= "root";
	private String uPass = "As947b";
	private ResultSet rs;
	
	public DatabaseImpl(){
		this.connect();
	}
	
	private void connect(){
		try{
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(host, uName, uPass);
		logger.info("Connection to the database is established.");
		}
		catch(Exception e){
			logger.error(e.getMessage());
		}
	}
	
	public boolean makeCredentials(Credentials credit, long pasnr, String rekeningnr){
		try{PreparedStatement ps = con.prepareStatement("INSERT INTO DEBITCARD " 
															+ "VALUES (?,?,?,?)");
		
		logger.info(pasnr + "            " + rekeningnr);
			ps.setLong(1, pasnr);
			 String hash = ( Long.toString( pasnr)+ Integer.toString(credit.getPincode()));
		       SaltMethode x = new SaltMethode();
		       String token = x.hash(hash);
		       
		       ps.setString(2, token);
		       ps.setInt(3, 0);
		       ps.setString(4, rekeningnr);
		       ps.execute();
		       logger.info(pasnr + "            " + rekeningnr + "  " + token);
		       return true;
	}catch(SQLException e){
		logger.error(e.getMessage());
		return false;
	}
	}
	
	public boolean checkCredentials(Credentials credit, long rekeningNr){
		try{
			PreparedStatement ps = con.prepareStatement("SELECT Pin_hash " 
															+ "FROM Geek_incorporated.DEBITCARD "
															+ "WHERE Geek_incorporated.DEBITCARD.Pasnr = ? ");
			 	ps.setLong(1, rekeningNr);
						
			rs = ps.executeQuery();
			rs.next();
			String a = rs.getString("Pin_hash");
			logger.debug("Saldo = " + a);
			 SaltMethode x = new SaltMethode();
			if(x.authenticate(( Long.toString(rekeningNr) + Integer.toString(credit.getPincode())), a)){
				
				PreparedStatement ps3 = con.prepareStatement("UPDATE DEBITCARD " 
																+ "SET Tries = 0"
																+ " WHERE Pasnr = ?");
				ps3.setLong(1, rekeningNr);
				ps3.execute();
				
				return true;
			}
			else{
				
				PreparedStatement ps2 = con.prepareStatement("UPDATE DEBITCARD "
																+ "SET Tries = Tries + 1"
																+ " WHERE Pasnr = ?");
				ps2.setLong(1, rekeningNr);
				ps2.execute();
				return false;
			}
			}
			catch(SQLException e){
				logger.error(e.getMessage());
				return false;
			}

	}
	
	
	public int getTransacties(){
		int size = 0;
		try{
			PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) "  
															+ "FROM TRANSACTIES");
			
			rs = ps.executeQuery();
			rs.next();
			size = rs.getInt("COUNT(*)");
			logger.debug("Aantal Transacties = " +size);
			}
			catch(SQLException e){
				logger.error(e.getMessage());
			}
			return size;
	}
	
	public Credentials checkCard(long rekeningNr){
		Credentials temp = new Credentials();
		try{
			
			PreparedStatement ps = con.prepareStatement("Select Tries "
															+ "FROM Geek_incorporated.DEBITCARD "
															+ "WHERE Geek_incorporated.DEBITCARD.Pasnr = ?" );
			ps.setLong(1, rekeningNr);
			rs = ps.executeQuery();
			rs.next();
			int a = rs.getInt("Tries");
			logger.debug("Saldo = " + a);
			if(a < 3){
				temp.setState(true);
				temp.setBlocked(false);
				return temp;
			}
			
			temp.setState(true);
			temp.setBlocked(true);
			return temp;
			}
			catch(SQLException e){
				temp.setState(false);
				temp.setBlocked(true);
				return temp;
			}
			
			
	}
	
	public String getRekeningnummer(String rekeningNr){
		try{
			PreparedStatement ps = con.prepareStatement("Select ACCOUNTS_Rekeningnr " 
															+ "FROM Geek_incorporated.DEBITCARD "
															+ "WHERE Geek_incorporated.DEBITCARD.Pasnr = ?" );
			ps.setLong(1, Long.parseLong(rekeningNr));
			rs = ps.executeQuery();
			rs.next();
			String a = rs.getString("ACCOUNTS_Rekeningnr");
			logger.debug("ACCOUNTS_Rekeningnr = " + a);
			return a;
			}
			catch(SQLException e){
				logger.error(e.getMessage());
			}
			return null;
			
	}
	
	public boolean setTransacties(WithdrawResponse response, String pasnummer){
		try {
			
					
					PreparedStatement ps = con.prepareStatement("INSERT INTO TRANSACTIES " 
																	+ "VALUES (?, NOW(), ?, ?,?,?)");
					ps.setInt(1, response.getTransactionNr());
					ps.setInt(2, (int)response.getAmount());
					ps.setString(3, response.getATM());
					ps.setBoolean(4, !response.isNoSaldo());
					ps.setLong(5, Long.parseLong(pasnummer));
					
					ps.execute();
					
					return true;
						
			
			}
			catch (SQLException e) {
					logger.error(e.getMessage());
					return false;
				}
			}
	
	
	public long getBalance(String pasNr){
		long saldo = 0;
		try{
			
			String rekeningNr = getRekeningnummer(pasNr);
		PreparedStatement ps = con.prepareStatement("Select Saldo " 
														+ "FROM Geek_incorporated.ACCOUNTS "
														+ "WHERE Geek_incorporated.ACCOUNTS.Rekeningnr = ?" );
		ps.setString(1, rekeningNr);
		rs = ps.executeQuery();
		rs.next();
		saldo = rs.getLong("Saldo");
		logger.debug("Saldo = " + saldo);
		}
		catch(SQLException e){
			logger.error(e.getMessage());
		}
		return saldo;
	
	}
	
	
	public boolean withdraw(String pasNr, long amount){
		try {
			long saldo = getBalance(pasNr);
			String rekeningNr = getRekeningnummer(pasNr);
			if(saldo < amount){
				return false;
			}
		
			logger.debug("Rekening nummer: {} \t saldo: {} ", rekeningNr, saldo);
			
				PreparedStatement ps = con.prepareStatement("UPDATE ACCOUNTS " 
																+ "SET Saldo = Saldo - ?"
																+ " WHERE Rekeningnr = ?");
				
				 ps.setLong(1, (amount));
	              ps.setString(2, rekeningNr);
	              ps.execute();
				
				
					logger.debug("Nieuwe saldo: {}", getBalance(pasNr));
				
			return true;
					
		
		}
		catch (SQLException e) {
				logger.error(e.getMessage());
				return false;
			}
		}
	
	
	}

