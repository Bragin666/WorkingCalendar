package Model;

import DataBase.ChangeFactory;
import DataBase.ScheduleFactory;
import DataBase.ShiftFactory;
import DataBase.WorkerFactory;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Рабочий график
 * Created by Евгений on 16.05.2016.
 */

@Entity
@Table(name = "schedules")
public class Schedule implements Serializable {
    /** Параметр - идентификацтонный номер */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /** Параметр - название графика */
    private String name;
    /** Параметр - пользователь */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /** Параметр - начальная дата графика рабочих*/
    @Temporal(TemporalType.TIMESTAMP)
    private Date workerScheduleStartData;
    /** Параметр - последовательность рабочих в графике*/
    @ElementCollection
    @CollectionTable(name ="workerSchedule")
    private List<Integer> workerSchedule = new ArrayList<>();
    /** Параметр - начальная дата графика смен*/
    @Temporal(TemporalType.TIMESTAMP)
    private Date shiftScheduleStartData;
    /** Параметр - последовательность смен в графике*/
    @ElementCollection
    @CollectionTable(name ="shiftSchedule")
    private List<Integer> shiftSchedule = new ArrayList<>();

    /** Параметр - набор работников */
    @OneToMany(mappedBy = "schedule")
    private Set<Worker> workers = new HashSet<>();
    /** Параметр - набор смен */
    @OneToMany(mappedBy = "schedule")
    private Set<Shift> shifts = new HashSet<>();
    /** Параметр - набор замен */
    @OneToMany(mappedBy = "schedule")
    private Set<Change> changes = new HashSet<>();

    /**
     * Добавляет новый график в базу данных
     * @param name
     */
    public Schedule(String name, User user) {
        this.name = name;
        this.user = user;
        ScheduleFactory.add(this);
    }

    /**
     * Обновляет график в базе данных
     */
    public void update() {
        ScheduleFactory.update(this);
    }

    /**
     * Расчитывает "карту" работников
     * @param year Рассматриваемый год
     * @param month Рассматриваемый месяц
     * @param dayMap "Карта" чисел
     * @return "Карта" работников
     */
    public Worker[][] calculateWorkerMap(int year, int month, int[][] dayMap) {
        Worker[][] workerMap = new Worker[6][7];

        if (workerScheduleStartData != null && workerSchedule.size() != 0) {
            ArrayList<Worker> workersLine = getWorkerLine();

            Date thisDate = new Date(year - 1900, month, 1);
            int dayDifference = (int) ((workerScheduleStartData.getTime() - thisDate.getTime()) / 86_400_000);
            int dayInSchedule;
            int remainder = (dayDifference - dayDifference / workerSchedule.size() * workerSchedule.size());
            if (remainder> 0) dayInSchedule = workerSchedule.size() - remainder;
            else if (remainder == 0) dayInSchedule = 0;
            else dayInSchedule = - remainder;

            int k = dayInSchedule;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    if (dayMap[i][j] == 0) workerMap[i][j] = null;
                    else {
                        workerMap[i][j] = workersLine.get(k);
                        k = k == workersLine.size() - 1 ? 0 : k + 1;
                    }
                }
            }
        }

        /** Проверка на замены */
        List<Change> thisMonthChanges = ChangeFactory.getThisMonthChanges(this, year, month);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (dayMap[i][j] != 0) {
                    for (Change change : thisMonthChanges) {
                        if (change.getDay() == dayMap[i][j]) {
                            workerMap[i][j] = change.getWorker();
                            break;
                        }
                    }
                }
            }
        }

        return workerMap;
    }

    /**
     * Возвращает последовательность работников согласно графику
     * @return ArrayList<Worker> - последовательность работников согласно графику
     */
    private ArrayList<Worker> getWorkerLine() {
        ArrayList<Worker> workersLine = new ArrayList<>();
        HashMap<Integer,Integer> ids = new HashMap<>();

        for (int i = 0; i <  workerSchedule.size(); i++) {
            int id = workerSchedule.get(i);
            if (!ids.containsKey(id)) {
                workersLine.add(WorkerFactory.get(id));
                ids.put(id,i);
            }
            else workersLine.add(workersLine.get(ids.get(id)));
        }

        return workersLine;
    }

    /**
     * Расчитывает "карту" смен
     * @param year Рассматриваемый год
     * @param month Рассматриваемый месяц
     * @param dayMap "Карта" чисел
     * @return "Карта" смен
     */
    public Shift[][] calculateShiftMap(int year, int month, int[][] dayMap) {
        Shift[][] shiftMap = new Shift[6][7];

        if (shiftScheduleStartData != null && shiftSchedule.size() != 0) {
            ArrayList<Shift> shiftsLine = getShiftLine();

            Date thisDate = new Date(year - 1900, month, 1);
            int dayDifference = (int) ((shiftScheduleStartData.getTime() - thisDate.getTime()) / 86_400_000);
            int dayInSchedule;
            int remainder = (dayDifference - dayDifference / shiftSchedule.size() * shiftSchedule.size());
            if (remainder > 0) dayInSchedule = shiftSchedule.size() - remainder;
            else if (remainder == 0) dayInSchedule = 0;
            else dayInSchedule = - remainder;

            int k = dayInSchedule;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    if (dayMap[i][j] == 0) shiftMap[i][j] = null;
                    else {
                        shiftMap[i][j] = shiftsLine.get(k);
                        k = k == shiftsLine.size() - 1 ? 0 : k + 1;
                    }
                }
            }
        }

        /** Проверка на замены */
        List<Change> thisMonthChanges = ChangeFactory.getThisMonthChanges(this, year, month);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (dayMap[i][j] != 0) {
                    for (Change change : thisMonthChanges) {
                        if (change.getDay() == dayMap[i][j]) {
                            shiftMap[i][j] = change.getShift();
                            break;
                        }
                    }
                }
            }
        }

        return shiftMap;
    }

    /**
     * Возвращает последовательность смен согласно графику
     * @return ArrayList<Shift> - последовательность смен согласно графику
     */
    private ArrayList<Shift> getShiftLine() {
        ArrayList<Shift> shiftLine = new ArrayList<>();
        HashMap<Integer,Integer> ids = new HashMap<>();

        for (int i = 0; i <  shiftSchedule.size(); i++) {
            int id = shiftSchedule.get(i);
            if (!ids.containsKey(id)) {
                shiftLine.add(ShiftFactory.get(id));
                ids.put(id,shiftLine.size() - 1);
            }
            else shiftLine.add(shiftLine.get(ids.get(id)));
        }

        return shiftLine;
    }

    public Schedule() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getWorkerScheduleStartData() {
        return workerScheduleStartData;
    }

    public void setWorkerScheduleStartData(Date workerScheduleStartData) {
        this.workerScheduleStartData = workerScheduleStartData;
    }

    public List<Integer> getWorkerSchedule() {
        return workerSchedule;
    }

    public void setWorkerSchedule(List<Integer> workerSchedule) {
        this.workerSchedule = workerSchedule;
    }

    public Date getShiftScheduleStartData() {
        return shiftScheduleStartData;
    }

    public void setShiftScheduleStartData(Date shiftScheduleStartData) {
        this.shiftScheduleStartData = shiftScheduleStartData;
    }

    public List<Integer> getShiftSchedule() {
        return shiftSchedule;
    }

    public void setShiftSchedule(List<Integer> shiftSchedule) {
        this.shiftSchedule = shiftSchedule;
    }

    public Set<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(Set<Worker> workers) {
        this.workers = workers;
    }

    public Set<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(Set<Shift> shifts) {
        this.shifts = shifts;
    }

    public Set<Change> getChanges() {
        return changes;
    }

    public void setChanges(Set<Change> changes) {
        this.changes = changes;
    }
}
