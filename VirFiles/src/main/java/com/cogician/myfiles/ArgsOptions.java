package com.cogician.myfiles;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * Arguments when starts application.
 *
 * @author FredSuvn
 * @version 2016-09-04
 */
public class ArgsOptions {

    @Option(name = "-port", usage = "Specifies listening port.", required = true)
    private int port;

    @Argument
    private List<String> arguments = new ArrayList<String>();

    public int getPort() {
        return port;
    }

    public List<String> getArguments() {
        return arguments;
    }
}
