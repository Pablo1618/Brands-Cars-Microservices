package AUI_lab2.aui;

import AUI_lab2.aui.Brand.Brand;
import AUI_lab2.aui.Brand.BrandCreateDTO;
import AUI_lab2.aui.Brand.BrandReadDTO;
import AUI_lab2.aui.Brand.BrandService;
import AUI_lab2.aui.Car.Car;
import AUI_lab2.aui.Car.CarReadDTO;
import AUI_lab2.aui.Car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Component
	public static class DataInitializer implements CommandLineRunner {

		private final BrandService brandService;
		private final CarService carService;

		@Autowired
		public DataInitializer(BrandService brandService, CarService carService) {
			this.brandService = brandService;
			this.carService = carService;
		}

		@Override
		public void run(String... args) {
			initializeData();
			runCommandLineInterface();
		}

		private void initializeData() {

			UUID toyotaBrandID = UUID.fromString("88888888-1234-48f6-96d0-95d3ec2913fd");
			UUID fordBrandID = UUID.randomUUID();
			UUID renaultBrandID = UUID.randomUUID();

			Brand toyota = Brand.builder().id(toyotaBrandID).name("Toyota").build();
			Brand ford = Brand.builder().id(fordBrandID).name("Ford").build();
			Brand renault = Brand.builder().id(renaultBrandID).name("Renault").build();

			brandService.initializeBrand(toyota);
			brandService.initializeBrand(ford);
			brandService.initializeBrand(renault);

			carService.addCar(Car.builder().id(UUID.fromString("782ebb2a-10e3-48f6-96d0-95d3ec2913fd")).model("Corolla").productionYear(2020).brand(toyota).build());
			carService.addCar(Car.builder().id(UUID.fromString("6921372a-10e3-48f6-96d0-55d3ec291344")).model("Camry").productionYear(2023).brand(toyota).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Aygo").productionYear(2019).brand(toyota).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Mustang").productionYear(1969).brand(ford).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Focus").productionYear(2024).brand(ford).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Ranger").productionYear(2016).brand(ford).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Clio").productionYear(2019).brand(renault).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Austral").productionYear(2019).brand(renault).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Symbioz").productionYear(2024).brand(renault).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Rafale").productionYear(2015).brand(renault).build());

		}

		private void runCommandLineInterface() {
			Scanner scanner = new Scanner(System.in);
			String command;

			System.out.println("\nKomendy: list_cars, list_brands, add_car, delete_car, add_brand, delete_brand, exit");
			while (true) {
				System.out.print("Wpisz komendę: ");
				command = scanner.nextLine();

				switch (command.toLowerCase()) {
					case "list_cars":
						listAllCars();
						break;
					case "list_brands":
						listAllBrands();
						break;
					case "delete_car":
						deleteThisCar(scanner);
						break;
					case "add_brand":
						addNewBrand(scanner);
						break;
					case "delete_brand":
						deleteThisBrand(scanner);
						break;
					case "exit":
						System.out.println("Zamykanie aplikacji");
						scanner.close();
						System.exit(0);
						return;
					default:
						System.out.println("Nie ma takiej komendy");
				}
			}
		}

		private void listAllCars() {
			List<CarReadDTO> allCars = carService.getAllCars();
			System.out.println("Wszystkie dostępne samochody:");
			for (CarReadDTO car : allCars) {
				System.out.println("- ID:[" + car.getId() + "] " + car.getModelName());
			}
		}

		private void listAllBrands() {
			List<BrandReadDTO> allBrands = brandService.getAllBrands();
			System.out.println("Wszystkie dostępne marki:");
			for (BrandReadDTO brand : allBrands) {
				System.out.println("- " + brand.getBrandName());
			}
		}

		private void addNewBrand(Scanner scanner) {
			System.out.println("Podaj nazwę marki: ");
			String brandName = scanner.nextLine();
			BrandCreateDTO brand = BrandCreateDTO.builder().id(UUID.randomUUID()).brandName((brandName)).build();
			brandService.addBrand(brand);
			System.out.println("Dodano nową markę: " + brandName);

		}

		private void deleteThisCar(Scanner scanner) {
			System.out.println("Podaj ID samochodu do usunięcia: ");
			String carIdString = scanner.nextLine();
			UUID carId = UUID.fromString(carIdString);
			carService.removeCar(carId);
			System.out.println("Usunięto samochód o ID: " + carId);
		}

		private void deleteThisBrand(Scanner scanner) {
			System.out.println("Podaj ID marki do usunięcia: ");
			String brandIdString = scanner.nextLine();
			UUID brandId = UUID.fromString(brandIdString);
			brandService.removeBrand(brandId);
			System.out.println("Pomyślnie usunięto markę razem z samochodami");
		}
	}
}
