package it.unipd.dei.dam.awesometournament.utils;

import it.unipd.dei.dam.awesometournament.utils.ResponseStatus;

public class ResponsePackage<T> {
    
    private ResponseStatus status;
    private String message;
    private T data;

    public ResponsePackage() {
        this.status = ResponseStatus.OK;
        this.message = "";
    }

    public ResponsePackage(ResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponsePackage(T data, ResponseStatus status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public T getData() { return data; }

    public void setData(T data) { this.data = data; }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
