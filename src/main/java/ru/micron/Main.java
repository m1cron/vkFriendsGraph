package ru.micron;

public class Main {
    public static void main(String[] args)  {
        VkAPI vk = new VkAPI(args[0], 2);
        vk.getDeepFriends("171728534");
    }
}
