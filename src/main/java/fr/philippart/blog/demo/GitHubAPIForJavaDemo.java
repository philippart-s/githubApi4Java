package fr.philippart.blog.demo;

import java.io.IOException;

import org.kohsuke.github.GitHub;

/**
 * Démo pour GitHubAPI for Java : https://github-api.kohsuke.org/
 */
public final class GitHubAPIForJavaDemo {
    private GitHubAPIForJavaDemo() {
    }

    /**
     * Exemples d'accès à GitHub.
     *
     * @param args The arguments of the program.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int nbRepositories = GitHub.connectAnonymously()
            .getUser("philippart-s")
            .getPublicRepoCount();

        System.out.println(String.format("nommbre de repositories de philippart-s : %s", nbRepositories));
    }
}
