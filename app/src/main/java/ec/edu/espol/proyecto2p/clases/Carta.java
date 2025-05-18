package ec.edu.espol.proyecto2p.clases;

public class Carta {
    protected int numero;
    protected TipoColor color;

    public Carta(int numero, TipoColor color){
        this.numero = numero;
        this.color = color;
    }

    public int getNumero(){
        return numero;
    }

    public TipoColor getColor(){
        return this.color;
    }

    @Override
    public String toString(){
        return "Color: " + color + " - Numero: " + numero;
    }
}
