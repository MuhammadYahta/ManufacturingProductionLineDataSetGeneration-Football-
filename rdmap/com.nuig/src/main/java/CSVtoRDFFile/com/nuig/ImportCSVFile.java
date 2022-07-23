package CSVtoRDFFile.com.nuig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfxml.xmlinput.states.HasSubjectFrameI;

public class ImportCSVFile {

	public static void main(String[] args) {
		String path_to_model = "C://Users/muhyah/OneDrive - National University of Ireland, Galway/PhD Publications/paper 2/Ontology Model/smO.owl";

		String smo = "http://www.semanticweb.org/manufacturingproductionline#";
		String dataNS = "http://www.semanticweb.org/manufacturingproductionline/data/";
		String tm = "http://www.w3.org/2006/time#";
		String ssn = "http://purl.oclc.org/NET/ssnx/ssn#";
		String sosa = "http://www.w3.org/ns/sosa#";

		OntModel model = ModelFactory.createOntologyModel();
		try {
			model.read(new FileInputStream(new File(path_to_model)), smo, "TURTLE");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/***
		 * Classes
		 ***/
		Resource masterProcess = model.getResource(smo + "Process");
		Resource machineProcess = model.getResource(smo + "ManufacturingProcess");
		Resource assemblyProcess = model.getResource(smo+ "AssemblingProcess");
		Resource manualProcess = model.getResource(smo + "ManualProcess");
		Resource Time = model.getResource(tm + "Time");
		Resource material = model.getResource(smo + "Material");
		Resource machine = model.getResource(smo + "ProcessingMachine");
		Resource aMachine = model.getResource(smo + "AssemblingMachine");
		Resource machineLocation = model.getResource(smo + "Location");
		//Resource tool = model.getResource(smo + "Tool");
		Resource highFrequency = model.getResource(smo + "HighFrequencyDie");
		Resource laserDie = model.getResource(smo + "LaserDie");
		Resource motor = model.getResource(smo + "Motor");
		Resource speed = model.getResource(smo + "Speed");
		Resource power = model.getResource(smo + "Power");
		Resource state = model.getResource(smo + "MotorState");
		Resource Tobservations = model.getResource(smo + "Temperature");
		Resource Pobservations = model.getResource(smo + "Pressure");
		Resource Vobservations = model.getResource(smo + "Viscosity");
		Resource heater = model.getResource(smo + "Heater");
		Resource Tempsensor = model.getResource(smo + "TemperatureSensor");
		Resource Vsensor = model.getResource(smo + "ViscositySensor");
		Resource Psensor = model.getResource(smo + "PressureSensor");
		Resource bed = model.getResource(smo + "Bed");
		Resource squeegee = model.getResource(smo + "Squeegee");
		Resource color = model.getResource(smo + "Color");
		Resource hardness = model.getResource(smo + "Hardness");
		Resource part = model.getResource(smo + "Part");
		Resource frequency = model.getResource(smo + "Frequency");
		Resource OpACapability = model.getResource(smo + "PluckTheCutPanels");
		Resource OpBCapability = model.getResource(smo + "MatchPanels");
		Resource OpCCapability = model.getResource(smo + "GlueCleaning");
		Resource OpDCapability = model.getResource(smo + "PolybagsPacking");
		Resource OpGCapability = model.getResource(smo + "CartoonPacking");
		Resource Operator = model.getResource(smo + "Operator");
		Resource gSNeedle = model.getResource(smo + "GlueSprayingNeedle");
		Resource heatActivatingConveyor = model.getResource(smo + "Conveyor");
		Resource conveyorOperation = model.getResource(smo + "ConvyerOperation");
		Resource fmDie = model.getResource(smo + "FormingMoldDie");
		Resource bsDie = model.getResource(smo + "BallShappingDie");
		
		
		
		/***
		 * ObjectProperties
		 ***/

		Property hasTime = model.getProperty(tm + "hasTime");
		Property hasSubProcess = model.getProperty(smo + "hasSubProcess");
		Property processMaterial = model.getProperty(smo + "processMaterial");
		Property performsProcess = model.getProperty(smo + "performsProcess");
		Property isInLocation = model.getProperty(smo + "isInLocation");
		Property hasTool = model.getProperty(smo + "hasTool");
		Property hasSpeed = model.getProperty(smo + "hasSpeed");
		Property consumesPower = model.getProperty(smo + "consumesPower");
		Property hasTemperature = model.getProperty(smo + "hasTemperature");
		Property madeObservation = model.getProperty(sosa + "madeObservation");
		Property hasMotorState = model.getProperty(smo + "hasMotorState");
		Property isPlacedOn = model.getProperty(smo + "isPlacedOn");
		Property printsColor = model.getProperty(smo + "printsColor");
		Property hasHardness = model.getProperty(smo + "hasHardness");
		Property produce = model.getProperty(smo + "produce");
		Property hasfrequency =model.getProperty(smo + "hasFrequency");
		Property hasCapability = model.getProperty(smo + "hasCapability");
		Property drives = model.getProperty(smo + "drives");
		/***
		 * DataProperties
		 ***/
		Property hasStartTime = model.getProperty(tm + "hasStartTime");
		Property hasFinishTime = model.getProperty(tm + "hasFinishTime");
		Property hasName = model.getProperty(smo + "hasName");
		Property hasModel = model.getProperty(smo + "hasModel");
		Property hasID = model.getProperty(smo + "hasID");
		Property hasValue = model.getProperty(smo + "hasValue");
		Property hasSimpleResult = model.getProperty(sosa + "hasSimpleResult");
		Property hasState = model.getProperty(smo + "hasState");
		Property hasDiameter = model.getProperty(smo + "hasDiameter");
		Property hasSprayedAmount = model.getProperty(smo + "hasSprayedGlueAmount");

		Reader reader;
		try {
			reader = Files.newBufferedReader(Paths.get(
					"C://Users/muhyah/OneDrive - National University of Ireland, Galway/PhD Publications/paper 2/dataGenerator/DataSampleResultNew.csv"));

			CSVParser csvParser = new CSVParser(reader,
					CSVFormat.DEFAULT.withSkipHeaderRecord().withFirstRecordAsHeader());

			int indCounter = 1;
			String column1Value = "";
			Individual masterProcessinstance = null;

			for (CSVRecord csvRecord : csvParser) {

				if (csvRecord.get(1) != null && !csvRecord.get(1).equals("")) {
					column1Value = csvRecord.get(1);
				}

				/*
				 * Masterprocess Instance
				 */

				if (csvRecord.get(0) != null && !csvRecord.get(0).equals("")) {
					masterProcessinstance = model.createIndividual(dataNS + csvRecord.get(0), masterProcess);
				}

				/*
				 * Time Instance masterProcess
				 */
				Individual timeInstance = null;
				if (csvRecord.get(1) != null && !csvRecord.get(1).equals("")) {
					timeInstance = model.createIndividual(dataNS + "Time_" + indCounter, Time);
					timeInstance.addProperty(hasStartTime, csvRecord.get(1));
					timeInstance.addProperty(hasFinishTime, csvRecord.get(2));
				}
				/*
				 * Time Instance Machine1 Process
				 */
				Individual m1timeInstance = null;
				if (csvRecord.get(3) != null && !csvRecord.get(3).equals("")) {
					m1timeInstance = model.createIndividual(dataNS + "machine1_Process_Time_" + indCounter, Time);
					m1timeInstance.addProperty(hasStartTime, csvRecord.get(3));
					m1timeInstance.addProperty(hasFinishTime, csvRecord.get(4));
				}

				/**
				 * Machine1 Material, Model, ID, Location, motor, motor speed, tool, Power,
				 * temperatureObservation,
				 */
				Individual machine1Material = null;
				if (csvRecord.get(5) != null && !csvRecord.get(5).equals("")) {
					machine1Material = model.createIndividual(dataNS + csvRecord.get(5), material);
				}
				String machine1Model = null;
				if (csvRecord.get(7) != null && !csvRecord.get(7).equals("")) {
					machine1Model = csvRecord.get(7);
				}
				String machine1ID = null;
				if (csvRecord.get(8) != null && !csvRecord.get(8).equals("")) {
					machine1ID = csvRecord.get(8);
				}
				Individual machine1LocationInstance = null;
				if (csvRecord.get(9) != null && !csvRecord.get(9).equals("")) {
					machine1LocationInstance = model.createIndividual(dataNS + csvRecord.get(9), machineLocation);
				}
				Individual machine1motorspeedInstance = null;
				if (csvRecord.get(14) != null && !csvRecord.get(14).equals("")) {
					machine1motorspeedInstance = model.createIndividual(dataNS + "Machine1_motor_Speed_" + indCounter,
							speed);
					machine1motorspeedInstance.addProperty(hasValue, csvRecord.get(14));
				}
				Individual machine1motorstateInstance = null;
				if (csvRecord.get(12) != null && !csvRecord.get(12).equals("")) {
					machine1motorstateInstance = model.createIndividual(dataNS + "Machine1_Motor_State_" + indCounter,
							state);
					machine1motorstateInstance.addProperty(hasState, csvRecord.get(20));
				}
				
				
				Individual machine1motorInstance = null;
				if (csvRecord.get(12) != null && !csvRecord.get(12).equals("")) {
					machine1motorInstance = model.createIndividual(dataNS + "Machine1_motor1", motor);
					machine1motorInstance.addProperty(hasName, csvRecord.get(12));
					machine1motorInstance.addProperty(hasModel, csvRecord.get(13));
					machine1motorInstance.addProperty(hasSpeed, machine1motorspeedInstance);
					machine1motorInstance.addProperty(hasMotorState, machine1motorstateInstance);
				}
				Individual machine1toolInstance = null;
				if (csvRecord.get(10) != null && !csvRecord.get(10).equals("") && !csvRecord.get(10).equals("")) {
					machine1toolInstance = model.createIndividual(dataNS + csvRecord.get(10), laserDie);
				}
				Individual machine1toolspeedInstance = null;
				if (csvRecord.get(17) != null && !csvRecord.get(17).equals("") && !csvRecord.get(17).equals("")) {
					machine1toolspeedInstance = model.createIndividual(dataNS + "Laser_cutter_speed" + indCounter,
							speed);
					machine1toolspeedInstance.addProperty(hasValue, csvRecord.get(17));
				}

				Individual machine1TemperatureObservationInstance = null;
				if (csvRecord.get(18) != null && !csvRecord.get(18).equals("")) {
					machine1TemperatureObservationInstance = model
							.createIndividual(dataNS + "Machine1_Temperature_Observation_" + indCounter, Tobservations);
					machine1TemperatureObservationInstance.addProperty(hasSimpleResult, csvRecord.get(18));
				}
				Individual machine1TemperatureSensorInstance = null;
				if (csvRecord.get(19) != null && !csvRecord.get(19).equals("")) {
					machine1TemperatureSensorInstance = model.createIndividual(dataNS + "Machine1_TemperatureSensor",
							Tempsensor);
					machine1TemperatureSensorInstance.addProperty(hasName, csvRecord.get(19));
					machine1TemperatureSensorInstance.addProperty(hasModel, "LCM_TmpSen2011");
					machine1TemperatureSensorInstance.addProperty(hasID, "LCM_TS11");
					machine1TemperatureSensorInstance.addProperty(madeObservation,
							machine1TemperatureObservationInstance);
				}
				Individual machine1ToolPowerInstance = null;
				if (csvRecord.get(16) != null && !csvRecord.get(16).equals("")) {
					machine1ToolPowerInstance = model.createIndividual(dataNS + "Laser_Power_" + indCounter, power);
					machine1ToolPowerInstance.addProperty(hasValue, csvRecord.get(16));
					machine1toolInstance.addProperty(consumesPower, machine1ToolPowerInstance);
					machine1toolInstance.addProperty(hasSpeed, machine1toolspeedInstance);
				}
				Individual machine1PowerInstance = null;
				if (csvRecord.get(15) != null && !csvRecord.get(15).equals("")) {
					machine1PowerInstance = model.createIndividual(dataNS + "Machine1_Power_" + indCounter, power);
					machine1PowerInstance.addProperty(hasValue, csvRecord.get(15));
				}
				/**
				 * Machine1
				 **/
				Individual machine1 = null;
				Individual machine1part = null;
				if (csvRecord.get(6) != null && !csvRecord.get(6).equals("")) {
					machine1 = model.createIndividual(dataNS + "Machine_1", machine);
					machine1.addProperty(processMaterial, machine1Material);
					machine1.addProperty(hasName, csvRecord.get(6));
					machine1.addProperty(hasModel, machine1Model);
					machine1.addProperty(hasID, machine1ID);
					machine1.addProperty(isInLocation, machine1LocationInstance);
					machine1.addProperty(hasTool, machine1toolInstance);
					machine1.addProperty(hasTool, machine1motorInstance);
					machine1.addProperty(hasTool, machine1TemperatureSensorInstance);
					machine1.addProperty(consumesPower, machine1PowerInstance);
					machine1.addProperty(hasTemperature, machine1TemperatureObservationInstance);
					machine1part = model.createIndividual(dataNS + "machine1_output" + indCounter, part);
					machine1part.addProperty(hasName, csvRecord.get(21));
					machine1.addProperty(produce, machine1part);
				}
				Individual machine1Processinstance = null;
				if (csvRecord.get(11) != null && !csvRecord.get(11).equals("")) {
					machine1Processinstance = model.createIndividual(dataNS + csvRecord.get(11), machineProcess);
					machine1Processinstance.addProperty(hasTime, m1timeInstance);
					machine1.addProperty(performsProcess, machine1Processinstance);

				}

				/**
				 * Machine 2 Time Instance
				 */
				Individual m2timeInstance = null;
				if (csvRecord.get(23) != null && !csvRecord.get(24).equals("")) {
					m2timeInstance = model.createIndividual(dataNS + "machine2_Process_Time_" + indCounter, Time);
					m2timeInstance.addProperty(hasStartTime, csvRecord.get(23));
					m2timeInstance.addProperty(hasFinishTime, csvRecord.get(24));
				}

				/**
				 * Machine 2 Time Instance
				 */
				Individual machine2Processinstance = null;
				if (csvRecord.get(22) != null && !csvRecord.get(22).equals("")) {
					machine2Processinstance = model.createIndividual(dataNS + csvRecord.get(22), machineProcess);
					machine2Processinstance.addProperty(hasTime, m2timeInstance);
				}
				/**
				 * Machine 2 Material Instance
				 */
				Individual machine2LocationInstance = null;
				if (csvRecord.get(29) != null && !csvRecord.get(29).equals("")) {
					machine2LocationInstance = model.createIndividual(dataNS + csvRecord.get(29), machineLocation);
				}

				/**
				 * Machine 2 Material Instance
				 */
				Individual machine2Material = null;
				if (csvRecord.get(26) != null && !csvRecord.get(26).equals("")) {
					machine2Material = model.createIndividual(dataNS + csvRecord.get(26), material);
				}

				/**
				 * Machine 2 Motor Instance
				 */
				Individual machine2motorstateInstance = null;
				if (csvRecord.get(30) != null && !csvRecord.get(30).equals("")) {
					machine2motorstateInstance = model.createIndividual(dataNS + "Machine2_Motor_State_" + indCounter,
							state);
					machine2motorstateInstance.addProperty(hasState, csvRecord.get(30));
				}
				Individual machine2motorspeedInstance = null;
				if (csvRecord.get(33) != null && !csvRecord.get(33).equals("")) {
					machine2motorspeedInstance = model.createIndividual(dataNS + "Machine2_motor_Speed_" + indCounter,
							speed);
					machine2motorspeedInstance.addProperty(hasValue, csvRecord.get(33));
				}

				Individual machine2motorInstance = null;
				if (csvRecord.get(31) != null && !csvRecord.get(31).equals("")) {
					machine2motorInstance = model.createIndividual(dataNS + "Machine2_motor2", motor);
					machine2motorInstance.addProperty(hasName, csvRecord.get(31));
					machine2motorInstance.addProperty(hasModel, csvRecord.get(32));

					if (machine2motorspeedInstance != null) {
						machine2motorInstance.addProperty(hasSpeed, machine2motorspeedInstance);
					}

				}
				machine2motorInstance.addProperty(hasMotorState, machine2motorstateInstance);

				// Machine2 Bed1
				Individual machine2bed1Instance = model.createIndividual(dataNS + "Machine2_Bed_1", bed);

				Individual machine2materialbed1Instance = null;
				if (csvRecord.get(34) != null && !csvRecord.get(34).equals("")) {
					machine2materialbed1Instance = model.createIndividual(dataNS + csvRecord.get(34), material);
					machine2materialbed1Instance.addProperty(isPlacedOn, machine2bed1Instance);
				}

				// Machine2 Squeegee1
				Individual machine2Squeegee1instance = null;
				Individual squeegee1color = null;
				Individual squeegee1Power = null;
				Individual squeegee1hardness = null;

				Individual Squeegee1ViscosityObservation = null;
				Individual squeegee1inkViscosity = null;

				Individual Squeegee1VPressureObservation = null;
				Individual squeegee1PressureSensor = null;

				if (csvRecord.get(35) != null && !csvRecord.get(35).equals("")) {
					machine2Squeegee1instance = model.createIndividual(dataNS + "machine2_Squeegee_1", squeegee);
					squeegee1color = model.createIndividual(dataNS + csvRecord.get(35), color);
					machine2Squeegee1instance.addProperty(printsColor, squeegee1color);
					squeegee1Power = model.createIndividual(dataNS + "Squeegee1_power_" + indCounter, power);
					squeegee1Power.addProperty(hasValue, csvRecord.get(36));
					squeegee1hardness = model.createIndividual(dataNS + "Squeegee1_hardness_" + indCounter, hardness);
					squeegee1hardness.addProperty(hasValue, csvRecord.get(37));

					Squeegee1ViscosityObservation = model.createIndividual(
							dataNS + "Machine2_Squeegee1_Viscosity_Observation_" + indCounter, Vobservations);
					Squeegee1ViscosityObservation.addProperty(hasSimpleResult, csvRecord.get(38));

					squeegee1inkViscosity = model.createIndividual(dataNS + "Squeegee1_Visocosity_Sensor", Vsensor);
					squeegee1inkViscosity.addProperty(hasName, "OPM_S1_Viscosity_Sensor");
					squeegee1inkViscosity.addProperty(hasModel, "OPM_S2_VisSen2013");
					squeegee1inkViscosity.addProperty(hasID, "OPM_S2_VS11");
					squeegee1inkViscosity.addProperty(madeObservation, Squeegee1ViscosityObservation);

					Squeegee1VPressureObservation = model.createIndividual(
							dataNS + "Machine2_Squeegee1_Pressure_Observation_" + indCounter, Pobservations);
					Squeegee1VPressureObservation.addProperty(hasSimpleResult, csvRecord.get(39));

					squeegee1PressureSensor = model.createIndividual(dataNS + "Squeegee1_Pressure_Sensor", Psensor);
					squeegee1PressureSensor.addProperty(hasName, "OPM_S1_Pressure_Sensor");
					squeegee1PressureSensor.addProperty(hasModel, "OPM_S2_PreSen2011");
					squeegee1PressureSensor.addProperty(hasID, "OPM_S2_PS11");
					squeegee1PressureSensor.addProperty(madeObservation, Squeegee1VPressureObservation);

					machine2Squeegee1instance.addProperty(consumesPower, squeegee1Power);
					machine2Squeegee1instance.addProperty(hasHardness, squeegee1hardness);

					machine2Squeegee1instance.addProperty(hasTool, squeegee1PressureSensor);
					machine2Squeegee1instance.addProperty(hasTool, squeegee1inkViscosity);
				}

				// Machine2 Heater1
				Individual Machine2Heater1 = null;
				Individual Heater1TempObservation = null;
				Individual Heater1TempSensor = null;

				if (csvRecord.get(40) != null && !csvRecord.get(40).equals("")) {
					Machine2Heater1 = model.createIndividual(dataNS + "Machine2_Heater_1", heater);

					Heater1TempObservation = model.createIndividual(
							dataNS + "Machine2_Heater1_Temperature_Observation_" + indCounter, Tobservations);
					Heater1TempObservation.addProperty(hasSimpleResult, csvRecord.get(40));

					Heater1TempSensor = model.createIndividual(dataNS + "Machine2_Heater1_Temperature_Sensor",
							Tempsensor);
					Heater1TempSensor.addProperty(hasName, "OPM_H1_Temperature_Sensor");
					Heater1TempSensor.addProperty(hasModel, "OPM_H1_TempSen2011");
					Heater1TempSensor.addProperty(hasID, "OPM_H1_TS11");
					Heater1TempSensor.addProperty(madeObservation, Heater1TempObservation);

					Machine2Heater1.addProperty(hasTool, Heater1TempSensor);
				}

				// Machine2 Bed2
				Individual machine2bed2Instance = model.createIndividual(dataNS + "Machine2_Bed_2", bed);

				Individual machine2materialbed2Instance = null;
				if (csvRecord.get(41) != null && !csvRecord.get(41).equals("")) {
					machine2materialbed2Instance = model.createIndividual(dataNS + csvRecord.get(41), material);
					machine2materialbed2Instance.addProperty(isPlacedOn, machine2bed2Instance);
				}

				// Machine2 Squeegee2
				Individual machine2Squeegee2instance = null;
				Individual squeegee2color = null;
				Individual squeegee2Power = null;
				Individual squeegee2hardness = null;

				Individual Squeegee2ViscosityObservation = null;
				Individual squeegee2inkViscosity = null;

				Individual Squeegee2VPressureObservation = null;
				Individual squeegee2PressureSensor = null;

				if (csvRecord.get(42) != null && !csvRecord.get(42).equals("")) {
					machine2Squeegee2instance = model.createIndividual(dataNS + "machine2_Squeegee_2", squeegee);

					squeegee2color = model.createIndividual(dataNS + csvRecord.get(42), color);
					machine2Squeegee2instance.addProperty(printsColor, squeegee2color);

					squeegee2Power = model.createIndividual(dataNS + "Squeegee2_power_" + indCounter, power);
					squeegee2Power.addProperty(hasValue, csvRecord.get(43));

					squeegee2hardness = model.createIndividual(dataNS + "Squeegee2_hardness_" + indCounter, hardness);
					squeegee2hardness.addProperty(hasValue, csvRecord.get(44));

					Squeegee2ViscosityObservation = model.createIndividual(
							dataNS + "Machine2_Squeegee2_Viscosity_Observation_" + indCounter, Vobservations);
					Squeegee2ViscosityObservation.addProperty(hasSimpleResult, csvRecord.get(45));

					squeegee2inkViscosity = model.createIndividual(dataNS + "Squeegee2_Visocosity_Sensor", Vsensor);
					squeegee2inkViscosity.addProperty(hasName, "OPM_S2_Viscosity_Sensor");
					squeegee2inkViscosity.addProperty(hasModel, "OPM_S_VisSen2013");
					squeegee2inkViscosity.addProperty(hasID, "OPM_S_VS11");
					squeegee2inkViscosity.addProperty(madeObservation, Squeegee2ViscosityObservation);

					Squeegee2VPressureObservation = model.createIndividual(
							dataNS + "Machine2_Squeegee2_Pressure_Observation_" + indCounter, Pobservations);
					Squeegee2VPressureObservation.addProperty(hasSimpleResult, csvRecord.get(46));

					squeegee2PressureSensor = model.createIndividual(dataNS + "Squeegee2_Pressure_Sensor", Psensor);
					squeegee2PressureSensor.addProperty(hasName, "OPM_S2_Pressure_Sensor");
					squeegee2PressureSensor.addProperty(hasModel, "OPM_S2_PreSen2011");
					squeegee2PressureSensor.addProperty(hasID, "OPM_S2_PS11");
					squeegee2PressureSensor.addProperty(madeObservation, Squeegee2VPressureObservation);

					machine2Squeegee2instance.addProperty(consumesPower, squeegee2Power);
					machine2Squeegee2instance.addProperty(hasHardness, squeegee2hardness);
					machine2Squeegee2instance.addProperty(hasTool, squeegee2PressureSensor);
					machine2Squeegee2instance.addProperty(hasTool, squeegee2inkViscosity);
				}

				// Machine2 Heater2
				Individual Machine2Heater2 = null;
				Individual Heater2TempObservation = null;
				Individual Heater2TempSensor = null;

				if (csvRecord.get(47) != null && !csvRecord.get(47).equals("")) {
					Machine2Heater2 = model.createIndividual(dataNS + "Machine2_Heater_2", heater);

					Heater2TempObservation = model.createIndividual(
							dataNS + "Machine2_Heater2_Temperature_Observation_" + indCounter, Tobservations);
					Heater2TempObservation.addProperty(hasSimpleResult, csvRecord.get(47));

					Heater2TempSensor = model.createIndividual(dataNS + "Machine2_Heater2_Temperature_Sensor",
							Tempsensor);
					Heater2TempSensor.addProperty(hasName, "OPM_H2_Temperature_Sensor");
					Heater2TempSensor.addProperty(hasModel, "OPM_H2_TempSen2011");
					Heater2TempSensor.addProperty(hasID, "OPM_H2_TS11");
					Heater2TempSensor.addProperty(madeObservation, Heater2TempObservation);

					Machine2Heater2.addProperty(hasTool, Heater2TempSensor);
				}

				// Machine2 Bed2
				Individual machine2bed3Instance = model.createIndividual(dataNS + "Machine2_Bed_3", bed);

				Individual machine2materialbed3Instance = null;
				if (csvRecord.get(48) != null && !csvRecord.get(48).equals("")) {
					machine2materialbed3Instance = model.createIndividual(dataNS + csvRecord.get(48), material);
					machine2materialbed3Instance.addProperty(isPlacedOn, machine2bed3Instance);
				}

				// Machine2 Squeegee2
				Individual machine2Squeegee3instance = null;
				Individual squeegee3color = null;
				Individual squeegee3Power = null;
				Individual squeegee3hardness = null;

				Individual Squeegee3ViscosityObservation = null;
				Individual squeegee3inkViscosity = null;

				Individual Squeegee3VPressureObservation = null;
				Individual squeegee3PressureSensor = null;

				if (csvRecord.get(49) != null && !csvRecord.get(49).equals("")) {
					machine2Squeegee3instance = model.createIndividual(dataNS + "machine2_Squeegee_3", squeegee);

					squeegee3color = model.createIndividual(dataNS + csvRecord.get(49), color);
					machine2Squeegee3instance.addProperty(printsColor, squeegee3color);

					squeegee3Power = model.createIndividual(dataNS + "Squeegee3_power_" + indCounter, power);
					squeegee3Power.addProperty(hasValue, csvRecord.get(50));

					squeegee3hardness = model.createIndividual(dataNS + "Squeegee3_hardness_" + indCounter, hardness);
					squeegee3hardness.addProperty(hasValue, csvRecord.get(51));

					Squeegee3ViscosityObservation = model.createIndividual(
							dataNS + "Machine2_Squeegee3_Viscosity_Observation_" + indCounter, Vobservations);
					Squeegee3ViscosityObservation.addProperty(hasSimpleResult, csvRecord.get(52));

					squeegee3inkViscosity = model.createIndividual(dataNS + "Squeegee3_Visocosity_Sensor", Vsensor);
					squeegee3inkViscosity.addProperty(hasName, "OPM_S3_Viscosity_Sensor");
					squeegee3inkViscosity.addProperty(hasModel, "OPM_S_VisSen2013");
					squeegee3inkViscosity.addProperty(hasID, "OPM_S_VS11");

					squeegee3inkViscosity.addProperty(madeObservation, Squeegee3ViscosityObservation);

					Squeegee3VPressureObservation = model.createIndividual(
							dataNS + "Machine2_Squeegee3_Pressure_Observation_" + indCounter, Pobservations);
					Squeegee3VPressureObservation.addProperty(hasSimpleResult, csvRecord.get(53));

					squeegee3PressureSensor = model.createIndividual(dataNS + "Squeegee3_Pressure_Sensor", Psensor);
					squeegee3PressureSensor.addProperty(hasName, "OPM_S3_Pressure_Sensor");
					squeegee3PressureSensor.addProperty(hasModel, "OPM_S3_PreSen2011");
					squeegee3PressureSensor.addProperty(hasID, "OPM_S3_PS11");

					squeegee3PressureSensor.addProperty(madeObservation, Squeegee3VPressureObservation);

					machine2Squeegee3instance.addProperty(consumesPower, squeegee3Power);
					machine2Squeegee3instance.addProperty(hasHardness, squeegee3hardness);
					machine2Squeegee3instance.addProperty(hasTool, squeegee3PressureSensor);
					machine2Squeegee3instance.addProperty(hasTool, squeegee3inkViscosity);
				}

				// Machine2 Heater2
				Individual Machine2Heater3 = null;
				Individual Heater3TemObservation = null;
				Individual Heater3TempSensor = null;
				Individual machine2part = null;
				if (csvRecord.get(54) != null && !csvRecord.get(54).equals("")) {
					Machine2Heater3 = model.createIndividual(dataNS + "Machine2_Heater_3", heater);

					Heater3TemObservation = model.createIndividual(
							dataNS + "Machine2_Heater3_Temperature_Observation_" + indCounter, Tobservations);
					Heater3TemObservation.addProperty(hasSimpleResult, csvRecord.get(54));

					Heater3TempSensor = model.createIndividual(dataNS + "Machine2_Heater3_Temperature_Sensor",
							Tempsensor);
					Heater3TempSensor.addProperty(hasName, "OPM_H3_Temperature_Sensor");
					Heater3TempSensor.addProperty(hasModel, "OPM_H3_TempSen2011");
					Heater3TempSensor.addProperty(hasID, "OPM_H3_TS11");
					Heater3TempSensor.addProperty(madeObservation, Heater3TemObservation);

					Machine2Heater3.addProperty(hasTool, Heater3TempSensor);
					machine2part = model.createIndividual(dataNS + "machine2_output" + indCounter, part);
					machine2part.addProperty(hasName, csvRecord.get(55));

				}

				/**
				 * Machine 2 Name Instance
				 */
				Individual machine2 = model.createIndividual(dataNS + "Machine_2", machine);
				
				machine2.addProperty(hasName, csvRecord.get(25));
				machine2.addProperty(hasModel, csvRecord.get(27));
				machine2.addProperty(hasID, csvRecord.get(28));
				machine2.addProperty(isInLocation, machine2LocationInstance);
				machine2.addProperty(hasTool, machine2motorInstance);
				machine2.addProperty(hasTool, machine2bed1Instance);
				machine2.addProperty(performsProcess, machine2Processinstance);
				machine2.addProperty(hasTool, machine2bed2Instance);
				machine2.addProperty(hasTool, machine2bed3Instance);
				if (machine2Material != null) {
					machine2.addProperty(processMaterial, machine2Material);
					machine2.addProperty(hasTool, machine2Squeegee1instance);
					machine2.addProperty(hasTool, Machine2Heater1);
				}

				if (machine2Squeegee2instance != null) {
					machine2.addProperty(hasTool, machine2Squeegee2instance);
					machine2.addProperty(hasTool, Machine2Heater2);
				}

				if (machine2Squeegee3instance != null) {
					machine2.addProperty(hasTool, machine2Squeegee3instance);
					machine2.addProperty(hasTool, Machine2Heater3);
					machine2.addProperty(produce, machine2part);
				}

				// Machine 3 Instances

				Individual m3timeInstance = null;
				if (csvRecord.get(61) != null && !csvRecord.get(61).equals("")) {
					m3timeInstance = model.createIndividual(dataNS + "machine3_Process_Time_" + indCounter, Time);
					m3timeInstance.addProperty(hasStartTime, csvRecord.get(61));
					m3timeInstance.addProperty(hasFinishTime, csvRecord.get(62));
				}

				/**
				 * Machine3 Material, Model, ID, Location, motor, motor speed, tool, Power,
				 * temperatureObservation,
				 */
				
				Individual machine3Material = null;
				if (csvRecord.get(63) != null && !csvRecord.get(63).equals("")) {
					machine3Material = model.createIndividual(dataNS + csvRecord.get(63), material);
				}
				String machine3Model = null;
				if (csvRecord.get(57) != null && !csvRecord.get(57).equals("")) {
					machine3Model = csvRecord.get(57);
				}
				String machine3ID = null;
				if (csvRecord.get(58) != null && !csvRecord.get(58).equals("")) {
					machine3ID = csvRecord.get(58);
				}
				Individual machine3LocationInstance = null;
				if (csvRecord.get(59) != null && !csvRecord.get(59).equals("")) {
					machine3LocationInstance = model.createIndividual(dataNS + csvRecord.get(59), machineLocation);
				}
				
				
				Individual machine3toolInstance = null;
				if (csvRecord.get(65) != null && !csvRecord.get(65).equals("") && !csvRecord.get(65).equals("")) {
					machine3toolInstance = model.createIndividual(dataNS + csvRecord.get(65), highFrequency);
				}
				
				Individual machine3ToolFrequency = null;
				if (csvRecord.get(66) != null && !csvRecord.get(66).equals("") && !csvRecord.get(66).equals("")) {
					machine3ToolFrequency = model.createIndividual(dataNS + "High_F_cutter_Frequency_" + indCounter,frequency);
					machine3ToolFrequency.addProperty(hasValue, csvRecord.get(66));
					machine3ToolFrequency.addProperty(hasfrequency, machine3ToolFrequency);
				}
				
				Individual machine3ToolTemperatureObservation = null; 
				if (csvRecord.get(67) != null && !csvRecord.get(67).equals("")) {
					machine3ToolTemperatureObservation = model .createIndividual(dataNS + "M3_cutter_Temperature_Observation_" + indCounter, Tobservations);
					machine3ToolTemperatureObservation.addProperty(hasSimpleResult, csvRecord.get(67));
					}
				 
				 Individual machine3ToolTemperatureSensor = null; 
				 if (csvRecord.get(68) != null && !csvRecord.get(68).equals("")) {
					 machine3ToolTemperatureSensor = model.createIndividual(dataNS + "M3_Cutter_Temperature_Sensor", Tempsensor);
					 machine3ToolTemperatureSensor.addProperty(hasName, csvRecord.get(68));
					 machine3ToolTemperatureSensor.addProperty(hasModel, "HFC_TmpSen2015");
					 machine3ToolTemperatureSensor.addProperty(hasID, "HFC_TS11");
					 machine3ToolTemperatureSensor.addProperty(madeObservation,  machine3ToolTemperatureObservation); 
				} 
				 
				 
				 Individual machine3ToolPower = null;
				 if (csvRecord.get(71) != null &&!csvRecord.get(71).equals("")) {
					 machine3ToolPower = model.createIndividual(dataNS + "HF_Cutter_Power_" + indCounter, power);
					 machine3ToolPower.addProperty(hasValue, csvRecord.get(71));
					 machine3toolInstance.addProperty(consumesPower, machine3ToolPower);
					 machine3toolInstance.addProperty(hasfrequency, machine3ToolFrequency); 
				  }
				
				 Individual machine3Power = null; 
				 if (csvRecord.get(69) != null && !csvRecord.get(69).equals("")) {
					 machine3Power = model.createIndividual(dataNS + "Machine3_Power_" + indCounter, power);
					 machine3Power.addProperty(hasValue, csvRecord.get(69));
					 }

				 Individual machine3ToolPressureOb = null; 
				 Individual machine3ToolPressureSensor = null; 
				 if (csvRecord.get(70) != null && !csvRecord.get(70).equals("")) {
				
					 machine3ToolPressureOb = model.createIndividual(dataNS + "M3_Cutter_Pressure_Observation_" + indCounter, Pobservations);
					 machine3ToolPressureOb.addProperty(hasSimpleResult, csvRecord.get(70));
					 machine3ToolPressureSensor = model.createIndividual(dataNS + "M3_Cutter_Pressure_Sensor", Psensor);
					 machine3ToolPressureSensor.addProperty(hasName, "HFC_Pressure_Sensor");
					 machine3ToolPressureSensor.addProperty(hasModel, "HFC_PreSen2011");
					 machine3ToolPressureSensor.addProperty(hasID, "HFC_PS11");
					 machine3ToolPressureSensor.addProperty(madeObservation, machine3ToolPressureOb);
					 machine3toolInstance.addProperty(hasTool, machine3ToolPressureSensor);
					 machine3toolInstance.addProperty(hasTool, machine3ToolTemperatureSensor);
		
				 }
				 
				 Individual machine3bed = model.createIndividual(dataNS + "Machine3_Bed", bed);
				 
				 Individual machine3Process = null;
					if (csvRecord.get(60) != null && !csvRecord.get(60).equals("")) {
						machine3Process = model.createIndividual(dataNS + csvRecord.get(60), machineProcess);
						machine3Process.addProperty(hasTime, m3timeInstance);
					}
				
					Individual machine3 = null;
					Individual machine3part = null;
					if (csvRecord.get(56) != null && !csvRecord.get(56).equals("")) {
						machine3 = model.createIndividual(dataNS + "Machine_3", machine);
						machine3Material.addProperty(isPlacedOn, machine3bed);
						machine3.addProperty(processMaterial, machine3Material);
						machine3.addProperty(hasName, csvRecord.get(56));
						machine3.addProperty(hasModel, machine3Model);
						machine3.addProperty(hasID, machine3ID);
						machine3.addProperty(isInLocation, machine3LocationInstance);
						machine3.addProperty(hasTool, machine3toolInstance);
						machine3.addProperty(hasTool, machine3bed);
						
						machine3.addProperty(consumesPower, machine3Power);
						machine3.addProperty(performsProcess, machine3Process);	
						machine3part = model.createIndividual(dataNS + "machine3_output" + indCounter, part);
						machine3part.addProperty(hasName, csvRecord.get(72));
						machine3.addProperty(produce, machine3part);
					}
				
					
					//Manual process by Operator A and Operator B
					Individual manualProcessOA = null;
					
					if (csvRecord.get(73) != null && !csvRecord.get(73).equals("")) {
						manualProcessOA = model.createIndividual(dataNS + csvRecord.get(73), manualProcess);
						Individual OpA = model.createIndividual(dataNS + csvRecord.get(74), Operator);
						Individual operatorACapability = model.createIndividual(dataNS + "Pluck_The_Cut_Panels", OpACapability);
						OpA.addProperty(performsProcess, manualProcessOA);
						OpA.addProperty(hasCapability,operatorACapability);
					}
				
					Individual manualProcessOB = null;
					
					if (csvRecord.get(75) != null && !csvRecord.get(75).equals("")) {
						manualProcessOB = model.createIndividual(dataNS + csvRecord.get(75), manualProcess);
						Individual OpB = model.createIndividual(dataNS + csvRecord.get(76), Operator);
						
						Individual operatorBCapability = model.createIndividual(dataNS + "MatchPanels", OpBCapability);
						OpB.addProperty(performsProcess, manualProcessOB);
						OpB.addProperty(hasCapability,operatorBCapability);
					}				
					
				
					/*
					 * Time Instance Machine4
					 */
					Individual m4timeInstance = null;
					if (csvRecord.get(82) != null && !csvRecord.get(82).equals("")) {
						m4timeInstance = model.createIndividual(dataNS + "machine4_Process_Time_" + indCounter, Time);
						m4timeInstance.addProperty(hasStartTime, csvRecord.get(82));
						m4timeInstance.addProperty(hasFinishTime, csvRecord.get(83));
					}

					/**
					 * Machine5 Material, Model, ID, Location, motor, motor speed, tool, Power,
					 * temperatureObservation,
					*/
					
					Individual machine4Material1 = null;
					if (csvRecord.get(84) != null && !csvRecord.get(84).equals("")) {
						machine4Material1 = model.createIndividual(dataNS + csvRecord.get(84), material);
					}
					Individual machine4Material2 = null;
					if (csvRecord.get(85) != null && !csvRecord.get(85).equals("")) {
						machine4Material2 = model.createIndividual(dataNS + csvRecord.get(85), material);
					}
					Individual machine4Material3 = null;
					if (csvRecord.get(86) != null && !csvRecord.get(86).equals("")) {
						machine4Material3 = model.createIndividual(dataNS + csvRecord.get(86), material);
					}
					
					
					String machine4Model = null;
					if (csvRecord.get(78) != null && !csvRecord.get(78).equals("")) {
						machine4Model = csvRecord.get(78);
					}
					String machine4ID = null;
					if (csvRecord.get(79) != null && !csvRecord.get(79).equals("")) {
						machine4ID = csvRecord.get(79);
					}
					Individual machine4LocationInstance = null;
					if (csvRecord.get(80) != null && !csvRecord.get(80).equals("")) {
						machine4LocationInstance = model.createIndividual(dataNS + csvRecord.get(80), machineLocation);
					} 
					Individual machine4motorspeedInstance = null;
					if (csvRecord.get(91) != null && !csvRecord.get(91).equals("")) {
						machine4motorspeedInstance = model.createIndividual(dataNS + "Machine4_motor_Speed_" + indCounter, speed);
						machine4motorspeedInstance.addProperty(hasValue, csvRecord.get(91));
					}
					Individual machine4motorstateInstance = null;
					if (csvRecord.get(92) != null && !csvRecord.get(92).equals("")) {
						machine4motorstateInstance = model.createIndividual(dataNS + "Machine4_Motor_State_" + indCounter, state);
						machine4motorstateInstance.addProperty(hasState, csvRecord.get(92));
					}
					
					
					Individual machine4motorInstance = null;
					if (csvRecord.get(12) != null && !csvRecord.get(12).equals("")) {
						machine4motorInstance = model.createIndividual(dataNS + "Machine4_motor", motor);
						machine4motorInstance.addProperty(hasName, csvRecord.get(89));
						machine4motorInstance.addProperty(hasModel, csvRecord.get(90));
						machine4motorInstance.addProperty(hasSpeed, machine4motorspeedInstance);
						machine4motorInstance.addProperty(hasMotorState, machine4motorstateInstance);
					}
					
					Individual machine4toolInstance = null;
					if (csvRecord.get(87) != null && !csvRecord.get(87).equals("") && !csvRecord.get(10).equals("")) {
						machine4toolInstance = model.createIndividual(dataNS + "Panel_BackSide_Glue_Spraying_Needle", gSNeedle);
						machine4toolInstance.addProperty(hasDiameter, csvRecord.get(87));
						machine4toolInstance.addProperty(hasSprayedAmount, csvRecord.get(88));
					}
					
					Individual machine4Power = null;
					if (csvRecord.get(93) != null && !csvRecord.get(93).equals("")) {
						machine4Power = model.createIndividual(dataNS + "Machine4_Power_" + indCounter, power);
						machine4Power.addProperty(hasValue, csvRecord.get(93));
					}
					
					
					Individual machine4Process = null;
					if (csvRecord.get(81) != null && !csvRecord.get(81).equals("")) {
						machine4Process = model.createIndividual(dataNS + csvRecord.get(81), machineProcess);
						machine4Process.addProperty(hasTime, m4timeInstance);
					}
				
					Individual machine4 = null;
					Individual machine4part = null;
					if (csvRecord.get(77) != null && !csvRecord.get(77).equals("")) {
						machine4 = model.createIndividual(dataNS + "Machine_4", machine);
						machine4.addProperty(processMaterial, machine4Material1);
						machine4.addProperty(processMaterial, machine4Material2);
						machine4.addProperty(processMaterial, machine4Material3);
						machine4.addProperty(hasName, csvRecord.get(77));
						machine4.addProperty(hasModel, machine4Model);
						machine4.addProperty(hasID,machine4ID); 
						machine4.addProperty(consumesPower, machine4Power);
						machine4.addProperty(performsProcess, machine4Process);
						machine4part = model.createIndividual(dataNS + "machine4_output_" +indCounter, part);
						machine4part.addProperty(hasName, csvRecord.get(94));
						machine4.addProperty(produce, machine4part);  
					}
					if (machine4toolInstance != null) {
					machine4.addProperty(isInLocation, machine4LocationInstance);
					machine4.addProperty(hasTool, machine4toolInstance);
					machine4.addProperty(hasTool, machine4motorInstance);
					}
					
					/*
					 * Time Instance Machine 5
					 */
					Individual m5timeInstance = null;
					if (csvRecord.get(101) != null && !csvRecord.get(101).equals("")) {
						m5timeInstance = model.createIndividual(dataNS + "machine5_Process_Time_" + indCounter, Time);
						m5timeInstance.addProperty(hasStartTime, csvRecord.get(101));
						m5timeInstance.addProperty(hasFinishTime, csvRecord.get(102));
					}

					/**
					 * Machine5 Material, Model, ID, Location, motor, motor speed, tool, Power,
					 * temperatureObservation,
					*/
					
					Individual machine5Material = null;
					if (csvRecord.get(100) != null && !csvRecord.get(100).equals("")) {
						machine5Material = model.createIndividual(dataNS + csvRecord.get(100), material);
					}
						
					String machine5Model = null;
					if (csvRecord.get(96) != null && !csvRecord.get(96).equals("")) {
						machine5Model = csvRecord.get(96);
					}
					String machine5ID = null;
					if (csvRecord.get(97) != null && !csvRecord.get(97).equals("")) {
						machine5ID = csvRecord.get(97);
					}
					Individual machine5LocationInstance = null;
					if (csvRecord.get(98) != null && !csvRecord.get(98).equals("")) {
						machine5LocationInstance = model.createIndividual(dataNS + csvRecord.get(98), machineLocation);
					} 
					Individual machine5motorspeedInstance = null;
					if (csvRecord.get(107) != null && !csvRecord.get(107).equals("")) {
						machine5motorspeedInstance = model.createIndividual(dataNS + "Machine5_motor_Speed_" + indCounter, speed);
						machine5motorspeedInstance.addProperty(hasValue, csvRecord.get(107));
					}
					Individual machine5motorstateInstance = null;
					if (csvRecord.get(106) != null && !csvRecord.get(106).equals("")) {
						machine5motorstateInstance = model.createIndividual(dataNS + "Machine5_Motor_State_" + indCounter, state);
						machine5motorstateInstance.addProperty(hasState, csvRecord.get(106));
					}
					Individual machine5motorInstance = null;
					if (csvRecord.get(104) != null && !csvRecord.get(104).equals("")) {
						machine5motorInstance = model.createIndividual(dataNS + "Machine5_motor", motor);
						machine5motorInstance.addProperty(hasName, csvRecord.get(104));
						machine5motorInstance.addProperty(hasModel, csvRecord.get(105));
						machine5motorInstance.addProperty(hasSpeed, machine5motorspeedInstance);
						machine5motorInstance.addProperty(hasMotorState, machine5motorstateInstance);
					}
					
					Individual machine5toolInstance = model.createIndividual(dataNS + "Heat_Activating_Conveyor", heatActivatingConveyor);
					
					Individual machine5toolProcess = model.createIndividual(dataNS + "Heat_conveyor_operation_" + indCounter, conveyorOperation);
					machine5toolInstance.addProperty(performsProcess, machine5toolProcess);
					
					Individual machine5Power = null; 
					if (csvRecord.get(108) != null && !csvRecord.get(108).equals("")) {
						 machine5Power = model.createIndividual(dataNS + "Machine5_Power_" + indCounter, power);
						 machine5Power.addProperty(hasValue, csvRecord.get(108));
						 machine5motorInstance.addProperty(drives, machine5toolInstance);
					 }

					Individual machine5Process = null;
					if (csvRecord.get(99) != null && !csvRecord.get(99).equals("")) {
						machine5Process = model.createIndividual(dataNS + csvRecord.get(99), machineProcess);
						machine5Process.addProperty(hasTime, m5timeInstance);
					}
					 
					Individual machine5ToolTemperatureSensor = null;
					Individual machine5ToolTemperatureObservation = null; 
					if (csvRecord.get(103) != null && !csvRecord.get(103).equals("")) {
						machine5ToolTemperatureObservation = model .createIndividual(dataNS + "M5_Temperature_Observation_" + indCounter, Tobservations);
						machine5ToolTemperatureObservation.addProperty(hasSimpleResult, csvRecord.get(103));
											 
						machine5ToolTemperatureSensor = model.createIndividual(dataNS + "M5_heat_activating_machine_Temperature_Sensor", Tempsensor);
					 	machine5ToolTemperatureSensor.addProperty(hasName, "HACM_Temperature_Sensor");
					 	machine5ToolTemperatureSensor.addProperty(hasModel, "HACM_TmpSen2015");
					 	machine5ToolTemperatureSensor.addProperty(hasID, "HACM_TS11");
					 	machine5ToolTemperatureSensor.addProperty(madeObservation,  machine5ToolTemperatureObservation); 
					} 
					
				  Individual machine5 = null; 
				  Individual machine5part = null;
				  if (csvRecord.get(95) != null && !csvRecord.get(95).equals("")) {
					  machine5 = model.createIndividual(dataNS + "Machine_5", machine);
					  machine5.addProperty(processMaterial, machine5Material);
					  machine5.addProperty(hasName, csvRecord.get(95));
					  machine5.addProperty(hasModel, machine5Model);
					  machine5.addProperty(hasID, machine5ID);
					  
					  machine5.addProperty(consumesPower,machine5Power);
					  
					  machine5part = model.createIndividual(dataNS + "machine5_output_" +indCounter,part);
					  machine5part.addProperty(hasName, csvRecord.get(109));
					  machine5.addProperty(produce, machine5part);
					  }
				 if (machine5 != null)  {
					 machine5.addProperty(isInLocation,machine5LocationInstance); 
					 machine5.addProperty(hasTool, machine5toolInstance);
					 machine5.addProperty(hasTool, machine5motorInstance);
					 machine5.addProperty(hasTool, machine5ToolTemperatureSensor);
					 }
				 
				 /* 
				  * Time Instance Machine 6
				*/
					Individual m6timeInstance = null;
					if (csvRecord.get(115) != null && !csvRecord.get(115).equals("")) {
						m6timeInstance = model.createIndividual(dataNS + "machine6_Process_Time_" + indCounter, Time);
						m6timeInstance.addProperty(hasStartTime, csvRecord.get(115));
						m6timeInstance.addProperty(hasFinishTime, csvRecord.get(116));
					}

					/**
					 * Machine6 Material, Model, ID, Location, motor, motor speed, tool, Power,
					 * temperatureObservation,
					*/
					
					Individual machine6Material = null;
					if (csvRecord.get(117) != null && !csvRecord.get(117).equals("")) {
						machine6Material = model.createIndividual(dataNS + csvRecord.get(117), material);
					}
					String machine6Model = null;
					if (csvRecord.get(111) != null && !csvRecord.get(111).equals("")) {
						machine6Model = csvRecord.get(111);
					}
					String machine6ID = null;
					if (csvRecord.get(112) != null && !csvRecord.get(112).equals("")) {
						machine6ID = csvRecord.get(112);
					}
					Individual machine6LocationInstance = null;
					if (csvRecord.get(113) != null && !csvRecord.get(113).equals("")) {
						machine6LocationInstance = model.createIndividual(dataNS + csvRecord.get(113), machineLocation);
					} 
					Individual machine6toolInstance = null;
					if (csvRecord.get(118) != null && !csvRecord.get(118).equals("") ) {
						machine6toolInstance = model.createIndividual(dataNS + "Forming_Mold_Die", fmDie);
						machine6toolInstance.addProperty(hasDiameter, csvRecord.get(118));
					}
					Individual machine6Power = null;
					if (csvRecord.get(121) != null && !csvRecord.get(121).equals("")) {
						machine6Power = model.createIndividual(dataNS + "Machine6_Power_" + indCounter, power);
						machine6Power.addProperty(hasValue, csvRecord.get(121));
					}
					Individual machine6ToolTemperatureObservation = null; 
					if (csvRecord.get(119) != null && !csvRecord.get(119).equals("")) {
						machine6ToolTemperatureObservation = model .createIndividual(dataNS + "M6_Folding_Mold_machine_Temperature_Observation_" + indCounter, Tobservations);
						machine6ToolTemperatureObservation.addProperty(hasSimpleResult, csvRecord.get(119));
						}
					 Individual machine6ToolTemperatureSensor = null; 
					 if (csvRecord.get(119) != null && !csvRecord.get(119).equals("")) {
						 machine6ToolTemperatureSensor = model.createIndividual(dataNS + "M6_Folding_Mold_machine_Temperature_Sensor", Tempsensor);
						 machine6ToolTemperatureSensor.addProperty(hasName, "M6_Folding_Mold_Die_Temperature");
						 machine6ToolTemperatureSensor.addProperty(hasModel, "FMD_TmpSen2015");
						 machine6ToolTemperatureSensor.addProperty(hasID, "FMD_TS17");
						 machine6ToolTemperatureSensor.addProperty(madeObservation,  machine6ToolTemperatureObservation); 
					}  
					 Individual machine6ToolPressureOb = null; 
					 Individual machine6ToolPressureSensor = null; 
					 if (csvRecord.get(120) != null && !csvRecord.get(120).equals("")) {
						 machine6ToolPressureOb = model.createIndividual(dataNS + "M6_Folding_Mold_machine_Pressure_Observation_" + indCounter, Pobservations);
						 machine6ToolPressureOb.addProperty(hasSimpleResult, csvRecord.get(120));
						 machine6ToolPressureSensor = model.createIndividual(dataNS + "M6_Folding_Mold_machine_Pressure_Sensor", Psensor);
						 machine6ToolPressureSensor.addProperty(hasName, csvRecord.get(122));
						 machine6ToolPressureSensor.addProperty(hasModel, "FMD_PreSen2011");
						 machine6ToolPressureSensor.addProperty(hasID, "FMD_PS11");
						 machine6ToolPressureSensor.addProperty(madeObservation, machine6ToolPressureOb);			
					 }
					 Individual machine6 = null; 
					  Individual machine6part = null;
					  if (csvRecord.get(110) != null && !csvRecord.get(110).equals("")) {
						  machine6 = model.createIndividual(dataNS + "Machine_6", aMachine);
						  machine6.addProperty(processMaterial, machine6Material);
						  machine6.addProperty(hasName, csvRecord.get(110));
						  machine6.addProperty(hasModel, machine6Model);
						  machine6.addProperty(hasID, machine6ID);
						  machine6.addProperty(consumesPower,machine6Power);
						  machine6part = model.createIndividual(dataNS + "machine6_output_" +indCounter,part);
						  machine6part.addProperty(hasName, csvRecord.get(123));
						  machine6.addProperty(produce, machine6part);
						  machine6.addProperty(hasTool, machine6ToolTemperatureSensor);
						  machine6.addProperty(hasTool, machine6ToolPressureSensor);
						  machine6.addProperty(isInLocation,machine6LocationInstance); 
						  machine6.addProperty(hasTool, machine6toolInstance);
						  						  }
					  
					  Individual machine6Process = null;
						if (csvRecord.get(114) != null && !csvRecord.get(114).equals("")) {
							machine6Process = model.createIndividual(dataNS + csvRecord.get(114), assemblyProcess);
							machine6Process.addProperty(hasTime, m6timeInstance);
							machine6.addProperty(performsProcess, machine6Process);
						}
					  
						/* 
						  * Time Instance Machine 7
						*/
							Individual m7timeInstance = null;
							if (csvRecord.get(128) != null && !csvRecord.get(128).equals("")) {
								m7timeInstance = model.createIndividual(dataNS + "machine7_Process_Time_" + indCounter, Time);
								m7timeInstance.addProperty(hasStartTime, csvRecord.get(128));
								m7timeInstance.addProperty(hasFinishTime, csvRecord.get(129));
							}

							/**
							 * Machine7 Material, Model, ID, Location, motor, motor speed, tool, Power,
							 * temperatureObservation,
							*/
							
							Individual machine7Material = null;
							if (csvRecord.get(127) != null && !csvRecord.get(127).equals("")) {
								machine7Material = model.createIndividual(dataNS + csvRecord.get(127), material);
							}
							String machine7Model = null;
							String machine7ID = null;
							if (csvRecord.get(125) != null && !csvRecord.get(125).equals("")) {
								machine7Model = csvRecord.get(125);
								machine7ID = "BSM_PL17";
							}
							Individual machine7LocationInstance = null;
							if (csvRecord.get(126) != null && !csvRecord.get(113).equals("")) {
								machine7LocationInstance = model.createIndividual(dataNS + csvRecord.get(126), machineLocation);
							} 
							
							Individual machine7toolInstance = null;
							if (csvRecord.get(131) != null && !csvRecord.get(131).equals("") ) {
								machine7toolInstance = model.createIndividual(dataNS + "Ball_Shapping_Die", bsDie);
								machine7toolInstance.addProperty(hasDiameter, csvRecord.get(131));
							}
							
							Individual machine7Power = null;
							if (csvRecord.get(133) != null && !csvRecord.get(133).equals("")) {
								machine7Power = model.createIndividual(dataNS + "Machine7_Power_" + indCounter, power);
								machine7Power.addProperty(hasValue, csvRecord.get(133));
							}
							
							
							Individual machine7TemperatureObservation = null; 
							if (csvRecord.get(130) != null && !csvRecord.get(130).equals("")) {
								machine7TemperatureObservation = model .createIndividual(dataNS + "M7_Ball_Shapping_Machine_Temperature_Observation_" + indCounter, Tobservations);
								machine7TemperatureObservation.addProperty(hasSimpleResult, csvRecord.get(130));
								}
							 
							 Individual machine7TemperatureSensor = null; 
							 if (csvRecord.get(130) != null && !csvRecord.get(130).equals("")) {
								 machine7TemperatureSensor = model.createIndividual(dataNS + "M7_Ball_Shapping_Machine_Temperature_Sensor", Tempsensor);
								 machine7TemperatureSensor.addProperty(hasName, "M7_Ball_Shapping_Machine_Temperature");
								 machine7TemperatureSensor.addProperty(hasModel, "BSM_TmpSen2015");
								 machine7TemperatureSensor.addProperty(hasID, "BSM_TS17");
								 machine7TemperatureSensor.addProperty(madeObservation,  machine7TemperatureObservation); 
							} 	
							 
							 Individual machine7PressureOb = null; 
							 Individual machine7PressureSensor = null; 
							 if (csvRecord.get(132) != null && !csvRecord.get(132).equals("")) {
							
								 machine7PressureOb = model.createIndividual(dataNS + "M7_Ball_Shapping_Machine_Pressure_Observation_" + indCounter, Pobservations);
								 machine7PressureOb.addProperty(hasSimpleResult, csvRecord.get(132));
								 machine7PressureSensor = model.createIndividual(dataNS + "M7_Ball_Shapping_Machine_Pressure_Sensor", Psensor);
								 machine7PressureSensor.addProperty(hasName, "Ball_Shapping_Machine_pressure_Sensor");
								 machine7PressureSensor.addProperty(hasModel, "BSM_PreSen2011");
								 machine7PressureSensor.addProperty(hasID, "BSM_PL11");
								 machine7PressureSensor.addProperty(madeObservation, machine7PressureOb);			
							 }
							 
							 Individual machine7 = null; 
							  Individual machine7part = null;
							  if (csvRecord.get(124) != null && !csvRecord.get(124).equals("")) {
								  machine7 = model.createIndividual(dataNS + "Machine_7", machine);
								  machine7.addProperty(processMaterial, machine7Material);
								  machine7.addProperty(hasName, csvRecord.get(124));
								  machine7.addProperty(hasModel, machine7Model);
								  machine7.addProperty(hasID, machine7ID);
								  
								  machine7.addProperty(consumesPower,machine7Power);
								  
								  machine7part = model.createIndividual(dataNS + "machine7_output_" +indCounter,part);
								  
								  machine7part.addProperty(hasName, csvRecord.get(134));
								  machine7.addProperty(produce, machine7part);
								  machine7.addProperty(hasTool, machine7TemperatureSensor);
								  machine7.addProperty(hasTool, machine7PressureSensor);
								  machine7.addProperty(isInLocation,machine7LocationInstance); 
								  machine7.addProperty(hasTool, machine7toolInstance);
								  						  }
							  
							  Individual machine7Process = null;
								if (csvRecord.get(135) != null && !csvRecord.get(135).equals("")) {
									machine7Process = model.createIndividual(dataNS + csvRecord.get(135), machineProcess);
									machine7Process.addProperty(hasTime, m7timeInstance);
									machine7.addProperty(performsProcess, machine7Process);
								}
							 
								
								/* 
								  * Time Instance Machine 8
								*/
									Individual m8timeInstance = null;
									if (csvRecord.get(142) != null && !csvRecord.get(142).equals("")) {
										m8timeInstance = model.createIndividual(dataNS + "machine8_Process_Time_" + indCounter, Time);
										m8timeInstance.addProperty(hasStartTime, csvRecord.get(142));
										m8timeInstance.addProperty(hasFinishTime, csvRecord.get(143));
									}

									/**
									 * Machine8 Material, Model, ID, Location, motor, motor speed, tool, Power,
									 * temperatureObservation,
									*/
									
									Individual machine8Material = null;
									if (csvRecord.get(140) != null && !csvRecord.get(140).equals("")) {
										machine8Material = model.createIndividual(dataNS + csvRecord.get(140), material);
									}
									String machine8Model = null;
									String machine8ID = null;
									if (csvRecord.get(137) != null && !csvRecord.get(137).equals("")) {
										machine8Model = csvRecord.get(137);
										machine8ID = csvRecord.get(138);
									}
									Individual machine8LocationInstance = null;
									if (csvRecord.get(139) != null && !csvRecord.get(113).equals("")) {
										machine8LocationInstance = model.createIndividual(dataNS + csvRecord.get(139), machineLocation);
									} 
									Individual machine8toolInstance = null;
									if (csvRecord.get(145) != null && !csvRecord.get(145).equals("") ) {
										machine8toolInstance = model.createIndividual(dataNS + "Ball_Seam_Glue_needle", gSNeedle);
										machine8toolInstance.addProperty(hasDiameter, csvRecord.get(145));
										machine8toolInstance.addProperty(hasSprayedAmount, csvRecord.get(146));
									}
									Individual machine8Power = null;
									if (csvRecord.get(133) != null && !csvRecord.get(133).equals("")) {
										machine8Power = model.createIndividual(dataNS + "Machine8_Power_" + indCounter, power);
										machine8Power.addProperty(hasValue, csvRecord.get(147));
									}
									Individual machine8TemperatureObservation = null; 
									if (csvRecord.get(130) != null && !csvRecord.get(130).equals("")) {
										machine8TemperatureObservation = model .createIndividual(dataNS + "M8_Ball_Seam_Gluing_Machine_Temperature_Observation_" + indCounter, Tobservations);
										machine8TemperatureObservation.addProperty(hasSimpleResult, csvRecord.get(144));
										}
									 Individual machine8TemperatureSensor = null; 
									 if (csvRecord.get(130) != null && !csvRecord.get(130).equals("")) {
										 machine8TemperatureSensor = model.createIndividual(dataNS + "M8_Ball_Seam_Gluing_Machine_Temperature_Sensor", Tempsensor);
										 machine8TemperatureSensor.addProperty(hasName, "M8_Ball_Seam_Gluing_Machine_Temperature_Sensor");
										 machine8TemperatureSensor.addProperty(hasModel, "BSM_TmpSen2013");
										 machine8TemperatureSensor.addProperty(hasID, "BSM_TS17");
										 machine8TemperatureSensor.addProperty(madeObservation,  machine8TemperatureObservation); 
									 }
									 Individual machine8motorspeedInstance = null;
										if (csvRecord.get(151) != null && !csvRecord.get(151).equals("")) {
											machine8motorspeedInstance = model.createIndividual(dataNS + "Machine8_motor_Speed_" + indCounter, speed);
											machine8motorspeedInstance.addProperty(hasValue, csvRecord.get(151));
										}
										Individual machine8motorstateInstance = null;
										if (csvRecord.get(150) != null && !csvRecord.get(150).equals("")) {
											machine8motorstateInstance = model.createIndividual(dataNS + "Machine8_Motor_State_" + indCounter, state);
											machine8motorstateInstance.addProperty(hasState, csvRecord.get(150));
										}
										Individual machine8motorInstance = null;
										if (csvRecord.get(148) != null && !csvRecord.get(148).equals("")) {
											machine8motorInstance = model.createIndividual(dataNS + "Machine8_motor", motor);
											machine8motorInstance.addProperty(hasName, csvRecord.get(148));
											machine8motorInstance.addProperty(hasModel, csvRecord.get(149));
											machine8motorInstance.addProperty(hasSpeed, machine8motorspeedInstance);
											machine8motorInstance.addProperty(hasMotorState, machine8motorstateInstance);
										}

										 Individual machine8 = null; 
										  Individual machine8part = null;
										  if (csvRecord.get(136) != null && !csvRecord.get(136).equals("")) {
											  machine8 = model.createIndividual(dataNS + "Machine_8", machine);
											  machine8.addProperty(processMaterial, machine8Material);
											  machine8.addProperty(hasName, csvRecord.get(136));
											  machine8.addProperty(hasModel, machine8Model);
											  machine8.addProperty(hasID, machine8ID);
											  machine8.addProperty(consumesPower,machine8Power);
											  machine8part = model.createIndividual(dataNS + "machine8_output_" +indCounter,part);
											  machine8part.addProperty(hasName, csvRecord.get(152));
											  machine8.addProperty(produce, machine8part);
											  machine8.addProperty(hasTool, machine8TemperatureSensor);
											  machine8.addProperty(isInLocation,machine8LocationInstance); 
											  machine8.addProperty(hasTool, machine8toolInstance);
											  machine8.addProperty(hasTool, machine8motorInstance);
											 }
										  Individual machine8Process = null;
											if (csvRecord.get(141) != null && !csvRecord.get(141).equals("")) {
												machine8Process = model.createIndividual(dataNS + csvRecord.get(141), machineProcess);
												machine8Process.addProperty(hasTime, m8timeInstance);
												machine8.addProperty(performsProcess, machine8Process);
											}
										 
										

											/* 
											  * Time Instance Machine 9
											*/
												Individual m9timeInstance = null;
												if (csvRecord.get(158) != null && !csvRecord.get(158).equals("")) {
													m8timeInstance = model.createIndividual(dataNS + "machine9_Process_Time_" + indCounter, Time);
													m8timeInstance.addProperty(hasStartTime, csvRecord.get(158));
													m8timeInstance.addProperty(hasFinishTime, csvRecord.get(159));
												}

												/**
												 * Machine9 Material, Model, ID, Location, motor, motor speed, tool, Power,
												 * temperatureObservation,
												*/
												
												Individual machine9Material = null;
												if (csvRecord.get(166) != null && !csvRecord.get(166).equals("")) {
													machine9Material = model.createIndividual(dataNS + csvRecord.get(166), material);
												}
												String machine9Model = null;
												String machine9ID = null;
												if (csvRecord.get(154) != null && !csvRecord.get(154).equals("")) {
													machine9Model = csvRecord.get(154);
													machine9ID = csvRecord.get(155);
												}
												Individual machine9LocationInstance = null;
												if (csvRecord.get(156) != null && !csvRecord.get(156).equals("")) {
													machine9LocationInstance = model.createIndividual(dataNS + csvRecord.get(156), machineLocation);
												} 
												

												Individual machine9toolInstance = model.createIndividual(dataNS + "M9_Heat_Drying_Conveyor", heatActivatingConveyor);
												
												Individual machine9toolProcess = model.createIndividual(dataNS + "M9_Heat_Drying_operation_" + indCounter, conveyorOperation);
												machine9toolInstance.addProperty(performsProcess, machine9toolProcess);
												
												
												
												Individual machine9Power = null;
												if (csvRecord.get(161) != null && !csvRecord.get(161).equals("")) {
													machine9Power = model.createIndividual(dataNS + "Machine9_Power_" + indCounter, power);
													machine9Power.addProperty(hasValue, csvRecord.get(161));
												}
											
												Individual machine9TemperatureObservation = null; 
												if (csvRecord.get(160) != null && !csvRecord.get(160).equals("")) {
													machine9TemperatureObservation = model .createIndividual(dataNS + "M9_Heat_Drying_Conveyor_Machine_Temperature_Observation_" + indCounter, Tobservations);
													machine9TemperatureObservation.addProperty(hasSimpleResult, csvRecord.get(160));
													}
												 
												 Individual machine9TemperatureSensor = null; 
												 if (csvRecord.get(161) != null && !csvRecord.get(161).equals("")) {
													 machine9TemperatureSensor = model.createIndividual(dataNS + "M9_Heat_Drying_Conveyor_Machine_Temperature_Sensor", Tempsensor);
													 machine9TemperatureSensor.addProperty(hasName, "M9_Heat_Drying_Conveyor_MachineTemperature_Sensor");
													 machine9TemperatureSensor.addProperty(hasModel, "HDCM_TmpSen2013");
													 machine9TemperatureSensor.addProperty(hasID, "M9_Conveyor");
													 machine9TemperatureSensor.addProperty(madeObservation,  machine9TemperatureObservation); 
												 }
											
												 Individual machine9motorspeedInstance = null;
													if (csvRecord.get(165) != null && !csvRecord.get(165).equals("")) {
														machine9motorspeedInstance = model.createIndividual(dataNS + "Machine9_motor_Speed_" + indCounter, speed);
														machine9motorspeedInstance.addProperty(hasValue, csvRecord.get(165));
													}
													Individual machine9motorstateInstance = null;
													if (csvRecord.get(164) != null && !csvRecord.get(164).equals("")) {
														machine9motorstateInstance = model.createIndividual(dataNS + "Machine9_Motor_State_" + indCounter, state);
														machine9motorstateInstance.addProperty(hasState, csvRecord.get(164));
													}
													
													
													Individual machine9motorInstance = null;
													if (csvRecord.get(162) != null && !csvRecord.get(162).equals("")) {
														machine9motorInstance = model.createIndividual(dataNS + "Machine9_motor", motor);
														machine9motorInstance.addProperty(hasName, csvRecord.get(162));
														machine9motorInstance.addProperty(hasModel, csvRecord.get(163));
														machine9motorInstance.addProperty(hasSpeed, machine9motorspeedInstance);
														machine9motorInstance.addProperty(hasMotorState, machine9motorstateInstance);
													}

													 Individual machine9 = null; 
													  Individual machine9part = null;
													  if (csvRecord.get(153) != null && !csvRecord.get(153).equals("")) {
														  machine9 = model.createIndividual(dataNS + "Machine_9", machine);
														  machine9.addProperty(processMaterial, machine9Material);
														  machine9.addProperty(hasName, csvRecord.get(153));
														  machine9.addProperty(hasModel, machine9Model);
														  machine9.addProperty(hasID, machine9ID);
														  machine9.addProperty(consumesPower,machine9Power);
														  machine9part = model.createIndividual(dataNS + "machine9_output_" +indCounter,part);
														  machine9part.addProperty(hasName, csvRecord.get(156));
														  machine9.addProperty(produce, machine9part);
														  machine9.addProperty(hasTool, machine9TemperatureSensor);
														  machine9.addProperty(isInLocation,machine9LocationInstance); 
														  machine9.addProperty(hasTool, machine9toolInstance);
														  machine9.addProperty(hasTool, machine9motorInstance);
														 }
													  Individual machine9Process = null;
														if (csvRecord.get(157) != null && !csvRecord.get(157).equals("")) {
															machine9Process = model.createIndividual(dataNS + csvRecord.get(157), machineProcess);
															machine9Process.addProperty(hasTime, m8timeInstance);
															machine9.addProperty(performsProcess, machine9Process);
														}
											
														//Manual process by Operator C,  Operator D and Operator E
														Individual manualProcessOc = null;
														
														if (csvRecord.get(168) != null && !csvRecord.get(168).equals("")) {
															manualProcessOc = model.createIndividual(dataNS + csvRecord.get(168), manualProcess);
															Individual Opc = model.createIndividual(dataNS + csvRecord.get(169), Operator);
															Individual operatorcCapability = model.createIndividual(dataNS + "Glue_Cleaing", OpCCapability);
															Opc.addProperty(performsProcess, manualProcessOc);
															Opc.addProperty(hasCapability,operatorcCapability);
														}								
														
														//Manual process by Operator C and Operator D
														Individual manualProcessOd = null;
														
														if (csvRecord.get(170) != null && !csvRecord.get(170).equals("")) {
															manualProcessOd = model.createIndividual(dataNS + csvRecord.get(170), manualProcess);
															Individual Opd = model.createIndividual(dataNS + csvRecord.get(171), Operator);
															Individual operatordCapability = model.createIndividual(dataNS + "Polybags_Packing", OpDCapability);
															Opd.addProperty(performsProcess, manualProcessOd);
															Opd.addProperty(hasCapability,operatordCapability);
														}	
														
														//Manual process by Operator C and Operator D
														Individual manualProcessOg = null;
														
														if (csvRecord.get(172) != null && !csvRecord.get(172).equals("")) {
															manualProcessOg = model.createIndividual(dataNS + csvRecord.get(172), manualProcess);
															Individual Opg = model.createIndividual(dataNS + csvRecord.get(173), Operator);
															Individual operatorgCapability = model.createIndividual(dataNS + "Glue_Cleaing", OpGCapability);
															Opg.addProperty(performsProcess, manualProcessOg);
															Opg.addProperty(hasCapability,operatorgCapability);
														}	
				/*
				 * Masterprocess
				 */

				if (masterProcessinstance != null) {
					if (timeInstance != null) {
						masterProcessinstance.addProperty(hasTime, timeInstance);
					}
					if (machine1Processinstance != null) {
						masterProcessinstance.addProperty(hasSubProcess, machine1Processinstance);
					}
					masterProcessinstance.addProperty(hasSubProcess, machine2Processinstance);
					
					}
					if (machine3Process != null) {
						masterProcessinstance.addProperty(hasSubProcess, machine3Process);
					}
					if (machine4Process != null) {
						masterProcessinstance.addProperty(hasSubProcess, machine4Process);
					}
					if (machine5Process != null) {
						masterProcessinstance.addProperty(hasSubProcess, machine5Process);
					}
					if (machine6Process != null) {
						masterProcessinstance.addProperty(hasSubProcess, machine6Process);
					}
					if (machine7Process != null) {
						masterProcessinstance.addProperty(hasSubProcess, machine7Process);
					}
					if (machine8Process != null) {
						masterProcessinstance.addProperty(hasSubProcess, machine8Process);
					}
					if (machine9Process != null) {
						masterProcessinstance.addProperty(hasSubProcess, machine9Process);
					}
					
				indCounter++;
				
//				if(column1Value.equals(csvRecord.get(1))){
//					//				}
				
				if (masterProcessinstance != null) {
					model.write(new FileOutputStream(new File(
							"C://Users/muhyah/OneDrive - National University of Ireland, Galway/PhD Publications/paper 2/Ontology Model/mappedModel.owl")),
							"TURTLE");
					
					
					/*
					 * final String service =
					 * "C://Users/muhyah/OneDrive - National University of Ireland, Galway/PhD Publications/paper 2/Ontology Model/smO.owl"
					 * ;
					 * 
					 * String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
					 * + "PREFIX smo: <http://www.semanticweb.org/manufacturingproductionline#>\n" +
					 * "PREFIX smodata: <http://www.semanticweb.org/manufacturingproductionline/data#>\n"
					 * + "PREFIX tm: <http://www.w3.org/2006/time#>\n" +
					 * "PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>\n";
					 * 
					 * String query_string = prefix +
					 * "select ?processingMachine ?nameOfProcessingMachine   where { "
					 * 
					 * + "?processingMachine a smo:ProcessingMachine." +
					 * "smo:hasName ?nameOfProcessingMachine." +"}";
					 * 
					 * 
					 * System.out.println("Query : "+query_string);
					 * 
					 * 
					 * Query query = QueryFactory.create(query_string, Syntax.syntaxARQ);
					 * 
					 * QueryExecution qexec = QueryExecutionFactory.sparqlService(service, query);
					 * ResultSet results = qexec.execSelect();
					 */
					
					
					
				}

			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

	}

}
