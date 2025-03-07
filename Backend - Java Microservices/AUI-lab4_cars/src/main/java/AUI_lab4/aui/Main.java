package AUI_lab4.aui;

import AUI_lab4.aui.Car.Car;
import AUI_lab4.aui.Car.CarReadDTO;
import AUI_lab4.aui.Car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Component
	public static class DataInitializer {

		private final CarService carService;

		@Autowired
		public DataInitializer(CarService carService) {
			this.carService = carService;
			initializeData();
		}

		private void initializeData() {
			UUID toyotaBrandID = UUID.fromString("11111111-1234-48f6-96d0-95d3ec2913fd");
			UUID fordBrandID = UUID.fromString("22222222-1234-48f6-96d0-95d3ec2913fd");
			UUID renaultBrandID = UUID.fromString("33333333-1234-48f6-96d0-95d3ec2913fd");
			UUID volkswagenBrandID = UUID.fromString("44444444-1234-48f6-96d0-95d3ec2913fd");
			UUID mazdaBrandID = UUID.fromString("55555555-1234-48f6-96d0-95d3ec2913fd");

			carService.addCar(Car.builder().id(UUID.fromString("782ebb2a-10e3-48f6-96d0-95d3ec2913fd")).model("Corolla").productionYear(2020).brandId(toyotaBrandID).build());
			carService.addCar(Car.builder().id(UUID.fromString("6921372a-10e3-48f6-96d0-55d3ec291344")).model("Camry").productionYear(2023).brandId(toyotaBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Aygo").productionYear(2019).brandId(toyotaBrandID).build());

			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Mustang").productionYear(1969).brandId(fordBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Mustang Mach-E").productionYear(2024).brandId(fordBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Puma").productionYear(2024).brandId(fordBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Kuga").productionYear(2024).brandId(fordBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Focus").productionYear(2024).brandId(fordBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Ranger").productionYear(2016).brandId(fordBrandID).build());

			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Clio").productionYear(2019).brandId(renaultBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Austral").productionYear(2019).brandId(renaultBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Symbioz").productionYear(2024).brandId(renaultBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Rafale").productionYear(2015).brandId(renaultBrandID).build());

			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Arteon").productionYear(2021).brandId(volkswagenBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Taigo").productionYear(2024).brandId(volkswagenBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Passat").productionYear(2023).brandId(volkswagenBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("T-Roc").productionYear(2014).brandId(volkswagenBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("Golf").productionYear(2011).brandId(volkswagenBrandID).build());

			carService.addCar(Car.builder().id(UUID.randomUUID()).model("CX-80").productionYear(2024).brandId(mazdaBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("CX-5").productionYear(2016).brandId(mazdaBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("3").productionYear(2017).brandId(mazdaBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("6").productionYear(2015).brandId(mazdaBrandID).build());
			carService.addCar(Car.builder().id(UUID.randomUUID()).model("MX-5").productionYear(2019).brandId(mazdaBrandID).build());
		}
	}
}
