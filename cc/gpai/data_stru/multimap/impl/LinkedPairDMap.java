package cc.gpai.data_stru.multimap.impl;

import java.util.LinkedList;
import java.util.List;

import cc.gpai.data_stru.multimap.ListPairDMap;

public class LinkedPairDMap<K, V> extends ListPairDMap<K, V> {
	@Override
    protected List<Entry<K, V>> createList() {
	    return new LinkedList<Entry<K, V>>();
    }

}
