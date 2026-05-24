package org.example.lesson1;


public class Demo {

    public static void main(String[] args) {

        CustomHashMap<String, Integer> map = new CustomHashMap<>();

        map.put("Kirill", 25);
        map.put("Andrei", 30);
        map.put("Ilya", 35);

        System.out.println(map.get("Kirill"));

        map.put("Andrei", 100);

        System.out.println(map.get("Andrei"));

        map.remove("Ilya");

        System.out.println(map.containsKey("Ilya"));

        System.out.println(map.size());

        System.out.println(map.keySet());

        System.out.println(map.values());

        System.out.println(map.entrySet());
    }
}