package Geek.inc.server;

import Geek.inc.api.Credentials;
import Geek.inc.api.WithdrawResponse;



public interface Database {

	long getBalance(String pasNr);
	boolean withdraw(String rekeningNr, long amount);
	int getTransacties();
	boolean setTransacties(WithdrawResponse response, String pasnummer);
	Credentials checkCard(long rekeningnummer);
	boolean checkCredentials(Credentials credentials, long rekeningnummer);
	boolean makeCredentials(Credentials credit, long pasnr,  String rekeningNr);
}
