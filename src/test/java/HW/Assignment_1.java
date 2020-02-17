package HW;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Assignment_1 {

    /*
    Q1:
- Given accept type is Json
- Path param value- US
- When users sends request to /countries
- Then status code is 200
- And Content - Type is Json
- And country_id is US
- And Country_name is United States of America
- And Region_id is
     */

    @BeforeClass
    public void setUpClass(){
        baseURI = ConfigurationReader.get("hrapi.uri");
    }

    @Test
    public void verifyUS(){
        Response response = given().accept(ContentType.JSON).pathParam("value","US").when().get("/countries/{value}");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");
        Assert.assertTrue(response.body().asString().contains("US"));
        Assert.assertTrue(response.body().asString().contains("States of America"));
        Assert.assertTrue(response.body().asString().contains("2"));
    }

    /*

    Q2:
- Given accept type is Json
- Query param value - q={"department_id":80}
- When users sends request to /employees
- Then status code is 200
- And Content - Type is Json
- And all job_ids start with 'SA'
- And all department_ids are 80
- Count is 25
     */

    @Test
    public void verifySA(){
    Response response =given().accept(ContentType.JSON).queryParam("q","{\"department_id\":80}")
                        .when().get("/employees");

    Assert.assertEquals(response.statusCode(),200);
    Assert.assertEquals(response.contentType(),"application/json");
    Assert.assertTrue(response.body().asString().contains("SA"));
    Assert.assertTrue(response.body().equals(80));
        JsonPath jsonData = response.jsonPath();
        List<Integer> departmentIds=jsonData.getList("items.department_id");
        Assert.assertEquals(departmentIds.size(),25);

    }
}
