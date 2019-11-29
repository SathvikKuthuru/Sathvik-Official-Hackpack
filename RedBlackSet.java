public class RBTest {

    public static void main(String[] args) {
        RedBlackSet<Integer> set = new RedBlackSet<>();
        set.add(5);
        set.add(7);
        set.add(2);
        set.add(21);
        set.add(9234);
        set.add(919);
        set.add(1231);
        set.add(1);
        set.add(-1);
        set.add(123);
        set.print();
    }

    static class RedBlackSet<T extends Comparable<T>> {
        static final boolean BLACK = false;
        static final boolean RED = true;
        Node root;

        public RedBlackSet() {
            root = null;
        }

        public void print() {
            printHelper(root);
        }

        public void printHelper(Node curr) {
            if(curr.left != null) printHelper(curr.left);
            System.out.println(curr);
            if(curr.right != null) printHelper(curr.right);
        }

        public void add(T val) {
            Node toInsert = new Node(null, val);
            root = insertionHelper(root, toInsert);
            balance(toInsert);
        }

        public void recolor(Node parent, Node uncle, Node grandparent) {
            parent.color = BLACK;
            uncle.color = BLACK;
            grandparent.color = RED;
        }

        public void rotate(Node curr, Node parent, Node grandparent) {
            if(parent == grandparent.left) {
                if(curr == parent.left) rotateLL(parent, grandparent);
                else rotateLR(curr, parent, grandparent);
            }
            else {
                if(curr == parent.right) rotateRR(parent, grandparent);
                else rotateRL(curr, parent, grandparent);
            }
        }

        public void rotateLL(Node parent, Node grandparent) {
            grandparent.left = parent.right;
            parent.parent = grandparent.parent;
            parent.right = grandparent;
            grandparent.parent = parent;
            if(parent.parent != null) {
                if(parent.parent.left == grandparent) parent.parent.left = parent;
                else parent.parent.right = parent;
            }
            boolean tempColor = grandparent.color;
            grandparent.color = parent.color;
            parent.color = tempColor;
        }
        public void rotateLR(Node curr, Node parent, Node grandparent) {
            curr.parent = grandparent;
            grandparent.left = curr;
            parent.right = curr.left;
            parent.parent = curr;
            curr.left = parent;
            rotateLL(curr, grandparent);
        }

        public void rotateRR(Node parent, Node grandparent) {
            grandparent.right = parent.left;
            parent.parent = grandparent.parent;
            parent.left = grandparent;
            grandparent.parent = parent;
            if(parent.parent != null) {
                if(parent.parent.left == grandparent) parent.parent.left = parent;
                else parent.parent.right = parent;
            }
            boolean tempColor = grandparent.color;
            grandparent.color = parent.color;
            parent.color = tempColor;
        }

        public void rotateRL(Node curr, Node parent, Node grandparent) {
            curr.parent = grandparent;
            grandparent.right = curr;
            parent.left = curr.right;
            parent.parent = curr;
            curr.right = parent;
            rotateRR(curr, grandparent);
        }


        public void balance(Node curr) {
            if(curr == root) curr.color = RedBlackSet.BLACK;
            else {
                Node parent = curr.parent;
                if(parent.color == BLACK) return;
                Node grandparent = parent.parent;
                Node uncle = null;
                if(grandparent != null) {
                    uncle = grandparent.left == parent ? grandparent.right : grandparent.left;
                }
                if(uncle != null && uncle.color == RED) {
                    recolor(parent, uncle, grandparent);
                    balance(grandparent);
                }
                else {
                    rotate(curr, parent, grandparent);
                }
            }
        }

        public Node insertionHelper(Node curr, Node toInsert) {
            if(curr == null) return toInsert;
            else {
                if(toInsert.data.compareTo(curr.data) == -1) {
                    curr.left = insertionHelper(curr.left, toInsert);
                    curr.left.parent = curr;
                }
                else {
                    curr.right = insertionHelper(curr.right, toInsert);
                    curr.right.parent = curr;
                }
            }
            return curr;
        }

        class Node {
            boolean color;
            Node left, right, parent;
            T data;

            public Node(Node parent, T input) {
                this.parent = parent;
                this.left = null;
                this.right = null;
                this.color = RedBlackSet.RED;
                this.data = input;
            }

            @Override
            public String toString() {
                return data.toString();
            }
        }
    }
}
