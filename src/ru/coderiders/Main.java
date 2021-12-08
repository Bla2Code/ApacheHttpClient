package ru.coderiders;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    final static int CONNECTION_TIMEOUT = 10000;

    public static void main(String[] args) throws IOException {
        System.out.println("HI IT'S POST");

        URL url = new URL ("https://api.challonge.com/v1/tournaments.json?api_key=QfP2r0UaMjRV8TZGMIf7XpwdJCxiIoJrZD63UX33");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setConnectTimeout(CONNECTION_TIMEOUT);
        con.setReadTimeout(CONNECTION_TIMEOUT);
        con.setDoOutput(true);

        String jsonInputString = "{\n" +
                "  \"tournament\": {\n" +
                "    \"name\": \"Турнир #3\"\n" +
                "  }\n" +
                "}";

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }

    }

//    public static void main(String[] args) throws IOException {
//        // write your code here
//        System.out.println("HI IT'S GET");
//
//        final URL url = new URL("https://api.challonge.com/v1/tournaments.json?api_key=QfP2r0UaMjRV8TZGMIf7XpwdJCxiIoJrZD63UX33");
////        final URL url = new URL("http://localhost:8080/api/users");
////        final URL url = new URL("http://jsonplaceholder.typicode.com/posts/1");
//        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//        con.setRequestMethod("GET");
//        con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
////        con.setRequestProperty("Authorization",	"Basic TmVfVG9ydDpRZlAycjBVYU1qUlY4VFpHTUlmN1hwd2RKQ3hpSW9KclpENjNVWDMz");
//        con.setConnectTimeout(CONNECTION_TIMEOUT);
//        con.setReadTimeout(CONNECTION_TIMEOUT);
//
//        InputStreamReader isr = new InputStreamReader(con.getInputStream());
////        int charCode;
////        while ((charCode = isr.read()) != -1) { // Read each character.
////            var s = List.of((char) charCode);
////            System.out.println((char) charCode + "  " + charCode);
////        }
//
//        try (final BufferedReader in = new BufferedReader(isr)) {
//            String inputLine;
//            final StringBuilder content = new StringBuilder();
//            while ((inputLine = in.readLine()) != null) {
//                content.append(inputLine);
//            }
//            System.out.println("Received: " + content.toString());
//        } catch (final Exception ex) {
//            ex.printStackTrace();
//            System.out.println(ex.getMessage());
//        }
//    }

}
