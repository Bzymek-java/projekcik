import model.User;
import repository.CsvUserRepository;
import repository.UserRepository;
import service.AuthService;

import java.util.List;

public class AppMain {
    public static void main(String[] args) {

        UserRepository repo = new CsvUserRepository();
        AuthService auth = new AuthService(repo);

        try {
            User u = auth.register();
            System.out.println("Zarejestrowano użytkownika: " + u.getLogin());

        } catch (Exception ex) {
            System.out.println("Rejestracja nie powiodła się: " + ex.getMessage());
        }

        User logged = auth.login();
        if (logged != null) {
            System.out.println("Zalogowano jako: " + logged.getLogin());
        }

        List<User> all = repo.findAll();
        System.out.println("Wszyscy użytkownicy w CSV:");

        for (User user : all) {
            System.out.println(
                    user.getId() + ": " +
                            user.getLogin() + " (" +
                            user.getLanguage() + ")"
            );
        }
    }
}