package ec.edu.espol.proyecto2p.clases;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;

public class Juego {
    private final Jugador jugador;
    private final Jugador maquina;
    private List<Carta> baraja;
    private List<Carta> pilaDeDescarte;
    private final SecureRandom random = new SecureRandom();

    public Juego(String nombreJugador){
        maquina = new Jugador(0,"Maquina");
        jugador = new Jugador(1,nombreJugador);
    }

    public void iniciarJuego(){
        baraja = Utilitaria.crearBaraja();
        pilaDeDescarte = Utilitaria.crearPiladeDescarte(baraja);
        jugador.setMano(Utilitaria.crearManoJugador(baraja));
        maquina.setMano(Utilitaria.crearManoJugador(baraja));
    }

    public void turnoJugador(Carta cartaAJugar) {

        jugador.jugarCarta(cartaAJugar);

        if(cartaAJugar instanceof CartaComodin){
            CartaComodin comodin = (CartaComodin) cartaAJugar;
            CartaComodin comodinJugado = comodinAJugar(comodin, maquina);
            pilaDeDescarte.add(comodinJugado);
        }else
            pilaDeDescarte.add(cartaAJugar);

        if(baraja.size() <= 4) {
            rellenarBaraja();
        }

    }

    public Carta turnoMaquina() {
        int indicePrueba = 0;
        Carta pruebaSeleccionada = maquina.getMano().get(indicePrueba);

        while (indicePrueba < maquina.getMano().size() && !cartaEsValida(pruebaSeleccionada)) {
            indicePrueba++;
            if(indicePrueba < maquina.getMano().size())
                pruebaSeleccionada = maquina.getMano().get(indicePrueba);
            else
                pruebaSeleccionada = null;
        }

        if (pruebaSeleccionada == null) {
            maquina.robarCarta(baraja,1);
        } else {

            if(pruebaSeleccionada instanceof CartaComodin){
                CartaComodin comodin = (CartaComodin) pruebaSeleccionada;
                CartaComodin comodinJugado = comodinAJugar(comodin, jugador);
                pilaDeDescarte.add(comodinJugado);
            }else {
                pilaDeDescarte.add(pruebaSeleccionada);
            }
            maquina.jugarCarta(pruebaSeleccionada);

        }

        if(baraja.size() <= 4) {
            rellenarBaraja();
        }

        return pruebaSeleccionada;

    }

    public boolean cartaEsValida(Carta carta){
        if(!(carta instanceof CartaComodin)){
            return ((carta.getColor() == ultimaEnPila().getColor()) || (carta.getNumero() == ultimaEnPila().getNumero()));
        } else {
            return ((carta.getColor() == ultimaEnPila().getColor()) || (carta.getColor() == TipoColor.N));
        }
    }

    private CartaComodin comodinAJugar(Carta c, Jugador player){
        CartaComodin comodin = (CartaComodin) c;
        if(comodin.getCaracter() == TipoComodin.MAS_2)
            player.robarCarta(baraja, 2);
        else if(comodin.getCaracter() == TipoComodin.MAS_4)
            player.robarCarta(baraja, 4);
        return comodin;
    }

    public boolean debeRepetirTurno(Jugador player){
        if(ultimaEnPila() instanceof CartaComodin && !player.getMano().isEmpty()){
            CartaComodin comodin = (CartaComodin) ultimaEnPila();
            return comodin.getCaracter() == TipoComodin.BLOQUEO || comodin.getCaracter() == TipoComodin.REVERSO;
        }
        return false;
    }

    private void rellenarBaraja(){
        int cartasAExtraer = pilaDeDescarte.size()-1;
        for (int i = 0; i < cartasAExtraer; i++) {
            Carta cartaExtraida = pilaDeDescarte.remove(0);

            if(cartaExtraida instanceof CartaComodin){
                CartaComodin comodinExtraido = (CartaComodin) cartaExtraida;

                if(comodinExtraido.getCaracter() == TipoComodin.CAMBIO_COLOR) {
                    comodinExtraido.setColor(TipoColor.N);
                    baraja.add(comodinExtraido);
                }else
                    baraja.add(cartaExtraida);

            } else
                baraja.add(cartaExtraida);

        }
        Collections.shuffle(baraja);
    }

    public Carta ultimaEnPila(){
        return pilaDeDescarte.get(pilaDeDescarte.size()-1);
    }

    public void setLastBlackCardColor(TipoColor tipoColor){
        if (ultimaEnPila().getColor() == TipoColor.N) {
            CartaComodin comodin = (CartaComodin) ultimaEnPila();
            comodin.setColor(tipoColor);
        }
    }

    public List<Carta> getBaraja(){
        return baraja;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Jugador getMaquina() {
        return maquina;
    }

}
