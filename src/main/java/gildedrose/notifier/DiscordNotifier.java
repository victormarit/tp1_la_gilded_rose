package gildedrose.notifier;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DiscordNotifier {
    DiscordNotifier INSTANCE = new DiscordNotifier();

    public DiscordNotifier getInstance() {
        return INSTANCE;
    }

    public static final String url = "https://discord.com/api/webhooks/1050164555776524420/VdySrPimgSKWQuo9PduT8mj-Cvku68V2B29lMgX19lz-yFbjE8ZLWbcxxlXYwMCwjGgI";

    public static int statusCode = 0;

    public static void notify(String message) throws IOException {
        URL urlLocal = new URL(DiscordNotifier.url);
        HttpURLConnection con = (HttpURLConnection) urlLocal.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = "{\"content\": \"" + message + "\"}";
        try (var os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        InputStream inputStream = con.getInputStream();

        inputStream.close();
        statusCode = con.getResponseCode();
        con.disconnect();
    }
}
