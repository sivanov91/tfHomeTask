package task.tree.avl;

import java.util.ArrayList;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.repeat;

public class AVLTree {


    private AVLNode root;

    /** Добавление узла в дерево. */
    public void addNode(int key, int value) {
        this.root = putNode(this.root, key, value);
    }

    /** Удаление ноды по ключу. */
    public void deleteNode(int key) {
        this.root = deleteNode(this.root, key);
    }

    /** Удаление ноды. */
    private AVLNode deleteNode(AVLNode node, int key) {
        if (isNull(node)) {
            return null;
        }

        if (node.getKey() > key) {
            node.setLeft(deleteNode(node.getLeft(), key));
        } else if (node.getKey() < key) {
            node.setRight(deleteNode(node.getRight(), key));
        } else {

            if (isNull(node.getRight())) {
                return node.getLeft();
            }
            if (isNull(node.getLeft())) {
                return node.getRight();
            }

            AVLNode t = node;

            node = getMin(t.getRight());
            node.setRight(deleteMin(t.getRight()));
            node.setLeft(t.getLeft());

            return balance(node);
        }

        return balance(node);
    }


    AVLNode deleteMin(AVLNode node) {

        if (isNull(node.getLeft())) {
            return node.getRight();
        }

        node.setLeft(deleteMin(node.getLeft()));
        node.setHeight(1 + getSize(node.getLeft()) + getSize(node.getRight()));

        return node;
    }


    AVLNode getMin(AVLNode node) {
        return nonNull(node.getLeft()) ? getMin(node.getLeft()) : node;
    }


    /** Перерасчет уровня узла. */
    private void correctHeight(AVLNode node) {
        int heightLeft = getSize(node.getLeft());
        int heightRight = getSize(node.getRight());
        // Берем максимальную высоту поддерева и добаляем 1.
        int height = (heightLeft > heightRight ? heightLeft : heightRight) + 1;
        node.setHeight(height);
    }

    /** Метод возвращает фактор баланса. (разницу в высоте между правым и левым под-деревом) */
    private int bfactor(AVLNode node) {
        return getSize(node.getRight()) - getSize(node.getLeft());
    }

    /** Возвращает высоту дерева для переданной ноды. */
    private int getSize(AVLNode node) {
        return nonNull(node) ? node.getHeight() : 0;
    }

    /** Балансировка поддерева для переданной ноды. */
    private AVLNode balance(AVLNode node) {
        correctHeight(node);
        if (bfactor(node) == 2) {
            if (bfactor(node.getRight()) < 0) {
                node.setRight(rotateRight(node.getRight()));
            }
            return rotateLeft(node);
        }

        if (bfactor(node) == -2) {
            if (bfactor(node.getLeft()) > 0) {
                node.setLeft(rotateLeft(node.getLeft()));
            }

            return rotateRight(node);
        }

        return node;
    }

    /** Выполняем правый поворот для переданной ноды. */
    private AVLNode rotateRight(AVLNode node) {
        AVLNode tempNode = node.getLeft();
        node.setLeft(tempNode.getRight());
        tempNode.setRight(node);

        correctHeight(node);
        correctHeight(tempNode);

        return tempNode;
    }

    /** Выполняем левый поворот для переданной ноды.*/
    private AVLNode rotateLeft(AVLNode node) {
        AVLNode tmpNode = node.getRight();
        node.setRight(tmpNode.getLeft());
        tmpNode.setLeft(node);

        correctHeight(node);
        correctHeight(tmpNode);

        return tmpNode;
    }

    /** Добавление узла с рекурсивным поиском места вставки и балансировкой, если это необходимо. */
    private AVLNode putNode(AVLNode node, int key, int value) {
        if (isNull(node)) {
            return new AVLNode(key, value);
        }

        if (node.getKey() > key) {
            node.setLeft(putNode(node.getLeft(), key, value));
        } else if (node.getKey() < key) {
            node.setRight(putNode(node.getRight(), key, value));
        } else if (node.getKey() == key) {
            node.setValue(value);
        }

        node.setHeight(1 + getSize(node.getLeft()) + getSize(node.getRight()));

        return balance(node);
    }

    public void printTree() {
        // Заполняем масив билдерами.
        // TODO Можно реализовать с меньшими затаратами памяти,
        //  заменив список с билдерами на одну целочисленную переменную. (исправлю чуть позже как появится время)
        ArrayList<StringBuilder> list = new ArrayList<>(root.getHeight() + 1);
        for (int i = 0; i <= root.getHeight(); i++) {
            list.add(new StringBuilder());
        }

        // Заполняем данными.
        printNodes(root, 0, list);

        // Печатаем.
        list.forEach(sb -> System.out.println(sb.toString()));
    }

    private void printNodes(AVLNode node, int level, ArrayList<StringBuilder> list) {
        if (isNull(node)) {
            return;
        }

        printNodes(node.getLeft(), level + 1, list);

        // Вычисляем макс. отступ.
        int maxDownLength = list.subList(level, list.size())
                .stream().mapToInt(sb -> sb.length()).max()
                .orElse(0);
        int maxUpLength = list.subList(0, level)
                .stream().mapToInt(sb -> sb.length()).max()
                .orElse(0);
        int maxLength = maxDownLength > maxUpLength ? maxDownLength : maxUpLength;

        StringBuilder sbCurrent = list.get(level);
        int countSpace = maxLength > sbCurrent.length() ? maxLength - sbCurrent.length() : 0;
        sbCurrent.append(repeat(" ", countSpace));
        sbCurrent.append(node.getKey());

        printNodes(node.getRight(), level + 1, list);
    }

    public AVLNode search(int key) {
        return search(this.root, key);
    }

    private AVLNode search(AVLNode node, int key) {
        if (isNull(node)) {
            return null;
        }

        if (node.getKey() == key) {
            return node;
        } else if (key > node.getKey()) {
            return search(node.getRight(), key);
        } else {
            return search(node.getLeft(), key);
        }
    }




}
