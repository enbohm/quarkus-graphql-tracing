# quarkus-graphql-tracing project

This project uses Quarkus with GraphQL and OpenTracing extensions. However, disabling tracing 
for a specific profile, e.g. test, triggers a NullpointerException (see application.properties how 
to disable tracing for a specific runtime profile).

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

Open a browser at http://127.0.0.1:8080/graphql-ui/ and execute the GraphQL dummy query. This works 
as expected and the traced as logged in the console. However, running the test case 
(GraphQLResourceTest) fails since OpenTraingDecorator-class expect that the tracing is enabled 
(NPE at row 66 tracer.scopeManager().active().close())