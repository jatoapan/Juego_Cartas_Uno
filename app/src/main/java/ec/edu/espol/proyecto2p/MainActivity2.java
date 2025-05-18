package ec.edu.espol.proyecto2p;

import ec.edu.espol.proyecto2p.clases.*;

import java.security.SecureRandom;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {
    Juego game;
    Jugador player;
    Jugador machine;
    LinearLayout playercont;
    LinearLayout machinecont;
    TextView tv2;
    ImageView iimv1;
    TextView tv4;
    TextView tv5;
    TextView tv6;
    TextView tv7;
    Button bb1;
    TextView[] colorButtons = new TextView[4];
    int turno = 1;
    TipoColor playerNewColor;
    SecureRandom random = new SecureRandom();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        playercont = (LinearLayout) findViewById(R.id.playercont); //Se asigna la zona de cartas del jugador en la interfaz gráfica
        machinecont = (LinearLayout) findViewById(R.id.machinecont); //Se asigna la zona de cartas de la máquina en la interfaz gráfica
        iimv1 = findViewById(R.id.iimv1); //Se asigna la zona de la pila de descarte en la interfaz gráfica
        tv2 = findViewById(R.id.tv2); //Se asigna la zona del nombre del jugador en la interfaz gráfica

        String playerName = getIntent().getStringExtra("playerName").trim(); //Se obtiene el nombre del jugador del anterior activity
        game = new Juego(playerName); //Se crea un nuevo juego
        tv2.setText(playerName); //Se asigna el nombre del jugador en la interfaz gráfica

        TextView tv4 = findViewById(R.id.tv4); //Se asigna el botón rojo en la interfaz gráfica
        TextView tv5 = findViewById(R.id.tv5); //Se asigna el botón amarillo en la interfaz gráfica
        TextView tv6 = findViewById(R.id.tv6); //Se asigna el botón verde en la interfaz gráfica
        TextView tv7 = findViewById(R.id.tv7); //Se asigna el botón azul en la interfaz gráfica
        //Se agregan los botones a un arreglo para poder manipularlos con mayor facilidad
        colorButtons[0] = tv4;
        colorButtons[1] = tv5;
        colorButtons[2] = tv6;
        colorButtons[3] = tv7;

        game.iniciarJuego(); //Se inicia el juego de modo que en el backend se crea la baraja, la pila de descarte con una sola carta inicial, la mano del jugador y la mano de la máquina
        player = game.getJugador();
        machine = game.getMaquina();

        iimv1.setImageResource(obtainCardId(game.ultimaEnPila())); //Se asigna la única carta inicial de la pila de descarte en la interfaz gráfica
        setImageViewParams(iimv1); //Se le da algunos ajustes al formato de la imagen para que se vea bien
        setColorButtonsColors(); //Se asignan colores a los botones que representan los colores de modo que el unico botón que resalta es el que es del mismo color que la única carta inicial de la pila de descarte

        setPlayerCards(player, playercont); //Se asignan las cartas de la mano del jugador en la interfaz gráfica
        setPlayerCards(machine, machinecont); //Se asignan las cartas de la mano de la máquina en la interfaz gráfica
        Toast.makeText(MainActivity2.this, "Turno del Jugador", Toast.LENGTH_SHORT).show();

    }

    //Esta funcion obtiene una carta del backend, con base en sus atributos construye el nombre de la imagen de dicha carta que previamente ya esta registrada en el Enum TipoCarta y por último devuelve el id de la imagen de dicha carta
    private int obtainCardId(Carta card) {
        int imageId;
        String colorType = card.getColor().name();
        String imageName = colorType + "_";

        if (card instanceof CartaComodin) {
            CartaComodin comodin = (CartaComodin) card;

            //Este bloque es sensible ya que cuando a una carta de cambio de color se le cambia el color lo que sucede es que no existe id para esa nueva carta

//            if(comodin.getCaracter() == TipoComodin.BLANCA || comodin.getCaracter() == TipoComodin.CAMBIO_COLOR)
//                imageName = "N_";

            if(comodin.getCaracter() == TipoComodin.CAMBIO_COLOR)
                imageName = "N_";

            String comodinType = comodin.getCaracter().name();
            imageName += comodinType;

        } else {
            int cardNumber = card.getNumero();
            imageName += cardNumber;
        }

        imageId = TipoCarta.valueOf(imageName).getImageId();
        return imageId;
    }

    //Esta función actualiza las imágenes de la zona de cartas del jugador o de la máquina en la interfaz gráfica con base en la mano actual del jugador o la máquina
    private void setPlayerCards(Jugador player, LinearLayout playerCont) {
        List<Carta> playerCards = player.getMano();
        playerCont.removeAllViews();
        for (Carta playerCard : playerCards) {
            int imageId = obtainCardId(playerCard);
            ImageView generalImv = new ImageView(this);
            playerCont.addView(generalImv);
            generalImv.setImageResource(imageId);
            if (player.getPlayerId() == 1)
                generalImv.setOnClickListener(view -> setButtonCardsParams(view));
            setImageViewParams(generalImv);
        }
    }

    //Esta función les otorga la funcionalidad de botón solo a las imágenes de las cartas del jugador
    private void setButtonCardsParams(View view) {
        int cardPosition = playercont.indexOfChild(view);
        Carta playerCard = player.getMano().get(cardPosition);
        if (game.cartaEsValida(playerCard) && turno == 1 && !player.getMano().isEmpty() && !machine.getMano().isEmpty()) {
            ImageView especificImv = (ImageView) view;
            iimv1.setImageDrawable(especificImv.getDrawable());
            game.turnoJugador(playerCard);
            playercont.removeView(view);
            setColorButtonsColors();
            setWinner();
            verifyChangeTurn();
        }
    }

    //Esta función se encarga de asignar los colores a los botones que representan los colores de modo que el unico botón que resalta es el que es del mismo color que la última carta de la pila de descarte
    private void setColorButtonsColors() {
        String initialColorLastPlayedCard = game.ultimaEnPila().getColor().name();
        for (TextView colorButton : colorButtons) {
            String initialColorButton = colorButton.getText().toString();
            if (initialColorLastPlayedCard.equals(initialColorButton))
                colorButton.setBackgroundColor(game.ultimaEnPila().getColor().getColorId());
            else
                colorButton.setBackgroundColor(0xFF49454F);
        }
    }

    //Esta función le da algunos ajustes al formato de la imagen de una carta para que se vea bien
    private void setImageViewParams(ImageView imv) {
        if (imv != null) {
            imv.setPadding(10, 10, 10, 10);
            imv.setAdjustViewBounds(true);
            imv.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    //Esta función verifica si se debe hacer un cambio de turno, el turno 1 representa el turno del jugador donde puede elegir cualquier carta de su baraja en la interfaz gráfica,
    //el turno -1 ocurre cuando el jugador utilizó una carta negra por lo que activa los botones de colores para que un nuevo color sea seleccionado y bloquea las demás funcionalidades
    //temporalmente para que no haya errores, el turno 0 representa el turno de la máquina
    private void verifyChangeTurn() {
        if(player.getMano().size() == 1 || machine.getMano().size() == 1)
            Toast.makeText(MainActivity2.this, "¡UNO!", Toast.LENGTH_SHORT).show();
        if (game.debeRepetirTurno(player))
            turno = 1;
        else if (game.ultimaEnPila().getColor() == TipoColor.N) {
            Toast.makeText(MainActivity2.this, "Por favor ingrese un nuevo color", Toast.LENGTH_SHORT).show();
            setPlayerCards(machine, machinecont);
            turno = -1;
        } else {
            setPlayerCards(machine, machinecont);
            turno = 0;
            machineLogic();
        }
    }

    //Este botón se encarga de cuando el jugador ha lanzado una carta negra y debe seleccionar un nuevo color
    public void selectNewColor(View v) {
        if (turno == -1 && game.ultimaEnPila().getColor() == TipoColor.N) {
            TextView txv = (TextView) v;
            playerNewColor = TipoColor.valueOf(txv.getText().toString());
            game.setLastBlackCardColor(playerNewColor);
            setColorButtonsColors();
            turno = 0;
            machineLogic();
        }
    }

    //Esta función esta vinculada al botón de tomar carta de modo que solo cuando se encuentra en el turno del jugador, el jugador decide si tomar una carta o no
    public void tomarCarta(View v){
        if (turno == 1) {
            player.robarCarta(game.getBaraja(),1);
            setPlayerCards(player, playercont);
            turno = 0;
            machineLogic();
        }
    }

    //Esta función representa el juego de la máquina en el frontend
    private void machineLogic() {
        if (turno == 0 && !player.getMano().isEmpty() && !machine.getMano().isEmpty()) {
            new Thread(() -> {
                do {
                    Carta selectedCard = game.turnoMaquina();

                    runOnUiThread(() -> {
                        if(selectedCard != null)
                            iimv1.setImageResource(obtainCardId(game.ultimaEnPila()));

                        if(game.ultimaEnPila().getColor() == TipoColor.N){
                            TipoColor machineNewColor = TipoColor.values()[random.nextInt(4)];
                            game.setLastBlackCardColor(machineNewColor);
                        }

                        setPlayerCards(machine, machinecont);
                        setPlayerCards(player, playercont);
                        setColorButtonsColors();

                        if(machine.getMano().size() == 1)
                            Toast.makeText(MainActivity2.this, "¡UNO!", Toast.LENGTH_SHORT).show();
                    });

                } while (game.debeRepetirTurno(machine));

                runOnUiThread(() -> {
                    setWinner();
                    turno = 1;
                    //Toast.makeText(MainActivity2.this, "Turno del Jugador", Toast.LENGTH_SHORT).show();
                });
            }).start();
        }
    }


    //Esta función anuncia al ganador mediante un aviso en la interfaz gráfica, cuando ya hay un ganador establece un turno fuera del rango para que en la interfaz ya no puedan haber cambios
    private void setWinner(){
        if(player.getMano().isEmpty())
            Toast.makeText(MainActivity2.this, "¡" + player.getNombre() + " ha ganado!", Toast.LENGTH_SHORT).show();
        else if (machine.getMano().isEmpty())
            Toast.makeText(MainActivity2.this, "¡" + machine.getNombre() + " ha ganado!", Toast.LENGTH_SHORT).show();
        if(player.getMano().isEmpty() || machine.getMano().isEmpty())
            turno = -10;
    }

}