package Utils.Exceptions;

public class Exception extends RuntimeException{
    protected int code;
    protected String description;

    public Exception(int code,String description){
        this.code=code;
        this.description=description;
    }

    public Exception(String description){
        this.description=description;
    }

    public int getCode(){
        return code;
    }

    public String getDescription(){
        return description;
    }
}
