package com.github.wesleyegberto.alura.recommendationmahout.simpleexample;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;

import java.io.File;
import java.io.IOException;

public class DataModelCreator {
    public static FileDataModel getDataModelFromFile(String path) throws IOException {
        File file = new File("src/main/resources/" + path);
        return new FileDataModel(file);
    }
}
