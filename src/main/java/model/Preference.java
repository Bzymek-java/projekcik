package model;

import java.util.List;

public class Preference {

    private int id;

    private String rank;

    private boolean preferredVoiceChat;

    private String preferredGame;

    private String csMap;

    private String lolMode;

    private String requiredMatchField;

    public Preference() {
    }

    public int calculateMatchScore(User currentUser, User otherUser) {

        int score = 0;

        Preference other = otherUser.getPreference();

        if (other == null) {
            return 0;
        }

        if (isRankCompatible(
                rank,
                other.rank,
                lolMode,
                other.lolMode
        )) {
            score += 3;
        }

        if (equals(
                currentUser.getServer(),
                otherUser.getServer()
        )) {
            score += 2;
        }

        if (equals(
                currentUser.getLanguage(),
                otherUser.getLanguage()
        )) {
            score += 2;
        }

        if (preferredVoiceChat ==
                other.preferredVoiceChat) {
            score += 1;
        }

        if (equals(csMap, other.csMap)) {
            score += 1;
        }

        if (equals(lolMode, other.lolMode)) {
            score += 1;
        }

        return score;
    }

    public boolean matchesRequiredField(
            User currentUser,
            User otherUser
    ) {

        Preference other =
                otherUser.getPreference();

        if (requiredMatchField == null
                || requiredMatchField.isBlank()) {
            return true;
        }

        switch (requiredMatchField.toLowerCase()) {

            case "rank":

                return isRankCompatible(
                        rank,
                        other.rank,
                        lolMode,
                        other.lolMode
                );

            case "game":
                return equals(
                        preferredGame,
                        other.preferredGame
                );

            case "voicechat":
                return preferredVoiceChat ==
                        other.preferredVoiceChat;

            case "csmap":
                return equals(
                        csMap,
                        other.csMap
                );

            case "lolmode":
                return equals(
                        lolMode,
                        other.lolMode
                );

            case "language":
                return equals(
                        currentUser.getLanguage(),
                        otherUser.getLanguage()
                );

            case "server":
                return equals(
                        currentUser.getServer(),
                        otherUser.getServer()
                );

            default:
                return true;
        }
    }

    private boolean isRankCompatible(
            String rank1,
            String rank2,
            String mode1,
            String mode2
    ) {

        if (rank1 == null || rank2 == null) {
            return false;
        }

        RankData r1 = parseRank(rank1);

        RankData r2 = parseRank(rank2);

        if (r1 == null || r2 == null) {
            return false;
        }

        if (
                ("MASTER".equals(r1.tier)
                        || "MASTER".equals(r2.tier))
                        &&
                        (
                                "SOLO_DUO".equalsIgnoreCase(mode1)
                                        ||
                                        "SOLO_DUO".equalsIgnoreCase(mode2)
                        )
        ) {

            return r1.tier.equals(r2.tier);
        }

        if (r1.tier.equals("DIAMOND")
                || r2.tier.equals("DIAMOND")) {

            return checkDiamondRules(r1, r2);
        }

        int diff =
                Math.abs(r1.tierIndex - r2.tierIndex);

        return diff <= 1;
    }

    private boolean checkDiamondRules(
            RankData r1,
            RankData r2
    ) {

        if (r1.tier.equals("DIAMOND")
                && r1.division == 1) {

            return r2.tier.equals("DIAMOND")
                    &&
                    (
                            r2.division == 1
                                    ||
                                    r2.division == 2
                    );
        }

        if (r2.tier.equals("DIAMOND")
                && r2.division == 1) {

            return r1.tier.equals("DIAMOND")
                    &&
                    (
                            r1.division == 1
                                    ||
                                    r1.division == 2
                    );
        }

        int diff =
                Math.abs(r1.tierIndex - r2.tierIndex);

        return diff <= 1;
    }

    private RankData parseRank(String rank) {

        try {

            String[] split =
                    rank.toUpperCase().split("_");

            String tier = split[0];

            int division = 4;

            if (split.length > 1) {
                division =
                        Integer.parseInt(split[1]);
            }

            List<String> tiers = List.of(
                    "IRON",
                    "BRONZE",
                    "SILVER",
                    "GOLD",
                    "PLATINUM",
                    "EMERALD",
                    "DIAMOND",
                    "MASTER"
            );

            int tierIndex =
                    tiers.indexOf(tier);

            return new RankData(
                    tier,
                    division,
                    tierIndex
            );

        } catch (Exception e) {

            return null;
        }
    }

    private boolean equals(String a, String b) {

        if (a == null || b == null) {
            return false;
        }

        return a.equalsIgnoreCase(b);
    }

    private static class RankData {

        String tier;

        int division;

        int tierIndex;

        RankData(
                String tier,
                int division,
                int tierIndex
        ) {

            this.tier = tier;
            this.division = division;
            this.tierIndex = tierIndex;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public boolean isPreferredVoiceChat() {
        return preferredVoiceChat;
    }

    public void setPreferredVoiceChat(boolean preferredVoiceChat) {
        this.preferredVoiceChat = preferredVoiceChat;
    }

    public String getPreferredGame() {
        return preferredGame;
    }

    public void setPreferredGame(String preferredGame) {
        this.preferredGame = preferredGame;
    }

    public String getCsMap() {
        return csMap;
    }

    public void setCsMap(String csMap) {
        this.csMap = csMap;
    }

    public String getLolMode() {
        return lolMode;
    }

    public void setLolMode(String lolMode) {
        this.lolMode = lolMode;
    }

    public String getRequiredMatchField() {
        return requiredMatchField;
    }

    public void setRequiredMatchField(String requiredMatchField) {
        this.requiredMatchField = requiredMatchField;
    }
}