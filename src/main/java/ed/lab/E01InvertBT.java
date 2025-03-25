package ed.lab;

public class E01InvertBT {
    public void invert(TreeNode<Integer> root) {
        if (root == null) {
            return;
        }

        TreeNode<Integer> tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        invert(root.left);
        invert(root.right);
    }

    public TreeNode<Integer> invertTree(TreeNode<Integer> root) {
        invert(root);
        return root;
    }
}
