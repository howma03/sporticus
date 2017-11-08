package com.sporticus.services;

import com.sporticus.domain.entities.User;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryUser;
import com.sporticus.interfaces.IServiceMail;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service("serviceUser")
@Qualifier("production")
public class ServiceUserImplRepository implements IServiceUser {

    private static final Logger LOGGER = LogFactory.getLogger(ServiceUserImplRepository.class.getName());

    private final IRepositoryUser repositoryUser;
    private final IServiceMail serviceMail;

    @Autowired
    public ServiceUserImplRepository(final IServiceMail serviceMail,
                                     final IRepositoryUser userRepository) {
        this.serviceMail = serviceMail;
        this.repositoryUser = userRepository;
    }

    private static <T> Stream<T> toStream(final Iterable<T> iterable) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        iterable.iterator(),
                        Spliterator.ORDERED
                ),
                false
        );
    }

    /**
     * Adds a new user
     *
     * @param user
     * @return IUser
     * @throws ExceptionServiceUser
     */
    @Override
    public IUser addUser(final IUser user) throws ExceptionServiceUser {
        final User foundUser = repositoryUser.findByEmail(user.getEmail());
        if (foundUser != null) {
            throw new ExceptionServiceUser("User already exists - " + foundUser);
        }

        try {
            final User newUser = new User(user);
            LOGGER.info(() -> "Adding user - new user=" + user);

            // if a password is provided we need to encode it
            newUser.setPassword(user.getPassword());
            newUser.setVerified(false);
            newUser.setVerificationCode(UUID.randomUUID().toString());
            newUser.setEnabled(true);
            newUser.setAdmin(false);

            return repositoryUser.save(newUser);

        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ExceptionServiceUser("addUser()", e);
        }
    }

    /**
     * Function to update a User
     * <p>
     * When providing a password the password MUST already be encoded
     *
     * @param inUser
     * @return IUser
     * @throws ExceptionServiceUser
     */
    @Override
    public IUser updateUser(final IUser inUser) throws ExceptionServiceUser {
        if (inUser == null) {
            throw new ExceptionServiceUser("User cannot be null!");
        }
        final User found = repositoryUser.findOne(inUser.getId());
        if (found == null) {
            throw new ExceptionServiceUser("User not found - " + inUser.getEmail());
        }

        // Ensure some properties are retained

        if (inUser.getVerificationCode() == null) {
            inUser.setVerificationCode(found.getVerificationCode());
        }
        // if we have been given a new password then we must encode it
        // otherwise we can use the one we found
        if (inUser.getPassword() == null || inUser.getPassword().length() == 0) {
            inUser.setPassword(found.getPassword());
        }
        inUser.setCreated(found.getCreated());
        inUser.setAdmin(found.isAdmin());
        inUser.setEmail(found.getEmail());

        IUser.COPY(inUser, found);

        return repositoryUser.save(found);
    }

    @Override
    public IUser findUser(final Long userId) {
        return repositoryUser.findOne(userId);
    }

    @Override
    public IUser findUserByEmail(final String email) {
        return repositoryUser.findByEmail(email);
    }

    @Transactional
    @Override
    public void deleteUser(final Long userId) {
        final User foundUser = repositoryUser.findOne(userId);
        if (foundUser == null) {
            return;
        }
        foundUser.setEnabled(false);

        repositoryUser.save(foundUser);

        // repositoryUser.delete(userId);
    }

    /**
     * Function to convert a Iterable<User> to a List<IUser>
     *
     * @param from<User>
     * @return List<User>
     */
    private static List<IUser> convert(final Iterable<User> from) {
        final List<IUser> list = new ArrayList<>();
        if (from != null) {
            for (final IUser ps : from) {
                list.add(ps);
            }
        }
        return list;
    }

    @Override
    public List<String> getAllUserEmailAddresses() {
        final List<String> l = new ArrayList<>();
        IteratorUtils.toList(repositoryUser.findAll().iterator()).forEach(u -> l.add(((User) u).getEmail()));
        return l;
    }

    @Override
    public List<IUser> getAll() {
        return convert(repositoryUser.findAll());
    }

    @Override
    public List<IUser> getUsers(final Integer page, final Integer pageSize, final Order order) {
        final List<IUser> l = new ArrayList<>();
        if (page != null && pageSize != null) {
            final Pageable pageable;
            if (order == null) {
                pageable = new PageRequest(page, pageSize);
            } else {
                pageable = new PageRequest(page, pageSize, new Sort(order));
            }
            final Page<User> list = repositoryUser.findAll(pageable);
            for (final User u : list) {
                l.add(u);
            }
        }
        return l;
    }

    @Override
    public long getUserCount() {
        return repositoryUser.count();
    }

}
