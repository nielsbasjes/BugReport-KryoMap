package nl.basjes.loops;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DefaultSerializer(Looper.KryoSerializer.class)
public abstract class Looper<OTHER> {
    private static final Logger LOG = LoggerFactory.getLogger(Looper.class);

    private final String name;
    private OTHER other;

    public Looper(String name)          { this.name = name;   }
    public String getName()             { return name;        }
    public OTHER getOther()             { return other;       }
    public void setOther(OTHER other)   { this.other = other; }

    public static class KryoSerializer extends FieldSerializer<Looper> {
        public KryoSerializer(Kryo kryo, Class<?> type) {
            super(kryo, type);
        }

        @Override
        public Looper read(Kryo kryo, Input input, Class<? extends Looper> type) {
            Looper instance = super.read(kryo, input, type);
            LOG.info("Loaded {} with name {}", instance.getClass().getSimpleName(), instance.getName());
            return instance;
        }
    }
}
