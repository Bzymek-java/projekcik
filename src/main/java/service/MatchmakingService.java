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

    public List<User> findMatchingUsers(User currentUser) {

        List<User> matches = new ArrayList<>();

        List<User> allUsers = userRepository.findAll();

        if (currentUser == null ||
                currentUser.getPreference() == null) {
            return matches;
        }

        Preference currentPreference =
                currentUser.getPreference();

        for (User otherUser : allUsers) {

            if (Objects.equals(
                    currentUser.getLogin(),
                    otherUser.getLogin()
            )) {
                continue;
            }

            if (otherUser.getPreference() == null) {
                continue;
            }

            boolean requiredMatches =
                    currentPreference.matchesRequiredField(
                            currentUser,
                            otherUser
                    );

            if (!requiredMatches) {
                continue;
            }

            int score =
                    currentPreference.calculateMatchScore(
                            currentUser,
                            otherUser
                    );

            // minimum sensownego matcha
            if (score >= 6) {
                matches.add(otherUser);
            }
        }

        return matches;
    }
}