# Gitlet Design Document

**Name**: Ming

## Classes and Data Structures

### Repository

#### Fields

1. File CWD
2. File GITLET_DIR
3. File COMMIT_DIR
4. File BLOB_DIR
5. File STAGE_DIR
6. Branches BRANCHES

#### Methods

1. public static void init()
<br> create the .gitlet directory to current working directory
2. public static boolean exist()
<br> return if CWD is initialized as a Gitlet directory 
3. public static getBranches()
<br> return the Branches Object if exist in .gitlet directory


### Commit

#### Fields

1. String message
2. Date timestamp
3. String parent(sha1)
4. String parent if Merged(sha1)
5. Map<String, String> blobs
<br> String file name, String blob(sha1)

#### Methods

1. three types of constructors
<br>a. initial commit
<br>b. normal commit
<br>c. merge commit
2. public static findCommit(String hashcode)
<br>return the Commit store in .gitlet by given hashcode
3. public void persist()
<br>save the commit in .gitlet

### Branches

#### Fields

1. String head
<br> head pointer to current commit in sha1 string
2. Map<String, String> branches
<br>branch name and the pointer to the latest commit
3. File BRANCH_FILE

#### Methods

1. public static Branches getInstance()
<br> return the Branches Object in .gitlet if exists
2. public void createNewBranch(String branchName, String pointer)
<br> create new branch and store in BRANCH_FILE

## Algorithms

## Persistence
GITLET_DIR
<br>L BRANCH_FILE
<br>L BLOB_DIR
<br>&nbsp;&nbsp;&nbsp;L BLOB_FILES
<br>L COMMIT_DIR
<br>&nbsp;&nbsp;&nbsp;L COMMIT_FILES
<br>L STAGE_DIR
<br>&nbsp;&nbsp;&nbsp;L STAGE_FILES
<br>&nbsp;&nbsp;&nbsp;L RM_DIR
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;L REMOVAL_FILES