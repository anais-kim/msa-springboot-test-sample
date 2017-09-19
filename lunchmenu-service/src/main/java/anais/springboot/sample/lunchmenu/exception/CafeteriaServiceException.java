package anais.springboot.sample.lunchmenu.exception;

import anais.springboot.sample.lunchmenu.model.exceptional.ExceptionMessage;

import java.util.Collections;

public class CafeteriaServiceException extends LunchMenuException {

    public CafeteriaServiceException() {
        super.message = new ExceptionMessage("ERROR.CF500",
                "Cannot contact to cafeteria service.", Collections.emptyList());
    }
}
