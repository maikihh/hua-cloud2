package restassured;

import io.restassured.builder.ResponseBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class filterDemo implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification filterableRequestSpecification, FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {
        Response resOrigin = filterContext.next(filterableRequestSpecification, filterableResponseSpecification);
        ResponseBuilder responseBuilder = new ResponseBuilder();
        return responseBuilder.clone(resOrigin).setStatusCode(400).build();
       // return responseBuilder.clone(resOrigin).setStatusCode(400).build();
    }
}
