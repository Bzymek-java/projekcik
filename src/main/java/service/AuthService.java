package service;

import model.User;
import repository.UserRepository;

import java.util.Objects;
import java.util.Scanner;

public class AuthService {

    private final UserRepository userRepository;
    private final Scanner scanner;

    public AuthService(UserRepository repo) {
        this.userRepository = repo;
        this.scanner = new Scanner(System.in);
    }

    // Rejestracja użytkownika przez wpisanie danych z konsoli
    public User register() {

        User user = new User();

        System.out.println("=== REJESTRACJA ===");

        System.out.print("Login: ");
        String login = scanner.nextLine();

        // sprawdzanie czy login istnieje
        if (userRepository.findByLogin(login) != null) {
            throw new IllegalArgumentException("Login already exists");
        }

        System.out.print("Hasło: ");
        String password = scanner.nextLine();

        System.out.print("Wiek: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.print("Płeć: ");
        String sex = scanner.nextLine();

        System.out.print("Język: ");
        String language = scanner.nextLine();

        System.out.print("Serwer: ");
        String server = scanner.nextLine();

        // ustawianie danych
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        user.setSex(sex);
        user.setLanguage(language);
        user.setServer(server);

        // zapis do CSV
        userRepository.save(user);

        System.out.println("Użytkownik został zarejestrowany.");

        return user;
    }

    // logowanie
    public User login() {

        System.out.println("=== LOGOWANIE ===");

        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Hasło: ");
        String password = scanner.nextLine();

        User user = userRepository.findByLogin(login);

        if (user == null) {
            System.out.println("Użytkownik nie istnieje.");
            return null;
        }

        if (checkPassword(password, user.getPassword())) {
            System.out.println("Zalogowano pomyślnie.");
            return user;
        }

        System.out.println("Niepoprawne hasło.");
        return null;
    }

    private boolean checkPassword(String input, String stored) {
        return Objects.equals(input, stored);
    }
}