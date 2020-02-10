import java.util.ArrayList;

public class SplayTest {
    public static void main(String[] args) {
        SplayTreeSet<Integer> set = new SplayTreeSet<>();
        set.add(3);
        set.add(1);
        set.add(8);
        System.out.println(set);
    }

    static class SplayTreeSet<T extends Comparable<T>> {
        Node<T> base;
        int size;

        public SplayTreeSet() {
            base = null;
            size = 0;
        }

        @Override
        public String toString() {
            ArrayList<T> curr = new ArrayList<>();
            traverse(base, curr);
            return curr.toString();
        }

        public void traverse(Node root, ArrayList<T> curr) {
            if(root != null) {
                traverse(root.left, curr);
                curr.add((T) root.key);
                traverse(root.right, curr);
            }
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public T first() {
            return first(base);
        }

        public T first(Node root) {
            if(root == null) return null;
            if(root.left == null) return (T) (root.key);
            return first(root.left);
        }

        public T pollFirst() {
            T f = first();
            remove(f);
            return f;
        }

        public T last() {
            return last(base);
        }

        public T last(Node root) {
            if(root == null) return null;
            if(root.right == null) return (T) (root.key);
            return first(root.right);
        }

        public T pollLast() {
            T l = last();
            remove(l);
            return l;
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

        public boolean contains(T key) {
            base = splay(base, key);
            return base != null && base.key.equals(key);
        }

        public Node splay(Node root, T key) {
            if(root == null || root.key.equals(key)) return root;
            if(root.key.compareTo(key) > 0) {
                if(root.left == null) return root;
                if(root.left.key.compareTo(key) > 0) {
                    root.left.left = splay(root.left.left, key);
                    root = rotateRight(root);
                }
                else if(root.left.key.compareTo(key) < 0) {
                    root.left.right = splay(root.left.right, key);
                    if(root.left.right != null) root.left = rotateLeft(root.left);
                }
                return root.left == null ? root : rotateRight(root);
            }
            else {
                if(root.right == null) return root;
                if(root.right.key.compareTo(key) > 0) {
                    root.right.left = splay(root.right.left, key);
                    if(root.right.left != null) root.right = rotateRight(root.right);
                }
                else if(root.right.key.compareTo(key) < 0) {
                    root.right.right = splay(root.right.right, key);
                    root = rotateLeft(root);
                }
                return root.right == null ? root : rotateLeft(root);
            }
        }

        public Node add(T key) {
            return base = add(base, key);
        }

        public Node add(Node root, T key) {
            if(root == null)
            {
                size++;
                return new Node(key);
            }
            root = splay(root, key);
            if(root.key.equals(key)) return root;
            size++;
            Node newNode = new Node(key);
            if(root.key.compareTo(key) > 0) {
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

        public Node remove(T key) {
            return base = remove(base, key);
        }

        public Node remove(Node root, T key) {
            if(root == null) return null;
            root = splay(root, key);
            if(root.key.compareTo(key) != 0) return root;
            size--;
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

    static class Node<T extends Comparable<T>> {
        T key;
        Node left, right;

        public Node(T a) {
            key = a;
            left = null;
            right = null;
        }
    }
}
