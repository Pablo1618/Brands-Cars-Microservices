import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface Brand {
  id: string;
  brandName: string;
}

@Injectable({
  providedIn: 'root',
})
export class BrandsService {
  private apiUrl = 'api/brands';

  constructor(private http: HttpClient) { }

  getBrands(): Observable<Brand[]> {
    return this.http.get<Brand[]>(this.apiUrl);
  }

  removeBrand(brandName: string): void {
    this.http.delete<{ message: string }>(`${this.apiUrl}/${brandName}`)
      .subscribe(({ message }) => console.log(message));
  }

  addBrand(newBrand: { id: string; brandName: string }): Observable<any> {
    return this.http.post<any>(this.apiUrl, newBrand);
  }

  editBrand(newBrand: { id: string; brandName: string }): Observable<any> {
    return this.http.put<any>(`api/brands/${newBrand.id}`, newBrand);
  }
}
