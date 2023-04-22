package cn.nean.notes.common.exception;

public class EchoNotesException extends RuntimeException{

    private static final long serialVersionUID = 5565760508056698922L;

    private String errMessage;

    public EchoNotesException() {
        super();
    }

    public EchoNotesException(String message) {
        super(message);
        this.errMessage = message;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public static void cast(String errMessage){
        throw new EchoNotesException(errMessage);
    }

    public static void cast(CommonError commonError){
        throw new EchoNotesException(commonError.getErrMessage());
    }
}
