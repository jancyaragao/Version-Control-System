package git.testes;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;

public class ExPorClasse {
	
	public static void main(String[]args) throws IOException, NoHeadException, GitAPIException {
		
		Git git = Git.open(new File("C:/Users/jancy/git/wicket"));
		Repository repositorio = git.getRepository();
		
		RevWalk rw = new RevWalk(repositorio);
		RevCommit commit = rw.parseCommit(repositorio.resolve("266c90037d689f47bf45722532536716dc9f5b06"));
		RevCommit parent = rw.parseCommit(repositorio.resolve("8b6fcd869ceb96f7b4ea003d3d5665a1626390ad"));
		
		DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
		df.setRepository(repositorio);
		df.setDiffComparator(RawTextComparator.DEFAULT);
		df.setDetectRenames(true);
		
		List<DiffEntry> diffs = df.scan(parent.getTree(), commit.getTree());

		System.out.println("Total: " + diffs.size());
		for (DiffEntry diff : diffs) {
			System.out.println("Tipo da altera��o: " + diff.getChangeType().name());
			System.out.println("Caminho novo     : " + diff.getNewPath());
			System.out.println("Caminho antigo   : " + diff.getOldPath());
			System.out.println("-----------------------");
		}
		
		df.close();
		rw.close();
	}

}