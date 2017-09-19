package anais.springboot.sample.cafeteria.exception;

import anais.springboot.sample.cafeteria.model.exceptional.ExceptionMessage;

import java.util.Collections;

public class DuplicatedException extends CafeteriaException {

    public DuplicatedException(Class<?> dataType, Object data) {
        super.message = new ExceptionMessage("ERROR.CF400",
                dataType.getSimpleName()+" is already exist: "+data, Collections.emptyList());
    }
}
