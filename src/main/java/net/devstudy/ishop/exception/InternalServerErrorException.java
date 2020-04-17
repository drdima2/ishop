package net.devstudy.ishop.exception;

public class InternalServerErrorException extends RuntimeException{
    private static final long serialVersionUID = -7176363593833772436L;

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(Throwable cause) {
        super(cause);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
