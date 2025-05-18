package ec.edu.espol.proyecto2p.clases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

abstract class Utilitaria {
    private static final Random random = new Random();
    
    private Utilitaria() {
        throw new IllegalStateException("No se puede instanciar esta clase.");
    }
    
    public static List<Carta> crearBaraja(){
        List<Carta> baraja = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            baraja.add(new Carta(i, TipoColor.R));
            baraja.add(new Carta(i, TipoColor.V));
            baraja.add(new Carta(i, TipoColor.A));
            baraja.add(new Carta(i, TipoColor.Z));
        }

        for(int i=0; i<4; i++){
           baraja.add(new CartaComodin(TipoColor.values()[i], TipoComodin.values()[i]));
           baraja.add(new CartaComodin(TipoColor.values()[i], TipoComodin.values()[i]));
        }

        for(int i=2; i<5; i++){
            baraja.add(new CartaComodin(TipoColor.N, TipoComodin.values()[i]));
            baraja.add(new CartaComodin(TipoColor.N, TipoComodin.values()[i]));
        }

        Collections.shuffle(baraja);
        return baraja;
    }

    public static List<Carta> crearManoJugador(List<Carta> baraja){
        List<Carta> mano = new ArrayList<>();

        for(int i=0; i<7; i++){
            Carta cartaSeleccionada = baraja.remove(random.nextInt(baraja.size()));
            mano.add(cartaSeleccionada);
        }

        return mano;
    }

    public static List<Carta> crearPiladeDescarte(List<Carta> baraja){
        List<Carta> pilaDeDescarte = new ArrayList<>();
        
        while(pilaDeDescarte.isEmpty()){
            Carta primeraCarta = baraja.remove(random.nextInt(baraja.size()));
            if(!(primeraCarta instanceof CartaComodin)){
                pilaDeDescarte.add(primeraCarta);
            }
        }

        return pilaDeDescarte;
    }

}