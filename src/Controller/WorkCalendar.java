package Controller;

import DataBase.ScheduleFactory;
import Model.Schedule;
import Model.Shift;
import Model.Worker;
import View.ConsoleHelper;
import java.sql.*;
import java.util.Calendar;

/**
 * Created by Евгений on 15.04.2016.
 */
public class WorkCalendar {
    public static void main(String[] args) throws SQLException {
        WorkCalendar workCalendar = new WorkCalendar();
        workCalendar.setSchedule(ScheduleFactory.get(13));
        workCalendar.execute(2016, 5);

        System.out.println();
        ConsoleHelper.writeCalendarMap(workCalendar.getDayMap());
        System.out.println();
        ConsoleHelper.writeWorkerMap(workCalendar.getWorkerMap());
        System.out.println();
        ConsoleHelper.writeShiftMap(workCalendar.getShiftMap());
    }

    /** Параметр - график */
    private Schedule schedule;
    /** Параметр - рассматриваемый месяц */
    private int month;
    /** Параметр - рассматриваемый год */
    private int year;

    /** Параметр - "карта" дней */
    private int[][] dayMap = new int[6][7];
    /** Параметр - "карта" работников */
    private Worker[][] workerMap = new Worker[6][7];
    /** Параметр - "карта смен" */
    private Shift[][] shiftMap = new Shift[6][7];

    /**
     * Рассчитывает рабочий график текущего месяца
     * @param year Текущий год
     * @param month Текущий месяц
     */
    public void execute(int year, int month) {
        this.year = year;
        this.month = month;

        dayMap = calculateDayMap(year, month);
        shiftMap = schedule.calculateShiftMap(year, month, dayMap);
        workerMap = schedule.calculateWorkerMap(year, month, dayMap);
    }

    /**
     * Расчитывает "карту" дней
     * @param year Рассматриваемый год
     * @param month Рассматриваемый месяц
     * @return "Карта" чисел
     */
    public int[][] calculateDayMap(int year, int month) {
        /** Определяем параметры месяца */
        Calendar myCalendar = (Calendar) Calendar.getInstance().clone();
        myCalendar.set(year, month, 1);
        /** Количество дней в месяце */
        int monthLength = myCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); //
        /** День недели первого числа этого месяца */
        int dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK) - 2 == -1 ? 6 : myCalendar.get(Calendar.DAY_OF_WEEK) - 2;

        /** Заполняем карту*/
        int[][] dayMap = new int[6][7];
        int j = dayOfWeek;
        int k = 0;
        for (int i = 1; i <= monthLength; i++) {
            if (j == 7) {
                j = 0;
                k++;
            }
            dayMap[k][j] = i;
            j++;
        }

        return dayMap;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int[][] getDayMap() {
        return dayMap;
    }

    public void setDayMap(int[][] dayMap) {
        this.dayMap = dayMap;
    }

    public Worker[][] getWorkerMap() {
        return workerMap;
    }

    public void setWorkerMap(Worker[][] workerMap) {
        this.workerMap = workerMap;
    }

    public Shift[][] getShiftMap() {
        return shiftMap;
    }

    public void setShiftMap(Shift[][] shiftMap) {
        this.shiftMap = shiftMap;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
