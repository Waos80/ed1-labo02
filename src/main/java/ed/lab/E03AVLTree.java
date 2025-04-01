package ed.lab;

import java.util.Comparator;

public class E03AVLTree<T> {
    public TreeNode<T> root;
    private final Comparator<T> comparator;
    private int size;

    private int height(TreeNode<T> node, int h) {
        if (node == null) {
            return h;
        }

        return Math.max(height(node.left, h + 1), height(node.right, h + 1));
    }

    private int balance(TreeNode<T> root) {
        return height(root.right, 0) - height(root.left, 0);
    }

    private TreeNode<T> treeSearch(TreeNode<T> node, T val) {
        if (node == null || comparator.compare(val, node.value) == 0) {
            return node;
        }

        if (comparator.compare(val, node.value) > 0) {
            return treeSearch(node.right, val);
        } else {
            return treeSearch(node.left, val);
        }
    }

    private void treeInsert(TreeNode<T> node, T value) {
        if (node == null) {
            node = new TreeNode<>(value);
            return;
        }

        if (comparator.compare(value, node.value) > 0){
            treeInsert(node.right, value);
        } else {
            treeInsert(node.left, value);
        }

        balanceTree(node);
    }

    private TreeNode<T> findSuccessor(TreeNode<T> right) {
        if (right.left == null) {
            return right;
        }

        return findSuccessor(right.left);
    }

    private void rotateLeft(TreeNode<T> root) {
        TreeNode<T> node = root.right;
        root.right = node.left;
        node.left = root;
        root = node;
    }

    private void rotateRight(TreeNode<T> root) {
        TreeNode<T> node = root.left;
        root.left = node.right;
        node.right = root;
        root = node;
    }

    private void balanceTree(TreeNode<T> root) {
        int balance = balance(root);

        if (balance > 1) {
            rotateLeft(root);
            if (balance(root.right) > 1) {
                rotateLeft(root);
            }

            if (balance(root.right) < -1) {
                rotateRight(root);
            }
        }

        if (balance < -1) {
            rotateRight(root);
            if (balance(root.right) > 1) {
                rotateLeft(root);
            }

            if (balance(root.right) < -1) {
                rotateRight(root);
            }
        }
    }

    public E03AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
        this.size = 0;
    }

    public void insert(T value) {
        if (root == null) {
            root = new TreeNode<>(value);
        } else {
            treeInsert(root, value);
        }

        size++;
    }

    public void delete(T value) {
        TreeNode<T> node = treeSearch(root, value);
        if (node == null) {
            return;
        }

        if (node.right != null) {
            TreeNode<T> successor = findSuccessor(node.right);
            node.right.left = successor.right;
            node = successor;
        } else if (node.left != null) {
            node = node.left;
        }

        size--;
        balanceTree(node);
    }

    public T search(T value) {
        TreeNode<T> found = treeSearch(root, value);
        return found != null ? found.value : null;
    }

    public int height() {
        return height(root, 0);
    }

    public int size() {
        return size;
    }
}
