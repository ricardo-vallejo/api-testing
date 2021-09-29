package trainingrestassure;

import models.Product;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SecondChallenge {

    @Test
    public void getProduct() {
        String endPoint = "http://localhost:80/api_testing/product/read_one.php";
        var response =
                given().queryParam("id", 18)
                        .when().get(endPoint)
                        .then();
        response.log().body();
        response.log().headers();
    }

    @Test
    public void validateStatusCode() {
        String endPoint = "http://localhost:80/api_testing/product/read_one.php";
        var response =
                given().queryParam("id", 18).
                        when().get(endPoint).
                        then().log().body();
        response.assertThat().statusCode(200);
    }

    @Test
    public void validateHeader() {
        String endPoint = "http://localhost:80/api_testing/product/read_one.php";
        var response =
                given().queryParam("id", 18).
                        when().get(endPoint).
                        then().log().body();

        response.assertThat().
                header("Content-Type", "application/json");
    }

    @Test
    public void validateBody() {
        String endPoint = "http://localhost:80/api_testing/product/read_one.php";
        var response =
                given().queryParam("id", 18).
                        when().get(endPoint).
                        then().log().body();

        response.assertThat().
                body("id", equalTo("18")).
                body("name", equalTo("Multi-Vitamin (90 capsules)")).
                body("description", equalTo("A daily dose of our Multi-Vitamins fulfills a day’s nutritional needs for over 12 vitamins and minerals.")).
                body("price", equalTo("10.00")).
                body("category_id", equalTo("4")).
                body("category_name", equalTo("Supplements"));
    }

    @Test
    public void validateDesBody() {
        String endPoint = "http://localhost:80/api_testing/product/read_one.php";
        Product expProduct = new Product(
                18,
                "Multi-Vitamin (90 capsules)",
                "A daily dose of our Multi-Vitamins fulfills a day’s nutritional needs for over 12 vitamins and minerals.",
                10.00,
                4,
                "Supplements"
        );

        Product actProduct = given().queryParam("id", 18).when().get(endPoint).as(Product.class);

        assertThat(actProduct, samePropertyValuesAs(expProduct));
    }
}
