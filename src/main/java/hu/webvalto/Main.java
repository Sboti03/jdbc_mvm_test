package hu.webvalto;

import hu.webvalto.database.orm.CrudRepositoryManager;
import hu.webvalto.user.User;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        System.out.println("Hello world!");
        CrudRepositoryManager<User, Integer> userRepository = new CrudRepositoryManager<>(User.class);
        User user = User
                .builder()
                .name("John Doe")
                .build();
        boolean i = userRepository.save(user);
        System.out.println(i);
        Optional<User> user1 = userRepository.findById(1);
        if (user1.isPresent()) {
            System.out.println(user1.get().getName());
        } else {
            System.out.println("User not found");
        }
    }
}