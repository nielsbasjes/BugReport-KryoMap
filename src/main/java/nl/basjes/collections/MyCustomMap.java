/*
 * Copyright (C) 2018-2020 Niels Basjes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.basjes.collections;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCustomMap<V extends Serializable> implements Map<String, V>, KryoSerializable, Serializable {
    private Boolean             someFlag;
    private TreeMap<String, V>  stuff;

    private static final Logger LOG = LoggerFactory.getLogger(MyCustomMap.class);

    // private constructor for serialization systems ONLY (like Kyro)
    private MyCustomMap() {
    }

    public MyCustomMap(boolean newSomeFlag) {
        someFlag = newSomeFlag;
        stuff = new TreeMap<>();
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeBoolean(someFlag);
        kryo.writeClassAndObject(output, stuff);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void read(Kryo kryo, Input input) {
        someFlag = input.readBoolean();
        stuff = (TreeMap<String, V>) kryo.readClassAndObject(input);
    }

    @Override
    public V put(String key, V value) {
        if (someFlag == null) {
            LOG.error("SOMEFLAG SHOULD NEVER BE NULL");
        }
        return stuff.put(key, value);
    }

    @Override
    public V remove(Object o) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> map) {
        stuff.putAll(map);
    }

    @Override
    public int size() {
        return stuff.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {
        stuff.clear();
    }

    @Override
    public Set<Entry<String, V>> entrySet() {
        return stuff.entrySet();
    }

    @Override
    public boolean containsKey(Object key) {
        return stuff.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return stuff.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return stuff.get(key);
    }

    @Override
    public Set<String> keySet() {
        return stuff.keySet();
    }

    @Override
    public Collection<V> values() {
        return stuff.values();
    }
}
