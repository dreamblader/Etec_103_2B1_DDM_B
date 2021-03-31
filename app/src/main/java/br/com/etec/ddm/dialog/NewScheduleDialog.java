package br.com.etec.ddm.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import br.com.etec.ddm.R;
import br.com.etec.ddm.util.HourTextWatcher;

public class NewScheduleDialog extends BottomSheetDialogFragment {

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
    }
}
