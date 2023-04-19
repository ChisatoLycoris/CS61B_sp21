package gitlet;

import java.io.File;

import static gitlet.Utils.*;

/**
 *  Represents a gitlet repository.
 *  contains all the gitlet command method.
 *
 *  @author Ming
 */
public class Repository {
    /**
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
     * add command
     * Adds a copy of the file as it currently exists to the staging area.
     * Staging an already-staged file overwrites the previous entry
     * in the staging area with the new contents.
     * If the current working version of the file is identical to the version in the current commit,
     * do not stage it to be added, and remove it from the staging area if it is already there
     * (as can happen when a file is changed, added, and then changed back to original version).
     * The file will no longer be staged for removal, if it was at the time of the command.
     * @param fileName file name
     * @param file file object representation
     */
    public static void add(String fileName, File file) {
        String fileHash = Utils.sha1(fileName, Utils.readContents(file));
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

    /**
     * commit command
     * @param message commit message as log information
     */
    public static void commit(String message) {
        getBranches().commit(message, null);
    }

    /**
     * rm command
     * @param fileName file name
     */
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
     * log command
     */
    public static void log() {
        getBranches().log();
    }

    /**
     * global-log command
     */
    public static void globalLog() {
        for (String fileName : Utils.plainFilenamesIn(Repository.COMMIT_DIR)) {
            Commit target = findCommit(fileName);
            System.out.println(target);
        }
    }

    /**
     * find command
     * @param message commit message
     */
    public static void find(String message) {
        boolean found = false;
        for (String fileName : Utils.plainFilenamesIn(Repository.COMMIT_DIR)) {
            Commit target = findCommit(fileName);
            if (target.getMessage().contains(message)) {
                System.out.println(fileName);
                found = true;
            }
        }
        if (!found) {
            Main.exitWithError("Found no commit with that message.");
        }
    }

    /**
     * status command
     */
    public static void status() {
        getBranches().status();
    }

    /**
     * checkout command *1 (checkout [branch name])
     * @param branchName branch name
     */
    public static void checkoutBranch(String branchName) {
        getBranches().checkoutBranch(branchName);
    }

    /**
     * checkout command *2 (checkout -- [file name])
     * @param fileName file name
     */
    public static void checkoutFile(String fileName) {
        Commit head = getBranches().headCommit();
        File commitFile = Utils.join(BLOB_DIR, head.trackingFile(fileName));
        createNewFile(CWD, fileName, commitFile);
    }

    /**
     * checkout command *3 (checkout [commit id] -- [file name])
     * @param commit commit object representation of the commit id
     * @param fileName file name
     */
    public static void checkoutFile(Commit commit, String fileName) {
        File commitFile = Utils.join(BLOB_DIR, commit.trackingFile(fileName));
        createNewFile(CWD, fileName, commitFile);
    }

    /**
     * branch command
     * @param branchName branch name
     */
    public static void branch(String branchName) {
        getBranches().branch(branchName);
    }

    /**
     * rm-branch command
     * @param branchName branch name
     */
    public static void rmBranch(String branchName) {
        getBranches().rmBranch(branchName);
    }

    /**
     * reset command
     * @param commit commit object representation
     */
    public static void reset(Commit commit) {
        File file = Utils.join(BLOB_DIR, "test");
        Utils.writeContents(file, "pass validate");
        getBranches().reset(commit);
    }

    /**
     * merge command
     * @param branchName branch name
     */
    public static void merge(String branchName) {
        Branches branch = getBranches();
        Commit branchHead = branch.getBranchHead(branchName);
        Commit spiltPoint = branch.findSpiltPoint(branchHead);
        if ("master".equals(branchName)) {
            File file = Utils.join(CWD, "spiltPoint");
            Utils.writeContents(file, spiltPoint.toString());
            File file2 = Utils.join(CWD, "head");
            Utils.writeContents(file2, branch.headCommit().toString());
            File file3 = Utils.join(CWD, "branchHead");
            Utils.writeContents(file3, branchHead.toString());
        }
        if (spiltPoint.hash().equals(branch.headCommit().hash())) {
            checkoutBranch(branchName);
            System.out.println("Current branch fast-forwarded.");
            return;
        }
        if (spiltPoint.hash().equals(branchHead.hash())) {
            System.out.println("Given branch is an ancestor of the current branch.");
            return;
        }
        getBranches().merge(branchName, branchHead, spiltPoint);
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

    /**
     * find the commit object stored in .gitlet directory with given commit id
     * @param commitHash commit id
     * @return commit object representation of the commit id, null if not existed
     */
    public static Commit findCommit(String commitHash) {
        File result = Utils.join(Repository.COMMIT_DIR, commitHash);
        if (!result.exists()) {
            return null;
        }
        return Utils.readObject(result, Commit.class);
    }

    /**
     * check the condition of current branch before checkout
     * @param branchName branch name client want to checkout
     * @return the check result represent in String
     */
    public static String checkBranchBeforeCheckout(String branchName) {
        return getBranches().checkBranchBeforeCheckout(branchName);
    }

    public static boolean untrackedFileBeImpacted(Commit commit) {
        return getBranches().untrackedFileBeImpactedByReset(commit);
    }

    public static boolean containsUntrackedFiles() {
        return !getBranches().untrackedFiles().isEmpty();
    }
}
