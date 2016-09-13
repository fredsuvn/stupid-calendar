package com.cogician.myfiles.model;

/**
 * Attributes of virtual file.
 *
 * @author FredSuvn
 * @version 2016-09-12
 */
public interface VirFileAttributes {

    /**
     * Returns file name, not detail path.
     *
     * @return file name, not detail path
     */
    String getFileName();

    /**
     * Returns path of this file. The path consists of storage://[account]username/path, [account] is optional such as:
     * <pre>
     *     local://user/files/myFile.txt
     *     BaiduYun://user/files/myFile.txt
     *     BaiduYun://[myAccount]user/files/myFile.txt
     * </pre>
     *
     * @return path of this file
     */
    String getPath();

    /**
     * Returns length of this file. If this file represents a directory, return 0.
     *
     * @return length of this file
     */
    long length();

    /**
     * Returns creation time of this file in UTC. If this file is not existed, return -1;
     *
     * @return creation time of this file in UTC
     */
    long creationTime();

    /**
     * Returns last access time of this file in UTC. If this file is not existed, return -1;
     *
     * @return last access time of this file in UTC
     */
    long lastAccess();

    /**
     * Returns last modified time of this file in UTC. If this file is not existed, return -1;
     *
     * @return last modified time of this file in UTC
     */
    long lastModified();
}
