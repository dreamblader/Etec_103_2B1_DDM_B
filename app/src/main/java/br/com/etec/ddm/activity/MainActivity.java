package br.com.etec.ddm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.com.etec.ddm.R;
import br.com.etec.ddm.dialog.NewScheduleDialog;
import br.com.etec.ddm.model.ScheduleModel;

import static java.util.Collections.emptyList;

public class MainActivity extends AppCompatActivity implements NewScheduleDialog.SaveCallback {

    List<ScheduleModel> scheduleList = new ArrayList<>();

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

    @Override
    public void saveScheduleModel(ScheduleModel model) {
        scheduleList.add(model);
        System.out.println(model.toString());
    }
}