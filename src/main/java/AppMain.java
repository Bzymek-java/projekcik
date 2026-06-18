import model.Preference;
import model.User;
import repository.CsvUserRepository;
import repository.UserRepository;
import service.AuthService;
import service.MatchmakingService;

import java.util.List;
import java.util.Scanner;

public class AppMain {

    public static void main(String[] args) {

        UserRepository repo =
                new CsvUserRepository();

        AuthService auth =
                new AuthService(repo);

        MatchmakingService matchmakingService =
                new MatchmakingService(repo);

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("""
                    
                    ===== GAME MATCH =====
                    1. Rejestracja
                    2. Logowanie
                    3. Exit
                    """);

            String option = scanner.nextLine();

            switch (option) {

                case "1":

                    try {

                        User u = auth.register();

                        System.out.println(
                                "Zarejestrowano: "
                                        + u.getLogin()
                        );

                    } catch (Exception ex) {

                        System.out.println(
                                "Błąd rejestracji: "
                                        + ex.getMessage()
                        );
                    }

                    break;

                case "2":

                    User logged = auth.login();

                    if (logged == null) {

                        System.out.println(
                                "Błędne dane logowania."
                        );

                        break;
                    }

                    System.out.println(
                            "Zalogowano jako: "
                                    + logged.getLogin()
                    );

                    System.out.println("""
                            
                            === MATCHMAKING ===
                            """);

                    List<User> matches =
                            matchmakingService
                                    .findMatchingUsers(logged);

                    if (matches.isEmpty()) {

                        System.out.println(
                                "Brak dopasowań."
                        );

                        break;
                    }

                    for (User user : matches) {

                        Preference p =
                                user.getPreference();

                        int score =
                                logged.getPreference()
                                        .calculateMatchScore(
                                                logged,
                                                user
                                        );

                        int percent =
                                (score * 100) / 13;

                        System.out.println("""
                                
                                -------------------
                                Login: %s
                                Gra: %s
                                Rank: %s
                                VC: %s
                                Server: %s
                                Language: %s
                                Match: %d%%
                                -------------------
                                """
                                .formatted(
                                        user.getLogin(),
                                        p.getPreferredGame(),
                                        p.getRank(),
                                        p.isPreferredVoiceChat(),
                                        user.getServer(),
                                        user.getLanguage(),
                                        percent
                                ));
                    }

                    break;

                case "3":

                    System.out.println(
                            "Zamknięto aplikację."
                    );

                    return;

                default:

                    System.out.println(
                            "Niepoprawna opcja."
                    );
            }
        }
    }
}