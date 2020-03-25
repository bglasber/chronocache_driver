# chronocache_driver

This repository contains the source code for a prototype JDBC driver for the ChronoCache system. 
The ChronoCache system itself uses a REST interface that simplifies development --- this JDBC driver
merely translates a subset of JDBC calls to REST calls to interact with ChronoCache. As many systems
use JDBC to interact with a database, this enables easier integration with ChronoCache. Note, however,
that the experiment results in the paper were obtained by using the REST interface *directly*. 

Note that both ChronoCache and this driver are *research prototypes*. You may extend them to support
functionality as you wish, and you will likely have to if you want to use them in production.


## Using this Driver

After compiling the driver using:
```mvn package```
copy the uberjar for this driver into the lib directory of the application you wish to use it with.
Set the CLASSPATH accordingly.

You can use the standard ```Class.forName("org.bjglasbe.ChronoCacheDriver")``` syntax as usual to load the driver.
The JDBC URL looks like: ```jdbc:cc://hostname:port```. Note that you will need to configure ChronoCache itself to
interact with the remote database.

## Reproducing experiment Results

The ChronoCache system code will be freely available under the BSD-3 license. The code we used for each Benchmark
will also be made available shortly --- i.e. benchmark modifications to use REST --- under the original licenses for each
benchmark.

## Driver License

BSD-3
