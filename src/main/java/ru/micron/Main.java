package ru.micron;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ArgsFilesIO {

    public static List<Integer> readArg(String filePath) {
        List<Integer> arr = new ArrayList<>(16);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                arr.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            System.out.println("Cannot open `" + filePath + "`.");
            System.exit(-1);
        }
        return arr;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Integer> ids = ArgsFilesIO.readArg(args[0]);

        VkAPI vk = new VkAPI(args[1], args[2]);
        vk.findFriends(ids.get(0));
        vk.searchDeps(ids);
    }
}
