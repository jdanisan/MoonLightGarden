package com.example.moonlightgarden.models;

public class Opinion {
    private String cliente;
    private String texto;
    private long timestamp;

    public Opinion() {}

    public Opinion(String cliente, String texto, long timestamp) {
        this.cliente = cliente;
        this.texto = texto;
        this.timestamp = timestamp;
    }

    public String getCliente() { return cliente; }
    public String getTexto() { return texto; }
    public long getTimestamp() { return timestamp; }
}
