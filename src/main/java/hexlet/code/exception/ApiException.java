package hexlet.code.exception;

public class ApiException {
    public static class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super(message);
        }
    }
}

