package org.acme;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class GraphQLResource {

    @Query("dummyQuery")
    public List<String> query() {
        return List.of("A", "B", "C");
    }
}

