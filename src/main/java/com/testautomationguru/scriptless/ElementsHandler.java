package com.testautomationguru.scriptless;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class ElementsHandler {

    private static final Map<String, BiConsumer<List<WebElement>, String>> map = new HashMap<>();

    //entering text box
    //we want only first element
    public static final BiConsumer<List<WebElement>, String> TEXT_HANDLER = (elements, value) -> {
        elements.get(0).sendKeys(value);
    };

    //radio button selection
    //iterate all the elements - click if the value matches
    public static final BiConsumer<List<WebElement>, String> RADIO_HANDLER = (elements, value) -> {
        elements.stream()
                .filter(ele -> ele.getAttribute("value").equals(value))
                .forEach(WebElement::click);
    };

    //checkbox selection
    //iterate all the elements - click all the elements if the value is present in the list
    public static final BiConsumer<List<WebElement>, String> CHECKBOX_HANDLER = (elements, value) -> {
        List<String> list = Arrays.asList(value.split("\t"));
        elements.stream()
                .filter(ele -> list.contains(ele.getAttribute("value")))
                .forEach(WebElement::click);
    };

    //dropdown selection
    //convert webelement to select
    private static final BiConsumer<List<WebElement>, String> SELECT_HANDLER = (element, value) -> {
        Select select = new Select(element.get(0));
        select.selectByValue(value);
    };

    //store all the above all elements handlers into a map
    static{
        map.put("input#text", TEXT_HANDLER);
        map.put("input#radio", RADIO_HANDLER);
        map.put("input#checkbox", CHECKBOX_HANDLER);
        map.put("select#select-one", SELECT_HANDLER);
        map.put("textarea#textarea", TEXT_HANDLER);
    }

    //handle element
    public static void handle(List<WebElement> elements, String value){
        String key = elements.get(0).getTagName() + "#" + elements.get(0).getAttribute("type");
        map.getOrDefault(key, TEXT_HANDLER).accept(elements, value);
    }


}
