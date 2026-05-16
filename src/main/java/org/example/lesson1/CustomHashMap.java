package org.example.lesson1;

import java.util.*;

public class CustomHashMap<K, V> implements Map<K, V> {
    private static final int CAPACITY = 16;
    private static final float LOAD = 0.8f;

    private Node<K, V>[] table;
    private int size = 0;

    public CustomHashMap() {
        this.table = new Node[CAPACITY];
    }

    private static class Node<K, V> implements Map.Entry<K, V> {

        private final K key;
        private V value;
        private Node<K, V> next;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }


        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    private int getIndex(Object key) {

        if (key == null) {
            return 0;
        }

        int hash = key.hashCode();
        return Math.abs(hash) % table.length;
    }

    private void resize() {

        Node<K, V>[] oldTable = table;
        this.table = new Node[oldTable.length * 2];
        this.size = 0;

        for (Node<K, V> node : oldTable) {
            while (node != null) {
                put(node.key, node.value);
                node = node.next;
            }
        }
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (Node<K, V> node : table) {

            while (node != null) {

                if (Objects.equals(node.value, value)) {
                    return true;
                }

                node = node.next;
            }
        }

        return false;
    }

    @Override
    public V get(Object key) {
        int index = getIndex(key);
        Node<K, V> current = table[index];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    @Override
    public V put(K key, V value) {

        if ((float) (size + 1) / table.length > LOAD) {
            resize();
        }
        int index = getIndex(key);
        Node<K, V> current = table[index];

        if (current == null) {
            table[index] = new Node<>(key, value);
            size++;
            return null;
        }

        while (true) {

            if (Objects.equals(current.key, key)) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }

            if (current.next == null) {
                break;
            }

            current = current.next;
        }

        current.next = new Node<>(key, value);
        size++;

        return null;
    }

    @Override
    public V remove(Object key) {

        int index = getIndex(key);

        Node<K, V> current = table[index];
        Node<K, V> previous = null;

        while (current != null) {

            if (Objects.equals(current.key, key)) {

                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }

            previous = current;
            current = current.next;
        }

        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();

        for (Node<K, V> node : table) {

            while (node != null) {
                keys.add(node.key);
                node = node.next;
            }
        }

        return keys;
    }

    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();

        for (Node<K, V> node : table) {

            while (node != null) {
                values.add(node.value);
                node = node.next;
            }
        }

        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entries = new HashSet<>();

        for (Node<K, V> node : table) {

            while (node != null) {
                entries.add(node);
                node = node.next;
            }
        }

        return entries;
    }
}
