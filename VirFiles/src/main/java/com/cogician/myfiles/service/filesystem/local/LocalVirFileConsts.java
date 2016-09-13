package com.cogician.myfiles.service.filesystem.local;

/**
 * Constants of local file system implementation.
 *
 * @author FredSuvn
 * @version 2016-09-04
 */
public class LocalVirFileConsts {

    /**
     * Suffix extension name of link file.
     */
    public static final String LINK_EXT_NAME = "link";
    /**
     * Suffix extension name of source of link file.
     */
    public static final String LINK_SRC_EXT_NAME = "srclink";
    /**
     * A type of suffix extension name. If original file name is suffix as {@link #LINK_EXT_NAME}, {@link
     * #LINK_SRC_EXT_NAME} or {@link #TRANS_EXT_NAME}, it will be append this suffix.
     */
    public static final String TRANS_EXT_NAME = "trans";
}
