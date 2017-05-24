package ml.hro.moneymakers.server;

import ml.hro.moneymakers.api.*;



public interface Database {

	long getBalance(long rekeningNr);
	boolean withdraw(long rekeningNr, long amount);
	int getTransacties();
	boolean setTransacties(WithdrawResponse response, long pasnummer);
	Credentials checkCard(long rekeningnummer);
	boolean checkCredentials(Credentials credentials, long rekeningnummer);
	boolean makeCredentials(Credentials credit, long pasnr,  String rekeningNr);
}
