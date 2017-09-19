package anais.springboot.sample.lunchmenu.exception;

import anais.springboot.sample.lunchmenu.model.exceptional.ExceptionMessage;

import java.util.Collections;

public class DuplicatedException extends LunchMenuException {

    public DuplicatedException(Class<?> dataType, Object data) {
        super.message = new ExceptionMessage("ERROR.LM400",
                dataType.getSimpleName()+" is already exist: "+data, Collections.emptyList());
    }
}
