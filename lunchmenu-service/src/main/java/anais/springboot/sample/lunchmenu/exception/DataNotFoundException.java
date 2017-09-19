package anais.springboot.sample.lunchmenu.exception;

import anais.springboot.sample.lunchmenu.model.exceptional.ExceptionMessage;

import java.util.Collections;

public class DataNotFoundException extends LunchMenuException {

    public DataNotFoundException(Class<?> dataClass, Object data) {
        super.message = new ExceptionMessage("ERROR.LM404",
                "Cannot find " + dataClass.getSimpleName() + ": " + data, Collections.emptyList());
    }

}
