import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Hospital } from '../../models/hospital.model';
import { HospitalOverview } from '../../models/hospital-overview.model';

@Injectable({
	providedIn: 'root',
})
export class HospitalService {
	private http = inject(HttpClient);

	private apiUrl = 'http://localhost:8080/api/hospitals';

	getAllHospitals(): Observable<Hospital[]> {
		return this.http.get<Hospital[]>(this.apiUrl);
	}

	getHospitalById(id: string): Observable<Hospital> {
		return this.http.get<Hospital>(`${this.apiUrl}/${id}`);
	}

	createHospital(hospital: Omit<Hospital, 'id'>): Observable<Hospital> {
		return this.http.post<Hospital>(this.apiUrl, hospital);
	}

	updateHospital(id: string, hospital: Hospital): Observable<Hospital> {
		return this.http.put<Hospital>(`${this.apiUrl}/${id}`, hospital);
	}

	deleteHospital(id: string): Observable<void> {
		return this.http.delete<void>(`${this.apiUrl}/${id}`);
	}

	getOverview(hospitalId: string): Observable<HospitalOverview> {
		return this.http.get<HospitalOverview>(
			`${this.apiUrl}/${hospitalId}/overview`
		);
	}
}
