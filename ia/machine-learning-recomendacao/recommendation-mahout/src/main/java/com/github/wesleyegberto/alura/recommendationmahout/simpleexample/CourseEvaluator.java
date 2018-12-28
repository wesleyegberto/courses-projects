package com.github.wesleyegberto.alura.recommendationmahout.simpleexample;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.common.RandomUtils;

import java.io.IOException;

public class CourseEvaluator {
    public static void main(String[] args) throws IOException, TasteException {

        // Usar valor fixo - assim nosso teste sempre será em cima da mesma massa de 10%
        RandomUtils.useTestSeed();

        FileDataModel model = DataModelCreator.getDataModelFromFile("cursos.csv");

        // Avaliador pela média absolute de erro
        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();

        RecommenderBuilder builder = new UserSimilarityRecommenderBuilder();

        // 90% para usar no treino e 100% para usar na avaliação
        double errorScore = evaluator.evaluate(builder, null, model, 0.9, 1.0);
        // o score de erro tem a mesma escala da notas dos produtos
        System.out.println(errorScore);
    }
}
