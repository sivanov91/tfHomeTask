package task;

import org.apache.commons.lang3.StringUtils;
import task.tree.avl.AVLTree;
import java.util.Arrays;

public class AVLTreeMain {

    public static void main(String... args) {

        // Создае дерево.
        AVLTree avlTree = new AVLTree();
        Arrays.asList(100, 15, 190, 171, 3, 91, 205, 155, 13, 17, 203)
                .forEach(i -> avlTree.addNode(i, i));

        // Распечатка дерева в требуемом формате.
        avlTree.printTree();

        // Поиск элемента в дереве.
        System.out.println(StringUtils.repeat("-", 20));
        System.out.println(avlTree.search(155).getValue());

    }

}
