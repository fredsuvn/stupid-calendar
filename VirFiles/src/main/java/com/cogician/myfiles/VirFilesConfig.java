package com.cogician.myfiles;

import javax.annotation.Nullable;

/**
 * Global configurations of VirFiles.
 *
 * @author FredSuvn
 * @version 2016-09-04
 */
public interface VirFilesConfig {

    /**
     * Returns config of specified key. Return null if no this key.
     *
     * @param key
     *         specified key
     * @return config of specified key
     */
    @Nullable
    String get(String key);

    /**
     * Returns path to store user info, same as:
     * <pre>
     *     get("user.path");
     * </pre>
     *
     * @return path to store user info
     */
    String getUserPath();

    /**
     * Returns path to store files, same as:
     * <pre>
     *     get("file.path");
     * </pre>
     *
     * @return path to store files
     */
    String getFilesPath();

    /**
     * Returns IO buffer size in bytes, same as:
     * <pre>
     *     get("io.buffer.size");
     * </pre>
     *
     * @return IO buffer size in bytes
     */
    int getIOBufferSize();

    /**
     * Returns directory list buffer in number of directories, same as:
     * <pre>
     *     get("directory.buffer.size");
     * </pre>
     *
     * @return IO buffer size in bytes
     */
    int getDirectoryListBufferSize();
}
