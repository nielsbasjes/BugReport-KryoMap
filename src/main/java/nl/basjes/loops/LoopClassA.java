package nl.basjes.loops;

import com.esotericsoftware.kryo.DefaultSerializer;

@DefaultSerializer(LoopClassA.KryoSerializer.class)
public class LoopClassA extends Looper<LoopClassB> {
    /** Used only by Kryo */
    private LoopClassA()            { super("Unspecified A"); }
    public  LoopClassA(String name) { super(name); }
}
