package com.epam.lab.group1.facultative.service;

import com.epam.lab.group1.facultative.dto.PersonRegistrationFormDTO;
import com.epam.lab.group1.facultative.model.User;
import com.epam.lab.group1.facultative.persistance.UserDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<User> getById(int id) {
        return userDAO.getById(id);
    }

    public Optional<User> getByEmail(String string) {
        return userDAO.getByEmail(string);
    }
    
    public void deleteById(int id) {
        userDAO.deleteById(id);
    }

    public List getList() {
        return userDAO.getList();
    }

    public User create(User user) {
        return userDAO.create(user);
    }

    public void update(User user) {
        userDAO.update(user);
    }

    public User createUserFromDto(PersonRegistrationFormDTO personRegistrationFormDTO) {
        User user = new User();
        user.setFirstName(personRegistrationFormDTO.getFirstName());
        user.setLastName(personRegistrationFormDTO.getLastName());
        user.setEmail(personRegistrationFormDTO.getEmail());
        user.setPassword(personRegistrationFormDTO.getPassword());
        user.setPosition(personRegistrationFormDTO.getPosition());
        return create(user);
    }

}