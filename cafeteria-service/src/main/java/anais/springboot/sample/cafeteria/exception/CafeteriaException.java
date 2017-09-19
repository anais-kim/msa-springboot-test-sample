package anais.springboot.sample.cafeteria.exception;

import anais.springboot.sample.cafeteria.model.exceptional.ExceptionMessage;

public class CafeteriaException extends RuntimeException {

    protected ExceptionMessage message;

    public ExceptionMessage getExceptionMessage() {
        return this.message;
    }
}
