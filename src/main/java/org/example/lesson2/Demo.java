package org.example.lesson2;

import org.example.lesson2.model.User;
import org.example.lesson2.service.UserService;

import java.util.List;
import java.util.Scanner;

public class Demo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserService service = new UserService();

        while (true) {

            System.out.println("""
                    1. Create user
                    2. Find user
                    3. Show all
                    4. Update user
                    5. Delete user
                    0. Exit
                    """);

            int choice = scanner.nextInt();

            switch (choice) {

                case 1 -> {

                    System.out.print("ID: ");
                    Integer id = scanner.nextInt();

                    scanner.nextLine();

                    System.out.print("Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    System.out.print("Age: ");
                    Integer age = scanner.nextInt();

                    service.createUser(id, name, email, age);

                    System.out.println("User created");
                }

                case 2 -> {

                    System.out.print("Enter ID: ");

                    Integer id = scanner.nextInt();

                    User user = service.getUser(id);

                    System.out.println(user);
                }

                case 3 -> {

                    List<User> users = service.getAllUsers();

                    users.forEach(System.out::println);
                }

                case 4 -> {

                    System.out.print("ID user: ");

                    Integer id = scanner.nextInt();

                    scanner.nextLine();

                    User user = service.getUser(id);

                    if (user != null) {

                        System.out.print("New name: ");
                        user.setName(scanner.nextLine());

                        System.out.print("New email: ");
                        user.setEmail(scanner.nextLine());

                        System.out.print("New age: ");
                        user.setAge(scanner.nextInt());

                        service.updateUser(user);

                        System.out.println("User updated");

                    } else {

                        System.out.println("User not found");
                    }
                }

                case 5 -> {

                    System.out.print("ID user: ");

                    Integer id = scanner.nextInt();

                    service.deleteUser(id);

                    System.out.println("User deleted");
                }

                case 0 -> {

                    System.out.println("Exit");

                    System.exit(0);
                }

                default -> System.out.println("Wrong");
            }
        }
    }
}
