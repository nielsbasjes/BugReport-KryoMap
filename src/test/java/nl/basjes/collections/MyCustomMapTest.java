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
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyCustomMapTest {
    private static final Logger LOG = LoggerFactory.getLogger(MyCustomMapTest.class);

    private MyCustomMap<String> createSerializationInstance() {
        MyCustomMap<String> newStuff = new MyCustomMap<String>(true);

        newStuff.put("one", "ONE");
        newStuff.put("two", "TWO");
        return newStuff;
    }

    private void verifySerializationInstance(MyCustomMap<String> instance) {
        assertEquals("ONE", instance.get("one"));
        assertEquals("TWO", instance.get("two"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testKyroSerialization() {
        MyCustomMap<String> instance = createSerializationInstance();
        verifySerializationInstance(instance);

        LOG.info("--------------------------------------------------------------");
        LOG.info("Serialize");
        Kryo kryo = new Kryo();
        kryo.register(MyCustomMap.class);

        ByteBufferOutput byteBufferOutput = new ByteBufferOutput(1_000_000, -1);
        kryo.writeClassAndObject(byteBufferOutput, instance);
        ByteBuffer bytes = byteBufferOutput.getByteBuffer();

        LOG.info("The Map was serialized into {} bytes", bytes.position());
        LOG.info("--------------------------------------------------------------");
        LOG.info("Deserialize");

        bytes.rewind();
        ByteBufferInput byteBufferInput = new ByteBufferInput(bytes);
        Object          mapObject       = kryo.readClassAndObject(byteBufferInput);
        assertTrue(mapObject instanceof MyCustomMap);

        instance = (MyCustomMap<String>) mapObject;

        LOG.info("Done");
        LOG.info("==============================================================");

        verifySerializationInstance(instance);
    }


}
