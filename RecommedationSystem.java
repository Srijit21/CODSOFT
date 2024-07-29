import java.util.*;

public class RecommedationSystem {

    // Sample data: user ratings for movies
    static Map<String, Map<String, Double>> userRatings = new HashMap<>();

    public static void main(String[] args) {
        // Sample data
        userRatings.put("Alice", Map.of("Inception", 5.0, "Titanic", 3.0, "Avatar", 4.0));
        userRatings.put("Bob", Map.of("Inception", 4.0, "Titanic", 2.0, "Avatar", 5.0, "Matrix", 4.0));
        userRatings.put("Carol", Map.of("Titanic", 5.0, "Avatar", 3.0, "Matrix", 5.0));

        // Generate recommendations for Alice
        List<String> recommendations = getRecommendations("Alice");
        System.out.println("Recommendations for Alice: " + recommendations);
    }

    // Function to get recommendations for a user
    public static List<String> getRecommendations(String user) {
        Map<String, Double> scores = new HashMap<>();
        Map<String, Double> similarities = new HashMap<>();

        for (String otherUser : userRatings.keySet()) {
            if (!otherUser.equals(user)) {
                double similarity = calculateSimilarity(userRatings.get(user), userRatings.get(otherUser));
                similarities.put(otherUser, similarity);
                for (Map.Entry<String, Double> entry : userRatings.get(otherUser).entrySet()) {
                    String item = entry.getKey();
                    Double rating = entry.getValue();
                    scores.put(item, scores.getOrDefault(item, 0.0) + similarity * rating);
                }
            }
        }

        // Exclude items the user has already rated
        Set<String> userItems = userRatings.get(user).keySet();
        scores.keySet().removeAll(userItems);

        // Sort items by score
        List<Map.Entry<String, Double>> sortedScores = new ArrayList<>(scores.entrySet());
        sortedScores.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        // Extract the top items
        List<String> recommendations = new ArrayList<>();
        for (Map.Entry<String, Double> entry : sortedScores) {
            recommendations.add(entry.getKey());
        }

        return recommendations;
    }

    // Function to calculate similarity between two users
    public static double calculateSimilarity(Map<String, Double> ratings1, Map<String, Double> ratings2) {
        Set<String> commonItems = new HashSet<>(ratings1.keySet());
        commonItems.retainAll(ratings2.keySet());

        if (commonItems.isEmpty())
            return 0.0;

        double sum = 0.0;
        for (String item : commonItems) {
            sum += ratings1.get(item) * ratings2.get(item);
        }

        return sum / (Math.sqrt(ratings1.values().stream().mapToDouble(r -> r * r).sum()) *
                Math.sqrt(ratings2.values().stream().mapToDouble(r -> r * r).sum()));
    }
}
