package ru.micron;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ArgsHandler {

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

    public static void argsError(int error) {
        System.err.println("Program arguments error!");
        switch (error) {
            case 0 -> System.err.println("Wrong number of arguments.");
            case 1 -> System.err.println("Check the number of IP addresses.");
        }
        System.err.println("\nExample program arguments:\n$ java -jar vkFriendsGraph-1.0.jar [id's txt file] [out.csv] [search depth] [VK API token].");
        System.exit(-1);
    }
}

public class Main {
    public static void main(String[] args) {
        if (args.length != 4)
            ArgsHandler.argsError(0);
        List<Integer> ids ;
        if ((ids = ArgsHandler.readArg(args[0])).size() < 2)
            ArgsHandler.argsError(1);
        VkAPI vk = new VkAPI(args[2], args[3]);
        vk.findFriends(ids.get(0));
        vk.searchDeps(ids);
        SqlVk.export("res.csv");
        SqlVk.clear();
    }
}