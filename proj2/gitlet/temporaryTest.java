package gitlet;

import java.io.File;

public class temporaryTest {
    public static void main(String[] args) {
        File result = Utils.join(Repository.COMMIT_DIR, "d2ee103fae34c3693e7c712f9063d46a6355bb42");
        System.out.println(Commit.findCommit("d2ee103fae34c3693e7c712f9063d46a6355bb42"));
    }
}
