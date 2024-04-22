package it.unipd.dei.dam.awesometournament.utils;

public class ResponsePackage<T> extends ResponsePackageNoData {

    private T data;

    public ResponsePackage() {
        super();
    }

    public ResponsePackage(T data, ResponseStatus status, String message) {
        super(status, message);
        this.data = data;
    }

    public T getData() { return data; }

    public void setData(T data) { this.data = data; }
}
