package db;

import connection.HttpManager;

public class DBError extends Exception
{
    public DBError(String msg)
    {
        super(msg);
    }

    public static void throwIfErrorCode(HttpManager httpManager) throws DBError {
        if (httpManager == null)
            return;

        if (httpManager.getCode() == HttpManager.ERR)
            throw new DBError(httpManager.getData() + "");


    }
}
