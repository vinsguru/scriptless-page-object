package com.testautomationguru.scriptless;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataMap {

    private static final String TEMPLATE_EXPRESSION = "\\$\\{([^}]+)\\}";
    private static final Pattern TEMPLATE_EXPRESSION_PATTERN = Pattern.compile(TEMPLATE_EXPRESSION);

    //json element names and values with ${..}
    final Map<String, String> elementsNameValueMap;

    //csv test data for a row
    final Map<String, String> csvRecord;

    public DataMap(Map<String, String> elementsNameValueMap, Map<String, String> csvRecord){
        this.elementsNameValueMap = elementsNameValueMap;
        this.csvRecord = csvRecord;

        //this replaces ${..} with corresponding value from the CSV
        this.updateMapWithValues(elementsNameValueMap);
    }

    //this map contains elements names and actual values to be entered
    public Map<String, String> getElementsNameValueMap(){
        return this.elementsNameValueMap;
    }

    private void updateMapWithValues(final Map<String, String> variablesMapping){
        variablesMapping
                .entrySet()
                .stream()
                .forEach(e -> e.setValue(updateTemplateWithValues(e.getValue())));
    }

    private String updateTemplateWithValues(String templateString){
        Matcher matcher = TEMPLATE_EXPRESSION_PATTERN.matcher(templateString);
        while(matcher.find()){
            templateString = this.csvRecord.get(matcher.group(1));
        }
        return templateString;
    }

}
