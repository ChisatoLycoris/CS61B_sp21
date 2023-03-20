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
        String fileHash = Utils.sha1(file);
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
//        File trackingFile = Utils.join(BLOB_DIR, fileHash);
//        File stageFile = Utils.join(STAGE_DIR, fileHash);
//        byte[] contents = Utils.readContents(file);
//        if (trackingFile.exists()) {
//            File rmFile = Utils.join(RM_DIR, fileHash);
//            Utils.restrictedDelete(stageFile);
//            Utils.restrictedDelete(rmFile);
//            // not sure if it is needed to recover the
//            return;
//        }
//        try {
//            stageFile.createNewFile();
//        } catch (IOException e) {
//            throw new GitletException("I/O error occurs while create new stageFile");
//        }
//        Utils.writeContents(stageFile, contents);
    }

    public static void commit() {

    }

    public static void rm() {

    }

}
