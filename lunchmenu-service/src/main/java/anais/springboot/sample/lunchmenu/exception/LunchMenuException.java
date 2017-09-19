package anais.springboot.sample.lunchmenu.exception;

import anais.springboot.sample.lunchmenu.model.exceptional.ExceptionMessage;

public class LunchMenuException extends RuntimeException {

    protected ExceptionMessage message;

    public ExceptionMessage getExceptionMessage() {
        return this.message;
    }
}
