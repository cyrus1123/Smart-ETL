import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ChatWithLlama {
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);
    private static final LinkedBlockingQueue<String> userQueue = new LinkedBlockingQueue<>();
    private static volatile boolean stopSignal = false;
    private static HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) {
        // Start listening to the user input (simulating speech recognition)
        executor.submit(ChatWithLlama::listenToUser);
        // Start managing chat interactions
        executor.submit(ChatWithLlama::manageChat);
    }

    // Simulate speech recognition - replace this with actual speech processing
    private static void listenToUser() {
        while (true) {
            try {
                System.out.println("Listening...");
                // Simulating user input
                String userInput = "Hello, Llama!"; // Replace with real speech-to-text
                System.out.println("Transcription: " + userInput);
                stopSignal = true;
                userQueue.put(userInput);
                TimeUnit.SECONDS.sleep(5); // Simulate delay
            } catch (Exception e) {
                System.err.println("Error in listenToUser: " + e.getMessage());
            }
        }
    }

    // Handle chat communication with Llama3.1
    private static void manageChat() {
        while (true) {
            try {
                String input = userQueue.take();
                stopSignal = false;
                chatWithLlama(input);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Error in manageChat: " + e.getMessage());
            }
        }
    }

    // Chat with Llama3.1 via Ollama
    private static void chatWithLlama(String text) {
        String json = "{\"model\":\"llama3.1\",\"prompt\":\"" + text + "\",\"stream\":true}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OLLAMA_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            // Send request and handle response asynchronously
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        if (response.statusCode() == 200) {
                            System.out.println("Bot: " + response.body());
                        } else {
                            System.err.println("Error: " + response.body());
                        }
                    });
        } catch (Exception e) {
            System.err.println("Error communicating with Ollama: " + e.getMessage());
        }
    }
}
