package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

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
    private TreeMap<String, String> branches;
    private static File BRANCH_FILE = Utils.join(Repository.GITLET_DIR, ".branches");
    private TreeMap<String, String> stagedFiles;
    private TreeMap<String, String> stagedForRemovalFiles;

    private Branches(Commit initialCommit) {
        branches = new TreeMap<>();
        stagedFiles = new TreeMap<>();
        stagedForRemovalFiles = new TreeMap<>();
        currentBranch = "master";
        branches.put("master", initialCommit.hash());
    }

    public static Branches init() {
        if (!BRANCH_FILE.exists()) {
            Commit initialCommit = new Commit();
            initialCommit.persist();
            Branches init = new Branches(initialCommit);
            init.persist();
            return init;
        }
        throw new GitletException("call Branches.init() in an initialized Gitlet directory. ");
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

    public Map<String, String> getStagedFiles() {
        Map<String, String> copy = new TreeMap<>();
        stagedFiles.keySet().forEach(fileName -> {
            copy.put(fileName, stagedFiles.get(fileName));
        });
        return copy;
    }

    public Map<String, String> getStagedForRemovalFiles() {
        Map<String, String> copy = new TreeMap<>();
        stagedForRemovalFiles.keySet().forEach(fileName -> {
            copy.put(fileName, stagedForRemovalFiles.get(fileName));
        });
        return copy;
    }

    public boolean isTracking(String fileName) {
        return headCommit().isTracking(fileName);
    }

    public boolean isTracking(String fileName, String fileHash) {
        return headCommit().isTracking(fileName, fileHash);
    }

    public boolean isStaged(String fileName) {
        return stagedFiles.containsKey(fileName);
    }

    public boolean isStagedForRemoval(String fileName) {
        return stagedForRemovalFiles.containsKey(fileName);
    }

    public void stage(String fileName, File file, String fileHash) {
        stagedFiles.put(fileName, fileHash);
        Repository.createNewFile(Repository.STAGE_DIR, fileHash, file);
        persist();
    }

    public void unStage(String fileName) {
        String fileHash = stagedFiles.remove(fileName);
        File stagedFile = Utils.join(Repository.STAGE_DIR, fileHash);
        Utils.restrictedDelete(stagedFile);
        persist();
    }

    public void stageForRemoval(String fileName) {
        String fileHash = Repository.findCommit(branches.get(currentBranch)).trackingFile(fileName);
        File commitFile = Utils.join(Repository.BLOB_DIR, fileHash);
        stagedForRemovalFiles.put(fileName, fileHash);
        Repository.createNewFile(Repository.RM_DIR, fileHash, commitFile);
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
        Repository.checkoutFile(headCommit(), fileName);
        persist();
    }

    public boolean stageAreaIsEmpty() {
        return stagedFiles.isEmpty() && stagedForRemovalFiles.isEmpty();
    }

    public void commit(String message, String branchName) {
        String branchHash = branchName == null ? null : branches.get(branchName);
        Commit newCommit = new Commit(message, branches.get(currentBranch), branchHash);
        Set<String> stagedFileNames = getStagedFiles().keySet();
        for (String fileName : stagedFileNames) {
            String fileHash = stagedFiles.remove(fileName);
            newCommit.trackFile(fileName, fileHash);
            File stagedFile = Utils.join(Repository.STAGE_DIR, fileHash);
            Repository.createNewFile(Repository.BLOB_DIR, fileHash, stagedFile);
            Utils.restrictedDelete(stagedFile);
        }
        Set<String> stagedForRemovalFileNames = getStagedForRemovalFiles().keySet();
        for (String fileName : stagedForRemovalFileNames) {
            String fileHash = stagedForRemovalFiles.remove(fileName);
            newCommit.unTrackFile(fileName, fileHash);
            File rmFile = Utils.join(Repository.RM_DIR, fileHash);
            Utils.restrictedDelete(rmFile);
        }
        newCommit.persist();
        branches.put(currentBranch, newCommit.hash());
        if (branchName != null) {
            branches.put(branchName, newCommit.hash());
        }
        persist();
    }

    public void log() {
        Commit current = Repository.findCommit(branches.get(currentBranch));
        while (current != null) {
            System.out.println(current);
            current = current.getParent();
        }
    }

    public void status() {
        printBranches();
        printStagedFile();
        printRemovedFile();
        printModificationNotStaged();
        printUntrackedFiles();
    }

    private void printBranches() {
        System.out.println("=== Branches ===");
        branches.navigableKeySet().forEach(branch -> {
            if (currentBranch.equals(branch)) {
                System.out.print("*");
            }
            System.out.println(branch);
        });
        System.out.println();
    }

    private void printStagedFile() {
        System.out.println("=== Staged Files ===");
        stagedFiles.navigableKeySet().forEach(System.out::println);
        System.out.println();
    }

    private void printRemovedFile() {
        System.out.println("=== Removed Files ===");
        stagedForRemovalFiles.navigableKeySet().forEach(System.out::println);
        System.out.println();
    }

    private void printModificationNotStaged() {
        System.out.println("=== Modifications Not Staged For Commit ===");
        Map<String, String> trackingFilesCopy = headCommit().getBlobs();
        Map<String, String> stagedFilesCopy = getStagedFiles();
        Set<String> result = new TreeSet<>();
        List<String> workingFiles = Utils.plainFilenamesIn(Repository.CWD);
        for (String fileName : workingFiles) {
            File cwdFile = Utils.join(Repository.CWD, fileName);
            String fileHash = Utils.sha1(fileName, Utils.readContents(cwdFile));
            String stagedHash = stagedFilesCopy.remove(fileName);
            String trackingHash = trackingFilesCopy.remove(fileName);
            if (stagedHash != null) {
                if (!stagedHash.equals(fileHash)) {
                    result.add(fileName + " (modified)");
                }
                continue;
            }
            if (trackingHash != null && !trackingHash.equals(fileHash)) {
                result.add(fileName + " (modified)");
            }
        }
        stagedFilesCopy.keySet().forEach(fileName -> {
            result.add(fileName + " (deleted)");
        });
        trackingFilesCopy.keySet().forEach(fileName -> {
            if (!stagedForRemovalFiles.containsKey(fileName)) {
                result.add(fileName + " (deleted)");
            }
        });
        result.stream().sorted().forEach(System.out::println);
        System.out.println();
    }

    private void printUntrackedFiles() {
        System.out.println("=== Untracked Files ===");
        List<String> workingFiles = Utils.plainFilenamesIn(Repository.CWD);
        workingFiles.forEach(fileName -> {
            if (!headCommit().isTracking(fileName) && !stagedFiles.containsKey(fileName)) {
                System.out.println(fileName);
            }
        });
        System.out.println();
    }

    public List<String> untrackedFiles() {
        List<String> workingFiles = Utils.plainFilenamesIn(Repository.CWD);
        List<String> untrackedFiles = new LinkedList<>();
        for (String fileName : workingFiles) {
            File file = Utils.join(Repository.CWD, fileName);
            String fileHash = Utils.sha1(fileName, Utils.readContents(file));
            if (!headCommit().isTracking(fileName, fileHash)) {
                untrackedFiles.add(fileName);
            }
        }
        return untrackedFiles;
    }

    public String checkBranchBeforeCheckout(String branchName) {
        if (!branches.containsKey(branchName)) {
            return "No such branch exists.";
        }
        if (currentBranch.equals(branchName)) {
            return "No need to checkout the current branch.";
        }
        if (untrackedFileBeImpactedByCheckout(Repository.findCommit(branches.get(branchName)))) {
            return "There is an untracked file in the way; delete it, or add and commit it first.";
        }
        return "ok";
    }

    public Commit headCommit() {
        return Repository.findCommit(branches.get(currentBranch));
    }

    public void checkoutBranch(String branchName) {
        List<String> workingFiles = Utils.plainFilenamesIn(Repository.CWD);
        Commit target = Repository.findCommit(branches.get(branchName));
        Map<String, String> branchFiles = target.getBlobs();
        branchFiles.keySet().forEach(fileName -> {
            File branchFile = Utils.join(Repository.BLOB_DIR, branchFiles.get(fileName));
            workingFiles.remove(fileName);
            Repository.createNewFile(Repository.CWD, fileName, branchFile);
        });
        workingFiles.forEach(fileName -> {
            File file = Utils.join(Repository.CWD, fileName);
            file.delete();
        });
        currentBranch = branchName;
        persist();
    }

    public void branch(String branchName) {
        branches.put(branchName, branches.get(currentBranch));
        persist();
    }

    public boolean containsBranch(String branchName) {
        return branches.containsKey(branchName);
    }

    public String getCurrentBranch() {
        return currentBranch;
    }

    public void rmBranch(String branchName) {
        branches.remove(branchName);
        persist();
    }

    public void reset(Commit commit) {
        List<String> workingFiles = Utils.plainFilenamesIn(Repository.CWD);
        commit.getBlobs().keySet().forEach(fileName -> {
            Repository.checkoutFile(commit, fileName);
            workingFiles.remove(fileName);
        });
        workingFiles.stream().filter(this::isTracking).forEach(fileName -> {
            File file = Utils.join(Repository.CWD, fileName);
            file.delete();
        });
        branches.put(currentBranch, commit.hash());
        clearStagedArea();
        persist();
    }

    private void clearStagedArea() {
        stagedFiles.keySet().forEach(fileName -> {
            File file = Utils.join(Repository.STAGE_DIR, stagedFiles.get(fileName));
            Utils.restrictedDelete(file);
        });
        stagedFiles.clear();
        stagedForRemovalFiles.keySet().forEach(fileName -> {
            File file = Utils.join(Repository.RM_DIR, stagedForRemovalFiles.get(fileName));
            Utils.restrictedDelete(file);
        });
        stagedForRemovalFiles.clear();
    }

    public void merge(String branchName, Commit branchHead, Commit spiltPoint) {
        Commit currentHead = headCommit();
        Map<String, String> branchHeadFiles = branchHead.getBlobs();
        Map<String, String> spiltPointFiles = spiltPoint.getBlobs();
        Map<String, String> currentHeadFiles = currentHead.getBlobs();
        boolean mergeConflict = false;
        for (String fileName : branchHead.getBlobs().keySet()) {
            String branchHeadFileHash = branchHeadFiles.remove(fileName);
            String spiltPointFileHash = spiltPointFiles.remove(fileName);
            String currentHeadFileHash = currentHeadFiles.remove(fileName);
            if (spiltPointFileHash == null) {
                if (currentHeadFileHash == null) {
                    // 5.
                    mergeBranchFile(fileName, branchHeadFileHash);
                    continue;
                }
                if (!branchHeadFileHash.equals(currentHeadFileHash)) {
                    // 8.
                    mergeConflict(fileName, currentHeadFileHash, branchHeadFileHash);
                    mergeConflict = true;
                }
                continue;
            }
            if (currentHeadFileHash == null) {
                if (!branchHeadFileHash.equals(spiltPointFileHash)) {
                    mergeConflict(fileName, null, branchHeadFileHash);
                    mergeConflict = true;
                }
                // 7.
                continue;
            }
            if (currentHeadFileHash.equals(branchHeadFileHash)) {
                // 3.
                continue;
            }
            if (spiltPointFileHash.equals(currentHeadFileHash)) {
                // 1.
                mergeBranchFile(fileName, branchHeadFileHash);
                continue;
            }
            // 8.
            mergeConflict(fileName, currentHeadFileHash, branchHeadFileHash);
            mergeConflict = true;
        }
        // 2.
        // 4.
        for (String fileName : spiltPointFiles.keySet()) {
            // 6.
            String spiltPointFileHash = spiltPointFiles.get(fileName);
            String currentHeadFileHash = currentHeadFiles.get(fileName);
            if (spiltPointFileHash.equals(currentHeadFileHash)) {
                stageForRemoval(fileName);
                continue;
            }
            if (currentHeadFiles.get(fileName) != null) {
                mergeConflict(fileName, currentHeadFileHash, null);
                mergeConflict = true;
            }
        }
        StringBuilder sb = new StringBuilder("Merged ");
        sb.append(branchName).append(" into ").append(currentBranch).append(".");
        commit(sb.toString(), branchName);
        if (mergeConflict) {
            System.out.println("Encountered a merge conflict.");
        }
    }

    private void mergeBranchFile(String fileName, String branchFileHash) {
        File branchFile = Utils.join(Repository.BLOB_DIR, branchFileHash);
        Repository.createNewFile(Repository.CWD, fileName, branchFile);
        stage(fileName, branchFile, branchFileHash);
    }

    private void mergeConflict(String fileName, String currentFileHash, String branchFileHash) {
        String currentFileContent = currentFileHash != null ? readContent(currentFileHash) : "";
        String branchFileContent = branchFileHash != null ? readContent(branchFileHash) : "";
        StringBuilder sb = new StringBuilder("<<<<<<< HEAD\n");
        sb.append(currentFileContent).append("=======\n");
        sb.append(branchFileContent).append(">>>>>>>\n");
        File file = Utils.join(Repository.CWD, fileName);
        Utils.writeContents(file, sb.toString());
        stage(fileName, file, Utils.sha1(fileName, Utils.readContents(file)));
    }

    private String readContent(String fileHash) {
        File file = Utils.join(Repository.BLOB_DIR, fileHash);
        return Utils.readContentsAsString(file);
    }

    public Commit findSpiltPoint(Commit target) {
        Commit currentHead = headCommit();
        Set<String> history = currentHead.history();
        Deque<Commit> traverse = new LinkedList<>();
        Commit spiltPoint = target;
        traverse.addLast(spiltPoint);
        while (!traverse.isEmpty()) {
            spiltPoint = traverse.removeFirst();
            if (spiltPoint == null) {
                continue;
            }
            if (history.contains(spiltPoint.hash())) {
                break;
            }
            traverse.addLast(spiltPoint.getParent());
            traverse.addLast(spiltPoint.getAnotherParent());
        }
        return spiltPoint;
    }

    public boolean untrackedFileBeImpactedByCheckout(Commit commit) {
        List<String> untrackedFiles = untrackedFiles();
        if (untrackedFiles.isEmpty()) {
            return false;
        }
        Commit spiltPoint = findSpiltPoint(commit);
        for (String fileName : untrackedFiles) {
            if (commit.isTracking(fileName) && spiltPoint.isTracking(fileName)
                && !commit.trackingFile(fileName).equals(spiltPoint.trackingFile(fileName))) {
                return true;
            }
        }
        return false;
    }

    public Commit getBranchHead(String branchName) {
        return Repository.findCommit(branches.get(branchName));
    }

    public boolean untrackedFileBeImpactedByReset(Commit commit) {
        List<String> untrackedFiles = untrackedFiles();
        if (untrackedFiles.isEmpty()) {
            return false;
        }
        for (String fileName : untrackedFiles) {
            File file = Utils.join(Repository.CWD, fileName);
            String fileHash = Utils.sha1(fileName, Utils.readContents(file));
            if (!commit.isTracking(fileName)) {
                continue;
            }
            if (!commit.isTracking(fileName, fileHash)) {
                return true;
            }
        }
        return false;
    }
}
