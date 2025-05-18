package ec.edu.espol.proyecto2p.clases;

public class CartaComodin extends Carta{
    private TipoComodin caracter;

    public CartaComodin(TipoColor color, TipoComodin caracter){
        super(-1, color);
        this.caracter = caracter;
    }

    public TipoComodin getCaracter() {
        return caracter;
    }

    void setColor(TipoColor color){
        if (this.caracter == TipoComodin.CAMBIO_COLOR && color == TipoColor.N)
            this.color = color;
        else if(this.color == TipoColor.N && color != TipoColor.N)
            this.color = color;
    }

    @Override
    public String toString(){
        return "Color: " + color + " - Comodin: " + caracter.getSimbolo();
    }

}
