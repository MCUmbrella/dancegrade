package vip.floatationdevice.dancegrade.data;

public class CommonMappedResult
{
    private final int code;
    private final String message;
    private final Object data;

    public CommonMappedResult(int code, String message, Object data)
    {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CommonMappedResult(int code, String message)
    {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public int getCode(){return code;}

    public String getMessage(){return message;}

    public Object getData(){return data;}
}
