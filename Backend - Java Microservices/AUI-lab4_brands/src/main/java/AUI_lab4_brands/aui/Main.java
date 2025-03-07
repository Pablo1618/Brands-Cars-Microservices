package AUI_lab4_brands.aui;

import AUI_lab4_brands.aui.Brand.Brand;
import AUI_lab4_brands.aui.Brand.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Component
	public static class DataInitializer {

		private final BrandService brandService;

		@Autowired
		public DataInitializer(BrandService brandService) {
			this.brandService = brandService;
			initializeData();
		}

		private void initializeData() {

			UUID toyotaBrandID = UUID.fromString("11111111-1234-48f6-96d0-95d3ec2913fd");
			UUID fordBrandID = UUID.fromString("22222222-1234-48f6-96d0-95d3ec2913fd");
			UUID renaultBrandID = UUID.fromString("33333333-1234-48f6-96d0-95d3ec2913fd");
			UUID volkswagenBrandId = UUID.fromString("44444444-1234-48f6-96d0-95d3ec2913fd");
			UUID mazdaBrandID = UUID.fromString("55555555-1234-48f6-96d0-95d3ec2913fd");

			Brand toyota = Brand.builder().id(toyotaBrandID).name("Toyota").build();
			Brand ford = Brand.builder().id(fordBrandID).name("Ford").build();
			Brand renault = Brand.builder().id(renaultBrandID).name("Renault").build();
			Brand volkswagen = Brand.builder().id(volkswagenBrandId).name("Volkswagen").build();
			Brand mazda = Brand.builder().id(mazdaBrandID).name("Mazda").build();

			brandService.initializeBrand(toyota);
			brandService.initializeBrand(ford);
			brandService.initializeBrand(renault);
			brandService.initializeBrand(volkswagen);
			brandService.initializeBrand(mazda);
		}
	}
}
