package com.cogician.myfiles.exception;

import com.cogician.myfiles.model.VirFile;

import java.io.IOException;

/**
 * Virtual IO Exception.
 *
 * @author FredSuvn
 * @version 2016-09-04
 */
public class VirIOException extends IOException {

    private static final long serialVersionUID = 0L;

    private static final String[] ERROR_CODES = {
            "/*" +
                    "This position is reserved." +
                    "*/",
            "File does not exist",
            "File has already existed",
            "Cannot rename a root directory",
            "Create a new same name file failed",
            "Overwrite file failed",
    };

    /**
     * Error code: other error.
     */
    public static final int CODE_OTHER = -1;

    /**
     * This special error code means no error.
     */
    public static final int CODE_NONE = 0;

    /**
     * Error code: File does not exist.
     */
    public static final int CODE_FILE_NOT_EXIST = 1;

    /**
     * Error code: Target file has already existed.
     */
    public static final int CODE_FILE_EXISTED = 2;

    /**
     * Error code: Target file has already existed.
     */
    public static final int CODE_CANNOT_RENAME_ROOT = 3;

    /**
     * Error code: Create a new same name file failed.
     */
    public static final int CODE_CREATE_NEW_SAME_NAME_FILE_FAILED = 4;

    /**
     * Error code: Overwrite file failed.
     */
    public static final int CODE_OVERWRITE_FAILED = 5;

    private static String doMessage(VirFile file, int errorCode) {
        return ERROR_CODES[errorCode] + ": " + file.getPath();
    }

    private static String doMessage(VirFile file, String message) {
        return message + ": " + file.getPath();
    }

    private final VirFile file;

    private final int code;

    /**
     * Constructs with specified file and code.
     *
     * @param file
     *         specified file
     * @param code
     *         specified code
     */
    public VirIOException(VirFile file, int code) {
        this(file, code, null);
    }

    /**
     * Constructs with specified file, code and cause.
     *
     * @param file
     *         specified file
     * @param code
     *         specified code
     * @param cause
     *         specified cause
     */
    public VirIOException(VirFile file, int code, Throwable cause) {
        super(doMessage(file, code), cause);
        this.file = file;
        this.code = code;
    }

    /**
     * Constructs with specified file and detail message.
     *
     * @param file
     *         specified file
     * @param message
     *         detail message
     */
    public VirIOException(VirFile file, String message) {
        this(file, message, null);
    }

    /**
     * Constructs with specified file, detail message and cause.
     *
     * @param file
     *         specified file
     * @param message
     *         detail message
     * @param cause
     *         specified cause
     */
    public VirIOException(VirFile file, String message, Throwable cause) {
        super(doMessage(file, message), cause);
        this.file = file;
        this.code = CODE_OTHER;
    }

    /**
     * Constructs with no message.
     */
    public VirIOException() {
        this(CODE_OTHER, null);
    }

    /**
     * Constructs with specified code.
     *
     * @param code
     *         specified code
     */
    public VirIOException(int code) {
        this(code, null);
    }

    /**
     * Constructs with detail message.
     *
     * @param message
     *         detail message
     */
    public VirIOException(String message) {
        this(message, null);
    }

    /**
     * Constructs with specified cause.
     *
     * @param cause
     *         specified cause
     */
    public VirIOException(Throwable cause) {
        this(CODE_OTHER, cause);
    }

    /**
     * Constructs with detail message and specified cause.
     *
     * @param message
     *         detail message
     * @param cause
     *         specified cause
     */
    public VirIOException(String message, Throwable cause) {
        super(message, cause);
        this.code = CODE_OTHER;
        this.file = null;
    }

    /**
     * Constructs with specified error code and cause.
     *
     * @param code
     *         specified error
     * @param cause
     *         specified cause
     */
    public VirIOException(int code, Throwable cause) {
        super(code == CODE_OTHER ? null : ERROR_CODES[code], cause);
        this.code = code;
        this.file = null;
    }

    /**
     * Returns error code of this exception.
     *
     * @return error code of this exception
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns the file where IO error occurs.
     *
     * @return the file where IO error occurs
     */
    public VirFile getFile() {
        return file;
    }
}
