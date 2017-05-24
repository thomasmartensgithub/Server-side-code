package ml.hro.moneymakers.api;



public class WithdrawRequest
{
    private long amount;
    private String ATM;
    

    public String getATM() {
		return this.ATM;
	}

	public void setATM(String aTM) {
		this.ATM = aTM;
	}

	public WithdrawRequest()
    {
    }

    public long getAmount()
    {
        return amount;
    }

    public void setAmount(long amount)
    {
        this.amount = amount;
    }
}