package domaine;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name ="user")
public class User {
    private String password;
    private String name;
    private String login;
    private String _id;

    public User(String password, String name, String login, String _id) {
        this.password = password;
        this.name = name;
        this.login = login;
        this._id = _id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String get_id() {
        return _id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
