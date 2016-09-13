package com.cogician.myfiles.model;

import com.cogician.quicker.util.config.ConfigMap;

/**
 * VirUser of this application.
 *
 * @author FredSuvn
 * @version 2016-09-05
 */
public interface VirUser {

    /**
     * Returns user ID. User ID is unmodified and only.
     *
     * @return user ID
     */
    long getUserID();

    /**
     * Returns user name.
     *
     * @return
     */
    String getUserName();

    /**
     * Returns local path of this user's local files on local file system.
     *
     * @return local path of this user's local files on local file system
     */
    String getUserLocalPath();

    ConfigMap getConfigs();
}
