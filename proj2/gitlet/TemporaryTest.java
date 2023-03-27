package gitlet;


import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class TemporaryTest {
    public static void main(String[] args) {
        String dir = "C:\\JavaWorkplace\\CS61B_sp21\\proj2\\testing\\test34-merge-conflicts_3\\.gitlet\\commit";
        List<String> list= Utils.plainFilenamesIn(dir);
        List<Commit> commitList = new LinkedList<>();
        for (String commitHash : list) {
            File file = Utils.join(dir, commitHash);
            commitList.add(Utils.readObject(file, Commit.class));
        }
        for (Commit commit : commitList) {
            System.out.println(commit.toString());
            System.out.println(commit.trackingFile("k.txt"));
        }
    }
}
