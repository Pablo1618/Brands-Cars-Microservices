import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';


interface Car {
  id: string;
  modelName: string;
}

interface CarInfoElement {
  brandName: string;
  modelName: string;
  productionYear: number;
  id: string;
  brandId: string;
}

@Injectable({
  providedIn: 'root',
})
export class CarsService {
  private apiUrl = 'api/cars';

  constructor(private http: HttpClient) { }

  getCars(): Observable<Car[]> {
    return this.http.get<Car[]>(this.apiUrl);
  }

  getCarsByBrand(brandName: string): Observable<Car[]> {
    return this.http.get<Car[]>(`api/cars/brandname/${brandName}`).pipe(
      catchError((error) => {
        if (error.status === 404) { // W przypadku braku samochodów danej marki
          return of([]);
        }
        throw error;
      })
    );
  }

  getCarInfo(carId: string): Observable<CarInfoElement> {
    return this.http.get<CarInfoElement>(`api/cars/${carId}`);
  }

  removeCar(carId: string): void {
    this.http.delete<{ message: string }>(`${this.apiUrl}/${carId}`)
      .subscribe(({ message }) => console.log(message));
  }

  addCar(newCar: { id: string; modelName: string; productionYear: number; brandName: string }): Observable<any> {
    return this.http.post<any>(this.apiUrl, newCar);
  }

  editCar(newCar: { id: string; modelName: string; productionYear: number; brandName: string }): Observable<any> {
    return this.http.put<any>(`api/cars/${newCar.id}`, newCar);
  }

}
