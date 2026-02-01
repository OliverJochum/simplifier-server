package com.oljochum.simplifier_server.users;

public interface UserService {
    public User registerUser(UserDTO userDTO);
    public User findById(long id);
}
