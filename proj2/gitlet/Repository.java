package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static gitlet.Utils.*;

/**
 *  Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Ming
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The commit directory. */
    public static final File COMMIT_DIR = join(GITLET_DIR, "commit");
    /** The blob directory. */
    public static final File BLOB_DIR = join(GITLET_DIR, "blob");
    /** The stage directory.*/
    public static final File STAGE_DIR = join(GITLET_DIR, "stage");

    public static final File RM_DIR = join(STAGE_DIR, "remove");
    private static Branches branches;

    /**
     * Creates a new Gitlet version-control system in the current directory.
     * The system will start with initial Commit and master Branch.
     */
    public static void init() {
        if (!GITLET_DIR.exists()) {
            GITLET_DIR.mkdir();
            COMMIT_DIR.mkdir();
            BLOB_DIR.mkdir();
            STAGE_DIR.mkdir();
            RM_DIR.mkdir();
            branches = Branches.init();
            return;
        }
        throw new GitletException("call Repository.init() in an initialized Gitlet directory ");
    }

    public static boolean exists() {
        return GITLET_DIR.exists();
    }

    public static Branches getBranches() {
        if (branches != null) {
            return branches;
        }
        if (GITLET_DIR.exists()) {
            branches = Branches.getInstance();
            return branches;
        }
        throw new GitletException("call Repository.getBranches() without .gitlet directory ");
    }

    /**
     * Adds a copy of the file as it currently exists to the staging area.
     * Staging an already-staged file overwrites the previous entry in the staging area with the new contents.
     * If the current working version of the file is identical to the version in the current commit,
     * do not stage it to be added, and remove it from the staging area if it is already there
     * (as can happen when a file is changed, added, and then changed back to it’s original version).
     * The file will no longer be staged for removal (see gitlet rm), if it was at the time of the command.
     * @param fileName
     * @param file
     */
    public static void add(String fileName, File file) {
        String fileHash = Utils.sha1(Utils.readContents(file));
        Branches current = getBranches();
        if (current.isTracking(fileName, fileHash)) {
            if (current.isStaged(fileName)) {
                current.unStage(fileName);
            }
            if (current.isStagedForRemoval(fileName)) {
                current.unStageForRemoval(fileName);
            }
            return;
        }
        current.stage(fileName, file, fileHash);
    }

    public static void commit(String message) {
        getBranches().commit(message);
    }

    public static void rm(String fileName) {
        Branches current = getBranches();
        if (current.isStaged(fileName)) {
            current.unStage(fileName);
            return;
        }
        if (current.isTracking(fileName)) {
            current.stageForRemoval(fileName);
            return;
        }
        Main.exitWithError("No reason to remove the file.");
    }

    /**
     * Create a new File under the given directory with given FileName and contents.
     * @param dir Target directory
     * @param fileName File Name
     * @param contentFile The content wants to store
     * @return the created File
     */
    public static File createNewFile(File dir, String fileName, File contentFile) {
        File newFile = Utils.join(dir, fileName);
        byte[] contents = Utils.readContents(contentFile);
        Utils.writeContents(newFile, contents);
        return newFile;
    }

    public static void recoverFile(String commitHash, String fileName) {
        File commitStorage = Utils.join(Repository.COMMIT_DIR, commitHash);
        if (!commitStorage.exists()) {
            Main.exitWithError("no such commit exists");
        }
        Commit commit = Utils.readObject(commitStorage, Commit.class);
        String commitFileHash = commit.trackingFile(fileName);
        File commitFile = Utils.join(Repository.BLOB_DIR, commitFileHash);
        Repository.createNewFile(Repository.CWD, fileName, commitFile);
    }

    public static void log() {
        getBranches().log();
    }

    public static void globalLog() {
        for (String fileName : Utils.plainFilenamesIn(Repository.COMMIT_DIR)) {
            Commit target = Commit.findCommit(fileName);
            System.out.println(target);
        }
    }

    public static void find(String message) {
        boolean found = false;
        for (String fileName : Utils.plainFilenamesIn(Repository.COMMIT_DIR)) {
            Commit target = Commit.findCommit(fileName);
            if (target.getMessage().contains(message)) {
                System.out.println(fileName);
                found = true;
            }
        }
        if (!found) {
            Main.exitWithError("Found no commit with that message.");
        }
    }

    public static void status() {
        getBranches().status();
    }

    public static String checkBranch(String branchName) {
        return getBranches().checkBranch(branchName);
    }

    public static boolean trackingByCommit(String commitHash, String fileName) {
        Commit commit = Commit.findCommit(commitHash);
        if (commit == null) {
            Main.exitWithError("No commit with that id exists.");
        }
        return commit.isTracking(fileName);
    }

    public static void checkoutBranch(String branch) {
        getBranches().checkoutBranch(branch);
    }

    public static void checkoutFile(String fileName) {
        Commit head = getBranches().headCommit();
        File commitFile = Utils.join(BLOB_DIR, head.trackingFile(fileName));
        createNewFile(CWD, fileName, commitFile);
    }

    public static void checkoutFile(String commitHash, String fileName) {
        Commit commit = Commit.findCommit(commitHash);
        File commitFile = Utils.join(BLOB_DIR, commit.trackingFile(fileName));
        createNewFile(CWD, fileName, commitFile);
    }

    public static void branch(String branchName) {
        getBranches().branch(branchName);
    }

    public static void rmBranch(String branchName) {
        getBranches().rmBranch(branchName);
    }
}
