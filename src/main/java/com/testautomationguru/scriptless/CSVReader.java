package com.testautomationguru.scriptless;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CSVReader {

    public static Stream<CSVRecord> getRecords(final String filename) throws IOException {
        Reader in = new InputStreamReader(new FileInputStream(new File(filename)));
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
        Stream<CSVRecord> stream = StreamSupport.stream(records.spliterator(), false);
        return stream;
    }

}
