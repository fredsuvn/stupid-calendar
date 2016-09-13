package com.cogician.myfiles.service.filesystem.local;

import com.cogician.myfiles.exception.VirIOException;
import com.cogician.myfiles.model.VirUser;
import com.cogician.myfiles.model.VirFile;
import com.cogician.myfiles.service.VirFileSystemService;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Path;

/**
 * Local virtual file implementation, based on local file system.
 *
 * @author FredSuvn
 * @version 2016-09-04
 */
public class LocalVirFile implements VirFile {

    private static VirFileSystemService fs = null;

    private final VirUser owner;

    private final File file;

    private final Path path;

    LocalVirFile(VirUser owner, String fileName) {
        this.owner = owner;
        this.file = new File(fileName);
        this.path = file.toPath();
    }

    @Override
    public String getFileName() {
        return file.getName();
    }

    @Override
    public String getPath() {
        return null;//fs.getPathScheme() + "://" + owner.getUserName() + "/" +
    }

    @Override
    public long length() {
        return 0;
    }

    @Override
    public long creationTime() {
        return 0;
    }

    @Override
    public long lastAccess() {
        return 0;
    }

    @Override
    public long lastModified() {
        return 0;
    }

    @Override
    public boolean isUserRootDirectory() {
        return false;
    }

    @Override
    public VirFileSystemService getFileSystem() {
        return null;
    }

    @Override
    public VirUser getOwner() {
        return null;
    }

    @Override
    public boolean exist() {
        return false;
    }

    @Override
    public boolean isSameFile(VirFile given) {
        return false;
    }

    @Override
    public void create() throws VirIOException {

    }

    @Override
    public void createDerectory() throws VirIOException {

    }

    @Override
    public void createLink(VirFile source) throws VirIOException {

    }

    @Override
    public void delete() throws VirIOException {

    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public long countSubFiles() throws VirIOException {
        return 0;
    }

    @Nullable
    @Override
    public VirFile getParent() throws VirIOException {
        return null;
    }

    @Override
    public VirFile addSubFile(String fileName) throws VirIOException {
        return null;
    }

    @Override
    public VirFile getSubFile(String fileName) throws VirIOException {
        return null;
    }

    @Override
    public long getTotalDirectoryLength() throws VirIOException {
        return 0;
    }

    @Override
    public boolean isLink() {
        return false;
    }

    @Override
    public VirFile getSource() throws VirIOException {
        return null;
    }

    @Override
    public VirFile getTerminalSource() throws VirIOException {
        return null;
    }

    @Override
    public boolean canRead() {
        return false;
    }

    @Override
    public boolean canWrite() {
        return false;
    }

    @Override
    public VirFile rename(String name) throws VirIOException {
        return null;
    }
}
