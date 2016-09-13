package com.cogician.myfiles.service.filesystem.local;

import com.cogician.myfiles.exception.VirIOException;
import com.cogician.myfiles.model.VirUser;
import com.cogician.myfiles.model.VirFile;
import com.cogician.myfiles.service.VirFileSystemService;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.stream.Stream;

/**
 * Local file system service.
 *
 * @author FredSuvn
 * @version 2016-09-12
 */
public class LocalFileSystemService implements VirFileSystemService<LocalVirFile> {

    @Override
    public String getPathScheme() {
        return "local";
    }

    @Override
    public LocalVirFile getFile(VirUser user, String filePath) throws VirIOException {
        return new LocalVirFile(user, user.getUserLocalPath() + "/" + filePath);
    }

    @Override
    public List<LocalVirFile> list(LocalVirFile dir) throws VirIOException {
        return null;
    }

    @Override
    public Stream<LocalVirFile> listStream(LocalVirFile dir) throws VirIOException {
        return null;
    }

    @Override
    public LocalVirFile move(LocalVirFile source, LocalVirFile dest, VirFile.VirCopyOption copyOption) throws VirIOException {
        return null;
    }

    @Override
    public VirFile.BatchResult moveDirectory(LocalVirFile source, LocalVirFile dest, VirFile.VirCopyOption copyOption) {
        return null;
    }

    @Override
    public LocalVirFile copy(LocalVirFile source, LocalVirFile dest, VirFile.VirCopyOption copyOption) throws VirIOException {
        return null;
    }

    @Override
    public VirFile.BatchResult copyDirectory(LocalVirFile source, LocalVirFile dest, VirFile.VirCopyOption copyOption) {
        return null;
    }

    @Override
    public InputStream getInputStream(LocalVirFile file, long pos) throws VirIOException {
        return null;
    }

    @Override
    public OutputStream getOutputStream(LocalVirFile file, long pos) throws VirIOException {
        return null;
    }

    @Override
    public FileChannel open(LocalVirFile file) throws VirIOException {
        return null;
    }

    @Override
    public boolean isSameFileSystem(VirFileSystemService fileSystemService) {
        return false;
    }
}
