package cn.nean.notes.common.exception;

public class EmailException extends RuntimeException{

    private static final long serialVersionUID = 5565760508056698922L;

    private String errMessage;

    public EmailException() {
        super();
    }

    public EmailException(String message) {
        super(message);
        this.errMessage = message;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public static void cast(String errMessage){
        throw new EmailException(errMessage);
    }

    public static void cast(CommonError commonError){
        throw new EmailException(commonError.getErrMessage());
    }
}
