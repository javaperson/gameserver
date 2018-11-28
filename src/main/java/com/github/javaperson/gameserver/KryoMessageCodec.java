package com.github.javaperson.gameserver;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.github.javaperson.gameserver.proto.Echo;
import io.netty.buffer.ByteBufInputStream;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

import static com.github.javaperson.gameserver.Constants.KRYO;

public class KryoMessageCodec implements MessageCodec {
    public static final KryoMessageCodec INSTANCE = new KryoMessageCodec();
    private final static KryoPool kryoPool = new KryoPool.Builder(new KryoFactory() {
        public Kryo create() {
            Kryo kryo = new Kryo();
            kryo.register(InvocationMessage.class, 20);
            kryo.register(ReturnMessage.class, 21);
            kryo.register(String[].class, 22);
            kryo.register(Echo.class, 23);
            return kryo;
        }
    }).build();

    public void encodeToWire(Buffer buffer, Object o) {
        Output output = new Output(32, 10240);
        Kryo kryo = kryoPool.borrow();
        try {
            kryo.writeClassAndObject(output, o);
        } finally {
            kryoPool.release(kryo);
        }
        output.close();
        buffer.appendBytes(output.toBytes());
    }

    public Object decodeFromWire(int pos, Buffer buffer) {
        Input input = new Input(new ByteBufInputStream(buffer.getByteBuf()));
        input.skip(pos);
        Kryo kryo = kryoPool.borrow();
        try {
            return kryo.readClassAndObject(input);
        } finally {
            kryoPool.release(kryo);
        }
    }

    public Object transform(Object o) {
        return o;
    }

    public String name() {
        return KRYO;
    }

    public byte systemCodecID() {
        return -1;
    }
}
