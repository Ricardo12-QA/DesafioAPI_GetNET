import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.BeforeClass;

public class BaseClass implements Constantes{
    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = BaseURL;
        RequestSpecBuilder reqbuilder = new RequestSpecBuilder();
        reqbuilder.setContentType(CONTENT_TYPE);
        RestAssured.requestSpecification = reqbuilder.build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
