package com.cogician.myfiles.service;

import com.cogician.myfiles.model.VirUser;

import javax.annotation.Nullable;

/**
 * User service of VirFiles.
 *
 * @author FredSuvn
 * @version 2016-09-12
 */
public interface VirUserService {

    /**
     * Returns user with specified user name. Null if user not found.
     *
     * @param userName
     *         specified user name
     * @return user with specified user name
     */
    @Nullable
    VirUser getUser(String userName);

    /**
     * Returns user with specified user ID. Null if user not found.
     *
     * @param userID
     *         specified user ID
     * @return user with specified user ID
     */
    @Nullable
    VirUser getUser(long userID);

    /**
     * Creates a new user.
     *
     * @return a new user
     */
    VirUser create();
}
