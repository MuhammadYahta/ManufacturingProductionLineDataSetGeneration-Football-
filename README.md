# Manufacturing Production Line DataSet Generation Football
In order to enable academic researchers to check and validate their tools and techniques, a manufacturing dataset generator is developed to generate a realistic dataset of the production line. 

# Prerequisite
Python

# How to Run DataGenerator to generate data

1. Download the code and Save to a location.

2. Open CLI 

3. Use the following command to navigate to the lcoation where you store source code.
> cd C:\Users\muhyah\dgPython

<img src="https://github.com/MuhammadYahta/ManufacturingProductionLineDataSetGeneration-Football-/blob/main/1 navigate using this command.JPG?sanitize=true">

4. use the following command to run the generator for creating data. when hit enter it will ask three options, i.create the data by number of processes , ii. create data by the number of days, iii. create data between dates
> python run.py
<img src="https://github.com/MuhammadYahta/ManufacturingProductionLineDataSetGeneration-Football-/blob/main/2. run main program.JPG?sanitize=true">

Due to the github and google drive storage limits, only 10 and 20 days KG-based dataset is made available that is accessible via the below google drive link.
https://drive.google.com/drive/folders/15G4tgVheu-gOHg8Ia4VKwF2UgXeZFWzn?usp=sharing

# Usage
Recently, we use this dataset  to analyze the performance of the five state-of-the-art KG embedding models, namely ComplEx, DistMult,TransE, ConvKB, and ConvE. We evaluate the models using two key metrics: Mean Reciprocal Rank (MRR), and Hits@N (Hits@10, Hits@3, and Hits@1). We observed that the TransE model outperforms other models, followed by ComplEx and DistMult, with ConvE demonstrating the lowest performance. The dataset can be used alternatively in other potential scenarios.
