package com.cogician.myfiles;

import org.apache.commons.io.FilenameUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;

/**
 * Launch class which the main method in.
 *
 * @author FredSuvn
 * @version 2016-09-03
 */
public class Launch {

    private static Logger logger = LoggerFactory.getLogger("console");

    /**
     * Main method.
     *
     * @param args
     *         arguments
     */
    public static void main(String[] args) {
//        launch("-port", "10086");
//        String regex = "(?i)book";
//        System.out.println("BoOk".matches(regex));
//
//        Path p = Paths.get("123.txt");
//        AclFileAttributeView v = Files.getFileAttributeView(p, AclFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
//        try {
//            //Files.createLink(Paths.get("456.txt"), p);
//            System.out.println(v.getAcl());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(FilenameUtils.concat("fass", "fsfas"));
//        System.out.println(FilenameUtils.getName("/eee/fefef/fsdfsd.d"));
//        System.out.println(FilenameUtils.getName("/eee/fefef/fsdfsd.d/"));
//        System.out.println(FilenameUtils.getPath("/eee/fefef/fsdfsd.d"));
//        System.out.println(FilenameUtils.getPath("/eee/fefef/fsdfsd.d/"));
//
//        File ff = new File("ccc.txt");
//        System.out.println(ff.getName());
//        ff.renameTo(new File("ttt.txt"));
//        System.out.println(ff.getName());
        launch("-port", "569", "");
    }

    public static void launch(String... args) {
        logger.info("Starts myfiles...");
        ArgsOptions options = new ArgsOptions();
        CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);
            logger.info("port = {}", options.getPort());
        } catch (CmdLineException e) {
            throw new RuntimeException(e);
        }
    }
}
