package ec.edu.espol.proyecto2p.clases;
import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private int playerId;
    private String nombre;
    private List<Carta> mano;

    public Jugador(int playerId, String nombre){
        this.playerId = playerId;
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }

    void jugarCarta(Carta carta){
        mano.remove(carta);
    }

    public void robarCarta(List<Carta> baraja, int numeroCartas){
        for(int i = 0; i < numeroCartas; i++){
            Carta carta = baraja.remove(baraja.size() - 1);
            mano.add(carta);  
        }      
    }
 
    @Override
    public String toString(){
        StringBuilder mensaje = new StringBuilder("Jugador: " + nombre);
        
        int i = 0;
        for(Carta c : mano){
            mensaje.append("\n[" +  c.toString() + "] (" + i + ")");
            i++;
        }
        mensaje.append("\n--------------------------");

        return mensaje.toString();  
    }

    public void setMano(List<Carta> mano){ this.mano = mano; }

    public List<Carta> getMano() {
        return new ArrayList<>(mano);
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getNombre() {
        return nombre;
    }

}