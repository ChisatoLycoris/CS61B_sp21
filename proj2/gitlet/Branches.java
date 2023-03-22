package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Record the head and branches pointer in the current Gitlet repository.
 * @author Ming
 */

public class Branches implements Serializable {
    /** name of current branch */
    private String currentBranch;
    /**
     * branches pointer
     * records branch name in first String
     * and the commit in sha-1 in second String
     *
     * branches.get(currentBranch) = head pointer
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
            init.currentBranch = "master";
            init.createNewBranch("master", initialCommit.hash());
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

    private void persist() {
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
        Commit head = Commit.findCommit(branches.get(fileName));
        return head.isTracking(fileName);
    }

    public boolean isTracking(String fileName, String fileHash) {
        Commit head = Commit.findCommit(branches.get(currentBranch));
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
        File stageFile = Repository.createNewFile(Repository.STAGE_DIR, fileHash, file);
        persist();
    }

    public void unStage(String fileName) {
        String fileHash = stagedFiles.remove(fileName);
        File stagedFile = Utils.join(Repository.STAGE_DIR, fileHash);
        Utils.restrictedDelete(stagedFile);
        persist();
    }

    public void stageForRemoval(String fileName) {
        String fileHash = Commit.findCommit(branches.get(currentBranch)).trackingFile(fileName);
        File commitFile = Utils.join(Repository.COMMIT_DIR, fileHash);
        stagedForRemovalFiles.put(fileName, fileHash);
        File rmFile = Repository.createNewFile(Repository.RM_DIR, fileHash, commitFile);
        File file = Utils.join(Repository.CWD, fileName);
        file.delete();
        persist();
    }

    public void unStageForRemoval(String fileName) {
        String rmFileHash = stagedForRemovalFiles.remove(fileName);
        if (rmFileHash != null) {
            File rmFile = Utils.join(Repository.RM_DIR, rmFileHash);
            Utils.restrictedDelete(rmFile);
        }
        Repository.recoverFile(branches.get(currentBranch), fileName);
        persist();
    }

    public boolean stageAreaIsEmpty() {
        return stagedFiles.isEmpty() && stagedForRemovalFiles.isEmpty();
    }

    public void commit(String message) {
        Commit newCommit = new Commit(message, branches.get(currentBranch));
        for (String fileName : stagedFiles.keySet()) {
            String fileHash = stagedFiles.remove(fileName);
            newCommit.trackFile(fileName, fileHash);
            File stagedFile = Utils.join(Repository.STAGE_DIR, fileHash);
            Repository.createNewFile(Repository.BLOB_DIR, fileHash, stagedFile);
            Utils.restrictedDelete(stagedFile);
        }
        for (String fileName : stagedForRemovalFiles.keySet()) {
            String fileHash = stagedForRemovalFiles.remove(fileName);
            newCommit.unTrackFile(fileName, fileHash);
            File rmFile = Utils.join(Repository.RM_DIR, fileHash);
            Utils.restrictedDelete(rmFile);
        }
        branches.put(currentBranch, newCommit.hash());
        newCommit.persist();
        persist();
    }

    public void log() {
        Commit current = Commit.findCommit(branches.get(currentBranch));
        while (current != null) {
            System.out.println(current);
            current = current.getParent();
        }
    }

}
