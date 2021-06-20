import io
import re

patternx = r'<U\+(.*?)>'
patternx1 = r'<U\+(....)>'
patternx2 = r'<U\+(........)>'

f_out_fix = io.open('2019-subset\germeval2019.training_subtask1_2_korrigiert_FIXED.txt', mode="a", encoding="utf-8")

with io.open("germeval2019.training_subtask1_2.txt", mode="r", encoding="utf-8") as f_in:
    lines = f_in.readlines()

def replaceMatch4(matched_obj):
    emo = f'\\u{matched_obj.group(1)}'
    return emo.encode('ASCII').decode('unicode-escape')

def replaceMatch8(matched_obj):
    emo = f'\\U{matched_obj.group(1)}'
    return emo.encode('ASCII').decode('unicode-escape')

def replaceEmojiCodes(lines):
    for line in lines:
        splittedLine = line.split('\t')

        tweet = splittedLine[0]
        
        tweet = re.sub(patternx1, replaceMatch4, tweet)
        tweet = re.sub(patternx2, replaceMatch8, tweet)

        encodedLine = tweet + '\t' + splittedLine[1] + '\t' + splittedLine[2]

        f_out_fix.write(encodedLine)


replaceEmojiCodes(lines)
