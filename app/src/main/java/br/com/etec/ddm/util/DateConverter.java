package br.com.etec.ddm.util;

public class DateConverter {
	private static String[] _daysOfWeek = {"Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sabado"};
    private static String[] _monthsNames = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

    static public String dayOfWeek(int day) {
        day -= 1;
        return (day < _daysOfWeek.length && day > 0) ? _daysOfWeek[day] : "";
    }

    static public String monthName(int month){
        return (month < _monthsNames.length && month > 0) ? _monthsNames[month] : "";
    }
}
