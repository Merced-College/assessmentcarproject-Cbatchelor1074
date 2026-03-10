// Chase Batchelor
// CPSC-39
// March 9, 2026
// This program will run search in relation to the Car class..

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //Testing purposes
        System.out.println("Program started.");

        ArrayList<Car> cars = loadCars("Car_Data.csv");

        if (cars.size() < 2000) {
            System.out.println("Not enough cars in the file to create a working list of 2000.");
            return;
        }

        ArrayList<Car> working = new ArrayList<>(cars.subList(0, 2000));
        Scanner scnr = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n----- Car Data Analyzer -----");
            System.out.println("1. Sort by Brand");
            System.out.println("2. Search by Brand");
            System.out.println("3. Show Statistics");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = scnr.nextInt();
            scnr.nextLine();

            switch (choice) {
                case 1:
                    insertionSortByBrand(working);
                    System.out.println("\nFirst 10 cars after sorting by brand: ");
                    printFirstTen(working);
                    break;
                
                case 2:
                    insertionSortByBrand(working);
                    System.out.print("Enter brand to search: ");
                    String brand = scnr.nextLine();

                    int index = binarySearchBrand(working, brand);

                    if (index == -1) {
                        System.out.println("No cars found for brand " + brand);
                    } else {
                        System.out.println("\nMatching cars: ");
                        printMatchingBrands(working, index, brand);
                    }
                    break;
                
                case 3:
                    showStatistics(working);
                    break;
                
                case 4:
                    System.out.println("Exiting program.");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        } while (choice != 4);

        scnr.close();
    }


    public static ArrayList<Car> loadCars(String filename) {
        ArrayList<Car> cars = new ArrayList<>();

        try {
            Scanner fileScanner = new Scanner(new File(filename));

            if (fileScanner.hasNextLine()) {
                //Skips the header line
                fileScanner.nextLine();
            }

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");

                if (parts.length != 7) {
                    // Skips any rows which may cause errors due to misformatting.
                    continue;
                }

                try {
                    String carId = parts[0].trim();
                    String brand = parts[1].trim();
                    String model = parts[2].trim();
                    int year = Integer.parseInt(parts[3].trim());
                    String fuelType = parts[4].trim();
                    String color = parts[5].trim();
                    double milageKmpl = Double.parseDouble(parts[6].trim());

                    Car car = new Car(carId, brand, model, year, fuelType, color, milageKmpl);
                    cars.add(car);
                } catch (NumberFormatException e) {

                }
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }

        System.out.println("Total cars loaded: " + cars.size());
        return cars;
    }

    public static void insertionSortByBrand(ArrayList<Car> list) {
        for (int i = 1; i < list.size(); i++) {
            Car key = list.get(i);
            int j = i - 1;

            while (j >= 0 && list.get(j).getBrand().compareToIgnoreCase(key.getBrand()) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }

            list.set(j + 1, key);
        }
    }

    public static int binarySearchBrand(ArrayList<Car> list, String targetBrand) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int compare = list.get(mid).getBrand().compareToIgnoreCase(targetBrand);

            if (compare == 0) {
                return mid;
            } else if (compare < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    public static void printMatchingBrands(ArrayList<Car> list, int foundIndex, String targetBrand) {
        int left = foundIndex;
        int right = foundIndex;

        while (left >= 0 && list.get(left).getBrand().equalsIgnoreCase(targetBrand)) {
            left--;
        }

        while (right < list.size() && list.get(right).getBrand().equalsIgnoreCase(targetBrand)) {
            right++;
        }

        for (int i = left + 1; i < right; i++) {
            System.out.println(list.get(i));
        }
    }

    public static void printFirstTen(ArrayList<Car> list) {
        int limit = Math.min(10, list.size());

        for (int i = 0; i < limit; i++) {
            System.out.println(list.get(i));
        }
    }

    public static void showStatistics(ArrayList<Car> list) {
        double totalMileage = 0;

        int petrolCount = 0;
        int dieselCount = 0;
        int electricCount = 0;
        int hybridCount = 0;
        int otherCount = 0;

        for (Car car: list) {
            totalMileage += car.getMileageKmpl();

            String fuel = car.getFuelType().trim().toLowerCase();

            if (fuel.equals("petrol")) {
                petrolCount++;
            } else if (fuel.equals("diesel")) {
                dieselCount++;
            } else if (fuel.equals("electric")) {
                electricCount++;
            } else if (fuel.equals("hybrid")) {
                hybridCount++;
            } else {
                otherCount++;
            }
        }

        double averageMileage = totalMileage / list.size();

        System.out.printf("\nAverage mileage: %.2f kmpl%n", averageMileage);
        System.out.println("Fuel type counts: ");
        System.out.println("Petrol: " + petrolCount);
        System.out.println("Diesel: " + dieselCount);
        System.out.println("Electric: " + electricCount);
        System.out.println("Hybrid: " + hybridCount);
        System.out.println("Other: " + otherCount);
    }

}