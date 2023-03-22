package gitlet;

import java.io.File;

/**
 *  Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Ming
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            exitWithError("Please enter a command.");
        }
        String firstArg = args[0];
        switch (firstArg) {
            case "init" -> {
                validateInit(args);
                Repository.init();
            }
            case "add" -> {
                File file = validateAdd(args);
                Repository.add(args[1], file);
            }
            case "commit" -> {
                validateCommit(args);
                Repository.commit(args[1]);
            }
            case "rm" -> {
                validateRm(args);
                Repository.rm(args[1]);
            }
            case "log" -> {
                validateLog(args);
                Repository.log();
            }
            case "global-log" -> {
                validateGlobalLog(args);
                Repository.globalLog();

            }
            case "find" -> {
                validateFind(args);
                Repository.find(args[1]);
            }
            case "status" -> {
                validateStatus(args);
                Repository.status();
            }
            default -> exitWithError("No command with that name exists.");
        }
    }

    public static void exitWithError(String message) {
        if (message != null && !message.equals("")) {
            System.out.println(message);
        }
        System.exit(0);
    }

    private static void validateInit(String[] args) {
        if (args.length != 1) {
            exitWithError("Incorrect operands.");
        }
        if (Repository.exists()) {
            exitWithError("A Gitlet version-control system already exists in the current directory.");
        }
    }

    private static File validateAdd(String[] args) {
        if (args.length != 2) {
            exitWithError("Incorrect operands.");
        }
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
        File file = Utils.join(Repository.CWD, args[1]);
        if (!file.exists()) {
            exitWithError("File does not exit.");
        }
        return file;
    }

    private static void validateCommit(String[] args) {
        if (args.length == 1 || args[1].trim().length() == 0) {
            exitWithError("Please enter a commit message.");
        }
        if (args.length > 2) {
            exitWithError("Incorrect operands.");
        }
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
        if (Repository.getBranches().stageAreaIsEmpty()) {
            exitWithError("No changes added to the commit.");
        }
    }

    private static void validateRm(String[] args) {
        if (args.length != 2) {
            exitWithError("Incorrect operands.");
        }
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
    }

    private static void validateLog(String[] args) {
        if (args.length != 1) {
            exitWithError("Incorrect operands.");
        }
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
    }

    private static void validateGlobalLog(String[] args) {
        if (args.length != 1) {
            exitWithError("Incorrect operands.");
        }
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
    }

    private static void validateFind(String[] args) {
        if (args.length != 2) {
            exitWithError("Incorrect operands.");
        }
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
    }

    private static void validateStatus(String[] args) {
        if (args.length != 1) {
            exitWithError("Incorrect operands.");
        }
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
    }
}
