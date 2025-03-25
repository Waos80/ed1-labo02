package ed.lab;

import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        Comparator<Integer> comparator = (o1, o2) -> {
            if (o1 > o2) {
                return 1;
            } else if (o1 < o2) {
                return -1;
            } else {
                return 0;
            }
        };

        E03AVLTree<Integer> AVLTree = new E03AVLTree<>(comparator);
        AVLTree.insert(1);
        AVLTree.insert(2);
        System.out.println(AVLTree.height());
    }
}