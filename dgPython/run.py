from utils import *
import random
random.seed(10)


def main(config, data_input):
    option = input("Enter you option\na. Run by number of process\nb. Run by number of days\nc. Run by Dates:  ")
    output_data = None
    if(option=="a"):
        n_process = int(input("Enter number of processes: ") )
        output_data = generate_processes(local=data_input, n_process=n_process)
    elif(option=="b"):
        #################################################
        n_days = int(input("Enter number of days: ") )
        output_data = generate_processes(local=data_input, n_days=n_days)
        ##################################################
    elif(option=="c"):
        start_date = input("Enter start date like YYY-MM-DD ")
        end_date   = input("Enter end date like YYY-MM-DD ")
        start_date = time_parser(start_date + " 20:00:00")
        end_date = time_parser(end_date + " 00:00:00")
        output_data = generate_processes(local=data_input, dates=[start_date, end_date])
    else:
        print("\nSorry invalid input please Enter like this: \na for number of process\nb for number of days\nc for Run by Dates")
    if(output_data is not None):
        print("Aggregating the data")
        result = merg_entries(output_data)
        print("Writing to file")
        result.to_excel("result.xlsx", index=False)
        print("Done writing to file")
    else:
        print("Generaring processes failed!")


data = pd.read_excel("DataSampleUpdated.xlsx")

data_input = {k.strip().replace("_ ", "_"):[i for i in v if str(i)!="nan"] for k, v in data.to_dict(orient="list").items()}

f = open('config.json', "r")
config = json.load(f)
f.close()


# start = 2010-01-16
# end = 2010-01-17

start = datetime.datetime.now()
main(config, data_input)
end = datetime.datetime.now()
print("elapsed time:", str( end-start ) )




