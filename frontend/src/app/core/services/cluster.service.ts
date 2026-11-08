import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cluster } from '../../models/cluster.model';
import { Hospital } from '../../models/hospital.model';

@Injectable({
  providedIn: 'root'
})
export class ClusterService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/clusters';

  getAllClusters(): Observable<Cluster[]> {
    return this.http.get<Cluster[]>(this.apiUrl);
  }

  createCluster(cluster: Partial<Cluster>): Observable<Cluster> {
    return this.http.post<Cluster>(this.apiUrl, cluster);
  }

  getClusterHospitals(clusterId: string): Observable<Hospital[]> {
    return this.http.get<Hospital[]>(
      `${this.apiUrl}/${clusterId}/hospitals`
    );
  }
}