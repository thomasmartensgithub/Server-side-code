package Geek_connect.api;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by thoma on 4-4-2017.
 */
public class BalanceResponse
{
    @JsonProperty
    private long balance;
    @JsonProperty
    private String rekeningNummer;

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getRekeningNummer() {
        return rekeningNummer;
    }

    public void setRekeningNummer(String rekeningNummer) {
        this.rekeningNummer = rekeningNummer;
    }

    public BalanceResponse() {
    }
}