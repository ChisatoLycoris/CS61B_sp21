package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Record the head and branches pointer in the current Gitlet repository.
 * @author Ming
 */

public class Branches implements Serializable {
    /** head pointer to the commit restore in sha-1 string */
    private String head;
    /** name of current branch */
    private String currentBranch;
    /**
     * branches pointer
     * records branch name in first String
     * and the commit in sha-1 in second String
     */
    private Map<String, String> branches;
    private static File BRANCH_FILE = Utils.join(Repository.GITLET_DIR, ".branches");
    private Map<String, String> stagedFiles;
    private Map<String, String> stagedForRemovalFiles;

    private Branches() {
        branches = new HashMap<>();
        stagedFiles = new HashMap<>();
        stagedForRemovalFiles = new HashMap<>();
    }

    public static Branches init() {
        if (!BRANCH_FILE.exists()) {
            Branches init = new Branches();
            Commit initialCommit = new Commit();
            initialCommit.persist();
            init.head = Utils.sha1(initialCommit);
            init.createNewBranch("master", init.head);
            init.currentBranch = "master";
            return init;
        }
        throw new GitletException("call Branches.init() in an initialized Gitlet directory. ");
    }

    public static boolean exists() {
        return BRANCH_FILE.exists();
    }

    public static Branches getInstance() {
        if (BRANCH_FILE.exists()) {
            return Utils.readObject(BRANCH_FILE, Branches.class);
        }
        throw new GitletException("Not in an initialized Gitlet directory.");
    }

    public void persist() {
        Utils.writeObject(BRANCH_FILE, this);
    }

    public void createNewBranch(String branchName, String pointer) {
        branches.put(branchName, pointer);
        Utils.writeObject(BRANCH_FILE, this);
    }

    public Map<String, String> getStagedFiles() {
        return stagedFiles;
    }

    public Map<String, String> getStagedForRemovalFiles() {
        return stagedForRemovalFiles;
    }

    public boolean isTracking(String fileName) {
        Commit head = Utils.readObject(Utils.join(Repository.COMMIT_DIR, this.head), Commit.class);
        return head.isTracking(fileName);
    }

    public boolean isTracking(String fileName, String fileHash) {
        Commit head = Utils.readObject(Utils.join(Repository.COMMIT_DIR, this.head), Commit.class);
        return head.isTracking(fileName, fileHash);
    }

    public boolean isStaged(String fileName) {
        return stagedFiles.containsKey(fileName);
    }

    public boolean isStagedForRemoval(String fileName) {
        return stagedForRemovalFiles.containsKey(fileName);
    }

    public void stage(String fileName, File file, String fileHash) {
        stagedFiles.put(fileName, fileHash);
        byte[] contents = Utils.readContents(file);
        File stageFile = Utils.join(Repository.STAGE_DIR, fileHash);
        try {
            stageFile.createNewFile();
        } catch (IOException e) {
            throw new GitletException("I/O error occurs while create new stageFile");
        }
        Utils.writeContents(stageFile, contents);
    }

    public void unStage(String fileName) {
        String fileHash = stagedFiles.remove(fileName);
        if (fileHash != null) {
            File stagedFile = Utils.join(Repository.STAGE_DIR, fileHash);
            Utils.restrictedDelete(stagedFile);
        }
    }

    public void stageForRemoval(String fileName, File file, String fileHash) {
        stagedForRemovalFiles.put(fileName, fileHash);
        File rmFile = Utils.join(Repository.RM_DIR, fileHash);
        try {
            rmFile.createNewFile();
        } catch (IOException e) {
            throw new GitletException("I/O error occurs while create new rmFile");
        }
        byte[] contents = Utils.readContents(file);
        Utils.writeContents(rmFile, contents);
        file.delete();
    }

    public void unStageForRemoval(String fileName) {
        String rmFileHash = stagedForRemovalFiles.remove(fileName);
        if (rmFileHash != null) {
            File rmFile = Utils.join(Repository.RM_DIR, rmFileHash);
            Utils.restrictedDelete(rmFile);
        }
        recoverFile(head, fileName);
    }

    public void recoverFile(String commitHash, String fileName) {
        File commitStorage = Utils.join(Repository.COMMIT_DIR, commitHash);
        if (!commitStorage.exists()) {
            Main.exitWithError("no such commit exists");
        }
        Commit commit = Utils.readObject(commitStorage, Commit.class);
        String commitFileHash = commit.trackingFile(fileName);
        File commitFile = Utils.join(Repository.BLOB_DIR, commitFileHash);
        byte[] contents = Utils.readContents(commitFile);
        File recoverFile = Utils.join(Repository.CWD, fileName);
        try {
            recoverFile.createNewFile();
        } catch (IOException e) {
            throw new GitletException("I/O error occurs while create new stageFile");
        }
        Utils.writeContents(recoverFile, contents);
    }
}
