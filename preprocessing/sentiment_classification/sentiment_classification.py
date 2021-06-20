from germansentiment import SentimentModel
from cassis import *

with open('TypeSystem.xml', 'rb') as f:
    typesystem = load_typesystem(f)

with open('anno1-subset.2019.training.1000.xmi', 'rb') as f:
   cas = load_cas_from_xmi(f, typesystem=typesystem)

model = SentimentModel()

PredictedSentiment = typesystem.get_type('de.uni.due.haring.annotation.PredictedSentiment')
predictedSentiments = list()

for sentence in cas.select('de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence'):
    sentences = list()
    sentences.append(sentence.get_covered_text())
    prediction = model.predict_sentiment(sentences)
    predictedSentiments.append(PredictedSentiment(begin=sentence.begin, end=sentence.end, coveredText=sentence.get_covered_text(), Sentiment=prediction[0], NegativeSentiment=prediction[0] == 'negative'))

for predictedSentiment in predictedSentiments:
    cas.add_annotation(predictedSentiment)
    print(predictedSentiment)

for predictedSentiment in cas.select('de.uni.due.haring.annotation.PredictedSentiment'):
    print(predictedSentiment.coveredText)
    print(predictedSentiment.Sentiment)

cas.to_xmi("processed-anno1-subset.2019.training.1000.xmi")