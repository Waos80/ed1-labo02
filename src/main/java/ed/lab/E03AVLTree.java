package ed.lab;

import java.util.Comparator;
import java.lang.Math;

public class E03AVLTree<T> {
    private int size;
    private TreeNode<T> root;
    private final Comparator<T> comparator;

    public E03AVLTree(Comparator<T> comparator) {
        this.size = 0;
        this.root = null;
        this.comparator = comparator;
    }

    private TreeNode<T> insertToTree(TreeNode<T> root, T value) {
        if (root == null) {
            size++;
            return new TreeNode<>(value);
        }

        if (comparator.compare(value, root.value) > 0) {
            root.right = insertToTree(root.right, value);
        } else if (comparator.compare(value, root.value) < 0) {
            root.left = insertToTree(root.left, value);
        }

        return root;
    }

    public int balance(TreeNode<T> root) {
        if (root == null) {
            return 0;
        }
        return height(root.right, 0) - height(root.left, 0);
    }

    private TreeNode<T> rotateLeft(TreeNode<T> root) {
        TreeNode<T> pivot = root.right;
        root.right = pivot.left;
        pivot.left = root;
        return pivot;
    }

    private TreeNode<T> rotateRight(TreeNode<T> root) {
        TreeNode<T> pivot = root.left;
        root.left = pivot.right;
        pivot.right = root;
        return pivot;
    }


    private TreeNode<T> balanceTree(TreeNode<T> root) {
        int balance = balance(root);
        if (balance > 1) {
            if (balance(root.right) < 0) {
                root.right = rotateRight(root.right);
            } else if (balance(root.right) > 0) {
                root.right = rotateLeft(root.right);
            }
            root = rotateLeft(root);
        }

        if (balance < -1) {
            if (balance(root.left) > 0) {
                root.left = rotateLeft(root.left);
            } else if (balance(root.left) < 0) {
                root.left = rotateRight(root.left);
            }
            root = rotateRight(root);
        }

        return root;
    }

    public void insert(T value) {
        root = insertToTree(root, value);
        root = balanceTree(root);
    }

    private TreeNode<T> findMinimum(TreeNode<T> root) {
        if (root.left == null) {
            return root;
        }
        return findMinimum(root.left);
    }

    private TreeNode<T> deleteToTree(TreeNode<T> root, T value) {
        TreeNode<T> found = searchInTree(root, value);
        if (found == null) {
            return null;
        }

        if (found.right != null) {
            TreeNode<T> min = findMinimum(found.right);
            if (found.right == min) {
                found = found.right;
            } else {
                found.right.left = min.right;
                min.right = found.right;
                min.left = found.left;
                found = min;
            }
        } else {
            found = found.left;
        }

        size--;
        return root;
    }

    public void delete(T value) {
        root = deleteToTree(root, value);
        root = balanceTree(root);
    }

    private TreeNode<T> searchInTree(TreeNode<T> root, T value) {
        if (root == null) {
            return null;
        }

        if (comparator.compare(value, root.value) > 0) {
            return searchInTree(root.right, value);
        } else if (comparator.compare(value, root.value) < 0) {
            return searchInTree(root.left, value);
        } else {
            return root;
        }
    }

    public T search(T value) {
        TreeNode<T> result = searchInTree(root, value);
        return result != null ? result.value : null;
    }

    private int height(TreeNode<T> root, int level) {
        if (root == null) {
            return level;
        }
        return Math.max(height(root.left, level + 1), height(root.right, level + 1));
    }

    public int height() {
        if (root == null) {
            return 0;
        }
        return height(root, 0);
    }

    public int size() {
        return size;
    }
}