package com.example.samsungschoolproject.util;

import com.example.samsungschoolproject.enums.SwitchToWeekStates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class CalendarUtils {
    public static LocalDate selectedDate, dateToScroll;
    // selectedDate - выбранная при нажатии дата;
    // dateToScroll - дата, относительно которой будет "прокрутка";
    public static SwitchToWeekStates state;
    // Необходимо чтобы уловить первый вход в WeekFragment.
    // Используется чтобы отобразить неделю, включающую в себя именно выбранный нажатием день,
    // при первом переходе во фрагмент переходе.
    public static int selectedDatePosition;
    // Позиция position выбранного нажатием дня в массиве days CalendarAdapter'а.
    // Необходимо для обновления окраса ячейки

    private static HashMap<String, String> parseMonth = new HashMap<String, String>() {{
            put("января", "Январь");
            put("февраля", "Февраль");
            put("марта", "Март");
            put("апреля", "Апрель");
            put("мая", "Май");
            put("июня", "Июнь");
            put("июля", "Июль");
            put("августа", "Август");
            put("сентября", "Сентябрь");
            put("октября", "Октябрь");
            put("ноября", "Ноябрь");
            put("декабря", "Декабрь");
    }};

    // Возвращает строку в формате "Месяц Год", чтобы отобразить ее вверху CalendarFragment
    public static String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        String[] unparsedDate = date.format(formatter).split(" ");
        return parseMonth.get(unparsedDate[0]) + " " + unparsedDate[1];
    }

    // Возвращает набор дней для месяца
    public static ArrayList<LocalDate> daysInMonthArray(LocalDate date){
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = CalendarUtils.dateToScroll.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 2; i <= 41; i++){
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek){
                daysInMonthArray.add(null);
            }
            else {
                daysInMonthArray.add(LocalDate.of(dateToScroll.getYear(), dateToScroll.getMonth(), i - dayOfWeek));
            }
        }

        return daysInMonthArray;
    }

    // Возвращает массив дней LocalDate для необходимой недели
    public static ArrayList<LocalDate> daysInWeekArray(LocalDate date){
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate startDate;

        if (state == SwitchToWeekStates.JUST_SWITCHED_TO_WEEK_MODE && selectedDate.getMonth().equals(dateToScroll.getMonth())) {
            startDate = mondayForDate(selectedDate);
            dateToScroll = selectedDate;
            state = SwitchToWeekStates.NOT_JUST_SWITCHED_TO_WEEK_MODE;
        }
        else{
            startDate = mondayForDate(dateToScroll);
        }
        LocalDate endDate = startDate.plusWeeks(1);

        while (startDate.isBefore(endDate)){
            days.add(startDate);
            startDate = startDate.plusDays(1);
        }

        return days;
    }

    // Возвращает понедельник для текущего dateToScroll
    private static LocalDate mondayForDate(LocalDate current) {
        LocalDate oneWeekAgo = current.minusWeeks(1);

        while (current.isAfter(oneWeekAgo)){
            if (current.getDayOfWeek() == DayOfWeek.MONDAY){
                return current;
            }
            current = current.minusDays(1);
        }

        return null;
    }
}
