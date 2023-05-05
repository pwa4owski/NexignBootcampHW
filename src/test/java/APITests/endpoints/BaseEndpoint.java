package APITests.endpoints;

import lombok.Getter;

public abstract class BaseEndpoint {
    @Getter
    String endpoint = this.getClass().getAnnotation(Endpoint.class).value();
}
