package trainingrestassure;

import models.Product;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTest {

    @Test
    public void getCategories() {
        String endPoint = "http://localhost:80/api_testing/category/read.php";
        var response = given().when().get(endPoint).then();
        response.log().body();
    }

    @Test
    public void getProduct() {
        String endPoint = "http://localhost:80/api_testing/product/read_one.php";
        var response =
                given().queryParam("id", 2)
                .when().get(endPoint)
                .then();
        response.log().body();
        response.assertThat().statusCode(200);
    }

    @Test
    public void createProduct() {
        String endPoint = "http://localhost:80/api_testing/product/create.php";
        String body = "{\n" +
                "  \"name\": \"Water Bottle\",\n" +
                "  \"description\": \"Blue water bottle. Holds 64 ounces\",\n" +
                "  \"price\": 12,\n" +
                "  \"category_id\": 3\n" +
                "}";
        var response = given().body(body).when().post(endPoint).then();
        response.log().body();
    }

    @Test
    public void updateProduct() {
        String endPoint = "http://localhost:80/api_testing/product/update.php";
        String body = "{\n" +
                "  \"id\": 1000, \n" +
                "  \"name\": \"Water Bottle\",\n" +
                "  \"description\": \"Blue water bottle. Holds 64 ounces\",\n" +
                "  \"price\": 15,\n" +
                "  \"category_id\": 3\n" +
                "}";
        var response = given().body(body).when().put(endPoint).then();
        response.log().body();
    }

    @Test
    public void deleteProduct() {
        String endPoint = "http://localhost:80/api_testing/product/delete.php";
        String body = "{\n" +
                "  \"id\": 1000 \n" +
                "}";
        var response = given().body(body).when().delete(endPoint).then();
        response.log().body();
    }

    @Test
    public void createSerializedProduct() {
        String endPoint = "http://localhost:80/api_testing/product/create.php";
        Product product = new Product(
                "Water Bottle",
                "Blue water bottle. Holds 64 ounces",
                12.0,
                3
        );
        var response = given().body(product).when().post(endPoint).then();
        response.log().body();
    }

    @Test
    public void getProductAssertStatus() {
        String endPoint = "http://localhost:80/api_testing/product/read_one.php";
        var response =
                given().queryParam("id", 2)
                        .when().get(endPoint)
                        .then();
        response.assertThat().statusCode(200); // assertThat() method is used to verify different response elements.
    }

    @Test
    public void getProductValidateBody() {
        String endPoint = "http://localhost:80/api_testing/product/read_one.php";
        var response =
                given().queryParam("id", 2)
                        .when().get(endPoint)
                        .then();

        response.assertThat().
                body("id", equalTo("2")).
                body("name", equalTo("Cross-Back Training Tank")).
                body("description", containsString("The most awesome")).
                body("price", greaterThan("290.00")).
                body("category_id", equalTo("2")).
                body("category_name", equalTo("Active Wear - Women"));

        response.log().body();

    }

    @Test
    public void getProductValidateComplexBody() {
        String endPoint = "http://localhost:80/api_testing/product/read.php";
        var response =
                given().queryParam("id", 2)
                        .when().get(endPoint)
                        .then();

        response.assertThat().
                statusCode(200).
                body("records.size()", equalTo(19));

        response.assertThat().
                statusCode(200).
                body("records.size()", greaterThan(0)).
                body("records.id", everyItem(notNullValue())).
                body("records.name", everyItem(notNullValue())).
                body("records.description", everyItem(notNullValue())).
                body("records.price", everyItem(notNullValue())).
                body("records.category_id", everyItem(notNullValue())).
                body("records.category_name", everyItem(notNullValue()));

        response.assertThat().statusCode(200).body("records.id[0]", equalTo("1001"));

        response.log().body();

    }

    @Test
    public void getProductValidateHeader() {
        String endPoint = "http://localhost:80/api_testing/product/read.php";
        var response =
                given().queryParam("id", 2)
                        .when().get(endPoint)
                        .then();

        response.log().headers();
        response.log().body();

        response.assertThat().
                header("Content-Type", equalTo("application/json; charset=UTF-8"));
    }

    @Test
    public void getDeserializedProduct() {
        String endPoint = "http://localhost:80/api_testing/product/read_one.php";

        Product expectedProduct = new Product(
                2,
                "Cross-Back Training Tank",
                "The most awesome phone of 2013!",
                299.00,
                2,
                "Active Wear - Women"
        );

        Product actualProduct =
                given().queryParam("id", 2).
                when().get(endPoint).
                as(Product.class);

        assertThat(actualProduct, samePropertyValuesAs(expectedProduct));
    }
}
