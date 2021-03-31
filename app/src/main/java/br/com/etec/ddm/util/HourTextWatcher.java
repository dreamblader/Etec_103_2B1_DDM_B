package br.com.etec.ddm.util;

import android.text.Editable;
import android.text.TextWatcher;

public class HourTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int length = s.length();

        if(length > 5){
            s.delete(5, length);
        } else if(length >= 4){
            checkForDoubleDot(s);
            checkHour(s);
            checkMinute(s);
        }
    }

    private void checkForDoubleDot(Editable s){ //14:15
        char doubleDotPositionChar = s.charAt(2);
        if(doubleDotPositionChar != ':'){
            s.insert(2,":");
        }
    }

    private void checkMinute(Editable s){
        int minuteDecimalValue = Character.getNumericValue(s.charAt(3));
        if(minuteDecimalValue > 5){
            s.replace(3,s.length(), "59");
        }
    }

    private void checkHour(Editable s){
        int hourDecimalValue = Character.getNumericValue(s.charAt(0));
        int hourUnitValue = Character.getNumericValue(s.charAt(1));

        if(hourDecimalValue > 2){
            s.replace(0,1, "2");
        } else if (hourDecimalValue == 2 && hourUnitValue > 3){
            s.replace(1,2, "3");
        }
    }
}
