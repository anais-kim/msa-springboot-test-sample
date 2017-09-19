package anais.springboot.sample.lunchmenu.model.exceptional;

import java.util.List;

public class ExceptionMessage {

    private String errorCode;
    private String errorMessage;
    private List<String> detailMessages;

    public ExceptionMessage(String errorCode, String errorMessage, List<String> detailMessages) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.detailMessages = detailMessages;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<String> getDetailMessages() {
        return detailMessages;
    }

    public void setDetailMessages(List<String> detailMessages) {
        this.detailMessages = detailMessages;
    }

    @Override
    public String toString() {
        return "Error{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", detailMessages=" + detailMessages +
                '}';
    }
}

