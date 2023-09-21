package com.example.library.seeders;

import com.example.library.model.User;
import com.example.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserSeeder {
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    @Transactional
    public void seed() {
        List<User> users = new ArrayList<>(Arrays.asList(
                new User("James", "Male", "08123131221", "Jalan Semangka", "Member"),
                new User("Kevin", "Male", "08122231231", "Jalan Melon", "Member"),
                new User("Jake", "Male", "08111131211", "Jalan Nanas", "Staff"),
                new User("Mary", "Female", "08133131251", "Jalan Mangga", "Member"),
                new User("Kath", "Female", "0811121461", "Jalan Anggur", "Staff")
        ));

        if (userRepository.findAllByDeletedAtIsNull().isEmpty()) {
            userRepository.saveAll(users);
        }
    }
}
