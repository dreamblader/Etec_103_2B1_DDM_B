package br.com.etec.ddm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatButton novoHorario = findViewById(R.id.am_criar_horas_btn);
        novoHorario.setOnClickListener(view -> {
            view.setBackgroundColor(getColor(R.color.black));
            Toast.makeText(this, "Voce clicou aqui!", Toast.LENGTH_LONG).show();
        });
    }
}