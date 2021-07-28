
using ClosedXML.Excel;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.OleDb;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataGenerator
{
    class Generator
    {
        private int MainHeaderCount = 1;
        string columsnToBeChanged = "Master_Process,Lead(Start_time),Lead(Finish_time),Process1_Start_time,Process1_finish_time,Machine1_process,machine1_ motor_RPM,machine_1_voltage,machine1_laser_power,machine_1_cutting_speed,machine_1_temperature,Machine2_Process2,Lead(start_time),Lead(Finish_time),machine2_bed_motor_power,machine2_bed1,m2_Squeegee1_power,m2_Squeegee1_hardness,m2_Squeegee1_Ink_viscosity,m2_Squeegee1_Pressure,m2_heatear1_temp,ma2_bed2,Machine2_Process2,Lead(start_time),Lead(Finish_time),machine2_bed_motor_power,machine2_bed1,m2_Squeegee1_power,m2_Squeegee1_hardness,m2_Squeegee1_Ink_viscosity,m2_Squeegee1_Pressure,m2_heatear1_temp,ma2_bed2,Squeegee2_power,Squeegee2_rubber_hardness,m2_Squeegee2_Ink_viscosity,m2_Squeegee2_Pressure,m2_heater2_temp,m2_bed3,m2_Squeegee3_power,m2_Squeegee3_hardness,m2_Squeegee3_Ink_viscosity,m2_Squeegee3_Pressure,m2_heater3,M2_output,M3_Process,M3_Start_Time,M3_Finish_time,M3_input,M3_Cutting_Frequency,M3_Die_temperature,M3_Current,M3_Cutting_pressure,M3_Cutting_Power,Machine_4_Process,M4_Start_time,M4_Finish_time,M4_input2,M4_glue_amount,M4_Motor_speed,M4_current,M4_voltage,M5_Process,M5_Start_time,M5_Finish_time,M5_Temperature,M5_motor_speed ,M5_power,M5_Power,M6_Process,M6_Start_time,M6_Finish_time,M6_temperture,M6_Mold_pressure,M6_output,M7_Input,M7_Start_time,M7_Finish_time,M7_Temperature,M7_pressure,M7_Power,M7_output,M8_input,M8_process,M8_Start_time,M8_Finish_time,M8_temperature,M8_glue_amount,M8_power,M8_Motor_speed,M8_Glue_type,M8_Output,M9_Heat_drying_coveyor_Process,M9_start_time,M9_finish_time,M9_Temperature,M9_power,M9_RPM,M9_output";


        private long ticks1 = 0;
        DateTime dayFirstProcessStarts = DateTime.MinValue;

        DateTime start = DateTime.MinValue;
        DateTime end = DateTime.MinValue;

        public void Start(int numberOfProcess)
        {

            DataTable dt = ConvertExcelToDataTable("DataSampleUpdated.xlsx");
            DataTable currentTable = dt.Copy();

            start = DateTime.Parse(currentTable.Rows[0][1].ToString());
            dayFirstProcessStarts = DateTime.Parse(currentTable.Rows[0][1].ToString());
            end = DateTime.Parse(currentTable.Rows[0][2].ToString());
            ticks1 = (end - start).Ticks;

            for (int j = 1; j <= numberOfProcess; j++)
            {
                currentTable = StartAddingRows(dt, currentTable);
            }

            var teemp = dt.Copy();



            for (int i = 0; i < dt.Rows.Count; i += 11)
            {
                dt.Rows[i]["Lead(Finish_Time)"] = dt.Rows[i + 10]["m9_finish_time"].ToString();

            }





            XLWorkbook wb = new XLWorkbook();

            wb.Worksheets.Add(dt, "WorksheetName");
            string fileLocation = ConfigurationSettings.AppSettings["filedestination"].ToString();
            wb.SaveAs(fileLocation);


        }

        int count = 0;

        private DataTable StartAddingRows(DataTable dt, DataTable currentTable)
        {
            MainHeaderCount++;

            DataTable tempTable = dt.Clone(); // copying structure


            for (int i = 0; i < 11; i++)
            {
                DataRow dr = currentTable.Rows[i];

                DataRow newRow = dt.NewRow();
                DataRow newRow2 = dt.NewRow();

                int ColNum = 0;
                foreach (DataColumn dc in currentTable.Columns)
                {

                    string randomdata = ContainsColumns(dc.ColumnName) ? GetRandomData(dr[dc.ColumnName].ToString(), dc.ColumnName, dayFirstProcessStarts, ColNum, count + 12, dt, newRow) : dr[dc.ColumnName].ToString();
                    ColNum++;

                    if (!string.IsNullOrEmpty(randomdata))
                    {
                        if (randomdata.Length >= 21)
                        {
                            newRow[dc.ColumnName] = randomdata;

                            newRow2[dc.ColumnName] = randomdata;

                        }
                        else
                        {
                            newRow[dc.ColumnName] = randomdata;
                            newRow2[dc.ColumnName] = dr[dc.ColumnName].ToString();
                        }
                    }



                }

                dt.Rows.Add(newRow);
                tempTable.Rows.Add(newRow2.ItemArray);
                count++;

            }
            currentTable.Rows.Clear();
            currentTable = tempTable;
            return currentTable;
        }

        public bool ContainsColumns(string colName)
        {
            string[] colums = columsnToBeChanged.ToLower().Split(',');
            bool found = false;

            foreach (string column in colums)
            {
                if (colName.ToLower().Contains(column) || colName.ToLower().Contains("start") || colName.ToLower().Contains("finish"))
                {
                    found = true;
                    return found;
                }
            }
            return found;

        }

        private string GetRandomData(string data, string columnName, DateTime firstTime, int colNum, int rowNum, DataTable tempTable, DataRow dr)
        {
            try
            {

                if (data.Length == 21)
                {
                    DateTime date = DateTime.ParseExact(data, "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture);
                    DateTime utcdate = date.ToUniversalTime();

                    if (columnName.ToLower().Contains("start"))
                    {
                        string colName = string.Empty;

                        if (columnName.ToLower().Contains("lead"))
                        {
                            colName = "m1";
                            colName = "m1";
                            string data1 = GetLastTime(tempTable, rowNum, "M1_finish_time");
                            DateTime finishDate = DateTime.ParseExact(data1, "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture); // DateTime.ParseExact(tempTable.Rows[rowNum - 9][colName + "_finish_time"].ToString(), "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture);
                            finishDate = finishDate.AddSeconds(1);
                            string dateString = finishDate.ToUniversalTime().ToString("yyyy-MM-ddT HH:mm:ssZ"); //2021-01-06T 08:45:23Z
                            return dateString;
                        }

                        else if (columnName.ToLower().Contains("m1"))
                        {
                            colName = "m1";
                            colName = "m1";
                            string data1 = GetLastTime(tempTable, rowNum, "M1_finish_time");
                            DateTime finishDate = DateTime.ParseExact(data1, "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture); // DateTime.ParseExact(tempTable.Rows[rowNum - 9][colName + "_finish_time"].ToString(), "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture);
                            finishDate = finishDate.AddSeconds(1);
                            string dateString = finishDate.ToUniversalTime().ToString("yyyy-MM-ddT HH:mm:ssZ"); //2021-01-06T 08:45:23Z
                            return dateString;
                        }


                        else if (columnName.ToLower().Contains("m2"))
                        {

                            string data1 = GetLastTime(tempTable, rowNum, "M2_finish_time");
                            DateTime finishDate = DateTime.ParseExact(data1, "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture); // DateTime.ParseExact(tempTable.Rows[rowNum - 9][colName + "_finish_time"].ToString(), "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture);
                            finishDate = finishDate.AddSeconds(1);
                            string dateString = finishDate.ToUniversalTime().ToString("yyyy-MM-ddT HH:mm:ssZ"); //2021-01-06T 08:45:23Z
                            return dateString;

                        }


                        else if (columnName.ToLower().Contains("m3"))
                        {
                            colName = "m2";
                            string data1 = dr["M2_finish_time"].ToString(); // GetStartTime(rowNum - 3, tempTable, colName, dr, false);
                            DateTime finishDate = DateTime.ParseExact(data1, "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture); // DateTime.ParseExact(tempTable.Rows[rowNum - 9][colName + "_finish_time"].ToString(), "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture);
                            finishDate = finishDate.AddSeconds(1);
                            string dateString = finishDate.ToUniversalTime().ToString("yyyy-MM-ddT HH:mm:ssZ"); //2021-01-06T 08:45:23Z
                            return dateString;


                        }

                        else if (columnName.ToLower().Contains("m4"))
                        {
                            colName = "m3";
                            string data1 = dr["M3_finish_time"].ToString(); // GetStartTime(rowNum - 3, tempTable, colName, dr, false);
                            DateTime finishDate = DateTime.ParseExact(data1, "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture); // DateTime.ParseExact(tempTable.Rows[rowNum - 9][colName + "_finish_time"].ToString(), "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture);
                            finishDate = finishDate.AddSeconds(1);
                            string dateString = finishDate.ToUniversalTime().ToString("yyyy-MM-ddT HH:mm:ssZ"); //2021-01-06T 08:45:23Z
                            return dateString;
                        }

                        else if (columnName.ToLower().Contains("m5"))
                        {
                            colName = "m4";
                            string data1 = dr["M4_finish_time"].ToString(); // GetStartTime(rowNum - 3, tempTable, colName, dr, false);
                            DateTime finishDate = DateTime.ParseExact(data1, "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture); // DateTime.ParseExact(tempTable.Rows[rowNum - 9][colName + "_finish_time"].ToString(), "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture);
                            finishDate = finishDate.AddSeconds(1);
                            string dateString = finishDate.ToUniversalTime().ToString("yyyy-MM-ddT HH:mm:ssZ"); //2021-01-06T 08:45:23Z
                            return dateString;
                        }

                        else if (columnName.ToLower().Contains("m6"))
                        {
                            if (string.IsNullOrEmpty(tempTable.Rows[rowNum - 2]["M6_finish_time"].ToString()))
                            {
                                colName = "m5";
                                string data1 = dr["M5_finish_time"].ToString(); // GetStartTime(rowNum - 3, tempTable, colName, dr, false);
                                DateTime finishDate = DateTime.ParseExact(data1, "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture); // DateTime.ParseExact(tempTable.Rows[rowNum - 9][colName + "_finish_time"].ToString(), "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture);
                                finishDate = finishDate.AddSeconds(1);
                                string dateString = finishDate.ToUniversalTime().ToString("yyyy-MM-ddT HH:mm:ssZ"); //2021-01-06T 08:45:23Z
                                return dateString;
                            }
                            else
                            {
                                string data1 = GetLastTime(tempTable, rowNum, "M6_finish_time");
                                DateTime finishDate = DateTime.ParseExact(data1, "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture); // DateTime.ParseExact(tempTable.Rows[rowNum - 9][colName + "_finish_time"].ToString(), "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture);
                                finishDate = finishDate.AddSeconds(1);
                                string dateString = finishDate.ToUniversalTime().ToString("yyyy-MM-ddT HH:mm:ssZ"); //2021-01-06T 08:45:23Z
                                return dateString;
                            }
                        }
                        else if (columnName.ToLower().Contains("m7"))
                        {
                            if (string.IsNullOrEmpty(tempTable.Rows[rowNum - 2]["M7_finish_time"].ToString()))
                            {
                                colName = "m6";
                                string data1 = dr["M6_finish_time"].ToString(); // GetStartTime(rowNum - 3, tempTable, colName, dr, false);
                                DateTime finishDate = DateTime.ParseExact(data1, "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture); // DateTime.ParseExact(tempTable.Rows[rowNum - 9][colName + "_finish_time"].ToString(), "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture);
                                finishDate = finishDate.AddSeconds(1);
                                string dateString = finishDate.ToUniversalTime().ToString("yyyy-MM-ddT HH:mm:ssZ"); //2021-01-06T 08:45:23Z
                                return dateString;
                            }
                            else
                            {
                                string data1 = GetLastTime(tempTable, rowNum, "M7_finish_time");
                                DateTime finishDate = DateTime.ParseExact(data1, "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture); // DateTime.ParseExact(tempTable.Rows[rowNum - 9][colName + "_finish_time"].ToString(), "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture);
                                finishDate = finishDate.AddSeconds(1);
                                string dateString = finishDate.ToUniversalTime().ToString("yyyy-MM-ddT HH:mm:ssZ"); //2021-01-06T 08:45:23Z
                                return dateString;
                            }

                        }


                        else if (columnName.ToLower().Contains("m8"))
                        {
                            colName = "m7";
                            string data1 = dr["M7_finish_time"].ToString(); // GetStartTime(rowNum - 3, tempTable, colName, dr, false);
                            DateTime finishDate = DateTime.ParseExact(data1, "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture); // DateTime.ParseExact(tempTable.Rows[rowNum - 9][colName + "_finish_time"].ToString(), "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture);
                            finishDate = finishDate.AddSeconds(1);
                            string dateString = finishDate.ToUniversalTime().ToString("yyyy-MM-ddT HH:mm:ssZ"); //2021-01-06T 08:45:23Z
                            return dateString;
                        }

                        else if (columnName.ToLower().Contains("m9"))
                        {
                            colName = "m8";
                            string data1 = dr["M8_finish_time"].ToString(); // GetStartTime(rowNum - 3, tempTable, colName, dr, false);
                            DateTime finishDate = DateTime.ParseExact(data1, "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture); // DateTime.ParseExact(tempTable.Rows[rowNum - 9][colName + "_finish_time"].ToString(), "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture);
                            finishDate = finishDate.AddSeconds(1);
                            string dateString = finishDate.ToUniversalTime().ToString("yyyy-MM-ddT HH:mm:ssZ"); //2021-01-06T 08:45:23Z
                            return dateString;
                        }


                    }
                    else if (columnName.ToLower().Contains("finish"))
                    {
                        string colName = string.Empty;

                        if (columnName.ToLower().Contains("m1"))
                        {
                            colName = "m1";
                        }
                        if (columnName.ToLower().Contains("m2"))
                        {
                            colName = "m2";
                        }

                        else if (columnName.ToLower().Contains("m3"))
                        {
                            colName = "m3";
                        }
                        if (columnName.ToLower().Contains("m4"))
                        {
                            colName = "m4";
                        }

                        else if (columnName.ToLower().Contains("m5"))
                        {
                            colName = "m5";
                        }

                        if (columnName.ToLower().Contains("m6"))
                        {
                            colName = "m6";
                        }

                        else if (columnName.ToLower().Contains("m7"))
                        {
                            colName = "m7";
                        }

                        if (columnName.ToLower().Contains("m8"))
                        {
                            colName = "m8";
                        }

                        else if (columnName.ToLower().Contains("m9"))
                        {
                            colName = "m9";
                        }



                        string finishTime = GetFinishTime(tempTable, dr, colName);


                        return finishTime;


                    }





                    int dif = (firstTime - date).Hours;

                    DateTime utcStartDate = firstTime.ToUniversalTime();

                    date = utcdate.AddTicks(ticks1);





                    string dateString1 = date.ToString("yyyy-MM-ddT HH:mm:ssZ"); //2021-01-06T 08:45:23Z
                    return dateString1;




                }
                else
                    throw new Exception();
            }
            catch (Exception e)
            {
                Random rnd = new Random();

                string[] splitteddata = data.Split(' ');
                string str = "";
                foreach (string d in splitteddata)
                {
                    if (!data.Contains("*") && !data.Contains("_") && !data.Contains("(") && !data.ToLower().Contains("rotation") && !data.ToLower().Contains("color") && !data.ToLower().Contains("HG-4000T"))
                    {
                        int number;
                        double doublenumber;
                        if (int.TryParse(d, out number))
                        {
                            int next = rnd.Next(-1, 1);
                            number = number + next;
                            str = str + number + " ";
                        }

                        else if (double.TryParse(d, out doublenumber))
                        {
                            if (columnName.Contains("M8_glue_amount") || columnName.Contains("M8_glue_amount"))
                            {
                                double next = rnd.NextDouble() * (0.2 - 0.1);
                                doublenumber = doublenumber + next;
                                str = str + doublenumber.ToString("0.0") + " ";
                            }

                            else if (columnName.Contains("M9_RPM"))
                            {
                                double next = rnd.NextDouble() * (0.02 - 0.01);
                                doublenumber = doublenumber + next;
                                str = str + doublenumber.ToString("0.00") + " ";
                            }
                            else
                            {
                                double next = rnd.NextDouble() * (0.5 - 0.1);
                                doublenumber = doublenumber + next;
                                str = str + doublenumber.ToString("0.0") + " ";
                            }

                        }

                        else
                        {

                            str = str + d + " ";
                        }
                    }
                    else
                    {
                        if (d.ToLower().Contains("football_production_process_"))
                        {
                            return "football_Production_Process_" + MainHeaderCount;
                        }

                        else if (d.ToLower().Contains("semi_finished_football_"))
                        {

                            char num = d[d.Length - 1];
                            int count = int.Parse(num.ToString()) + MainHeaderCount;
                            return "semi_finished_football_" + count;
                        }
                        str = str + d + " ";
                    }


                }

                return str.Trim();
            }

        }


        private string GetFinishTime(DataTable tempTable, DataRow dr, string colName)
        {
            DateTime mStartTime = DateTime.ParseExact(dr[colName + "_start_time"].ToString(), "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture);
            long ticks = GetTicks(tempTable, colName + "_start_time", colName + "_finish_time");
            return mStartTime.AddTicks(ticks).ToUniversalTime().ToString("yyyy-MM-ddT HH:mm:ssZ"); //2021-01-06T 08:45:23Z;;
        }

        private string GetLastTime(DataTable tempTable, int rowNum, string colNum)
        {
            for (int i = rowNum - 1; i >= 0; i--)
            {
                if (tempTable.Rows.Count > i)
                {
                    string data = tempTable.Rows[i][colNum].ToString();
                    if (!string.IsNullOrEmpty(data))
                    {
                        return data;
                    }
                }
            }
            return string.Empty;
        }


        private bool IsPrevEmpty(DataTable tempTable, int rowNum, string colNum)
        {
            for (int i = rowNum - 1; i >= 0; i--)
            {
                string data = tempTable.Rows[i][colNum].ToString();
                if (string.IsNullOrEmpty(data))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            return false;
        }


        private long GetTicks(DataTable tempTable, string Col1Name, string Col2Name)
        {
            foreach (DataRow dr in tempTable.Rows)
            {
                string col1Value = dr[Col1Name].ToString();
                string col2Value = dr[Col2Name].ToString();

                if (!string.IsNullOrEmpty(col1Value) && !string.IsNullOrEmpty(col2Value))
                {
                    DateTime start = DateTime.ParseExact(col1Value, "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture);
                    DateTime end = DateTime.ParseExact(col2Value, "yyyy-MM-ddT HH:mm:ssZ", CultureInfo.CurrentCulture);
                    long ticks = (end - start).Ticks;
                    return ticks;
                }

            }
            return 0;
        }

        public static DataTable ConvertExcelToDataTable(string FileName)
        {
            DataTable dtResult = null;
            int totalSheet = 0; //No of sheets on excel file  
            using (OleDbConnection objConn = new OleDbConnection(@"Provider=Microsoft.ACE.OLEDB.12.0;Data Source=" + FileName + ";Extended Properties='Excel 12.0;HDR=YES;IMEX=1;';"))
            {
                objConn.Open();
                OleDbCommand cmd = new OleDbCommand();
                OleDbDataAdapter oleda = new OleDbDataAdapter();
                DataSet ds = new DataSet();
                DataTable dt = objConn.GetOleDbSchemaTable(OleDbSchemaGuid.Tables, null);
                string sheetName = string.Empty;
                if (dt != null)
                {
                    var tempDataTable = (from dataRow in dt.AsEnumerable()
                                         where !dataRow["TABLE_NAME"].ToString().Contains("FilterDatabase")
                                         select dataRow).CopyToDataTable();
                    dt = tempDataTable;
                    totalSheet = dt.Rows.Count;
                    sheetName = dt.Rows[0]["TABLE_NAME"].ToString();
                }
                cmd.Connection = objConn;
                cmd.CommandType = CommandType.Text;
                cmd.CommandText = "SELECT top 11 * FROM [" + sheetName + "]";
                oleda = new OleDbDataAdapter(cmd);
                oleda.Fill(ds, "excelData");
                dtResult = ds.Tables["excelData"];
                objConn.Close();
                return dtResult; //Returning Dattable  
            }
        }



    }
}
