package service;

import model.Preference;
import model.User;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MatchmakingService {
    private final UserRepository userRepository;

    public MatchmakingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findMatchingUser(User user, Preference pref) {
        List<User> all = userRepository.findAll();
        List<User> matches = new ArrayList<>();
        if (user == null) return matches;
        for (User u : all) {
            if (Objects.equals(u.getLogin(), user.getLogin())) continue;
            boolean sameLanguage = user.getLanguage() != null && user.getLanguage().equalsIgnoreCase(u.getLanguage());
            boolean sameServer = user.getServer() != null && user.getServer().equalsIgnoreCase(u.getServer());
            if (sameLanguage && sameServer) {
                matches.add(u);
            }
        }
        return matches;
    }
}

