/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.walter.rak4630data;

/**
 *
 * This class contains the sensor data received from the com port.
 *
 * @author User
 */
public class SensorData {

    private int command;
    private int timeRecorded;
    private int recordId;
    private int iaq;
    private int iaqAccuracy;
    private int staticIaq;
    private int co2Equivalent;
    private double breathVocEquivalent;
    private double rawTemperature;
    private double pressure;
    private double rawHumidity;
    private double gasResistance;
    private int stabStatus;
    private int runInStatus;
    private double temperature;
    private double humidity;
    private double gasPercentage;

    // Constructors
    public SensorData() {
        // Default constructor
    }

    public SensorData(int timeTrigger, int iaq, int iaqAccuracy, int staticIaq, int co2Equivalent,
            double breathVocEquivalent, double rawTemperature, double pressure, double rawHumidity,
            double gasResistance, int stabStatus, int runInStatus, double temperature, double humidity,
            double gasPercentage) {
        this.timeRecorded = timeTrigger;
        this.iaq = iaq;
        this.iaqAccuracy = iaqAccuracy;
        this.staticIaq = staticIaq;
        this.co2Equivalent = co2Equivalent;
        this.breathVocEquivalent = breathVocEquivalent;
        this.rawTemperature = rawTemperature;
        this.pressure = pressure;
        this.rawHumidity = rawHumidity;
        this.gasResistance = gasResistance;
        this.stabStatus = stabStatus;
        this.runInStatus = runInStatus;
        this.temperature = temperature;
        this.humidity = humidity;
        this.gasPercentage = gasPercentage;
    }

    @Override
    public String toString() {
        return "SensorData{"
                + "timeTrigger=" + getTimeRecorded()
                + ", iaq=" + getIaq()
                + ", iaqAccuracy=" + getIaqAccuracy()
                + ", staticIaq=" + getStaticIaq()
                + ", co2Equivalent=" + getCo2Equivalent()
                + ", breathVocEquivalent=" + getBreathVocEquivalent()
                + ", rawTemperature=" + getRawTemperature()
                + ", pressure=" + getPressure()
                + ", rawHumidity=" + getRawHumidity()
                + ", gasResistance=" + getGasResistance()
                + ", stabStatus=" + getStabStatus()
                + ", runInStatus=" + getRunInStatus()
                + ", temperature=" + getTemperature()
                + ", humidity=" + getHumidity()
                + ", gasPercentage=" + getGasPercentage()
                + '}';
    }

    /**
     * @return the timeRecorded
     */
    public int getTimeRecorded() {
        return timeRecorded;
    }

    /**
     * @param timeRecorded the timeRecorded to set
     */
    public void setTimeRecorded(int timeRecorded) {
        this.timeRecorded = timeRecorded;
    }

    /**
     * @return the iaq
     */
    public int getIaq() {
        return iaq;
    }

    /**
     * @param iaq the iaq to set
     */
    public void setIaq(int iaq) {
        this.iaq = iaq;
    }

    /**
     * @return the iaqAccuracy
     */
    public int getIaqAccuracy() {
        return iaqAccuracy;
    }

    /**
     * @param iaqAccuracy the iaqAccuracy to set
     */
    public void setIaqAccuracy(int iaqAccuracy) {
        this.iaqAccuracy = iaqAccuracy;
    }

    /**
     * @return the staticIaq
     */
    public int getStaticIaq() {
        return staticIaq;
    }

    /**
     * @param staticIaq the staticIaq to set
     */
    public void setStaticIaq(int staticIaq) {
        this.staticIaq = staticIaq;
    }

    /**
     * @return the co2Equivalent
     */
    public int getCo2Equivalent() {
        return co2Equivalent;
    }

    /**
     * @param co2Equivalent the co2Equivalent to set
     */
    public void setCo2Equivalent(int co2Equivalent) {
        this.co2Equivalent = co2Equivalent;
    }

    /**
     * @return the breathVocEquivalent
     */
    public double getBreathVocEquivalent() {
        return breathVocEquivalent;
    }

    /**
     * @param breathVocEquivalent the breathVocEquivalent to set
     */
    public void setBreathVocEquivalent(double breathVocEquivalent) {
        this.breathVocEquivalent = breathVocEquivalent;
    }

    /**
     * @return the rawTemperature
     */
    public double getRawTemperature() {
        return rawTemperature;
    }

    /**
     * @param rawTemperature the rawTemperature to set
     */
    public void setRawTemperature(double rawTemperature) {
        this.rawTemperature = rawTemperature;
    }

    /**
     * @return the pressure
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * @param pressure the pressure to set
     */
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    /**
     * @return the rawHumidity
     */
    public double getRawHumidity() {
        return rawHumidity;
    }

    /**
     * @param rawHumidity the rawHumidity to set
     */
    public void setRawHumidity(double rawHumidity) {
        this.rawHumidity = rawHumidity;
    }

    /**
     * @return the gasResistance
     */
    public double getGasResistance() {
        return gasResistance;
    }

    /**
     * @param gasResistance the gasResistance to set
     */
    public void setGasResistance(double gasResistance) {
        this.gasResistance = gasResistance;
    }

    /**
     * @return the stabStatus
     */
    public int getStabStatus() {
        return stabStatus;
    }

    /**
     * @param stabStatus the stabStatus to set
     */
    public void setStabStatus(int stabStatus) {
        this.stabStatus = stabStatus;
    }

    /**
     * @return the runInStatus
     */
    public int getRunInStatus() {
        return runInStatus;
    }

    /**
     * @param runInStatus the runInStatus to set
     */
    public void setRunInStatus(int runInStatus) {
        this.runInStatus = runInStatus;
    }

    /**
     * @return the temperature
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * @param temperature the temperature to set
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * @return the humidity
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * @param humidity the humidity to set
     */
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    /**
     * @return the gasPercentage
     */
    public double getGasPercentage() {
        return gasPercentage;
    }

    /**
     * @param gasPercentage the gasPercentage to set
     */
    public void setGasPercentage(double gasPercentage) {
        this.gasPercentage = gasPercentage;
    }

    /**
     * @return the command
     */
    public int getCommand() {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand(int command) {
        this.command = command;
    }

    /**
     * @return the recordId
     */
    public int getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
}
