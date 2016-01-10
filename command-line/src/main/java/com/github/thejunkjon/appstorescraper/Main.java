package com.github.thejunkjon.appstorescraper;

import com.github.thejunkjon.appstorescraper.googleplay.GooglePlayInformationProvider;
import org.apache.commons.cli.*;

public class Main {

    public static void main(final String[] args) {
        final Options options = new Options();
        options.addOption("h", false, "prints the help information");
        options.addOption("l", true, "retrieves app information for the specified package name.");

        final CommandLineParser commandLineParser = new DefaultParser();
        try {
            final CommandLine commandLine = commandLineParser.parse(options, args, true);
            if (commandLine.hasOption("h")) {
                final HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.printHelp("java: ", options);
            }

            if (commandLine.hasOption("l")) {
                final GooglePlayInformationProvider googlePlayInformationProvider = new GooglePlayInformationProvider();
                final String packageName = commandLine.getOptionValue("l");
                final String googlePlayAppInformation = googlePlayInformationProvider.getAppInformation(packageName);
                System.out.println(googlePlayAppInformation);
            }
        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
        }
    }
}
