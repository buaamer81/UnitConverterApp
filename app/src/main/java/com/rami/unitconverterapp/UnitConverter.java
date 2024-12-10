package com.rami.unitconverterapp;



public class UnitConverter {

    public static String[] getUnitsForCategory(String category) {
        switch (category) {
            case "Length":
                return new String[]{"Meters", "Kilometers", "Feet"};
            case "Weight":
                return new String[]{"Kilograms", "Pounds", "Grams"};
            case "Temperature":
                return new String[]{"Celsius", "Fahrenheit", "Kelvin"};
            default:
                return new String[]{};
        }
    }

    public static double convert(String category, double value, String fromUnit, String toUnit) {
        switch (category) {
            case "Length":
                return convertLength(value, fromUnit, toUnit);
            case "Weight":
                return convertWeight(value, fromUnit, toUnit);
            case "Temperature":
                return convertTemperature(value, fromUnit, toUnit);
            default:
                return Double.NaN;
        }
    }

    private static double convertLength(double value, String from, String to) {
        double valueInMeters;
        switch (from) {
            case "Meters":
                valueInMeters = value;
                break;
            case "Kilometers":
                valueInMeters = value * 1000;
                break;
            case "Feet":
                valueInMeters = value * 0.3048;
                break;
            default:
                return Double.NaN;
        }

        switch (to) {
            case "Meters":
                return valueInMeters;
            case "Kilometers":
                return valueInMeters / 1000;
            case "Feet":
                return valueInMeters / 0.3048;
            default:
                return Double.NaN;
        }
    }

    private static double convertWeight(double value, String from, String to) {
        double valueInKilograms;
        switch (from) {
            case "Kilograms":
                valueInKilograms = value;
                break;
            case "Pounds":
                valueInKilograms = value * 0.453592;
                break;
            case "Grams":
                valueInKilograms = value / 1000;
                break;
            default:
                return Double.NaN;
        }

        switch (to) {
            case "Kilograms":
                return valueInKilograms;
            case "Pounds":
                return valueInKilograms / 0.453592;
            case "Grams":
                return valueInKilograms * 1000;
            default:
                return Double.NaN;
        }
    }

    private static double convertTemperature(double value, String from, String to) {
        double valueInCelsius;

        switch (from) {
            case "Celsius":
                valueInCelsius = value;
                break;
            case "Fahrenheit":
                valueInCelsius = (value - 32) * 5 / 9;
                break;
            case "Kelvin":
                valueInCelsius = value - 273.15;
                break;
            default:
                return Double.NaN;
        }

        switch (to) {
            case "Celsius":
                return valueInCelsius;
            case "Fahrenheit":
                return (valueInCelsius * 9 / 5) + 32;
            case "Kelvin":
                return valueInCelsius + 273.15;
            default:
                return Double.NaN;
        }
    }
}
