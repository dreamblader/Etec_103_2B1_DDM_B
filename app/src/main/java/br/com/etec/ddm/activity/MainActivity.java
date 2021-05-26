package br.com.etec.ddm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.WriteAbortedException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

    private static final String MY_DAY = "MY_DAY";

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
        setupData();
    }

    private void setupView(){
        mainDay = getIntent().getParcelableExtra(MY_DAY);

        if(mainDay == null){
            Calendar calendar = Calendar.getInstance();
            mainDay = new ScheduleModel(calendar.getTimeInMillis());
        }

        String titleString = mainDay.getWeekDay()+" - "+mainDay.getDay()+" de "+mainDay.getMonthName();
        TextView title = findViewById(R.id.ids_day_name);
        title.setText(titleString);
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

    private void setupData(){
        File file = new File(getFilesDir(), mainDay.getFileName());

        if(file.exists()){
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
                ScheduleModel temp;
                while((temp = (ScheduleModel) ois.readObject()) != null){
                    sortSchedule(temp);
                }
            }catch (Exception error){
                //file.delete();
               if(!(error instanceof EOFException)){
                    Toast.makeText(this,"Falha na Leitura do Arquivo.\n Causa:"+error.getMessage(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            }
        }
    }

    @Override
    public void saveScheduleModel(ScheduleModel model) {

        File file = new File(getFilesDir(), model.getFileName());

        try{
            boolean exist = !file.createNewFile();

            try(FileOutputStream fos = new FileOutputStream(file, true)){
                try(ObjectOutputStream oos = new ObjectOutputStream(fos)){
                    if(exist && file.length() > 0){
                        long pos = fos.getChannel().position()-4;
                        fos.getChannel().truncate(pos);
                    }
                    oos.writeObject(model);
                }
            }
        }catch (Exception error){
            Toast.makeText(this,"Falha no Salvamento de Arquivo.\n Causa:"+error.getMessage(), Toast.LENGTH_LONG).show();
            error.printStackTrace();
        }

        if(mainDay.isInThisDate(model)){
            sortSchedule(model);
        }
    }

    private void sortSchedule(ScheduleModel model){
        String initHour = model.getInitialHour();

        if(initHour.compareTo("12:00") <= 0){

            morningScheduleList.add(model);
            sortList(morningScheduleList);
            morningAdapter.notifyDataSetChanged();

        } else if(initHour.compareTo("19:00")<= 0){

            afternoonScheduleList.add(model);
            sortList(afternoonScheduleList);
            afternoonAdapter.notifyDataSetChanged();

        } else {

            nightScheduleList.add(model);
            sortList(nightScheduleList);
            nightAdapter.notifyDataSetChanged();

        }
    }

    private void sortList(List<ScheduleModel> list) {
        Collections.sort(list, (sm1, sm2) -> sm1.getInitialHour().compareTo(sm2.getInitialHour()));
    }


}