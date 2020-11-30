package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class Utility {

    public static boolean isEmailValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email != null && email.matches(regex);
    }

    public static boolean invalidPassword(String password) {
        return password == null || password.length() < 8;
    }


    public static void clearScreen() {

        System.out.print("\033[H\033[2J");

        System.out.flush();

    }

    public static Map<String, String> getBodyMap(final BufferedReader bufferedReader) throws IOException {
        final String json = bufferedReader.readLine();
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Map.class);

    }



}
