import model.User;
import repository.CsvUserRepository;
import repository.UserRepository;
import service.AuthService;

import java.util.List;

public class AppMain {
    public static void main(String[] args) {
        UserRepository repo = new CsvUserRepository();
        AuthService auth = new AuthService(repo);

        User u = new User();
        u.setLogin("bob");
        u.setPassword("pass123");
        u.setAge(30);
        u.setLanguage("en");
        u.setSex("M");
        u.setServer("EUNE");

        try {
            auth.register(u);
            System.out.println("Zarejestrowano użytkownika: " + u.getLogin());
        } catch (Exception ex) {
            System.out.println("Rejestracja nie powiodła się: " + ex.getMessage());
        }

        List<User> all = repo.findAll();
        System.out.println("Wszyscy użytkownicy w CSV:");
        for (User user : all) {
            System.out.println(user.getId() + ": " + user.getLogin() + " (" + user.getLanguage() + ")");
        }
    }
}

