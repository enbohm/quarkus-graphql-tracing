# quarkus-graphql-tracing project

This project uses Quarkus with GraphQL and OpenTracing extensions. However, disabling tracing 
for a specific profile, e.g. test, triggers a NullpointerException (see application.properties how 
to disable tracing for a specific runtime profile).

## Running the application in dev mode

You can run this example application in dev mode using:
```
./mvnw quarkus:dev
```

Open a browser at http://127.0.0.1:8080/graphql-ui/ and execute the GraphQL dummy query. This works 
as expected, and the request is traced (logged) in the console. However, running the test case 
GraphQLResourceTest.java, where tracing is disabled, fails. This is due to that the OpenTraingDecorator-class expect that the tracing is 
enabled and hence a NPE is thrown (OpenTraingDecorator#execute(executionContext) -> tracer.scopeManager().active().close()) see stacktrace below.

```
2020-08-03 14:45:31,968 ERROR [io.sma.graphql] (vert.x-worker-thread-0) SRGQL012000: Data Fetching Error: java.lang.NullPointerException
	at io.smallrye.graphql.execution.datafetcher.decorator.OpenTracingDecorator.execute(OpenTracingDecorator.java:77)
	at io.smallrye.graphql.execution.datafetcher.ExecutionContextImpl.proceed(ExecutionContextImpl.java:66)
	at io.smallrye.graphql.execution.datafetcher.AbstractDataFetcher.execute(AbstractDataFetcher.java:144)
	at io.smallrye.graphql.execution.datafetcher.ReflectionDataFetcher.get(ReflectionDataFetcher.java:72)
	at io.smallrye.graphql.execution.datafetcher.ReflectionDataFetcher.get(ReflectionDataFetcher.java:22)
	at graphql.execution.ExecutionStrategy.fetchField(ExecutionStrategy.java:277)
	at graphql.execution.ExecutionStrategy.resolveFieldWithInfo(ExecutionStrategy.java:202)
	at graphql.execution.AsyncExecutionStrategy.execute(AsyncExecutionStrategy.java:74)
	at graphql.execution.Execution.executeOperation(Execution.java:167)
	at graphql.execution.Execution.execute(Execution.java:108)
	at graphql.GraphQL.execute(GraphQL.java:598)
	at graphql.GraphQL.parseValidateAndExecute(GraphQL.java:529)
	at graphql.GraphQL.executeAsync(GraphQL.java:493)
	at graphql.GraphQL.execute(GraphQL.java:426)
	at io.smallrye.graphql.execution.ExecutionService.execute(ExecutionService.java:124)
	at io.smallrye.graphql.execution.ExecutionService.execute(ExecutionService.java:96)
	at io.quarkus.smallrye.graphql.runtime.SmallRyeGraphQLExecutionHandler.doRequest(SmallRyeGraphQLExecutionHandler.java:164)
	at io.quarkus.smallrye.graphql.runtime.SmallRyeGraphQLExecutionHandler.doRequest(SmallRyeGraphQLExecutionHandler.java:157)
	at io.quarkus.smallrye.graphql.runtime.SmallRyeGraphQLExecutionHandler.handlePost(SmallRyeGraphQLExecutionHandler.java:100)
	at io.quarkus.smallrye.graphql.runtime.SmallRyeGraphQLExecutionHandler.doHandle(SmallRyeGraphQLExecutionHandler.java:81)
	at io.quarkus.smallrye.graphql.runtime.SmallRyeGraphQLExecutionHandler.handle(SmallRyeGraphQLExecutionHandler.java:59)
	at io.quarkus.smallrye.graphql.runtime.SmallRyeGraphQLExecutionHandler.handle(SmallRyeGraphQLExecutionHandler.java:35)
	at io.vertx.ext.web.impl.BlockingHandlerDecorator.lambda$handle$0(BlockingHandlerDecorator.java:48)
	at io.vertx.core.impl.ContextImpl.lambda$executeBlocking$2(ContextImpl.java:313)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.base/java.lang.Thread.run(Thread.java:834)

```
