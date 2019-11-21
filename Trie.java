import java.util.*;

public class TrieTest {
	
	public static void main(String[] args) {
		Trie root = new Trie();
		root.insertWord("oof");
		root.insertWord("cool");
		System.out.println(root.hasWord("cool"));
		System.out.println(root.hasWord("oofs"));
		System.out.println(root.hasWord("oof"));
		root.deleteWord("oof");
		System.out.println(root.hasWord("oof"));
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
				if(children[at] == null) return false;
				curr = children[at];
			}
			return curr.wordCount > 0;
		}
		
		void deleteWord(String in) {
			deleteWord(in, -1);
		}
		
		boolean deleteWord(String in, int depth) {
			if(depth == in.length()-1) {
				wordCount--;
				if(wordCount == 0) {
					for(Trie c : children) {
						if(c != null) return false;
					}
					return true;
				}
				return false;
			}
			int next = in.charAt(depth+1) - 'a';
			if(children[next] == null) {

				return false;

			}
			if(children[next].deleteWord(in, depth+1)) {
				children[next] = null;
				return wordCount == 0;
			}
			return false;
		}
		
	}

}
