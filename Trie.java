import java.util.*;

public class TrieTest {
	
	public static void main(String[] args) {
		Trie root = new Trie();
		root.insertWord("oof");
		root.insertWord("cool");
		System.out.println(root.hasWord("cool"));
		System.out.println(root.hasWord("oofs"));
	}
	
	static class Trie {
		Trie[] children;
		int wordCount;
		
		public Trie() {
			wordCount = 0;
			children = new Trie[26];
		}
		
		void insertWord(String in) {
			Trie curr = this;
			for(int i = 0; i < in.length(); i++) {
				int at = in.charAt(i) - 'a';
				if(children[at] == null) children[at] = new Trie();
				curr = children[at];
			}
			curr.wordCount++;
		}
		
		boolean hasWord(String in) {
			Trie curr = this;
			for(int i = 0; i < in.length(); i++) {
				int at = in.charAt(i) - 'a';
				if(children[at] == null) children[at] = new Trie();
				curr = children[at];
			}
			return curr.wordCount > 0;
		}
		
	}

}
