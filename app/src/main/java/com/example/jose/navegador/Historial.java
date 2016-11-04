package com.example.jose.navegador;

/**
 * Created by jose on 18/10/2016.
 */

public class Historial {
    private String text;
    private String url;
    private String hora;

    public Historial(String text,String url,String hora){
        this.hora=hora;// No se puede obtener la hora del telefono.
        this.text=text;
        this.url=url;
    }

    public String getTexto() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public String getHora() {
        return hora;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
