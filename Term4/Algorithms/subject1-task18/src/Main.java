import java.io.*;
import java.util.Scanner;

public class Main {

    public static class BinaryTree {

        public class Node {
            long key;
            Node left;
            Node right;

            long h;
            long l;
            long a;
            long b;
            long MSL;


            public Node(long key) {
                this.key = key;
            }

            @Override
            public String toString() {
                return "Node{" +
                        "key=" + key +
                        ", h=" + h +
                        ", l=" + l +
                        ", b=" + b +
                        ", a=" + a +
                        ", MSL=" + MSL +
                        '}';
            }

            public long getH() {
                return h;
            }

            public void setH(long h) {
                this.h = h;
            }

            public long getL() {
                return l;
            }

            public void setL(long l) {
                this.l = l;
            }
        }

        Node root;
        PrintWriter writer = null;

        long maxSemipathLength = 0;
        long deletedNode;
        Boolean f = true;

        public BinaryTree() {
            root = null;
        }

        public void add(long value) {
            if (root == null) {
                root = new Node(value);
            } else {
                addNode(root, value);
            }
        }

        private void addNode(Node node, long value) {
            if (node.key < value) {
                if (node.right == null) {
                    node.right = new Node(value);
                } else addNode(node.right, value);
            } else if (node.key > value) {
                if (node.left == null) {
                    node.left = new Node(value);
                } else addNode(node.left, value);
            }

        }

        public void delete(long value) {
            Node parent = null;
            Node curr = root;

            while (curr != null) {
                if (curr.key < value) {
                    parent = curr;
                    curr = curr.right;
                } else if (curr.key > value) {
                    parent = curr;
                    curr = curr.left;
                } else break;
            }

            if (curr == null) return;

            if (curr.left == null && curr.right == null) {
                changeNode(parent, curr, null);
            } else if (curr.left == null) {
                changeNode(parent, curr, curr.right);
            } else if (curr.right == null) {
                changeNode(parent, curr, curr.left);
            } else {
                Node leftmostParent = curr;
                Node leftmost = curr.right;

                while (leftmost.left != null) {
                    leftmostParent = leftmost;
                    leftmost = leftmost.left;
                }

                curr.key = leftmost.key;
                changeNode(leftmostParent, leftmost, leftmost.right);
            }
        }

        private void changeNode(Node parent, Node oldChild, Node newChild) {
            if (parent == null) root = newChild;
            else if (parent.left == oldChild) parent.left = newChild;
            else if (parent.right == oldChild) parent.right = newChild;
        }

        public void preOrederTraversal() {
            try {
                writer = new PrintWriter(new FileWriter("tst.out"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            preOrderTraversal(root);
            writer.close();
        }

        private void preOrderTraversal(Node node) {
            if (node != null) {
                writer.println(node.key);
                preOrderTraversal(node.left);
                preOrderTraversal(node.right);

            }
        }

        ///////////////////////////////////////////////////////////////////////////

        public void findAnswer() {
            firstTraverse(root);
            secondTraverse(root);
            thirdTraverse(root);
            findDeletedNode(root);
            if (f == false) delete(deletedNode);
            preOrederTraversal();

        }

        private void findDeletedNode(Node node) {
            if (node != null) {
                findDeletedNode(node.left);
                if ((node.a + node.b) % 2 == 0 && f == true && (node.a + node.b != 0)) {
                    deletedNode = node.key;
                    f = false;
                }
                findDeletedNode(node.right);
            }
        }

        private void secondTraverse(Node node) {
            if (node != null) {
                secondTraverse(node.left);
                secondTraverse(node.right);
                findB(node);
            }
        }

        private void findB(Node node) {
            if (node.MSL != maxSemipathLength) node.b = 0;
            else {
                if (node.left == null && node.right == null) node.b = 0;
                else if (node.left == null) node.b = node.right.l;
                else if (node.right == null) node.b = node.left.l;
                else node.b = node.right.l * node.left.l;
            }
        }

        private void thirdTraverse(Node node) {
            if (node != null) {
                findA(node);
                thirdTraverse(node.left);
                thirdTraverse(node.right);
            }
        }

        private void findA(Node node) {
            if (node == root) node.a = 0;

            if (node.left == null && node.right == null) return;
            else if (node.left == null) node.right.a = node.a + node.b;
            else if (node.right == null) node.left.a = node.a + node.b;
            else {
                if (node.left.h > node.right.h) {
                    node.left.a = node.a + node.b;
                    node.right.a = node.b;
                } else if (node.left.h < node.right.h) {
                    node.right.a = node.a + node.b;
                    node.left.a = node.b;
                } else if (node.left.h == node.right.h) {
                    node.right.a = node.b + node.right.l * (node.a / node.l);
                    node.left.a = node.b + node.left.l * (node.a / node.l);
                }
            }
        }

        private void firstTraverse(Node node) {
            if (node != null) {
                firstTraverse(node.left);
                firstTraverse(node.right);
                node.setH(getHight(node));
                node.setL(getLeaves(node));
                node.MSL = getMSL(node);
                if (maxSemipathLength < node.MSL) maxSemipathLength = node.MSL;
            }
        }

        private long getHight(Node node) {
            if (node.left == null && node.right == null) return 0;
            else if (node.left == null) return node.right.h + 1;
            else if (node.right == null) return node.left.h + 1;
            else return Math.max(node.left.h, node.right.h) + 1;
        }

        private long getMSL(Node node) {
            if (node.left == null && node.right == null) return 0;
            else if (node.left == null) return node.right.h + 1;
            else if (node.right == null) return node.left.h + 1;
            else return node.right.h + node.left.h + 2;
        }

        private long getLeaves(Node node) {
            if (node.left == null && node.right == null) return 1;
            else if (node.left == null) return (node.right).getL();
            else if (node.right == null) return (node.left).getL();
            else {
                if ((node.right).getH() == (node.left).getH()) return (node.right).getL() + (node.left).getL();
                else if ((node.right).h > (node.left).h) return (node.right).l;
                else return (node.left).l;
            }
        }
    }

    public static void main(String[] args) {
        BinaryTree bst = new BinaryTree();

        try {
            Scanner sc = new Scanner(new File("tst.in"));

            while (sc.hasNext()) {
                bst.add(sc.nextLong());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        bst.findAnswer();
    }
}