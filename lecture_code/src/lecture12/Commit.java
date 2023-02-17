package lecture12;

import java.io.File;
import java.io.Serializable;

/**
 * Created by hug
 */
public class Commit implements Serializable {
    public String author;
    public String date;
    public String commitMessage;
    public String parentID;

    public Commit(String a, String d, String c, String p) {
        author = a;
        date = d;
        commitMessage = c;
        parentID = p;
    }

    public static void main(String[] args) {
        Commit c = new Commit("Josh Hug", "Feb 17,2021", "a commit message", "66ccdc645c9d156d5c796dbe6ed768430c1562a2");
        File f = new File("commit_example.txt");
//        Utils.writeObject(f,c);
    }
}
