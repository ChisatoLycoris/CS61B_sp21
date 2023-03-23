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
            }
            case "add" -> {
                validateAdd(args);
            }
            case "commit" -> {
                validateCommit(args);
            }
            case "rm" -> {
                validateRm(args);
            }
            case "log" -> {
                validateLog(args);
            }
            case "global-log" -> {
                validateGlobalLog(args);
            }
            case "find" -> {
                validateFind(args);
            }
            case "status" -> {
                validateStatus(args);
            }
            case "checkout" -> {
                validateCheckout(args);
            }
            case "branch" -> {
                validateBranch(args);
            }
            case "rm-branch" -> {
                validateRmBranch(args);
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
        if (Repository.exists()) {
            exitWithError("A Gitlet version-control system already exists in the current directory.");
        }
        if (args.length != 1) {
            exitWithError("Incorrect operands.");
        }
        Repository.init();
    }

    private static void validateAdd(String[] args) {
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
        if (args.length != 2) {
            exitWithError("Incorrect operands.");
        }
        File file = Utils.join(Repository.CWD, args[1]);
        if (!file.exists()) {
            exitWithError("File does not exit.");
        }
        Repository.add(args[1], file);
    }

    private static void validateCommit(String[] args) {
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
        if (args.length == 1 || args[1].trim().length() == 0) {
            exitWithError("Please enter a commit message.");
        }
        if (args.length > 2) {
            exitWithError("Incorrect operands.");
        }
        if (Repository.getBranches().stageAreaIsEmpty()) {
            exitWithError("No changes added to the commit.");
        }
        Repository.commit(args[1]);
    }

    private static void validateRm(String[] args) {
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
        if (args.length != 2) {
            exitWithError("Incorrect operands.");
        }
        Repository.rm(args[1]);
    }

    private static void validateLog(String[] args) {
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
        if (args.length != 1) {
            exitWithError("Incorrect operands.");
        }
        Repository.log();
    }

    private static void validateGlobalLog(String[] args) {
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
        if (args.length != 1) {
            exitWithError("Incorrect operands.");
        }
        Repository.globalLog();
    }

    private static void validateFind(String[] args) {
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
        if (args.length != 2) {
            exitWithError("Incorrect operands.");
        }
        Repository.find(args[1]);
    }

    private static void validateStatus(String[] args) {
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
        if (args.length != 1) {
            exitWithError("Incorrect operands.");
        }
        Repository.status();
    }

    private static void validateCheckout(String[] args) {
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
        switch (args.length) {
            case 2 -> {
                String checkBranchResult = Repository.checkBranch(args[1]);
                if (!"ok".equals(checkBranchResult)) {
                    exitWithError(checkBranchResult);
                }
                Repository.checkoutBranch(args[1]);
            }
            case 3 -> {
                if (!"--".equals(args[1])) {
                    exitWithError("Incorrect operands.");
                }
                if (!Repository.getBranches().isTracking(args[2])) {
                    exitWithError("File does not exist in that commit.");
                }
                Repository.checkoutFile(args[2]);
            }
            case 4 -> {
                if (!"--".equals(args[2])) {
                    exitWithError("Incorrect operands.");
                }
                if (!Repository.trackingByCommit(args[1], args[3])) {
                    exitWithError("File does not exist in that commit.");
                }
                Repository.checkoutFile(args[1], args[3]);
            }
            default -> {
                exitWithError("Incorrect operands.");
            }
        }
    }

    private static void validateBranch(String[] args) {
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
        if (args.length != 2) {
            exitWithError("Incorrect operands.");
        }
        if (Repository.getBranches().containsBranch(args[1])) {
            exitWithError("A branch with that name already exists.");
        }
        Repository.branch(args[1]);
    }

    private static void validateRmBranch(String[] args) {
        if (!Repository.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
        if (args.length != 2) {
            exitWithError("Incorrect operands.");
        }
        if (!Repository.getBranches().containsBranch(args[1])) {
            exitWithError("A branch with that name does not exist.");
        }
        if (Repository.getBranches().getCurrentBranch().equals(args[1])) {
            exitWithError("Cannot remove the current branch.");
        }
        Repository.rmBranch(args[1]);
    }
}
