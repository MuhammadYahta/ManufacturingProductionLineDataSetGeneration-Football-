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


# Data Accessibility
The datafiles listed and queries listed in the paper can be accessed from here (https://doi.org/10.5281/zenodo.7779522).

# Usage
To replicate the results, you can download any of the owl files and upload them to a virtuoso server. If you don't have a virtuoso server on your system, you would need to install one first. Once the dataset is uploaded to the virtuoso server, you can copy and paste the queries into the query section to obtain the desired results. Alternatively, you can import the data file in python or java and convert the queries to the corresponding programming language syntax and execute it to replicate the results.

# Use case
Recently, this dataset is used to analyze the performance of the five state-of-the-art KG embedding models, namely ComplEx, DistMult,TransE, ConvKB, and ConvE. The models results are evaluated using two key metrics: Mean Reciprocal Rank (MRR), and Hits@N (Hits@10, Hits@3, and Hits@1). It is observed that the TransE model outperforms other models, followed by ComplEx and DistMult, with ConvE demonstrating the lowest performance. The dataset can be used alternatively in other potential scenarios.

<img src="https://github.com/MuhammadYahta/ManufacturingProductionLineDataSetGeneration-Football-/blob/main/t-test results.jpg?sanitize=true">
