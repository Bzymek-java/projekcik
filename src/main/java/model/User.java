package model;

public class User {
    private int id;
    private String login;
    private String password;
    private int age;
    private String sex;
    private String language;
    private String server;

    public User() {}

    public void updatePreferences(Preference p) {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getServer() { return server; }
    public void setServer(String server) { this.server = server; }
}
