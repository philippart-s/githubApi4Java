package fr.philippart.blog.demo;

import java.io.IOException;

import org.kohsuke.github.GHRef;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTreeBuilder;
import org.kohsuke.github.GitHub;

/**
 * Démo pour GitHubAPI for Java : https://github-api.kohsuke.org/
 */
public final class GitHubAPIForJavaDemo {
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

        GitHub.connect("philippart-s", args[0]) //arg[0] est le token associé au compte
                .getUser("philippart-s")
                .getRepository("githubApi4Java")
                .createIssue("Issue créée avec GitHub API for Java !!!")
                .body("Cette issue est vraiment cool ! :wink:")
                .assignee("philippart-s")
                .create();

        GitHub.connect("philippart-s", args[0]) //arg[0] est le token associé au compte
                .getUser("philippart-s")
                .getRepository("githubApi4Java")
                .createContent()
                .content("Hello workd !!!")
                .path("HelloWorld.md")
                .message(":tada: My first commit !")
                .commit();

        GHRepository repo = GitHub.connect("philippart-s", args[0]) //arg[0] est le token associé au compte
                                    .getUser("philippart-s")
                                    .getRepository("githubApi4Java");

        // Référence vers la branche main
        GHRef mainRef = repo.getRef("heads/main");

        // SHA1 du dernier commit sur la main
        String mainTreeSha = repo
                .getTreeRecursive("main", 1)
                .getSha();

        // Création de l'arbre à commiter
        GHTreeBuilder ghTreeBuilder = repo.createTree()
                                            .baseTree(mainTreeSha);
        ghTreeBuilder.add("HelloWorld1.md", "Hello world 1 !!!", false);
        ghTreeBuilder.add("HelloWorld2.md", "Hello world 2 !!!", false);

        // Récupération du SHA1
        String treeSha = ghTreeBuilder.create().getSha();

        // Création du commit pour le tree
        String commitSha = repo.createCommit()
                                .message(":tada: My global commit !")
                                .tree(treeSha)
                                .parent(mainRef.getObject().getSha())
                                .create()
                                .getSHA1();

        // Positionnement de la branche main sur le dernier commit
        mainRef.updateTo(commitSha);
    }
}
