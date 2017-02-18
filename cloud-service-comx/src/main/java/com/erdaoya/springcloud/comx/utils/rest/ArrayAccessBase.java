package com.erdaoya.springcloud.comx.utils.rest;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by xue on 12/20/16.
 * TODO 再次考虑做法
 */
public interface ArrayAccessBase<K, V> extends Map<K, V> {
    default int size()                      {return 0;}
    default boolean isEmpty()               {return false;}
    //default boolean containsKey(Object key) {return false;}
    default boolean containsValue(Object v) {return false;}
    // get
    default V put(K key, V value)           {return null;}
    default V remove (Object key)           {return null;}
    default void putAll(Map<? extends K, ? extends V> map)      {}
    default void clear  ()                                      {}
    default Set keySet ()                                       {return null;}
    default Collection values()                                 {return null;}
    default Set entrySet()                                      {return null;}
    // other map default interfaces;
}
