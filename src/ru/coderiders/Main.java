package ru.coderiders;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Main {

    final static int CONNECTION_TIMEOUT = 2000;

    public static void main(String[] args) throws IOException {
        // write your code here
        System.out.println("HI IT'S MARIO");

//        final URL url = new URL("http://Ne_Tort:QfP2r0UaMjRV8TZGMIf7XpwdJCxiIoJrZD63UX33@api.challonge.com/v1/tournaments.json");
        final URL url = new URL("http://jsonplaceholder.typicode.com/posts");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(CONNECTION_TIMEOUT);
        con.setReadTimeout(CONNECTION_TIMEOUT);

//        try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
//            String inputLine;
//            final StringBuilder content = new StringBuilder();
//            while ((inputLine = in.readLine()) != null) {
//                content.append(inputLine);
//            }
//            System.out.println(content.toString());
//        } catch (final Exception ex) {
//            ex.printStackTrace();
//            System.out.println(ex.getMessage());
//        }

        final Map<String, String> parameters = new HashMap<>();
        parameters.put("title", "foo");
        parameters.put("body", "bar");
        parameters.put("userId", "1");

        con.setDoOutput(true);
        final DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(getParamsString(parameters));
        out.flush();
        out.close();
    }

    public static String getParamsString(final Map<String, String> params) {
        final StringBuilder result = new StringBuilder();

        params.forEach((name, value) -> {
            try {
                result.append(URLEncoder.encode(name, "UTF-8"));
                result.append('=');
                result.append(URLEncoder.encode(value, "UTF-8"));
                result.append('&');
            } catch (final UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });

        final String resultString = result.toString();
        return !resultString.isEmpty()
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}
