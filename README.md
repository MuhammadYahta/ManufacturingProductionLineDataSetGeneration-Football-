# A Benchmark Dataset with Knowledge Graph Generation for Industry 4.0 Production Lines
We build a benchmark dataset for knowledge graph generation in Industry 4.0 production lines and to show the benefits of using ontologies and semantic annotations of data to showcase how I4.0 industry can benefit from KGs and semantic datasets. This work is a  result of collaborations with the production line managers, supervisors, and engineers of a football industry to acquire realistic production line data. Knowledge Graphs (KGs) or a Knowledge Graph (KG) emerged as a significant technology to store the semantics of the domain entities. The data is mapped and populated with RGOM classes and relations using an automated solution based on JenaAPI, producing an I4.0 KG.

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

The datafile can be accessed from here (https://zenodo.org/deposit/7779522)

# Usage
Recently, we use this dataset  to analyze the performance of the five state-of-the-art KG embedding models, namely ComplEx, DistMult,TransE, ConvKB, and ConvE. We evaluate the models using two key metrics: Mean Reciprocal Rank (MRR), and Hits@N (Hits@10, Hits@3, and Hits@1). We observed that the TransE model outperforms other models, followed by ComplEx and DistMult, with ConvE demonstrating the lowest performance. The dataset can be used alternatively in other potential scenarios.

<img src="https://github.com/MuhammadYahta/ManufacturingProductionLineDataSetGeneration-Football-/blob/main/t-test results.jpg?sanitize=true">
