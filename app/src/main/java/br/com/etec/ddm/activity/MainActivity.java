package br.com.etec.ddm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.com.etec.ddm.R;
import br.com.etec.ddm.adapter.ScheduleDetailAdapter;
import br.com.etec.ddm.dialog.CalendarDetailDialog;
import br.com.etec.ddm.dialog.NewScheduleDialog;
import br.com.etec.ddm.model.ScheduleModel;

import static br.com.etec.ddm.util.DateConverter.dayOfWeek;
import static br.com.etec.ddm.util.DateConverter.monthName;

public class MainActivity extends AppCompatActivity implements NewScheduleDialog.SaveCallback {

    ScheduleModel mainDay;

    List<ScheduleModel> morningScheduleList = new ArrayList<>();
    ScheduleDetailAdapter morningAdapter;

    List<ScheduleModel> afternoonScheduleList = new ArrayList<>();
    ScheduleDetailAdapter afternoonAdapter;

    List<ScheduleModel> nightScheduleList = new ArrayList<>();
    ScheduleDetailAdapter nightAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        setupRecyclerView();
        setupListeners();
    }

    private void setupView(){
        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        String weekDay = dayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
        String monthName = monthName(month);
        String titleString = weekDay+" - "+day+" de "+monthName;

        TextView title = findViewById(R.id.ids_day_name);
        title.setText(titleString);

        mainDay = new ScheduleModel(day, month, year);
    }

    private void setupRecyclerView(){
        morningAdapter = new ScheduleDetailAdapter(morningScheduleList);
        afternoonAdapter = new ScheduleDetailAdapter(afternoonScheduleList);
        nightAdapter = new ScheduleDetailAdapter(nightScheduleList);

        RecyclerView morningView = findViewById(R.id.ids_morning_list);
        morningView.setLayoutManager(new LinearLayoutManager(this));
        morningView.setAdapter(morningAdapter);

        RecyclerView afternoonView = findViewById(R.id.ids_afternoon_list);
        afternoonView.setLayoutManager(new LinearLayoutManager(this));
        afternoonView.setAdapter(afternoonAdapter);

        RecyclerView nightView = findViewById(R.id.ids_night_list);
        nightView.setLayoutManager(new LinearLayoutManager(this));
        nightView.setAdapter(nightAdapter);
    }

    private void setupListeners(){
        AppCompatButton novoHorario = findViewById(R.id.am_criar_horas_btn);
        AppCompatButton detalhes = findViewById(R.id.am_detalhes_btn);

        novoHorario.setOnClickListener(button -> {
            NewScheduleDialog dialog = new NewScheduleDialog();
            dialog.show(getSupportFragmentManager(), NewScheduleDialog.class.getName());
        });

        detalhes.setOnClickListener(button ->{
            CalendarDetailDialog dialog = new CalendarDetailDialog();
            dialog.show(getSupportFragmentManager(), CalendarDetailDialog.class.getName());
        });
    }

    @Override
    public void saveScheduleModel(ScheduleModel model) {

        File file = new File(getFilesDir(), model.getFileName());

        //TODO Salvar o nosso Arquivo

        if(mainDay.isInThisDate(model)){
            sortSchedule(model);
        }
    }

    private void sortSchedule(ScheduleModel model){
        String initHour = model.getInitialHour();

        if(initHour.compareTo("12:00") <= 0){

            morningScheduleList.add(model);
            morningAdapter.notifyDataSetChanged();

        } else if(initHour.compareTo("19:00")<= 0){

            afternoonScheduleList.add(model);
            afternoonAdapter.notifyDataSetChanged();

        } else {

            nightScheduleList.add(model);
            nightAdapter.notifyDataSetChanged();

        }
    }


}