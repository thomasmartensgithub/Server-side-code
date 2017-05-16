package Geek_connect.Geek_inc.server;

/**
 * Created by thoma on 4-4-2017.
 */
public interface Database
{
    long getBalance(String rekeningNr);

    boolean withdraw(String rekeningNr, long amount);

}
