package ru.coderiders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {

    final static int CONNECTION_TIMEOUT = 10000;

    public static void main(String[] args) throws IOException {
        System.out.println("HI IT'S POST");

        //для работы с ресурсами в интеренете используем специальный класс
        //URL хранит путь к интерент ресурсу
        var url = new URL("https://api.challonge.com/v1/tournaments.json?api_key=C8Wqy2FysAowMwqUrjPHyJ11caLEpYzwSdLPKLaJ");
        //Открываем соединение с URL, позволяя клиенту взаимодействовать с ресурсом
        //Преобразуем возвращаемый тип данных HttpURLConnection, зачем?
        // Для того что бы выполнить задуманное, а точнне нам нужен метод "setRequestMethod" для Методов запросов
        var httpURLConnection = (HttpURLConnection) url.openConnection();
        //вставочка про методы Http
        //тело запроса
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        //Осталось только отправить запрос записав в тело наш токен и лимит, в данном примере токен передаем в url по этому в теле его передавать не будем,
        // хотя исходя из документации поддержка этого есть
        httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
        httpURLConnection.setReadTimeout(CONNECTION_TIMEOUT);
        // Отправляем запрос. Для этого устанавливаем поле doOutput объекта URLConnection в положение true, используя метод setDoOutput();
        httpURLConnection.setDoOutput(true);

        var jsonInputString = "{\n" +
                "  \"tournament\": {\n" +
                "    \"name\": \"Турнир #2\"\n" +
                "  }\n" +
                "}";

        //try-with-resources
        //Ресурсы, открытые в скобках после оператора try, понадобятся только здесь и сейчас.
        // Я вызову их методы .close (), как только закончу работу в блоке try.
        // Если в блоке try возникнет исключение, я все равно закрою эти ресурсы.
        try (var outputStream = httpURLConnection.getOutputStream()) { // получаем поток для отправки данных
            var inputByte = jsonInputString.getBytes(StandardCharsets.UTF_8); //кодирует данную строку в последовательность байтов, используя кодировку, сохраняет результат в новый массив байтов
            outputStream.write(inputByte, 0, inputByte.length); // отправляем данные (записывает в поток len байтов массива, начиная с элемента b[off])
        }

        try (
                //InputStreamReader - это подкласс Reader, который представляет собой мост,
                // позволяющий преобразовать byte stream в character stream (httpURLConnection.getInputStream()).
                // Другими словами, он позволяет преобразовать InputStream в Reader
                //Reader - это класс в package java.io, который является базовым классом,
                // представляющим поток символов (stream of characters),
                // полученных при чтении определенного источника данных, например текстового файла.
                var inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8);
                //BufferedReader - это подкласс Reader, который используется для упрощения чтения текста из потоков ввода символов
                // (character input stream) и повышения производительности программы.
                var bufferedReader = new BufferedReader(inputStreamReader)) {
            //Сюда будем складывать ответ, считайте что это просто класс String
            //Рекомендация: при работе со строками, которые часто будут модифицироваться, в однопоточной среде
            var StringBuilderResponse = new StringBuilder();
            String responseLine;
            //метод readLine() читает строки текста из buffer
            while ((responseLine = bufferedReader.readLine()) != null) {
                StringBuilderResponse.append(responseLine.trim());
            }
            //Вывод в консоль полученный результат
            System.out.println(StringBuilderResponse.toString());
        }

    }

    public static void main2(String[] args) throws IOException {
        System.out.println("HI IT'S GET");

        //модифицируем пример для получения ответа
        final var url = new URL("https://api.challonge.com/v1/tournaments/10634350.json?api_key=C8Wqy2FysAowMwqUrjPHyJ11caLEpYzwSdLPKLaJ");
        final var httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
//        httpURLConnection.setRequestProperty("Authorization",	"Basic TmVfVG9ydDpRZlAycjBVYU1qUlY4VFpHTUlmN1hwd2RKQ3hpSW9KclpENjNVWDMz");
        //таймауты
        httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
        httpURLConnection.setReadTimeout(CONNECTION_TIMEOUT);

        try (
                var inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8);
                var bufferedReader = new BufferedReader(inputStreamReader)) {
            var StringBuilderResponse = new StringBuilder();
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                StringBuilderResponse.append(inputLine);
            }
            System.out.println(StringBuilderResponse.toString());
        }
    }

}
