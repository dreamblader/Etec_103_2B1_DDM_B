package br.com.etec.ddm.dialog;

import android.content.Context;
import android.os.Bundle;
import android.service.autofill.SaveCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import androidx.appcompat.widget.AppCompatButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import br.com.etec.ddm.R;
import br.com.etec.ddm.model.ScheduleModel;
import br.com.etec.ddm.util.HourTextWatcher;

public class NewScheduleDialog extends BottomSheetDialogFragment {

    private static final String MAIN_DAY = "MAIN_DAY";

    boolean canSave;
    SaveCallback activityCallback;

    public static NewScheduleDialog newInstance(ScheduleModel mainDay){
        NewScheduleDialog dialog = new NewScheduleDialog();
        Bundle params = new Bundle();
        params.putSerializable(MAIN_DAY,mainDay);
        dialog.setArguments(params);

        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            activityCallback = (SaveCallback) context;
        } catch(ClassCastException error){
            throw new ClassCastException("Activity: "+context.toString()+" do not implement SaveCallback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_new_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText initHour = view.findViewById(R.id.dns_init_hour);
        initHour.addTextChangedListener(new HourTextWatcher());

        EditText finishHour = view.findViewById(R.id.dns_finish_hour);
        finishHour.addTextChangedListener(new HourTextWatcher());

        AppCompatButton saveButton = view.findViewById(R.id.dns_submit_btn);
        CalendarView calendarView = view.findViewById(R.id.dns_calendar);

        assert getArguments() != null;
        ScheduleModel mainDay = (ScheduleModel) getArguments().getSerializable(MAIN_DAY);
        if(mainDay != null){
            calendarView.setDate(mainDay.getDate());
        }

        AtomicLong myDateMilis = new AtomicLong(calendarView.getDate());

        calendarView.setOnDateChangeListener((v, year, month, dayOfMonth) ->{
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            myDateMilis.set(calendar.getTimeInMillis());
        });

        saveButton.setOnClickListener( button -> {
            canSave = true;
            EditText eventName = view.findViewById(R.id.dns_event_name);

            String eventNameText = eventName.getText().toString();
            String initHourText = initHour.getText().toString();
            String finishHourText = finishHour.getText().toString();

            checkEmptyEditText(eventName);
            checkEmptyEditText(initHour);
            checkEmptyEditText(finishHour);

            if(canSave){
                ScheduleModel schedule = new ScheduleModel(eventNameText, initHourText, finishHourText, myDateMilis.get());
                activityCallback.saveScheduleModel(schedule);
                dismiss();
            }

        });
    }

    private void checkEmptyEditText(EditText editText){
        String text = editText.getText().toString();

        if(text.isEmpty()){
            editText.setHintTextColor(ContextCompat.getColor(getContext(), R.color.red_alert));
            canSave = false;
        }
    }

    public interface SaveCallback{
        void saveScheduleModel(ScheduleModel model);
    }

}
