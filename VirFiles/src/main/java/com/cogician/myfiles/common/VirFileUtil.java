package com.cogician.myfiles.common;

import com.cogician.myfiles.exception.VirIOException;
import com.cogician.myfiles.model.VirFile;
import com.cogician.quicker.Checker;
import org.apache.commons.io.FilenameUtils;

/**
 * Helper class for virtual file.
 *
 * @author FredSuvn
 * @version 2016-09-11
 */
public class VirFileUtil {

    /**
     * Creates a new same name file or directory of specified file such as:
     * <pre>
     *     specified file:        myFile.txt
     *     new same name file: myFIle(2).txt
     * </pre>
     *
     * @param file
     *         specified file
     * @param isDirectory
     *         specifying whether create a file or a directory
     * @return new same name file or directory
     * @throws VirIOException
     *         if any IO problem occurs
     */
    public static VirFile createSameNameFile(VirFile file, boolean isDirectory) throws VirIOException {
        String name = FilenameUtils.getBaseName(file.getFileName());
        String ext = FilenameUtils.getExtension(file.getFileName());
        VirFile parent = file.getParent();
        long l = 2;
        while (l < Long.MAX_VALUE) {
            VirFile newFile = parent.getSubFile(name + "(" + l + ")" + (Checker.isEmpty(ext) ? "" : ("." + ext)));
            if (!newFile.exist()) {
                try {
                    if (isDirectory) {
                        newFile.createDerectory();
                    } else {
                        newFile.create();
                    }
                } catch (VirIOException e) {
                    if (e.getCode() == VirIOException.CODE_FILE_EXISTED) {
                        continue;
                    } else {
                        throw e;
                    }
                }
                return newFile;
            }
        }
        throw new VirIOException(file, VirIOException.CODE_CREATE_NEW_SAME_NAME_FILE_FAILED);
    }

    /**
     * Tries to create specified file if specified file doesn't exist. If specified file has already
     * existed, create a new same name file.
     *
     * @param file
     *         specified file
     * @param isDirectory
     *         specifying whether create a file or a directory
     * @return created specified file or same name of specified file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    public static VirFile createFileOrSameNameFile(VirFile file, boolean isDirectory) throws VirIOException {
        if (file.exist()) {
            return createSameNameFile(file, isDirectory);
        } else {
            try {
                if (isDirectory) {
                    file.createDerectory();
                } else {
                    file.create();
                }
            } catch (VirIOException e) {
                if (e.getCode() == VirIOException.CODE_FILE_EXISTED) {
                    return createSameNameFile(file, isDirectory);
                } else {
                    throw e;
                }
            }
            return file;
        }
    }

    /**
     * Tries to create specified file by overwrite. If file doesn't exist, create new, else overwrite.
     *
     * @param file
     *         specified file
     * @param isDirectory
     *         specifying whether create a file or a directory
     * @return created or overwritten file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    public static VirFile overwrite(VirFile file, boolean isDirectory) throws VirIOException {
        if (file.exist()) {
            file.deleteDirectory();
        }
        try {
            if (file.isDirectory()) {
                file.createDerectory();
            } else {
                file.create();
            }
        } catch (VirIOException e) {
            if (e.getCode() == VirIOException.CODE_FILE_EXISTED) {
                throw new VirIOException(file, VirIOException.CODE_OVERWRITE_FAILED);
            } else {
                throw e;
            }
        }
        return file;
    }

    /**
     * Creates a new file with specified option.
     *
     * @param file
     *         created file
     * @param isDirectory
     *         specifying whether create a file or a directory
     * @param copyOption
     *         specified option
     * @return created file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    public static VirFile create(VirFile file, boolean isDirectory, VirFile.VirCopyOption copyOption) throws VirIOException {
        try {
            if (isDirectory) {
                file.createDerectory();
            } else {
                file.create();
            }
        } catch (VirIOException e) {
            if (e.getCode() == VirIOException.CODE_FILE_EXISTED) {
                switch (copyOption) {
                    case THROW:
                        throw e;
                    case OVERWRITE:
                        return VirFileUtil.overwrite(file, isDirectory);
                    case NEW:
                        return VirFileUtil.createSameNameFile(file, isDirectory);
                }
            } else {
                throw e;
            }
        }
        return file;
    }
}
