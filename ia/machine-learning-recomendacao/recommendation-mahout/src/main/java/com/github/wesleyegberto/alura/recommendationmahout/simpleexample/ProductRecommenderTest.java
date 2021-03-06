package com.github.wesleyegberto.alura.recommendationmahout.simpleexample;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import java.util.List;

public class ProductRecommenderTest {
    public static void main(String[] args) throws Exception {
        FileDataModel model = DataModelCreator.getDataModelFromFile("dados.csv");

        Recommender recommender = new UserSimilarityRecommenderBuilder().buildRecommender(model);

        List<RecommendedItem> recommendations = recommender.recommend(3, 3);
        for (RecommendedItem recommendation : recommendations) {
            System.out.println(recommendation);
        }
    }
}