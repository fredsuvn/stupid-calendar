package com.cogician.myfiles.model;

import com.cogician.myfiles.common.VirFileUtil;
import com.cogician.myfiles.exception.VirIOException;
import com.cogician.myfiles.service.VirFileSystemService;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Virtual file, representing a file or directory in corresponding file system.
 *
 * @author FredSuvn
 * @version 2016-09-04
 */
public interface VirFile {

    /**
     * Returns file name, not detail path.
     *
     * @return file name, not detail path
     */
    String getFileName();

    /**
     * Returns path of this file. The path consists of scheme://[account]username/path, [account] is optional such as:
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

    /**
     * Returns current attributes of this file. Returned attributes will not reflected by this file, and vice versa.
     *
     * @return current attributes of this file
     */
    default VirFileAttributes getAttributes() {
        return new VirFileAttributes() {
            private final String name = VirFile.this.getFileName();
            private final String path = VirFile.this.getPath();
            private final long length = VirFile.this.length();
            private final long creationTime = VirFile.this.creationTime();
            private final long lastAccess = VirFile.this.lastAccess();
            private final long lastModified = VirFile.this.lastModified();

            @Override
            public String getFileName() {
                return name;
            }

            @Override
            public String getPath() {
                return path;
            }

            @Override
            public long length() {
                return length;
            }

            @Override
            public long creationTime() {
                return creationTime;
            }

            @Override
            public long lastAccess() {
                return lastAccess;
            }

            @Override
            public long lastModified() {
                return lastModified;
            }
        };
    }

    /**
     * Returns whether this file is root directory of current user.
     *
     * @return whether this file is root directory of current user
     */
    boolean isUserRootDirectory();

    /**
     * Returns file system belonged by this file.
     *
     * @return file system belonged by this file
     */
    VirFileSystemService getFileSystem();

    /**
     * Returns owner of this file. A file only has one owner.
     *
     * @return owner of this file
     */
    VirUser getOwner();

    /**
     * Returns whether this file exists.
     *
     * @return whether this file exists
     */
    boolean exist();

    /**
     * Returns whether given file is same one as this.
     *
     * @param given
     *         given file
     * @return whether given file is same one as this
     */
    boolean isSameFile(VirFile given);

    /**
     * Creates this file in underline file system if this file is not existing.
     *
     * @throws VirIOException
     *         if any IO problem occurs
     */
    void create() throws VirIOException;

    /**
     * Creates directory represented by this file in underline file system if this file is not existing. Necessary
     * parents directories will also be created.
     *
     * @throws VirIOException
     *         if any IO problem occurs
     */
    void createDerectory() throws VirIOException;

    /**
     * Create a link file at position specified by this file, the link source file is specified source.
     *
     * @param source
     *         specified source
     * @throws VirIOException
     *         if any IO problem occurs
     */
    void createLink(VirFile source) throws VirIOException;

    /**
     * Deletes this file. If this file is a directory, it must be empty.
     *
     * @throws VirIOException
     *         if any IO problem occurs
     */
    void delete() throws VirIOException;

    /**
     * Returns whether this file is a directory. A directory exist certainly.
     *
     * @return whether this file is a directory
     */
    boolean isDirectory();

    /**
     * Returns whether directory specified by this file is empty. If this file is not a directory, a VirIOException
     * thrown.
     *
     * @return whether directory specified by this file is empty
     * @throws VirIOException
     *         if any IO problem occurs
     */
    default boolean isEmptyDirectory() throws VirIOException {
        return countSubFiles() == 0;
    }

    /**
     * Returns number of sub-files and directories under the directory specified by this file. If this file is not a
     * directory, a VirIOException thrown.
     *
     * @return number of sub-files and directories under the directory specified by this file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    long countSubFiles() throws VirIOException;

    /**
     * Lists sub-files and directories under the directory specified by this file. If this file is not a directory, a
     * VirIOException thrown.
     *
     * @return sub-files and directories under the directory specified by this file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    default List<VirFile> list() throws VirIOException {
        return getFileSystem().list(this);
    }

    /**
     * Lists sub-files and directories as a stream under the directory specified by this file. If this file is not a
     * directory, a VirIOException thrown.
     *
     * @return sub-files and directories under the directory specified by this file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    default Stream<VirFile> listStream() throws VirIOException {
        return getFileSystem().listStream(this);
    }

    /**
     * Returns parent directory of this file, or parent directory of this sub-directory. If this file represents a root
     * directory, return null.
     *
     * @return parent directory of this file, or parent directory of this sub-directory
     * @throws VirIOException
     *         if any IO problem occurs
     */
    @Nullable
    VirFile getParent() throws VirIOException;

    /**
     * Adds a sub-file or directory under the directory specified by this file. If this file is not a directory, a
     * VirIOException thrown.
     *
     * @param fileName
     *         new file name
     * @return added new file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    VirFile addSubFile(String fileName) throws VirIOException;

    /**
     * Gets specified sub-file or directory under the directory specified by this file. If this file is not a directory,
     * a VirIOException thrown.
     *
     * @param fileName
     *         specified file name
     * @return specified sub-file or directory
     * @throws VirIOException
     *         if any IO problem occurs
     */
    VirFile getSubFile(String fileName) throws VirIOException;

    /**
     * Returns total length of all files and sub-directories under the directory specified by this file. If this file is
     * not a directory, a VirIOException thrown.
     *
     * @return total length of all files and sub-directories under the directory specified by this file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    long getTotalDirectoryLength() throws VirIOException;

    /**
     * Returns whether this file is a link.
     *
     * @return whether this file is a link
     */
    boolean isLink();

    /**
     * Returns source file of this link file. If this file is not a link file, a VirIOException thrown.
     *
     * @return source file of this link file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    VirFile getSource() throws VirIOException;

    /**
     * Returns terminal source file of this link file, that means, if this file links another link file and that link
     * file links another and so on, the finally linked real source file will be returned. If this file is not a link
     * file, a VirIOException thrown.
     *
     * @return terminal source file of this link file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    VirFile getTerminalSource() throws VirIOException;

    /**
     * Returns whether this file can be read. This method only check permission of current user for this file, it
     * doesn't consider the lock.
     *
     * @return whether this file can be read
     */
    boolean canRead();

    /**
     * Returns whether this file can be written. This method only check permission of current user for this file, it
     * doesn't consider the lock.
     *
     * @return whether this file can be written
     */
    boolean canWrite();

    /**
     * Renames this file and returns renamed virtual file of this file. After renaming, this instance will be invalid,
     * returned instance will represent the original file. If this file is a directory, it must be empty.
     *
     * @param name
     *         new name
     * @return a new instance can represent the file after renaming
     * @throws VirIOException
     *         if any IO problem occurs
     */
    VirFile rename(String name) throws VirIOException;

    /**
     * Deletes this file, or this file's its sub-files and directories recursively if this file is a directory.
     *
     * @return delete result
     */
    default BatchResult deleteDirectory() {
        BatchResult result = new BatchResult();
        if (!exist()) {
            result.addOperated(this, null, VirIOException.CODE_FILE_NOT_EXIST);
            return result;
        }
        try {
            if (!isDirectory()) {
                delete();
                result.addOperated(this, null, VirIOException.CODE_NONE);
            } else {
                listStream().forEach(f -> {
                    if (f.isDirectory()) {
                        result.addResult(f.deleteDirectory());
                    } else {
                        try {
                            f.delete();
                            result.addOperated(f, null, VirIOException.CODE_NONE);
                        } catch (VirIOException e) {
                            result.addOperated(f, null, e.getCode());
                        }
                    }
                });
                delete();
                result.addOperated(this, null, VirIOException.CODE_NONE);
            }
        } catch (VirIOException e) {
            result.addOperated(this, null, e.getCode());
        }
        return result;
    }

    /**
     * Moves this file into specified destination. Actual dest file will be returned.
     *
     * @param dest
     *         specified destination
     * @param copyOption
     *         option of move
     * @return actual dest file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    default VirFile move(VirFile dest, VirCopyOption copyOption) throws VirIOException {
        if (getFileSystem().isSameFileSystem(dest.getFileSystem())) {
            return getFileSystem().move(this, dest, copyOption);
        }
        VirFile actual = channelCopy(dest, copyOption);
        delete();
        return actual;
    }

    /**
     * Moves this file into specified destination. If this file is a directory, it will move all sub-files and
     * directories recursively. This method try to move each file under the directory, it doesn't break if any file
     * leads a problem.
     *
     * @param dest
     *         specified destination
     * @param copyOption
     *         option of move
     * @return moving result
     */
    default BatchResult moveDirectory(VirFile dest, VirCopyOption copyOption) {
        if (getFileSystem().isSameFileSystem(dest.getFileSystem())) {
            return getFileSystem().moveDirectory(this, dest, copyOption);
        }
        BatchResult result = new BatchResult();
        if (!exist()) {
            result.addOperated(this, dest, VirIOException.CODE_FILE_NOT_EXIST);
            return result;
        }
        try {
            if (!isDirectory()) {
                VirFile actual = channelCopy(dest, copyOption);
                delete();
                result.addOperated(this, actual, VirIOException.CODE_NONE);
            } else {
                VirFile actualDest = null;
                try {
                    actualDest = VirFileUtil.create(dest, true, copyOption);
                } catch (VirIOException e) {
                    result.addOperated(this, actualDest, e.getCode());
                    return result;
                }
                result.addOperated(this, actualDest, VirIOException.CODE_NONE);
                VirFile lamActual = actualDest;
                listStream().forEach(f -> {
                    VirFile sub = null;
                    try {
                        sub = lamActual.getSubFile(f.getFileName());
                        if (f.isDirectory()) {
                            result.addResult(f.moveDirectory(sub, copyOption));
                        } else {
                            VirFile actualSub = f.channelCopy(sub, copyOption);
                            f.delete();
                            result.addOperated(f, actualSub, VirIOException.CODE_NONE);
                        }
                    } catch (VirIOException e) {
                        result.addOperated(f, e.getFile(), e.getCode());
                    }
                });
            }
        } catch (VirIOException e) {
            result.addOperated(this, null, e.getCode());
        }
        return result;
    }

    /**
     * Copies this file into specified destination. Actual dest file will be returned.
     *
     * @param dest
     *         specified destination
     * @param copyOption
     *         option of copy
     * @return actual dest file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    default VirFile copy(VirFile dest, VirCopyOption copyOption) throws VirIOException {
        if (getFileSystem().isSameFileSystem(dest.getFileSystem())) {
            return getFileSystem().copy(this, dest, copyOption);
        }
        return channelCopy(dest, copyOption);
    }

    /**
     * Copies this file into specified destination. If this file is a directory, it will copy all sub-files and
     * directories recursively. This method try to copy each file under the directory, it doesn't break if any file
     * leads a problem.
     *
     * @param dest
     *         specified destination
     * @param copyOption
     *         option of move
     * @return copy result
     */
    default BatchResult copyDirectory(VirFile dest, VirCopyOption copyOption) {
        if (getFileSystem().isSameFileSystem(dest.getFileSystem())) {
            return getFileSystem().copyDirectory(this, dest, copyOption);
        }
        if (getFileSystem().isSameFileSystem(dest.getFileSystem())) {
            return getFileSystem().moveDirectory(this, dest, copyOption);
        }
        BatchResult result = new BatchResult();
        if (!exist()) {
            result.addOperated(this, dest, VirIOException.CODE_FILE_NOT_EXIST);
            return result;
        }
        try {
            if (!isDirectory()) {
                VirFile actual = channelCopy(dest, copyOption);
                result.addOperated(this, actual, VirIOException.CODE_NONE);
            } else {
                VirFile actualDest = null;
                try {
                    actualDest = VirFileUtil.create(dest, true, copyOption);
                } catch (VirIOException e) {
                    result.addOperated(this, actualDest, e.getCode());
                    return result;
                }
                result.addOperated(this, actualDest, VirIOException.CODE_NONE);
                VirFile lamActual = actualDest;
                listStream().forEach(f -> {
                    VirFile sub = null;
                    try {
                        sub = lamActual.getSubFile(f.getFileName());
                        if (f.isDirectory()) {
                            result.addResult(f.copyDirectory(sub, copyOption));
                        } else {
                            VirFile actualSub = f.channelCopy(sub, copyOption);
                            result.addOperated(f, actualSub, VirIOException.CODE_NONE);
                        }
                    } catch (VirIOException e) {
                        result.addOperated(f, e.getFile(), e.getCode());
                    }
                });
            }
        } catch (VirIOException e) {
            result.addOperated(this, null, e.getCode());
        }
        return result;
    }

    /**
     * Forcibly using file channel to copy this file into specified destination, even if there exist better
     * platform-interrelated ways. If this file is a directory, it must be empty. Actual dest file will be returned.
     * <p>
     * {@link #move(VirFile, VirCopyOption)}, {@link #copy(VirFile, VirCopyOption)}, {@link
     * #moveDirectory(VirFile, VirCopyOption)} and {@link #copyDirectory(VirFile, VirCopyOption)} will call this method
     * if the copy should across the file file system.
     *
     * @param dest
     *         specified destination
     * @param copyOption
     *         option of copy
     * @return actual dest file
     * @throws VirIOException
     *         if any IO problem occurs
     */
    default VirFile channelCopy(VirFile dest, VirCopyOption copyOption) throws VirIOException {
        if (!exist()) {
            throw new VirIOException(this, VirIOException.CODE_FILE_NOT_EXIST);
        }
        if (isDirectory() && dest.isDirectory()) {
            return dest;
        }
        VirFile actualDest = null;
        try {
            if (isDirectory()) {
                dest.createDerectory();
            } else {
                dest.create();
            }
            actualDest = dest;
        } catch (VirIOException e) {
            if (e.getCode() == VirIOException.CODE_FILE_EXISTED) {
                switch (copyOption) {
                    case THROW:
                        throw new VirIOException(dest, VirIOException.CODE_FILE_EXISTED);
                    case OVERWRITE:
                        actualDest = VirFileUtil.overwrite(dest, isDirectory());
                        break;
                    case NEW:
                        actualDest = VirFileUtil.createSameNameFile(dest, isDirectory());
                }
            } else {
                throw e;
            }
        }
        if (actualDest.isDirectory()) {
            return actualDest;
        }
        try {
            FileChannel s = open();
            FileChannel d = actualDest.open();
            s.transferTo(0, length(), d);
            d.force(true);
            d.close();
            s.close();
        } catch (IOException e) {
            throw new VirIOException(e);
        }
        return actualDest;
    }

    /**
     * Returns input stream at specified position.
     *
     * @param pos
     *         specified position
     * @return input stream at specified position
     * @throws VirIOException
     *         if any IO problem occurs
     */
    default InputStream getInputStream(long pos) throws VirIOException {
        return getFileSystem().getInputStream(this, pos);
    }

    /**
     * Returns output stream at specified position.
     *
     * @param pos
     *         specified position
     * @return output stream at specified position
     * @throws VirIOException
     *         if any IO problem occurs
     */
    default OutputStream getOutputStream(long pos) throws VirIOException {
        return getFileSystem().getOutputStream(this, pos);
    }

    /**
     * Opens this file and returns its channel.
     *
     * @return channel of this file
     */
    default FileChannel open() throws VirIOException {
        return getFileSystem().open(this);
    }

    /**
     * Node of result of batch operation, including operated file, dest file and result code. Result code is same as
     * code of {@link VirIOException}. If the operation is single-file-ed, dest file may be null.
     *
     * @author FredSuvn
     * @version 2016-09-12
     */
    class BatchResultNode {

        private final VirFile operated;

        private final VirFile dest;

        private final int result;

        /**
         * Constructs with operated file, dest file and result code.
         *
         * @param operated
         *         operated file
         * @param dest
         *         dest file
         * @param result
         *         result code
         */
        public BatchResultNode(VirFile operated, VirFile dest, int result) {
            this.operated = operated;
            this.dest = dest;
            this.result = result;
        }

        /**
         * Returns operated file.
         *
         * @return operated file
         */
        public VirFile getOperated() {
            return operated;
        }

        /**
         * Returns dest file.
         *
         * @return dest file
         */
        public VirFile getDest() {
            return dest;
        }

        /**
         * Returns result code.
         *
         * @return result code
         */
        public int getResultCode() {
            return result;
        }
    }

    /**
     * Status summary of result of batch operation.
     */
    enum ResultStatus {

        /**
         * Complete success.
         */
        SUCCESS,

        /**
         * Complete failure.
         */
        FAILED,

        /**
         * Partial success.
         */
        PARTIAL
    }

    /**
     * Result set of batch operation such as delete, move, copy whole directory.
     *
     * @author FredSuvn
     * @version 2016-09-08
     */
    class BatchResult {

        private final List<BatchResultNode> list = new LinkedList<>();

        private long total;

        private long success;

        private ResultStatus status = ResultStatus.SUCCESS;

        /**
         * Constructs an empty result.
         */
        public BatchResult() {
            this.total = 0;
            this.success = 0;
        }

        /**
         * Returns number of total operations.
         *
         * @return number of total operations
         */
        public long getTotal() {
            return total;
        }

        /**
         * Returns number of success operations.
         *
         * @return number of success operations
         */
        public long getSuccess() {
            return success;
        }

        /**
         * Returns operated file list. Returned list is read-only.
         *
         * @return operated file list
         */
        public List<BatchResultNode> getOperatedList() {
            return Collections.unmodifiableList(list);
        }

        /**
         * Adds a new operation result, including operated file, dest file and result code, into this result set.
         * Result code is same as code of {@link VirIOException}.
         *
         * @param operated
         *         operated file
         * @param dest
         *         dest file
         * @param result
         *         result code
         * @return this result after adding
         */
        public BatchResult addOperated(VirFile operated, VirFile dest, int result) {
            list.add(new BatchResultNode(operated, dest, result));
            total++;
            if (result == VirIOException.CODE_NONE) {
                success++;
            }
            if (result != VirIOException.CODE_NONE) {
                if (success == 0) {
                    status = ResultStatus.FAILED;
                } else {
                    status = ResultStatus.PARTIAL;
                }
            }
            return this;
        }

        /**
         * Adds specified result set into this result set.
         *
         * @param result
         *         specified result
         * @return this result after adding
         */
        public BatchResult addResult(BatchResult result) {
            list.addAll(result.getOperatedList());
            total += result.getTotal();
            success += result.getSuccess();
            if (success < total) {
                status = ResultStatus.PARTIAL;
            } else if (success == 0 && total > 0) {
                status = ResultStatus.FAILED;
            }
            return this;
        }

        /**
         * Returns status of this result set.
         *
         * @return status of this result set
         */
        public ResultStatus getStatus() {
            return status;
        }
    }

    /**
     * Copy option.
     *
     * @author FredSuvn
     * @version 2016-09-08
     */
    enum VirCopyOption {

        /**
         * Throwing exception if dest file has already existed.
         */
        THROW,

        /**
         * Overwriting if dest file has already existed.
         */
        OVERWRITE,

        /**
         * Creating new file of which name like "dest(2)" if dest file has already existed.
         */
        NEW
    }
}