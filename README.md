# Test setup for getting pre-configured Caches of a CacheContainer from infinispan subsystem of Wildfly
This simple test setup works up to the point where it receives the CacheContainer (or better EmbeddedCacheManager) from the infinispan subsystem using resource injection.
As soon as it requests the configured caches and unexpected behavior is faced, the caches are created from scratch without any of the configuration details we provided in the subsystem configuration.

Setup:
* Fresh Wildfly 10.0.0.Final
* Run commands of _add-caches.cli_ to setup caches for the test (i.e. `jboss-cli.sh -c --file=add-caches.cli`)
** This adds the cache container _TEST_ with default cache _x_
** It also adds cache x with the following eviction settings: max-entries=3, strategy=LRU
** It the adds cache y with the following eviction settings: max-entries=5, strategy=FIFO

Start Wildfly:
* sh standalone.sh

Build and deploy application:
* mvn clean package wildfly:deploy-only

Run the test:
* [http://localhost:8080/cache-test](http://localhost:8080/cache-test)
