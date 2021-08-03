# Analysis of Group and Person Address Annotations and negative Sentiments as potential Hate Speech Indicators
This repository contains the programs for the data processing and experiments of the master thesis "Annotation and analysis of group and person address and negative sentiments as potential hate speech indicators", which was conducted at the University of Duisburg-Essen. It includes the programs for the creation of the underlying dataset which was loaded into the tool [INCEpTION](https://github.com/inception-project/inception) for an annotation with the proposed annotation scheme. The data used for the annotations and experiments was derived from the [Germeval Task 2, 2019 - Shared Task on the Identification of Offensive Language](https://projects.fzai.h-da.de/iggsa/). In addition, two pipelines are provided. An agreement on the annotations of the respective annotators is determined to validate the reliability of the presented annotation scheme. Furthermore, the surface structure of the resulting dataset is evaluated and the experiments for an automated detection of the scheme are applied.

The annotated files in tsv format can be found at `src\main\resources\tsv-files`. The files used for the experiments in XMI format are located at `src\main\resources\set-2.` The corresponding TypeSystem.xml is stored there as well.

### Prerequisites & Resources
The following items should be installed in your system:
- Java 14+
	- e.g. [adoptopenjdk](https://adoptopenjdk.net/)
- [Maven 3+](https://maven.apache.org/download.cgi)
- [Python 3](https://www.python.org/downloads/) (preprocessing only)

For the determination of the sentiments within the conducted experiments the [Broad-Coverage German Sentiment Classification Model for Dialog Systems](https://github.com/oliverguhr/german-sentiment) was used. Further, this process step was implemented using the library [dkpro-cassis](https://github.com/dkpro/dkpro-cassis). The pipelines for the determination of the agreement, the analysis of the surface structure and the majority of the experiments were implemented through the [DKPro framework](https://dkpro.github.io/).

### Configuration
A pipeline is provided that determines the agreement of the respective coders. The corresponding annotated files are located under `src/main/resources/agreement-*`. 

To generate a new dataset, the following files should first be executed in `\preprocessing\dataset_collection`
- `emoji_string_to_unicode.py` needs to be executed to replace emojis encoded as strings with their corresponding unicode representation (if necessary), and
- `dataset_processor.py` should be executed to prepare the files for the pipeline.

However, it should be noted that these preprocessing was designed particularly for the source dataset.

### Run the Experimets
To run the pipelines load the repository
- `git clone https://github.com/luckybobo/hatespeech-sentiment-experiments.git`. 
- Then execute `App.java` in `src/main/java/en/uni/due/haring/annotation/analyser/`. 

The execution with the provided dataset leads to the results, which are discussed, analyzed and presented in the thesis. The results of the experiments are located at `src/main/resources/results/*`.

#### License
The present project is distributed under [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/deed.de), just like the GermEval dataset on which this thesis is based.



