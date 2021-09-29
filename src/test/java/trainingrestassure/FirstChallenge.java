package trainingrestassure;

import models.Product;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class FirstChallenge {

    @Test
    public void createProduct() {
        String endPoint = "http://localhost:80/api_testing/product/create.php";
        Product newProduct = new Product(
                "Sweatband",
                "The sports headband will keep your face dry and dry quickly.",
                5.0,
                3
        );
        var response = given().body(newProduct).when().post(endPoint).then();
        response.log().body();
    }

    @Test
    public void getProduct() {
        String endPoint = "http://localhost:80/api_testing/product/read_one.php";
        var response =
                given().queryParam("id", 1002)
                .when().get(endPoint)
                .then();
        response.log().body();
    }

    @Test
    public void updateProduct() {
        String endPoint = "http://localhost:80/api_testing/product/update.php";
        Product updateProduct = new Product(
                1002,
                "Sweatband",
                "The sports headband will keep your face dry and dry quickly.",
                6.0,
                3
        );
        var response = given().body(updateProduct).when().put(endPoint).then();
        response.log().body();
    }

    @Test
    public void deleteProduct() {
        String endPoint = "http://localhost:80/api_testing/product/delete.php";
        Product deleteProduct = new Product();
        deleteProduct.setId(1002);
        var response = given().body(deleteProduct).when().delete(endPoint).then();
        response.log().body();
    }
}
