package ml.hro.moneymakers.api;


public class WithdrawResponse {
	
	
	
	private String ATM;

	private long amount;
	
	

	private int transactionNr;
	
	private boolean noSaldo;

	public String getATM() {
		return this.ATM;
	}

	public void setATM(String aTM) {
		this.ATM = aTM;
	}

	public long getAmount() {
		return this.amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	 public int  getTransactionNr() {
		return this.transactionNr;
	}

	public void setTransactionNr(int transactionNr) {
		this.transactionNr = transactionNr;
	}

	public boolean isNoSaldo() {
		return this.noSaldo;
	}

	public void setNoSaldo(boolean noSaldo) {
		this.noSaldo = noSaldo;
	}

	public WithdrawResponse()
    {
		
    }

  


   
}

