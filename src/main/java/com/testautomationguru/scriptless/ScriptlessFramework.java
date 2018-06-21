package com.testautomationguru.scriptless;

import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import supplier.DriverFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static supplier.DriverType.CHROME;

public class ScriptlessFramework {

    private static final String PAGE_OBJECT_JSON_PATH = "page-objects/patient-information-page.json";
    private static final String TEST_DATA_CSV_PATH = "test-data/patient-information-data.csv";

    public static void main(String[] args) throws IOException {

        //get csv data
        Stream<CSVRecord> records = CSVReader.getRecords(TEST_DATA_CSV_PATH);

        //csv records
        records
                .map(CSVRecord::toMap)  //convert each row to a map of key value pair - contains column name and row value
                .map(csvMap -> new DataMap(PageObjectParser.parse(PAGE_OBJECT_JSON_PATH), csvMap)) //feed page object map and csv row map to get the page object element name and actual value
                .map(DataMap::getElementsNameValueMap) // get the map which contains elements name and actual test data from csv
                .forEach(ScriptlessFramework::accessPageEnterData); //this method is responsible for accessing the page and calling elements handler
    }

    private static void accessPageEnterData(Map<String, String> map){

        //start webdriver
        WebDriver driver = DriverFactory.getDriver(CHROME);
        driver.get("https://form.jotform.com/81665408084158");

        //enter data
        map.entrySet()
                .stream()
                .forEach(entry -> {
                    List<WebElement> elements = driver.findElements(By.name(entry.getKey()));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elements.get(0));
                    ElementsHandler.handle(elements, entry.getValue());
                });

        //quit
        driver.quit();
    }





}
