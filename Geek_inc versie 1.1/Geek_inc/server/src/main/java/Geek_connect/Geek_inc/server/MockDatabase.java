package Geek_connect.Geek_inc.server;

/**
 * Created by thoma on 4-4-2017.
 */
public class MockDatabase implements Database
{
    @Override
    public long getBalance(String rekeningNr)
    {
        return 5000;
    }

    @Override
    public boolean withdraw(String rekeningNr, long amount)
    {
        return true;
    }
}