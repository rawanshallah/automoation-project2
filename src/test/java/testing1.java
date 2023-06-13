import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class testing1 {
    public static void main(String[] args) {


        //1.Launch Chrome browser
        WebDriver driver = new ChromeDriver();

        //2. Navigate to smartbear website
        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
        driver.manage().window().maximize();
        driver.manage().timeouts().getImplicitWaitTimeout();

        //3.  Login using username and password test

        driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester");
        driver.findElement(By.id("ctl00_MainContent_password")).sendKeys("test");
        driver.findElement(By.id("ctl00_MainContent_login_button")).click();

        //4. Click on Order link
        driver.findElement(By.xpath("//a[@href=\"Process.aspx\"]")).click();

        //5. Enter a random product quantity between 1 & 100
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")).sendKeys("80", Keys.ENTER);
//

        //6. Click on Calculate and verify total value as expected
        driver.findElement(By.xpath("//input[@value= 'Calculate' ]")).click();

        //6.a Generate and enter random first name and last name
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int values = random.nextInt(26) + 65;
            char randoms = (char) values;
            sb.append(randoms);
        }
        String randomFirstname = sb.toString();

        //random lastname
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int values = random.nextInt(26) + 65;
            char randoms2 = (char) values;
            sb2.append(randoms2);
        }
        String randomLastname = sb2.toString();
        String fullFirstAndLast = (randomFirstname + " " + randomLastname).toLowerCase();
        //----------------

        driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtName")).sendKeys(fullFirstAndLast);



        //7. Generate and enter random street address

        StringBuilder sb3 = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int values = random.nextInt(26) + 65;
            char randoms3 = (char) values;
            sb3.append(randoms3);
        }
        String streetName = sb3.toString().toLowerCase();

        //-------------------
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox2")).sendKeys(streetName);

        //8. Generate and enter random city
        String[] city = {"San Diego",
                "Washington, DC",
                "New York",
                "Chicago"};

//        Random randomC= new Random();
        int cit = random.nextInt(city.length);
        String randomCity = city[cit];

        //-------------
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox3")).sendKeys(randomCity);

        //9. Generate and enter a random state
        String[] state = {"California",
                "Texas",
                "Floria",
                "New Jersey",
                "New York"};

        int st = random.nextInt(state.length);
        String randomState = state[st];
        //----------------
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4")).sendKeys(randomState);

        //10. Generate and enter a random 5 digit zip code
        int randomZipCode = (int) (Math.random() * 100000);

        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox5")).sendKeys(randomZipCode + "");

        //11. Select the card type randomly
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList"));
        List<WebElement> checkboxes = driver.findElements(By.xpath("//input[@type= \"radio\" ] "));

        for (WebElement checkbox : checkboxes) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }


        checkboxes.get(new Random().nextInt(3)).click();

        //12. Generate and enter the random card number
        long randomNumber = Math.abs(random.nextLong()) % 900000000000000L + 100000000000000L;
        long randomNumber2 = Math.abs(random.nextLong()) % 90000000000000L + 10000000000000L;

        String selectedCard = driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList")).getText();

        String card1 = driver.findElement(By.xpath("//table[@id='ctl00_MainContent_fmwOrder_cardList']//label[@for='ctl00_MainContent_fmwOrder_cardList_0'] ")).getText();
        String card2 = driver.findElement(By.xpath("//table[@id='ctl00_MainContent_fmwOrder_cardList']//label[@for='ctl00_MainContent_fmwOrder_cardList_1'] ")).getText();
        String card3 = driver.findElement(By.xpath("//table[@id='ctl00_MainContent_fmwOrder_cardList']//label[@for='ctl00_MainContent_fmwOrder_cardList_2'] ")).getText();


        WebElement cardNo= driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6"));
        if (card1.equals("Visa")) {
            cardNo.sendKeys("4" +"" +randomNumber);
        }else if (card2.equals("MasterCard")) {
            cardNo.sendKeys("5" +"" +randomNumber);
        } else if (card3.equals("American Express")) {
            cardNo.sendKeys("3" + ""+randomNumber);
        }


        //13. Enter a valid expiration date
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox1")).sendKeys("06/27", Keys.ENTER);

        //14. Click on Process
        driver.findElement(By.xpath("//div[@class='buttons_process']//a[@id='ctl00_MainContent_fmwOrder_InsertButton']")).click();

        //15. Verify "New order has been successfully added"
       String orderVerification= driver.findElement(By.xpath("//div[@class='buttons_process']//strong['New order has been successfully added.']")).getText();
        System.out.println(orderVerification);

        //16. Click on View All Orders link
        driver.findElement(By.xpath("//*[@id=\"ctl00_menu\"]/li[1]/a")).click();

        //17.  Verify that the entire information contained on the row  matches the previously entered information in previous steps
        List<String> expectedProductNames= List.of(" ","Name","Product","#","Date","Street","City","State","Zip","Card","Card Number","Exp"," ");
        List<WebElement> actualP= driver.findElements(By.xpath("//table[@class='SampleTable']//tr//th"));

        List<String> actualProducts=actualP.stream()
                .map(s -> s.getText())
                .collect(Collectors.toList());

//        Assert.assertEquals(actualP, expectedProductNames);
        System.out.println(actualProducts.stream().toList());

        //18. Logout
        driver.findElement(By.id("ctl00_logout")).click();








    }

}

