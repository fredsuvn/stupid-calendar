package com.cogician.myfiles.model;

import javax.annotation.Nullable;

/**
 * Types of virtual file, using suffix extension name to distinguish.
 *
 * @author FredSuvn
 * @version 2016-09-04
 */
public class VirFileType {

    private static final VirFileType UNKNOWN = new VirFileType("BIN", null);

    /**
     * Returns virtual file type according to given suffix extension name, dot "." exclusive.
     *
     * @param extName
     *         given suffix extension name
     * @return virtual file type according to given suffix extension name
     */
    public static VirFileType getType(@Nullable String extName) {
        return null;
    }

    private String typeName;

    private String typePattern;

    /**
     * Constructs with pattern of suffix extension name (dot "." exclusive).
     *
     * @param typeName
     *         name of type
     * @param typePattern
     *         pattern of extension suffix name
     */
    public VirFileType(String typeName, String typePattern) {
        this.typeName = typeName;
        this.typePattern = typePattern;
    }

    /**
     * Returns name of this type.
     *
     * @return name of this type
     */
    public String getName() {
        return typeName;
    }

    /**
     * Returns pattern of extension suffix name of this type.
     *
     * @return pattern of extension suffix name of this type
     */
    public String getPattern() {
        return typePattern;
    }
}
