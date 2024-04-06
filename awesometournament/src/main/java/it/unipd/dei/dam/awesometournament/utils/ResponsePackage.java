package it.unipd.dei.dam.awesometournament.utils;

import it.unipd.dei.dam.awesometournament.utils.ResponseStatus;

public class ResponsePackage {
    
    private ResponseStatus status;
    private String message;

    public ResponsePackage() {
        this.status = ResponseStatus.OK;
        this.message = "";
    }

    public ResponsePackage(ResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }

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
