package Model;

import DataBase.UserFactory;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Пользователь
 * Created by Евгений on 31.05.2016.
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    /** Параметр - идентификационный номер */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /** Параметр - логин */
    private String login;
    /** Параметр - пароль */
    private String password;
    /** Параметр - доступные графики */
    @OneToMany(mappedBy = "user")
    private Set<Schedule> schedules;

    /**
     * Добавляет нового пользователя в базу данных
     * @param login логин
     * @param password пароль
     */
    public User(String login, String password) {
        this.login = login;
        this.password = password;
        UserFactory.add(this);
    }

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
