package br.com.etec.ddm.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import br.com.etec.ddm.R;
import br.com.etec.ddm.activity.MainActivity;
import br.com.etec.ddm.model.ScheduleModel;

import static br.com.etec.ddm.activity.MainActivity.MY_DAY;

public class CalendarDetailDialog extends BottomSheetDialogFragment {

    private static final String MAIN_DAY = "MAIN_DAY";

    public static CalendarDetailDialog newInstance(ScheduleModel mainDay){
        CalendarDetailDialog dialog = new CalendarDetailDialog();
        Bundle params = new Bundle();
        params.putSerializable(MAIN_DAY,mainDay);
        dialog.setArguments(params);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_calendar_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatButton checkButton = view.findViewById(R.id.dcd_ok_btn);
        CalendarView calendarView = view.findViewById(R.id.dcd_calendar);

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

        checkButton.setOnClickListener(button -> {
            ScheduleModel result = new ScheduleModel(myDateMilis.get());
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(MY_DAY, result);
            startActivity(intent);
        });
    }
}
