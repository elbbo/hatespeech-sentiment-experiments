import io
import random


f_out_no_label = io.open('2019-subset\subset.2019.training.no_label.txt', mode="a", encoding="utf-8")
f_out_orginial_format = io.open('2019-subset\subset.2019.training.txt', mode="a", encoding="utf-8")
f_out_orginial_format_with_id = io.open('2019-subset\subset.2019.training.with_ids.txt', mode="a", encoding="utf-8")

with io.open("germeval2019.training_subtask1_2_korrigiert_FIXED.txt", mode="r", encoding="utf-8") as f_in:
    lines = f_in.readlines()

def prepareSubset(lines): 
    indexOffense = 0
    indexOther = 0
    resultData = []
    for line in lines:
        splittedLine = line.split('\t')
        if splittedLine[1] == 'OFFENSE' and indexOffense < 1000:
            indexOffense = indexOffense + 1
            resultData.append(line)
        
        if splittedLine[1] == 'OTHER' and indexOther < 500:
            indexOther = indexOther + 1
            resultData.append(line)
    
    return resultData

def writeDataToFile(tweetData):
    for i, resultLine in enumerate(tweetData, start=1):
        f_out_orginial_format.write(resultLine)
        
        tweet_with_id = "{0} {1}".format(i, resultLine)
        f_out_orginial_format_with_id.write(tweet_with_id)
        
        splittedLine = resultLine.split('\t')
        f_out_no_label.write(splittedLine[0] + '\n')




resultSet = prepareSubset(lines)
random.shuffle(resultSet)
writeDataToFile(resultSet)