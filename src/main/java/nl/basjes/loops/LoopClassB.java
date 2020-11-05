package nl.basjes.loops;

import com.esotericsoftware.kryo.DefaultSerializer;

@DefaultSerializer(LoopClassB.KryoSerializer.class)
public class LoopClassB extends Looper<LoopClassA> {
    /** Used only by Kryo */
    private LoopClassB()            { super("Unspecified B"); }
    public  LoopClassB(String name) { super(name); }
}
