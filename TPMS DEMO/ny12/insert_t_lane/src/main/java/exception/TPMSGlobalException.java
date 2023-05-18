package exception;

public class TPMSGlobalException extends RuntimeException 
{
    private static final long serialVersionUID = 1L;
    
    private String nameOfClass;
    private String methodName;
    private String errorMessage;
    
    public TPMSGlobalException(String message, String nameOfClass, String methodName) 
    {
        super(message);
        this.nameOfClass = nameOfClass;
        this.methodName = methodName;
        this.errorMessage = message;
    }
    
    
    public String getNameOfClass() {
        return nameOfClass;
    }
    public void setNameOfClass(String nameOfClass) {
        this.nameOfClass = nameOfClass;
    }
    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
}