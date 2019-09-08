package task.tree.avl;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** Содержит информацию о ноде дерева. */
@Getter
@Setter
@AllArgsConstructor
public class AVLNode {

    /** Ключ. */
    private int key;

    /** Значение. */
    private int value;

    /** Высонта дерева.*/
    private int height;

    /** Ссылка на правле поддерево. */
    private AVLNode right;

    /** Ссылка на левое поддерево. */
    private AVLNode left;

    public AVLNode(int key, int value) {
        this(key, value, 0,  null, null);
    }

}
