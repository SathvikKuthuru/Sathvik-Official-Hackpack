import java.util.ArrayList;

public class SplayTest {
    public static void main(String[] args) {
        SplayTree set = new SplayTree();
        System.out.print(set);
    }

    static class SplayTree {
        Node base;

        public SplayTree() {
            base = null;
        }

        @Override
        public String toString() {
            ArrayList<Integer> curr = new ArrayList<>();
            traverse(base, curr);
            return curr.toString();
        }

        public void traverse(Node root, ArrayList<Integer> curr) {
            if(root != null) {
                traverse(root.left, curr);
                curr.add(root.key);
                traverse(root.right, curr);
            }
        }

        public Node rotateRight(Node curr) {
            Node y = curr.left;
            curr.left = y.right;
            y.right = curr;
            return y;
        }

        public Node rotateLeft(Node curr) {
            Node y = curr.right;
            curr.right = y.left;
            y.left = curr;
            return y;
        }

        public Node search(int key) {
            return base = splay(base, key);
        }

        public Node splay(Node root, int key) {
            if(root == null || root.key == key) return root;
            if(root.key > key) {
                if(root.left == null) return root;
                if(root.left.key > key) {
                    root.left.left = splay(root.left.left, key);
                    root = rotateRight(root);
                }
                else if(root.left.key < key) {
                    root.left.right = splay(root.left.right, key);
                    if(root.left.right != null) root.left = rotateLeft(root.left);
                }
                return root.left == null ? root : rotateRight(root);
            }
            else {
                if(root.right == null) return root;
                if(root.right.key > key) {
                    root.right.left = splay(root.right.left, key);
                    if(root.right.left != null) root.right = rotateRight(root.right);
                }
                else if(root.right.key < key) {
                    root.right.right = splay(root.right.right, key);
                    rotateLeft(root);
                }
                return root.right == null ? root : rotateLeft(root);
            }
        }

        public Node insert(int key) {
            return base = insert(base, key);
        }

        public Node insert(Node root, int key) {
            if(root == null) return new Node(key);
            root = splay(root, key);
            if(root.key == key) return root;
            Node newNode = new Node(key);
            if(root.key > key) {
                newNode.right = root;
                newNode.left = root.left;
                root.left = null;
            }
            else {
                newNode.left = root;
                newNode.right = root.right;
                root.right = null;
            }
            return newNode;
        }

        public Node delete(int key) {
            return base = delete(base, key);
        }

        public Node delete(Node root, int key) {
            if(root == null) return null;
            root = splay(root, key);
            if(root.key != key) return root;
            if(root.left == null) {
                root = root.right;
            }
            else {
                Node temp = root.right;
                root = splay(root.left, key);
                root.right = temp;
            }
            return root;
        }
    }

    static class Node {
        int key;
        Node left, right;

        public Node(int a) {
            key = a;
            left = null;
            right = null;
        }
    }
}
