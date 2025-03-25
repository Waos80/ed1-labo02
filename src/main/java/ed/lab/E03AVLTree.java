package ed.lab;

import java.util.Comparator;

public class E03AVLTree<T> {
    public TreeNode<T> root;
    private final Comparator<T> comparator;


    private int height(TreeNode<T> node, int h) {
        if (node == null) {
            return h;
        }

        return Math.max(height(node.left, h + 1), height(node.right, h + 1));
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
            node = new TreeNode<T>(value);
            return;
        }

        if (comparator.compare(value, node.value) > 0){
            treeInsert(node.right, value);
        } else {
            treeInsert(node.left, value);
        }
    }

    private void size(TreeNode<T> node, int sum) {
        if (node != null) {
            sum++;
            size(node.left, sum);
            size(node.right, sum);
        }
    }

    private TreeNode<T> findSuccessor(TreeNode<T> right) {
        if (right.left == null) {
            return right;
        }

        return findSuccessor(right.left);
    }

    public E03AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
    }

    public void insert(T value) {
        if (root == null) {
            root = new TreeNode<T>(value);
        } else {
            treeInsert(root, value);
        }
    }

    public void delete(T value) {
        TreeNode<T> node = treeSearch(root, value);
        if (node == null) {
            return;
        }

        if (node.left == null && node.right == null) {
            node = null;
        }
        else if (node.left != null && node.right == null) {
            node = node.left;
        }
        else {
            TreeNode<T> tmp = findSuccessor(node.right);
            TreeNode<T> successor = tmp;

            tmp = successor.right;
            successor.right = node.right;
            node = successor;
        }
    }

    public T search(T value) {
        return treeSearch(root, value).value;
    }

    public int height() {
        return height(root, -1);
    }

    public int size() {
        int sum = 0;
        size(root, sum);
        return sum;
    }
}
