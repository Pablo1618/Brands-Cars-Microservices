import { Component, model } from '@angular/core';
import { BrandsService } from './brands/brands.service';
import { CarsService } from './cars/cars.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [CommonModule, FormsModule],
})

export class AppComponent {

  brands: { id: string; brandName: string }[] = [];

  cars: { id: string; modelName: string }[] = [];

  carInfoElement: {
    brandName: string;
    modelName: string;
    productionYear: number;
    id: string;
    brandId: string
  } | null = null;

  newCar = {
    id: this.generateRandomId(),
    modelName: '',
    productionYear: 2024,
    brandName: ''
  };

  newBrand = {
    id: this.generateRandomId(),
    brandName: ''
  };

  currentPage = "Home";

  expandedBrandId: string | null = null;
  expandedCarId: string | null = null;

  constructor(
    private brandService: BrandsService,
    private carService: CarsService
  ) { }


  showBrandsList(): void {
    this.brandService.getBrands().subscribe((brands) => {
      this.brands = brands;
      this.currentPage = "BrandsList";
    });
  }

  showCarsList(): void {
    this.carService.getCars().subscribe((cars) => {
      this.cars = cars;
      this.currentPage = "CarsList";
      this.expandedBrandId = null;
    });
  }

  showNewCarCreateForm(): void {
    this.currentPage = "CarCreationForm";
    this.newCar.id = this.generateRandomId();
    this.newCar.modelName = '';
    this.newCar.brandName = '';
    this.newCar.productionYear = 2024;
  }

  showNewBrandCreateForm(): void {
    this.currentPage = "BrandCreationForm";
    this.newBrand.id = this.generateRandomId();
    this.newBrand.brandName = '';
  }

  showCarEditForm(carId: string): void {
    this.currentPage = "CarEditForm";
    this.toggleCarDetails(carId);

    this.carService.getCarInfo(carId).subscribe(
      (carInfoReceived) => {
        this.newCar.id = carInfoReceived.id;
        this.newCar.modelName = carInfoReceived.modelName;
        this.newCar.productionYear = carInfoReceived.productionYear;
        this.newCar.brandName = carInfoReceived.brandName;
      }
    );
  }

  showBrandEditForm(brandId: string, brandName: string): void {
    this.currentPage = "BrandEditForm";
    this.newBrand.id = brandId;
    this.newBrand.brandName = brandName;
  }

  toggleBrandDetails(brandId: string, brandName: string): void {
    console.log("Shownig brand details..");
    if (this.expandedBrandId === brandId) {
      this.expandedBrandId = null;
      this.cars = [];
    } else {
      this.expandedBrandId = brandId;

      console.log("Getting cars from " + brandName + "...");
      this.carService.getCarsByBrand(brandName).subscribe((cars) => {
        this.cars = cars;
      });
    }
  }

  toggleCarDetails(carId: string): void {
    console.log("Showing car details...");
    if (this.expandedCarId === carId) {
      this.expandedCarId = null;
      this.carInfoElement = null;
    } else {
      this.expandedCarId = carId;

      this.carService.getCarInfo(carId).subscribe(
        (carInfoElement) => {
          this.carInfoElement = carInfoElement;
          console.log("Car details loaded:", this.carInfoElement);
        }
      );
    }
  }

  deleteBrand(brandName: string): void {
    console.log("Deleting brand " + brandName + "...");
    this.brandService.removeBrand(brandName);
    // Aby usuniecie bylo widoczne od razu bez odswiezania strony filtrujemy liste
    this.brands = this.brands.filter(brand => brand.brandName !== brandName);
  }

  deleteCar(carId: string): void {
    console.log("Deleting car " + carId + "...");
    this.carService.removeCar(carId);
    // Aby usuniecie bylo widoczne od razu bez odswiezania strony filtrujemy liste
    this.cars = this.cars.filter(car => car.id !== carId);
  }

  generateRandomId(): string {
    // UUID w formacie: (8-4-4-4-12)
    const randomSegment = (length: number) => {
      return Math.floor((1 + Math.random()) * Math.pow(16, length)).toString(16).substring(1);
    };

    return `${randomSegment(8)}-${randomSegment(4)}-${randomSegment(4)}-${randomSegment(4)}-${randomSegment(12)}`;
  }

  submitNewCarCreation(): void {
    this.carService.addCar(this.newCar).subscribe(() => {
      console.log("New car added successfully");
      this.newCar = { id: this.generateRandomId(), modelName: '', productionYear: 0, brandName: '' }
    });
  }

  submitCarEdit(): void {
    this.carService.editCar(this.newCar).subscribe(() => {
      console.log("Car edited successfully");
      this.showCarsList(); // Aby odswiezyc liste
      this.currentPage = "CarsList";
      this.toggleCarDetails(this.newCar.id);
      this.newCar = { id: this.generateRandomId(), modelName: '', productionYear: 0, brandName: '' }
    });
  }

  submitNewBrandCreation(): void {
    this.brandService.addBrand(this.newBrand).subscribe(() => {
      this.newBrand = { id: this.generateRandomId(), brandName: '' }
      console.log("New brand added successfully");
    });
  }

  submitBrandEdit(): void {
    this.brandService.editBrand(this.newBrand).subscribe(() => {
      this.showBrandsList(); // Aby odswiezyc liste
      this.currentPage = "BrandsList";
      this.newBrand = { id: this.generateRandomId(), brandName: '' }
      console.log("Brand edited successfully");
    });
  }





}
