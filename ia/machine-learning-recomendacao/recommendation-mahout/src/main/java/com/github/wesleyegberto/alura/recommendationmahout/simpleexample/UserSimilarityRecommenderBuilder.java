package com.github.wesleyegberto.alura.recommendationmahout.simpleexample;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * Builder que constrói um recomendados com base no comportamento de
 * usuários com gostos parecidos.
 */
public class UserSimilarityRecommenderBuilder implements RecommenderBuilder {
    public Recommender buildRecommender(DataModel model) throws TasteException {
        // utiliza o Person correlation onde é verificado a similaridade de gostos a partir dos itens que ambos gostam
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        // calculadora dos vizinhos com gostos parecidos
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0, similarity, model);

        return new GenericUserBasedRecommender(model, neighborhood, similarity);
    }
}