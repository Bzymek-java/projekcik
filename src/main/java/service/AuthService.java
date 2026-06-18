package service;

import model.Preference;
import model.User;
import repository.UserRepository;

import java.util.Scanner;

public class AuthService {

    private final UserRepository userRepository;

    private final Scanner scanner;

    public AuthService(UserRepository userRepository) {

        this.userRepository = userRepository;

        this.scanner = new Scanner(System.in);
    }

    public User register() {

        User user = new User();

        System.out.println("=== REJESTRACJA ===");

        System.out.print("Login: ");
        user.setLogin(scanner.nextLine());

        System.out.print("Hasło: ");
        user.setPassword(scanner.nextLine());

        System.out.print("Wiek: ");
        user.setAge(Integer.parseInt(scanner.nextLine()));

        System.out.print("Płeć: ");
        user.setSex(scanner.nextLine());

        System.out.print("Język: ");
        user.setLanguage(scanner.nextLine());

        Preference preference = new Preference();

        System.out.println("""
                
                Wybierz grę:
                1. LOL
                2. CS
                """);

        String gameChoice = scanner.nextLine();

        if (gameChoice.equals("1")) {
            preference.setPreferredGame("LOL");
        } else {
            preference.setPreferredGame("CS");
        }

        user.setServer(
                chooseServer(
                        preference.getPreferredGame()
                )
        );

        System.out.print("Preferowany VC (true/false): ");

        preference.setPreferredVoiceChat(
                Boolean.parseBoolean(scanner.nextLine())
        );

        if (preference.getPreferredGame()
                .equalsIgnoreCase("LOL")) {

            preference.setRank(
                    chooseLolRank()
            );

            preference.setLolMode(
                    chooseLolMode()
            );
        }

        if (preference.getPreferredGame()
                .equalsIgnoreCase("CS")) {

            System.out.print("""
                    
                    Rank CS:
                    SILVER
                    GOLD
                    AK
                    SHERIFF
                    GLOBAL
                    
                    Podaj rangę:
                    """);

            preference.setRank(scanner.nextLine());

            preference.setCsMap(
                    chooseCsMap()
            );
        }

        System.out.print("""
                
                Obowiązkowy match:
                rank
                game
                voiceChat
                csMap
                lolMode
                language
                server
                
                Wybór:
                """);

        preference.setRequiredMatchField(
                scanner.nextLine()
        );

        user.setPreference(preference);

        userRepository.save(user);

        return user;
    }

    public User login() {

        System.out.println("=== LOGOWANIE ===");

        System.out.print("Login: ");

        String login = scanner.nextLine();

        System.out.print("Hasło: ");

        String password = scanner.nextLine();

        User user = userRepository.findByLogin(login);

        if (user == null) {
            return null;
        }

        if (!user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }

    private String chooseServer(String game) {

        if (game.equalsIgnoreCase("LOL")) {

            System.out.println("""
                    
                    Wybierz serwer LoL:
                    1. EUNE
                    2. EUW
                    3. NA
                    4. KR
                    """);

            int choice =
                    Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    return "EUNE";

                case 2:
                    return "EUW";

                case 3:
                    return "NA";

                case 4:
                    return "KR";

                default:
                    return "EUNE";
            }
        }

        System.out.println("""
                
                Wybierz serwer CS2:
                1. Frankfurt
                2. Warsaw
                3. Vienna
                4. Stockholm
                5. Madrid
                6. Amsterdam
                """);

        int choice =
                Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1:
                return "Frankfurt";

            case 2:
                return "Warsaw";

            case 3:
                return "Vienna";

            case 4:
                return "Stockholm";

            case 5:
                return "Madrid";

            case 6:
                return "Amsterdam";

            default:
                return "Frankfurt";
        }
    }

    private String chooseLolRank() {

        System.out.println("""
                
                Wybierz tier:
                1. IRON
                2. BRONZE
                3. SILVER
                4. GOLD
                5. PLATINUM
                6. EMERALD
                7. DIAMOND
                8. MASTER
                """);

        int tierChoice =
                Integer.parseInt(scanner.nextLine());

        String tier;

        switch (tierChoice) {

            case 1:
                tier = "IRON";
                break;

            case 2:
                tier = "BRONZE";
                break;

            case 3:
                tier = "SILVER";
                break;

            case 4:
                tier = "GOLD";
                break;

            case 5:
                tier = "PLATINUM";
                break;

            case 6:
                tier = "EMERALD";
                break;

            case 7:
                tier = "DIAMOND";
                break;

            case 8:
                return "MASTER";

            default:
                return "GOLD_4";
        }

        System.out.println("""
                
                Wybierz dywizję:
                1. I
                2. II
                3. III
                4. IV
                """);

        int divisionChoice =
                Integer.parseInt(scanner.nextLine());

        int division;

        switch (divisionChoice) {

            case 1:
                division = 1;
                break;

            case 2:
                division = 2;
                break;

            case 3:
                division = 3;
                break;

            default:
                division = 4;
        }

        return tier + "_" + division;
    }

    private String chooseLolMode() {

        System.out.println("""
                
                Wybierz tryb:
                1. SOLO_DUO
                2. FLEX
                3. ARAM
                4. NORMAL
                """);

        int choice =
                Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1:
                return "SOLO_DUO";

            case 2:
                return "FLEX";

            case 3:
                return "ARAM";

            case 4:
                return "NORMAL";

            default:
                return "NORMAL";
        }
    }

    private String chooseCsMap() {

        System.out.println("""
                
                Wybierz mapę:
                1. Mirage
                2. Inferno
                3. Dust2
                4. Nuke
                5. Ancient
                """);

        int choice =
                Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1:
                return "Mirage";

            case 2:
                return "Inferno";

            case 3:
                return "Dust2";

            case 4:
                return "Nuke";

            case 5:
                return "Ancient";

            default:
                return "Mirage";
        }
    }
}