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
package nl.basjes.loops;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.esotericsoftware.minlog.Log;
import nl.basjes.collections.MyCustomMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLoopReference {
    private static final Logger LOG = LoggerFactory.getLogger(TestLoopReference.class);

    private LoopClassA createSerializationInstance() {
        LoopClassA classA = new LoopClassA("One");
        LoopClassB classB = new LoopClassB("Two");
        classA.setOther(classB);
        classB.setOther(classA);
        return classA;
    }

    private void verifySerializationInstance(LoopClassA instance) {
        Assertions.assertEquals("One", instance.getName());
        Assertions.assertEquals("One", instance.getOther().getOther().getName());
        Assertions.assertEquals("Two", instance.getOther().getName());
        Assertions.assertEquals(instance, instance.getOther().getOther());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testKyroSerialization() {
        LoopClassA instance = createSerializationInstance();
        verifySerializationInstance(instance);

        LOG.info("--------------------------------------------------------------");
        LOG.info("Serialize");
        Kryo kryo = new Kryo();
        Log.TRACE();
        kryo.register(LoopClassA.class);
        kryo.register(LoopClassB.class);


        ByteBufferOutput byteBufferOutput = new ByteBufferOutput(1_000_000, -1);
        kryo.writeClassAndObject(byteBufferOutput, instance);
        ByteBuffer bytes = byteBufferOutput.getByteBuffer();

        LOG.info("The Map was serialized into {} bytes", bytes.position());
        LOG.info("--------------------------------------------------------------");
        LOG.info("Deserialize");

        bytes.rewind();
        ByteBufferInput byteBufferInput = new ByteBufferInput(bytes);
        Object          mapObject       = kryo.readClassAndObject(byteBufferInput);
        Assertions.assertTrue(mapObject instanceof LoopClassA);

        instance = (LoopClassA) mapObject;

        LOG.info("Done");
        LOG.info("==============================================================");

        verifySerializationInstance(instance);
    }


}
