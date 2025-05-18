package ec.edu.espol.proyecto2p;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        et1 = findViewById(R.id.et1);
    }

    public void start(View v) {
        String playerName = et1.getText().toString().trim(); //Se obtiene el nombre del jugador
        if (!playerName.isEmpty() && playerName.length() <= 24) { //Se verifica que se haya ingresado un nombre y que sea corto
            Intent intent = new Intent(MainActivity.this, MainActivity2.class); // Se crea otro activity
            intent.putExtra("playerName",playerName); //Se transfiere el nombre al otro activity
            startActivity(intent); //Se inicia el otro activity
        } else
            Toast.makeText(MainActivity.this, "Por favor ingrese un apodo corto", Toast.LENGTH_SHORT).show();
    }

}