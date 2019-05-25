package work.touchstr.manhua.Model.Web;

public class ConnectionException extends Exception {
    int error;
    public ConnectionException(int error)
    {
        super();
        this.error=error;
    }
    public int getError()
    {
        return error;
    }
}
