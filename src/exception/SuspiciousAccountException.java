package exception;

public class SuspiciousAccountException extends RuntimeException {
    public SuspiciousAccountException(String message) {

        super(message);
    }
}