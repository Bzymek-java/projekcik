package repository;

import model.Preference;
import model.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvUserRepository implements UserRepository {

    private final String filePath;

    public CsvUserRepository() {
        this("users.csv");
    }

    public CsvUserRepository(String filePath) {

        this.filePath = filePath;

        try {

            File f = new File(filePath);

            if (!f.exists()) {
                f.createNewFile();
            }

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByLogin(String login) {

        for (User user : findAll()) {

            if (user.getLogin().equals(login)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();

        try (
                BufferedReader br =
                        Files.newBufferedReader(
                                Paths.get(filePath)
                        )
        ) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",", -1);

                User user = new User();

                user.setId(Integer.parseInt(parts[0]));
                user.setLogin(parts[1]);
                user.setPassword(parts[2]);
                user.setAge(Integer.parseInt(parts[3]));
                user.setSex(parts[4]);
                user.setLanguage(parts[5]);
                user.setServer(parts[6]);

                Preference preference =
                        new Preference();

                if (parts.length > 7)
                    preference.setRank(parts[7]);

                if (parts.length > 8)
                    preference.setPreferredVoiceChat(
                            Boolean.parseBoolean(parts[8])
                    );

                if (parts.length > 9)
                    preference.setPreferredGame(parts[9]);

                if (parts.length > 10)
                    preference.setCsMap(parts[10]);

                if (parts.length > 11)
                    preference.setLolMode(parts[11]);

                if (parts.length > 12)
                    preference.setRequiredMatchField(parts[12]);

                user.setPreference(preference);

                users.add(user);
            }

        } catch (IOException e) {

            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public synchronized void save(User user) {

        List<User> all = findAll();

        int maxId = 0;

        for (User u : all) {

            if (u.getId() > maxId) {
                maxId = u.getId();
            }
        }

        if (user.getId() <= 0) {
            user.setId(maxId + 1);
        }

        Preference p = user.getPreference();

        String line = String.join(",",
                String.valueOf(user.getId()),
                user.getLogin(),
                user.getPassword(),
                String.valueOf(user.getAge()),
                user.getSex(),
                user.getLanguage(),
                user.getServer(),

                p.getRank(),
                String.valueOf(p.isPreferredVoiceChat()),
                p.getPreferredGame(),
                p.getCsMap(),
                p.getLolMode(),
                p.getRequiredMatchField()
        );

        try (
                BufferedWriter bw =
                        new BufferedWriter(
                                new FileWriter(filePath, true)
                        )
        ) {

            bw.write(line);
            bw.newLine();

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}