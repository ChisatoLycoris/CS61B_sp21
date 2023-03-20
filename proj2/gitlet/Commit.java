package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  Represents a gitlet commit object.
 *  Contains a log message, timestamp,
 *  a mapping of file names to blob references,
 *  a parent reference, and (for merge) a second parent reference.
 *
 *  @author Ming
 */
public class Commit implements Serializable {
    /**
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private final String message;
    /** The timestamp of this Commit. */
    private final Date timestamp;
    /** The parent of this Commit in sha-1 string. */
    private final String parent;
    /** Another parent of this Commit in sha-1 string if this is a merge Commit. */
    private final String anotherParentIfMerge;
    /** The mapping of file names to blob in sha-1 string. */
    private Map<String, String> blobs;

    /* TODO: fill in the rest of this class. */

    /** Constructor for initialization. */
    public Commit() {
        message = "initial commit";
        parent = null;
        timestamp = new Date(0);
        anotherParentIfMerge = null;
        blobs = new HashMap<>();
    }

    /** Constructor for normal commit. */
    public Commit (String message, String parent) {
        this.message = message;
        this.parent = parent;
        this.anotherParentIfMerge = null;
        timestamp = new Date();
    }

    /** Constructor for merge commit.*/
    public Commit (String message, String parent, String anotherParentIfMerge) {
        this.message = message;
        this.parent = parent;
        this.anotherParentIfMerge = anotherParentIfMerge;
        timestamp = new Date();
    }

    public void persist() {
        File result = Utils.join(Repository.COMMIT_DIR, Utils.sha1(this));
        Utils.writeObject(result, this);
    }

    public static Commit findCommit(String sha1) {
        File result = Utils.join(Repository.COMMIT_DIR, sha1);
        if (!result.exists()) {
            throw new GitletException("no commit with given sha1 string exists. ");
        }
        return Utils.readObject(result, Commit.class);
    }

    public boolean isTracking(String fileName) {
        return blobs.containsKey(fileName);
    }

    public boolean isTracking(String fileName, String fileHash) {
        return fileHash.equals(blobs.get(fileName));
    }

    public String trackingFile(String fileName) {
        return blobs.get(fileName);
    }

    public Commit getParent() {
        return Utils.readObject(Utils.join(Repository.COMMIT_DIR, parent), Commit.class);
    }

    public Commit getAnotherParent() {
        if (anotherParentIfMerge != null) {
            return Utils.readObject(Utils.join(Repository.COMMIT_DIR, anotherParentIfMerge), Commit.class);
        }
        return null;
    }

    public boolean isMerged() {
        return anotherParentIfMerge != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("===");
        sb.append("\ncommit ");
        sb.append(Utils.sha1(this));
        sb.append("\nDate: ").append(timestamp).append("\n");
        sb.append(message);
        return sb.toString();
    }
}
