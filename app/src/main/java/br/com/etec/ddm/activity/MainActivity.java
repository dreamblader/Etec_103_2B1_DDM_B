package br.com.etec.ddm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import br.com.etec.ddm.R;
import br.com.etec.ddm.dialog.NewScheduleDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatButton novoHorario = findViewById(R.id.am_criar_horas_btn);
        novoHorario.setOnClickListener(view -> {
            NewScheduleDialog dialog = new NewScheduleDialog();
            dialog.show(getSupportFragmentManager(), NewScheduleDialog.class.getName());
        });

    }
}