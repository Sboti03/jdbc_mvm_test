package hu.webvalto.ui;

import hu.webvalto.orm.crud.CrudRepositoryManager;
import hu.webvalto.ui.colors.Color;
import hu.webvalto.ui.colors.WriteOut;
import hu.webvalto.user.User;
import lombok.extern.log4j.Log4j2;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

@Log4j2
public final class UiManager {

    private final CrudRepositoryManager<User, Integer> userRepository;
    private final Scanner scanner;
    public UiManager() {
        scanner = new Scanner(System.in);
        log.info("Initializing UiManager");
        userRepository = new CrudRepositoryManager<>(User.class);
        startConsole();
    }

    private void startConsole() {
        log.info("Starting console");
        while(true) {
            WriteOut.writeLine("Select an action: ", Color.BLUE);
            WriteOut.writeLine("1. List all users", Color.GREEN);
            WriteOut.writeLine("2. Add new user", Color.GREEN);
            WriteOut.writeLine("3. Delete user", Color.GREEN);
            WriteOut.writeLine("4. Update user", Color.GREEN);
            WriteOut.writeLine("5. Exit", Color.RED);
            WriteOut.write("Action: ", Color.BLUE);
            int action = scanner.nextInt();
            switch (action) {
                case 1:
                    listAllUsers();
                    break;
                case 2:
                    addUser();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    updateUser();
                    break;
                case 5:
                    log.info("Exiting console");
                    return;
                default:
                    WriteOut.writeLine("Invalid action!", Color.RED);
            }
            scanner.nextLine();
            scanner.nextLine();
        }
    }

    private void updateUser() {
        WriteOut.write("Enter user id: ", Color.BLUE);
        int id = scanner.nextInt();
        scanner.nextLine();
        WriteOut.write("Enter new username: ", Color.BLUE);
        String username = scanner.nextLine();
        User user = new User();
        user.setName(username);
        try {
            userRepository.update(id, user);
            WriteOut.writeLine("User updated successfully!", Color.GREEN);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteUser() {
        WriteOut.write("Enter user id: ", Color.BLUE);
        int id = scanner.nextInt();
        userRepository.delete(id);
        WriteOut.writeLine("User deleted successfully!", Color.GREEN);
    }

    private void addUser() {
        WriteOut.write("Enter username: ", Color.BLUE);
        String username = scanner.next();
        User user = new User();
        user.setName(username);
        try {
            userRepository.save(user);
            WriteOut.writeLine("User added successfully!", Color.GREEN);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            WriteOut.writeLine("Error while adding user!", Color.RED);
            log.error("Error while adding user!", e);
        }
    }

    private void listAllUsers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            WriteOut.writeLine(user.toString(), Color.YELLOW);
        }
    }


}
