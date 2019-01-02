package com.github.wesleyegberto.alura.recommendationmahout.simpleexample;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

/**
 * Builder que constrói um recomendador com base no comportamento de
 * usuários que tenham pelo menos um item igual.
 *
 * A nota que o usuário deu para o produto não importa (preferência), importa apenas a presença desta nota (GenericBooleanPrefItemBasedRecommender).
 * Caso queira considerar um pouco mais a preferência (nota) então retornar instância de GenericItemBasedRecommender.
 */
public class ItemSimilarityRecommenderBuilder implements RecommenderBuilder {
    public Recommender buildRecommender(DataModel model) throws TasteException {
        ItemSimilarity similarity = new TanimotoCoefficientSimilarity(model);

        // return new GenericItemBasedRecommender(model, similarity);
        return new GenericBooleanPrefItemBasedRecommender(model, similarity);
    }
}