package anais.springboot.sample.cafeteria.exception;

import anais.springboot.sample.cafeteria.model.exceptional.ExceptionMessage;

import java.util.Collections;

public class DataNotFoundException extends CafeteriaException {

    public DataNotFoundException(Class<?> dataClass, Object data) {
        super.message = new ExceptionMessage("ERROR.CF404",
                "Cannot find " + dataClass.getSimpleName() + ": " + data, Collections.emptyList());
    }

}
