package ovh.paulem.buildscript;

import org.jetbrains.annotations.Nullable;
import org.kohsuke.github.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class NewGithubChangelog {
    private static final String NO_CHANGELOG = "No changelog was specified.";

    public static String getChangelog(Path projectPath, @Nullable String githubToken) {
        if (githubToken == null || githubToken.isEmpty()) {
            System.err.println("GitHub token is not provided or is empty. Skipping changelog generation.");
            return NO_CHANGELOG;
        }

        try {
            Path changelogPath = projectPath.resolve("changelog.txt");
            File changelogFile = changelogPath.toFile();

            String newChangelog = NO_CHANGELOG;

            // Si le fichier changelog existe et qu'il a plus de 10 secondes, le supprimer
            if (changelogFile.exists()) {
                long lastModified = changelogFile.lastModified();
                long now = System.currentTimeMillis();
                if (now - lastModified > 10_000) {
                    System.out.println("Changelog file is older than 10 seconds, deleting it.");
                    Files.delete(changelogPath);
                } else {
                    System.out.println("Changelog file is recent, reading existing content.");
                    newChangelog = Files.readString(changelogPath);
                }
            }

            if(!changelogFile.exists()) {
                // If the changelog file does not exist, create it
                System.out.println("Changelog file does not exist, generating new changelog.");
                newChangelog = doChangelog(githubToken);
            }

            // If the changelog file already exists and the new changelog isn't empty
            if (changelogFile.exists() && !newChangelog.equalsIgnoreCase(NO_CHANGELOG)) {
                newChangelog = Files.readString(changelogPath);
            }

            if (!newChangelog.equalsIgnoreCase(NO_CHANGELOG)) {
                Files.writeString(changelogPath, newChangelog);
            }

            return newChangelog;
        } catch (Exception e) {
            return NO_CHANGELOG;
        }
    }

    private static String doChangelog(String githubToken) {
        try {
            System.out.println("Fetching changelog...");

            GitHub gitHub = new GitHubBuilder()
                    .withRateLimitHandler(GitHubRateLimitHandler.FAIL)
                    .withOAuthToken(githubToken)
                    .build();

            GHRepository repository = gitHub.getRepository("Paulem79/SimpleOres-fabric");

            StringBuilder changelog = new StringBuilder("This version is uploaded automatically by GitHub Actions.")
                    .append("\n\nChangelog:");

            List<GHCommit> commits = repository
                    .queryCommits()
                    .from("refs/heads/stonecutter")
                    .list()
                    .toList();

            @Nullable GHCommit lastPublish = null;

            for (GHCommit commit : commits) {
                String message = commit.getCommitShortInfo().getMessage();
                String hash = commit.getSHA1();
                String commit_url = commit.getHtmlUrl().toString();

                if (isPublishWorkflowSuccess(repository, commit) && commits.indexOf(commit) != 0) {
                    lastPublish = commit;
                    break;
                }

                changelog
                        .append("\n")
                        .append("- [")
                        .append(hash, 0, 7)
                        .append("](")
                        .append(commit_url)
                        .append(") ")
                        .append(message);
            }

            if(lastPublish != null) {
                changelog
                        .append("\n\n")
                        .append("Diff: ")
                        .append(repository.getCompare(lastPublish, commits.getFirst()).getHtmlUrl().toString());
            }

            System.out.println("Done.");

            return changelog.toString();
        } catch (IOException e) {
            System.err.println("Failed to fetch changelog: " + e.getMessage());
            e.printStackTrace();
            return NO_CHANGELOG;
        }
    }

    private static boolean isPublishWorkflowSuccess(GHRepository repository, GHCommit commit) throws IOException {
        List<GHWorkflowRun> runs = repository.queryWorkflowRuns()
                .headSha(commit.getSHA1())
                .list()
                .toList();

        if (runs.isEmpty()) {
            return false;
        }

        return runs.stream().anyMatch(run -> run.getName().toLowerCase().contains("publish") && run.getConclusion() == GHWorkflowRun.Conclusion.SUCCESS);
    }
}