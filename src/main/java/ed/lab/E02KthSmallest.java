package ed.lab;

import java.util.ArrayList;

public class E02KthSmallest {

    private void inorder(TreeNode<Integer> root, ArrayList<Integer> list) {
        if (root == null) {
            return;
        }

        inorder(root.left, list);
        list.add(root.value);
        inorder(root.right, list);
    }

    public int kthSmallest(TreeNode<Integer> root, int k) {

        ArrayList<Integer> inorderList = new ArrayList<>();
        inorder(root, inorderList);

        return inorderList.get(k - 1);
    }

}