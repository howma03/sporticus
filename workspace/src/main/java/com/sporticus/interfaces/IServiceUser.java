package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IUser;
import org.springframework.data.domain.Sort.Order;

import java.util.List;

public interface IServiceUser {

    IUser addUser(IUser user) throws ExceptionServiceUser;

    IUser updateUser(IUser user) throws ExceptionServiceUser;

    List<String> getAllUserEmailAddresses();

    List<IUser> getAll();

    long getUserCount();

    IUser findOne(Long userId);

    IUser findUserByEmail(String email);

    void deleteUser(Long userId);

    List<IUser> getUsers(Integer page, Integer pageSize, Order order);

    class ExceptionServiceUser extends RuntimeException {

        public ExceptionServiceUser(final String message, final Exception e) {
            super(message, e);
        }

        public ExceptionServiceUser(final String message) {
            super(message);
        }
    }

}
