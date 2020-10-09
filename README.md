Reproduce Kryo problem I have
===
This demonstrates that I have created a custom implementation of a Map that I want to be serializable with Kryo.

I have implemented KryoSerializable ( https://github.com/EsotericSoftware/kryo/blob/master/README.md#kryoserializable )
yet when actually running the test it uses the more generic MapSerializer instead of my write/read methods.
As a consequence I get an NPE.

This project tries to be the smallest reproduction of this problem possible.

The Kryo project has my permission to include any of this in their project to reproduce this or as a test case.
