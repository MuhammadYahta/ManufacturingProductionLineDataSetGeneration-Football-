##Muhammad Yahya and Muhammad Intizar Ali###
###m.yahya1@nuigalway.ie###
###intizar.ali@dcu.ie###

import numpy as np
import pandas as pd
import json
import datetime
from datetime import timedelta
from tqdm import tqdm
import random
random.seed(10)


def time_parser(t):
    try:
        d1 = datetime.datetime.strptime(t, '%Y-%m-%dT %H:%M:%S%fZ')
        micro = int(str(d1.second)+str(d1.microsecond)[:1])
        d1 = d1.replace(second= micro)
        d1 = d1.replace(microsecond=0)
    except:
        d1 = datetime.datetime.strptime(t, '%Y-%m-%d %H:%M:%S')
    return d1


def merg_entries(data_list):
    dfs = []
    offset = 0
    for new_entry1 in data_list:
        df = pd.DataFrame.from_dict(new_entry1, orient='index')
        df = df.T
        df = pd.DataFrame(df.values[:12], columns=df.columns)
        cols = list(df.columns)

        data = []
        delta = 3
        for col in cols:
            data.append(  list(df[col])+[None for i in range(delta)] )

        df1 = pd.DataFrame()
        for name, col in zip(cols, data):
            df1[name]=pd.Series(col)

        for name in list(df1.columns)[41:48]:
            df1[name] = pd.Series([np.nan]+list(df1[name]) )

        for name in list(df1.columns)[48:74]:
            df1[name] = pd.Series([np.nan, np.nan]+list(df1[name]) )

        for name in list(df1.columns)[74:94]:
            ls = ([np.nan, np.nan, np.nan, np.nan]+list(df1[name]))
            df1[name] = pd.Series( ls )

        for name in list(df1.columns)[94:]:
            ls = ([np.nan, np.nan, np.nan, np.nan, np.nan, np.nan, np.nan]+list(df1[name]))
            df1[name] = pd.Series( ls )
        dfs.append(df1)
    result = pd.concat(dfs[offset:])
    return result




safety =  timedelta(seconds=1)


def generate_m1(local, new_entry, by_dates=None):
    delta_m1  = timedelta(minutes=2.5)
    new_entry["M1_Start_time"]=[str(time_parser(local["M1_finish_time"][0])+safety)]
    new_entry["M1_finish_time"]=[str(time_parser(local["M1_finish_time"][0])+safety+delta_m1)]
    
def generate_m2(local, new_entry, first_time=False):
    M2_start_time = [time_parser(i)  for i in local["M2_start_time"] ]
    M2_Finish_time = [time_parser(i) for i in local["M2_Finish_time"] ]

    for k, v in zip(M2_start_time, M2_Finish_time):
        delta = v-k
        ########################################################################
        if(first_time==True):
            if("M2_start_time" not in new_entry):
                d1 = time_parser(local["M1_Start_time"][0])
                d2 = time_parser(local["M1_finish_time"][0])
                new_entry["M2_start_time"] = [str(time_parser(local["M1_finish_time"][0])+(d2-d1)+safety)]
                new_entry["M2_Finish_time"] =[str(time_parser(new_entry["M2_start_time"][0])+delta) ]
            else:
                new_entry["M2_start_time"].append(str(time_parser(new_entry["M2_Finish_time"][-1])+safety ))
                new_entry["M2_Finish_time"].append(str(time_parser(new_entry["M2_start_time"][-1])+delta ))
        ########################################################################
        elif(first_time==False):
            if("M2_start_time" not in new_entry):
                new_entry["M2_start_time"] = [str(time_parser(local["M2_Finish_time"][-1])+safety)]
                new_entry["M2_Finish_time"] =[str(time_parser(new_entry["M2_start_time"][-1])+delta) ]
            else:
                new_entry["M2_start_time"].append(str(time_parser(new_entry["M2_Finish_time"][-1])+safety ))
                new_entry["M2_Finish_time"].append(str(time_parser(new_entry["M2_start_time"][-1])+delta ))


def generate_m3(local, new_entry):
    delta_m3 = timedelta(minutes=0.5)
    new_entry["M3_Start_Time"] = [str(time_parser(i)+safety) for i in new_entry["M2_Finish_time"][-6:]]
    
    new_entry["M3_Finish_time"] = [str(time_parser(i)+delta_m3) for i in new_entry["M3_Start_Time"]]
def generate_m4(local, new_entry):
    delta_m4 = timedelta(minutes=0.25)
    new_entry["M4_Start_time"] = [str(time_parser(i)+safety) for i in new_entry["M3_Finish_time"][-4:]]

    new_entry["M4_Finish_time"] = [str(time_parser(i)+delta_m4) for i in new_entry["M4_Start_time"]]
def generate_m5(local, new_entry):
    delta_m5 = timedelta(seconds=71)
    new_entry["M5_Start_time"] = [str(time_parser(new_entry["M4_Finish_time"][-1])+safety)]
    new_entry["M5_Finish_time"] = [str(time_parser(new_entry["M5_Start_time"][0])+delta_m5)]

#######################################################
def generate_m6(local, new_entry):
    for _ in range(4):
        delta = timedelta(seconds=29)
        if("M6_Start_time" not in new_entry):
            if("M5_Finish_time" in new_entry):
                new_entry["M6_Start_time"] = [str(time_parser(new_entry["M5_Finish_time"][0])+safety)]
                new_entry["M6_Finish_time"] =[str(time_parser(new_entry["M6_Start_time"][0])+delta) ]
        else:
            new_entry["M6_Start_time"].append(
            str(time_parser(new_entry["M6_Finish_time"][-1])+safety ))
            new_entry["M6_Finish_time"].append(str(time_parser(new_entry["M6_Start_time"][-1])+delta ))
#######################################################
def generate_m7(local, new_entry):
    for _ in range(4):
        delta = timedelta(seconds=75)
        if("M7_Start_time" not in new_entry):
            if("M6_Finish_time" in new_entry):
                new_entry["M7_Start_time"] = [str(time_parser(new_entry["M6_Finish_time"][0])+safety)]
                new_entry["M7_Finish_time"] =[str(time_parser(new_entry["M7_Start_time"][0])+delta) ]
        else:
            new_entry["M7_Start_time"].append(
            str(time_parser(new_entry["M7_Finish_time"][-1])+safety ))
            new_entry["M7_Finish_time"].append(str(time_parser(new_entry["M7_Start_time"][-1])+delta ))
##########################################################
def generate_m8(local, new_entry):
    delta_m8 = timedelta(seconds=59)
    new_entry["M8_Start_time"] = [str(time_parser(i)+safety) for i in new_entry["M7_Finish_time"]]

    new_entry["M8_Finish_time"] = [str(time_parser(i)+delta_m8) for i in new_entry["M8_Start_time"]]
##########################################################
def generate_m9(local, new_entry):
    delta_m9 = timedelta(seconds=15)
    new_entry["M9_start_time"] = [str(time_parser(i)+safety) for i in new_entry["M8_Finish_time"]]
    new_entry["M9_finish_time"] = [str(time_parser(i)+delta_m9) for i in new_entry["M9_start_time"]]
def generate_master(local, new_entry):
    if("M9_finish_time" not in new_entry):
        print("error first compute 'M9_finish_time'")
    else:
        new_entry["Lead(Start_time)"] = new_entry["M1_Start_time"]
        new_entry["Lead(Finish_time)"] = [new_entry["M9_finish_time"][-1]]
        
def add_m1_details(local, new_entry, count=0):
    new_entry["machine1_Input"]    = [local["machine1_Input"][0].replace("_1", "")]
    new_entry["machine1_name"]     = local["machine1_name"]
    new_entry["machine1_Model"]    = local["machine1_Model"]
    new_entry["machine1_Id"]       = local["machine1_Id"]
    new_entry["M1_location"]       = local["M1_location"]
    new_entry["Machine1_Laser"]    = local["Machine1_Laser"]
    #new_entry["Machine1_process"]  = [ "_".join(local["Machine1_process"][0].split("_")[:2])+"_"+str(count)  ]
    new_entry["Machine1_process"]  = local["Machine1_process"]
    new_entry["machine1_motor"]    = local["machine1_motor"]
    new_entry["M1_motor_Model"]    = local["M1_motor_Model"]
    ###################################################
    new_entry["machine1_motor_RPM"]    = [str(3197+np.random.choice(7, 1)[0])]
    new_entry["machine_1_voltage"]= [str(218+np.random.choice(6, 1)[0])+" V"]
    
    new_entry["machine1_laser_power"]= [str(87+np.random.choice(6, 1)[0])+" Watt"]
    new_entry["machine_1_cutting_speed"]= [str(1199+np.random.choice(2, 1)[0])+" mm/sec"]
    new_entry["machine_1_temperature"]= [str(38+np.random.choice(4, 1)[0])+" celcius"]
    ###################################################
    new_entry["machine1_Temp_sensor"]    = local["machine1_Temp_sensor"]
    if( int(new_entry["machine1_motor_RPM"][0].split()[0])<3190):
        new_entry["machine1_motor_state"]    = ["Fault"]
    else:
        new_entry["machine1_motor_state"]    = ["Working"]
        
    new_entry["machine1_Output"]  = [ local["machine1_Output"][0].replace("_1", "_"+str(count))  ]


def add_bed(local, new_entry, col_names):
    new_entry[col_names[0]]  = local[col_names[0]]
    new_entry[col_names[1]]  = local[col_names[1]]
    d = [old_rec.split()[0].split(".") for old_rec in local[col_names[2]]]
    new_entry[col_names[2]]= [old_rec[0]+"." + str((int(old_rec[1])-2)
                                                       +np.random.choice(4, 1)[0])+" Watt" for old_rec in d]
    new_entry[col_names[3]]= [str((int(old_rec.split()[0])-2)
                                             +np.random.choice(4, 1)[0])+ " "+old_rec.split()[1]
                                         for old_rec in  local[col_names[3]]]
    new_entry[col_names[4]]= [str((int(old_rec.split()[0])-2)
                                             +np.random.choice(5, 1)[0])+ " "+old_rec.split()[1]
                                         for old_rec in  local[col_names[4]]]
    d = [old_rec.split()[0] for old_rec in local[col_names[5]]]
    new_entry[col_names[5]]= [str(np.round(float(old_rec.strip())-0.1
                                                       +np.random.choice(5, 1)[0]/10 , 3) )+" bar" for old_rec in d]
    new_entry[col_names[6]]= [str((int(local[col_names[6]][i].split()[0].strip())-2)
                                         +np.random.choice(4, 1)[0])+" celcius" for i in range(6)]
    #### end #######
    
def add_m2_details(local, new_entry):
    new_entry["Machine2_Process2"]  = ["Oval_Printning_Process_"+str(i) for i in range(1, 9)  ]
    for name in ["machine2_Name", "machine2_Input", "machine2_model",
                 "M2_motor_status", "m2_motor_Name", "m2_motor_Model", "machine2_id","M2_Location"]:
        new_entry[name]  = local[name]
    new_entry["machine2_motor_speed"]  = ["1 RPM" for i in range(8)]
    ################################################ bed 1 ######################
    col_names = ["machine2_bed1", "m2_Squeegee1", "m2_Squeegee1_power", "m2_Squeegee1_hardness",
             "m2_Squeegee1_Ink_viscosity", "m2_Squeegee1_Pressure", "m2_heatear1_temp"]
    add_bed(local, new_entry, col_names)
    ####################################### bed 2 ##############################
    col_names = ["ma2_bed2", "machine2_Squeegee2", "Squeegee2_power", "Squeegee2_rubber_hardness",
                "m2_Squeegee2_Ink_viscosity", "m2_Squeegee2_Pressure", "m2_heater2_temp"
                ]
    add_bed(local, new_entry, col_names)
    ####################################### bed 3 ##############################
    col_names = ["m2_bed3","m2_Squeegee3","m2_Squeegee3_power",
                "m2_Squeegee3_hardness", "m2_Squeegee3_Ink_viscosity","m2_Squeegee3_Pressure","m2_heater3"]
    add_bed(local, new_entry, col_names)
    new_entry["M2_output"]  = local["M2_output"]
    
def add_m3_details(local, new_entry):
    new_entry["M3_machine_Name"]   = local["M3_machine_Name"]
    new_entry["M3_machine_model"]  = local["M3_machine_model"]
    new_entry["M3_machine_ID"]     = local["M3_machine_ID"]
    new_entry["M3_Location"]       = local["M3_Location"]
    new_entry["M3_Process"]        = local["M3_Process"]
    new_entry["M3_input"]          = local["M3_input"]
    new_entry["M3_bed"]            = local["M3_bed"]
    new_entry["M3_Die_Name"]       = local["M3_Die_Name"]
    
    new_entry["M3_die_Frequency"]= [str(2998+np.random.choice(4, 1)[0])
                                    +" "+local["M3_die_Frequency"][i].split()[1] for i in range(6)]
    new_entry["M3_Die_temperature"]= [str(94+np.random.choice(4, 1)[0])
                                     +" "+local["M3_Die_temperature"][i].split()[1] for i in range(6)]
    
    new_entry["M3_Die_Temp_Sensor"]       = local["M3_Die_Temp_Sensor"]
    d = [old_rec.split()[0] for old_rec in local["M3_Power"]]
    new_entry["M3_Power"]= [str(np.round(4.1+np.random.choice(4, 1)[0]/10 , 3) )+" Watt" for old_rec in d]
    new_entry["M3_Cutting_pressure"]= [str((int(local["M3_Cutting_pressure"][i].split()[0].strip())-2)
                                         +np.random.choice(4, 1)[0])
                                     +" "+local["M3_Cutting_pressure"][i].split()[1] for i in range(6)]
    
    new_entry["M3_Cutting_pressure"]= [str(6+np.random.choice(2, 1)[0])
                                     +" "+local["M3_Cutting_pressure"][i].split()[1] for i in range(6)]
    
    d = [old_rec.split()[0] for old_rec in local["M3_Cutting_Power"]]
    new_entry["M3_Cutting_Power"]= [str(np.round(3.0+np.random.choice(7, 1)[0]/10 , 3) )+" Watt" for old_rec in d]
    for name in "M3_output,Manual_Process,Operator,Manual_process1,Operator,M4_Name,M4_Model,M4_iD,M4_location,Machine_4_Process".split(","):
        new_entry[name] = local[name]

def add_m4_details(local, new_entry):
    for name in "M4_input1,M4_input2,M4_input3".split(","):
        new_entry[name] = local[name]
    
    d = [old_rec.split()[0] for old_rec in local["M4_Spraying Needle diameter"]]
    new_entry["M4_Spraying Needle diameter"]= [str(np.round(0.5+np.random.choice(2, 1)[0]/10 , 3) )+" mm" for old_rec in d]
    
    
    new_entry["M4_glue_amount"]= [str(np.round(0.44+np.random.choice(2, 1)[0]/100 , 3) 
                                     )+ " mm" for old_rec in local["M4_glue_amount"]]
    
    new_entry["M4_Motor"] = local["M4_Motor"]
    new_entry["M4_Motor_Model"] = local["M4_Motor_Model"]
    new_entry["M4_Motor_speed"]= [str(199+np.random.choice(3, 1)[0])+ " RPM" for old_rec in local["M4_Motor_speed"]]
    new_entry["M4_motor_Status"]= local["M4_motor_Status"]
    new_entry["M4_Power"]= [str(5+np.random.choice(2, 1)[0])+" "+old_rec.split()[1] for old_rec in local["M4_Power"]]
    
    new_entry["M4_output"] = local["M4_output"]

def add_m5_details(local, new_entry):
    for name in ['M5_Name','M5_Model','M5_ID','M5_location','M5_Process','M5_input']:
        new_entry[name] = local[name]
    
    new_entry["M5_Temperature"]= [str(49+np.random.choice(2, 1)[0]
                                     )+" "+old_rec.split()[1] for old_rec in local["M5_Temperature"]]
    
    for name in ['M5_motor_Name',"M5_motor_Model","M5_motor_Status"]:
        new_entry[name.strip()] = local[name]
    
    new_entry["M5_motor_speed"]= [str(19+np.random.choice(3, 1)[0]
                                     ) for old_rec in local["M5_motor_speed"]]
    new_entry["M5_power"]= [str(3+np.random.choice(2, 1)[0]
                                     )+" "+old_rec.split()[1] for old_rec in local["M5_power"]]
    
    new_entry["M5_Output"] = local["M5_Output"]

def add_m6_details(local, new_entry):
    for name in ["M6_Name","M6_model","M6_ID","M6_Location","M6_Process"]:
        new_entry[name] = local[name]
    
    new_entry["M6_input"] = local["M6_input"]
    for name in ["M6_Mold_die","M6_temperture","M6_Mold_pressure","M6_power"]:
        new_entry[name]= [str((int(old_rec.split()[0]))+np.random.choice(2, 1)[0]
                                     )+" "+old_rec.split()[1] for old_rec in local[name]]
    
    for name in ["M6_Pressure_Sensor","M6_output"]:
        new_entry[name] = local[name]
    
def add_m7_details(local, new_entry):
    for name in ["Machine7_name","Machine7_Model","M7_location","M7_Input"]:
        new_entry[name] = local[name]
    for name in ['M7_Temperature',"M7_Die_size","M7_pressure","M7_Power"]:
        new_entry[name]= [str((int(old_rec.split()[0])-1)+np.random.choice(2, 1)[0]
                                     )+" "+old_rec.split()[1] for old_rec in local[name]]
    for name in ["M7_output","m7_process"]:
        new_entry[name] = local[name]

def add_m8_details(local, new_entry):
    for name in ["Machine8_Name","Machine8_model","Machine8_ID","Machine8_Location","M8_input","M8_process"]:
        new_entry[name] = local[name]
    
    new_entry["M8_temperature"]= [str((int(old_rec)-1)+np.random.choice(2, 1)[0]) for old_rec in local["M8_temperature"]]
    
    d = [old_rec.split() for old_rec in local["M8_spraying_needle_diameter"]]
    new_entry["M8_spraying_needle_diameter"]= [str(np.round(float(old_rec[0].strip())-0.1
                                   +np.random.choice(3, 1)[0]/10 , 3)
                                 )+" "+old_rec[1] for old_rec in d]
    
    new_entry["M8_glue_amount"]= [str(np.round(float(old_rec)-0.1
                                   +np.random.choice(3, 1)[0]/10 , 3)
                                 ) for old_rec in local["M8_glue_amount"]]
    new_entry["M8_power"]= [str((int(old_rec)-1)+np.random.choice(2, 1)[0]) for old_rec in local["M8_power"]]
    for name in ["M8_motor_Name","M8_motor_Model","M8_model_Status"]:
        new_entry[name] = local[name]
    new_entry["M8_Motor_speed"]= [str((int(old_rec)-1)+np.random.choice(2, 1)[0]) for old_rec in local["M8_Motor_speed"]]
    new_entry["M8_Output"] = local["M8_Output"]

def add_m9_details(local, new_entry):
    for name in ["M9_Name","M9_Model","M9_ID","M9_Location",'M9_Heat_drying_coveyor_Process']:
        new_entry[name] = local[name]
        
    new_entry["M9_Temperature"]= [str((int(old_rec.split()[0])-2)+np.random.choice(4, 1)[0]
                                     )+" "+old_rec.split()[1] for old_rec in local["M9_Temperature"]]
    new_entry["M9_power"]= [str((int(old_rec.split()[0])-1)+np.random.choice(2, 1)[0]
                                     )+" "+old_rec.split()[1] for old_rec in local["M9_power"]]
    for name in ["M9_motor_Name","M9_motor_model","M9_motor_Status"]:
        new_entry[name] = local[name]
    
    new_entry["M9_RPM"]= [str(np.round(float(old_rec)-0.1
                                   +np.random.choice(3, 1)[0]/10 , 3)
                                 ) for old_rec in local["M9_RPM"]]
    
    new_entry['M9_inout'] = local["M9_inout"]
    new_entry['M9_output'] = local["M9_output"]
    
def add_manually_process(local, new_entry):
    for name in ["Manual_process2","Operator_Name","Manual_process3","Operator_Name2","Manual_process4","Operator_Name3"]:
        new_entry[name] = local[name]



def generate_entry(local, new_entry, new_entry1, count, by_dates=None, first_time=False):
    ####################################
    generate_m1(local, new_entry, by_dates)
    add_m1_details(local, new_entry, count=count)
    generate_m2(local, new_entry, first_time=first_time)
    add_m2_details(local, new_entry)
    generate_m3(local, new_entry)
    add_m3_details(local, new_entry)
    generate_m4(local, new_entry)
    add_m4_details(local, new_entry)
    generate_m5(local, new_entry)
    add_m5_details(local, new_entry)
    generate_m6(local, new_entry)
    add_m6_details(local, new_entry)
    generate_m7(local, new_entry)
    add_m7_details(local, new_entry)
    generate_m8(local, new_entry)
    add_m8_details(local, new_entry)
    generate_m9(local, new_entry)
    add_m9_details(local, new_entry)
    add_manually_process(local, new_entry)
    generate_master(local, new_entry)
    names = [i.strip().replace("_ ", "_") for i in local.keys()]
    for n in names:
        if(n not in new_entry):
            continue
        else:
            new_entry1[n] = new_entry[n]
    return new_entry1
    ####################################


def generate_processes(local=None, n_process=None, n_days=None, dates=None):
    output_data = []
    if(n_process is not None):
        for i in tqdm(range(1, n_process+1)):
            new_entry = {}
            new_entry1 = {"Master_Process":['football_Production_Process_'+str(i)]}
            if(i>1):
                local = output_data[-1]
                entry = generate_entry(local, new_entry, new_entry1, count=i, first_time=False)
            else:
                entry = generate_entry(local, new_entry, new_entry1, count=i, first_time=True)
            output_data.append(entry)
    elif(n_days is not None):
        ##################################
        i=0
        completed=False
        while(completed is False):
            i+=1
            new_entry = {}
            new_entry1 = {"Master_Process":['football_Production_Process_'+str(i)]}
            if(i>1):
                local = output_data[-1]
                entry = generate_entry(local, new_entry, new_entry1, count=i, first_time=False)
            else:
                entry = generate_entry(local, new_entry, new_entry1, count=i, first_time=True)
            output_data.append(entry)
            if(len(output_data)>=2):
                start = output_data[0]
                end   = output_data[-1]
                delta = time_parser(end["Lead(Finish_time)"][0]) - time_parser(start["Lead(Start_time)"][0])
                days = int(delta.days)
                if(days>=n_days):
                    completed=True
    elif(dates is not None):
        i=0
        completed=False
        delta1 = dates[1] - dates[0]
        delta2 = time_parser(local["M1_finish_time"][0]) - time_parser(local["M1_Start_time"][0])
        if(delta1<delta2):
            print("Invalid dates ranges too short interval, cant complete a single process:")
            return None
        
        while(completed is False):
            i+=1
            new_entry = {}
            new_entry1 = {"Master_Process":['football_Production_Process_'+str(i)]}
            #################################################################################
            if(i==1):
                safety    =  timedelta(seconds=1)
                delta_m1  = timedelta(minutes=2.5)
                local["M1_Start_time"] = [str(dates[0])]
                local["M1_finish_time"] = [str(dates[0]+safety+delta_m1)]
                entry = generate_entry(local, new_entry, new_entry1, count=i, by_dates=True, first_time=True)
            ##################################################################################
            elif(i>1):
                local = output_data[-1]
                entry = generate_entry(local, new_entry, new_entry1, count=i, by_dates=True, first_time=False)
                
            output_data.append(entry)
            current_end = time_parser(entry["M1_finish_time"][0])
            if(current_end>=dates[1]):
                completed=True
        ##################################
    return output_data
