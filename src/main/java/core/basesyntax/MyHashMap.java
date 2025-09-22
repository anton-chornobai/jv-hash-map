package core.basesyntax;

import java.util.LinkedList;

public class MyHashMap<K, V> implements MyMap<K, V> {
    private static final float LOAD_FACTOR = 0.75f;
    private static final int INITIAL_CAPACITY = 16;

    private LinkedList<Node<K, V>>[] list;
    private int capacity;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        list = (LinkedList<Node<K, V>>[]) new LinkedList[INITIAL_CAPACITY];
        capacity = INITIAL_CAPACITY;
    }

    @Override
    public void put(K key, V value) {
        extendCapacity();

        Node<K, V> newEntry = new Node<>(key, value);
        int index = (key == null) ? 0 : Math.abs(key.hashCode()) % capacity;

        if (list[index] == null) {
            list[index] = new LinkedList<>();
        }

        for (Node<K, V> node : list[index]) {
            if ((node.key == null && key == null) || (node.key != null && node.key.equals(key))) {
                node.value = value;
                return;
            }
        }

        list[index].add(newEntry);
        size++;
    }

    @Override
    public V getValue(K key) {
        int index = (key == null) ? 0 : Math.abs(key.hashCode()) % capacity;

        if (list[index] == null) {
            return null;
        }

        for (Node<K, V> node : list[index]) {
            if ((node.key == null && key == null) || (node.key != null && node.key.equals(key))) {
                return node.value;
            }
        }

        return null;
    }

    private void extendCapacity() {
        if (size >= capacity * LOAD_FACTOR) {
            int newCapacity = capacity * 2;
            LinkedList<Node<K, V>>[] newList = new LinkedList[newCapacity];

            for (LinkedList<Node<K, V>> bucket : list) {
                if (bucket != null) {
                    for (Node<K, V> node : bucket) {
                        int newIndex = (node.key == null)
                                ? 0
                                : Math.abs(node.key.hashCode()) % newCapacity;
                        if (newList[newIndex] == null) {
                            newList[newIndex] = new LinkedList<>();
                        }
                        newList[newIndex].add(node);
                    }
                }
            }

            list = newList;
            capacity = newCapacity;
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    public class Node<K, V> {
        private K key;
        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public void setKey(K key) {
            this.key = key;
        }
    }
}
