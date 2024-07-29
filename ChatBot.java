import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatBot {
    private static Map<String, String> predefinedResponses;

    public static void main(String[] args) {
        initializeResponses();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! I'm your enhanced chatbot. How can I help you today?");

        while (true) {
            String userInput = scanner.nextLine().toLowerCase().trim();

            if (userInput.equals("exit") || userInput.equals("quit")) {
                System.out.println("Goodbye! Have a great day!");
                break;
            }

            String response = getResponse(userInput);
            System.out.println(response);
        }

        scanner.close();
    }

    private static void initializeResponses() {
        predefinedResponses = new HashMap<>();
        predefinedResponses.put("hi", "Hello! How can I assist you?");
        predefinedResponses.put("hello", "Hi there! How can I help you?");
        predefinedResponses.put("how are you",
                "I'm just a program, so I don't have feelings, but thank you for asking!");
        predefinedResponses.put("what's your name", "I'm Jenny! The ChatBot.");
        predefinedResponses.put("what can you do",
                "I can chat with you based on predefined rules. How can I assist you?");

    }

    private static String getResponse(String userInput) {
        for (Map.Entry<String, String> entry : predefinedResponses.entrySet()) {
            if (userInput.matches(entry.getKey() + ".*")) {
                return entry.getValue();
            }
        }

        return matchPattern(userInput);
    }

    private static String matchPattern(String userInput) {
        Pattern weatherPattern = Pattern.compile(".*\\b(weather|temperature|forecast)\\b.*");
        Matcher weatherMatcher = weatherPattern.matcher(userInput);

        if (weatherMatcher.matches()) {
            return "I'm not able to check the weather for you, but you can use weather apps or websites for that.";
        }

        Pattern namePattern = Pattern.compile(".*\\b(name|who are you)\\b.*");
        Matcher nameMatcher = namePattern.matcher(userInput);

        if (nameMatcher.matches()) {
            return "I'm Jenny! The ChatBot.";
        }

        return "I'm sorry, I don't understand that. Can you please rephrase?";
    }
}
