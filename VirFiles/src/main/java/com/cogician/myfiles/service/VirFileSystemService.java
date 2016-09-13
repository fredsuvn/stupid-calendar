package com.cogician.myfiles.service;

import com.cogician.myfiles.exception.VirIOException;
import com.cogician.myfiles.model.VirUser;
import com.cogician.myfiles.model.VirFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.stream.Stream;

/**
 * File system service.
 *
 * @param <VF>
 *         type of underline file system
 * @author FredSuvn
 * @version 2016-09-04
 */
public interface VirFileSystemService<VF extends VirFile> {

    /**
     * Returns scheme of this file system.
     *
     * @return scheme of this file system
     */
    String getPathScheme();

    /**
     * Returns specified virtual file of  user by specified virtual file filePath. The file path's root directory is
     * user directory.
     *
     * @param user
     *         specified user
     * @param filePath
     *         specified virtual file filePath
     * @return specified virtual file of  user by specified virtual file filePath
     * @throws VirIOException
     *         if any IO problem occurs
     */
    VF getFile(VirUser user, String filePath) throws VirIOException;

    /**
     * Lists sub-files and directories under specified directory, non-recursively.
     *
     * @param dir
     *         specified directory
     * @return sub-files and directories under specified directory
     * @throws VirIOException
     *         if any IO problem occurs
     */
    List<VF> list(VF dir) throws VirIOException;

    /**
     * Lists sub-files and directories as a stream under specified directory, non-recursively.
     *
     * @param dir
     *         specified directory
     * @return sub-files and directories under specified directory
     * @throws VirIOException
     *         if any IO problem occurs
     */
    Stream<VF> listStream(VF dir) throws VirIOException;

    /**
     * Moves source file to dest file. If source file is a directory, it must be empty. Actual dest file will be
     * returned.
     *
     * @param source
     *         source file
     * @param dest
     *         dest file
     * @param copyOption
     *         copy option
     * @return actual dest file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    VF move(VF source, VF dest, VirFile.VirCopyOption copyOption) throws VirIOException;

    /**
     * Moves source file to dest file. If source file is a directory, all sub-files and directories will be moved
     * recursively.
     *
     * @param source
     *         source file
     * @param dest
     *         dest file
     * @param copyOption
     *         copy option
     * @return move result
     */
    VirFile.BatchResult moveDirectory(VF source, VF dest, VirFile.VirCopyOption copyOption);

    /**
     * Copies source file to dest file. If source file is a directory, it must be empty. Actual dest file will be
     * returned.
     *
     * @param source
     *         source file
     * @param dest
     *         dest file
     * @param copyOption
     *         copy option
     * @return actual dest file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    VF copy(VF source, VF dest, VirFile.VirCopyOption copyOption) throws VirIOException;

    /**
     * Copies source file to dest file. If source file is a directory, all sub-files and directories will be moved
     * recursively.
     *
     * @param source
     *         source file
     * @param dest
     *         dest file
     * @param copyOption
     *         copy option
     * @return copy result
     */
    VirFile.BatchResult copyDirectory(VF source, VF dest, VirFile.VirCopyOption copyOption);

    /**
     * Returns input stream at specified position of given file.
     *
     * @param file
     *         given file
     * @param pos
     *         specified position
     * @return input stream at specified position of given file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    InputStream getInputStream(VF file, long pos) throws VirIOException;

    /**
     * Returns output stream at specified position of given file.
     *
     * @param file
     *         given file
     * @param pos
     *         specified position
     * @return output stream at specified position of given file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    OutputStream getOutputStream(VF file, long pos) throws VirIOException;

    /**
     * Returns channel of given file.
     *
     * @param file
     *         given file
     * @return channel of given file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    FileChannel open(VF file) throws VirIOException;

    /**
     * Returns whether specified file system is the same one as this.
     *
     * @param fileSystemService
     *         specified file system
     * @return whether specified file system is the same one as this
     */
    boolean isSameFileSystem(VirFileSystemService fileSystemService);
}
