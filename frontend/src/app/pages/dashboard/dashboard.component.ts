import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { Cluster } from '../../models/cluster.model';
import { Hospital } from '../../models/hospital.model';

import { ClusterService } from '../../core/services/cluster.service';
import { HospitalService } from '../../core/services/hospital.service';

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [FormsModule, RouterLink],
    templateUrl: './dashboard.component.html',
    styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

    private clusterService = inject(ClusterService);
    private hospitalService = inject(HospitalService);

    clusters: Cluster[] = [];
    hospitals: Hospital[] = [];

    selectedCluster: Cluster | null = null;

    loading = false;
    errorMessage = '';
    successMessage = '';

    showClusterForm = false;
    showHospitalForm = false;

    newCluster = {
        clusterName: '',
        clusterCode: '',
        location: '',
        status: 'ACTIVE'
    };

    newHospital = {
        hospitalName: '',
        hospitalCode: '',
        location: '',
        status: 'ACTIVE',
        clusterId: ''
    };

    ngOnInit(): void {
        this.loadClusters();
    }

    loadClusters(): void {
        this.loading = true;
        this.clusterService.getAllClusters().subscribe({
            next: (data) => {
                this.clusters = data;
                this.loading = false;
                this.loadClusterCounts();

                if (this.selectedCluster) {
                    const updated = data.find(
                        c => c.id === this.selectedCluster?.id
                    );

                    if (updated) {
                        this.selectCluster(updated);
                    } else {
                        this.selectedCluster = null;
                        this.hospitals = [];
                    }
                }
            },
            error: () => {
                this.errorMessage = 'Unable to load clusters.';
                this.loading = false;
            }
        });
    }

    selectCluster(cluster: Cluster): void {
        this.selectedCluster = cluster;
        this.errorMessage = '';
        this.hospitals = [];
        this.loading = true;

        this.clusterService.getClusterHospitals(cluster.id).subscribe({
            next: (data) => {
                this.hospitals = data;
                this.loading = false;
            },
            error: () => {
                this.hospitals = [];
                this.errorMessage = 'Unable to load hospitals for this cluster.';
                this.loading = false;
            }
        });
    }

    get totalHospitals(): number {
        return this.clusters.reduce(
            (total, cluster) => total + this.getHospitalCount(cluster.id), 0
        );
    }

    getHospitalCount(clusterId: string): number {
        // Counts are fetched when a cluster is selected.
        // This value is updated using the cached counts below.
        return this.hospitalCounts[clusterId] ?? 0;
    }

    hospitalCounts: Record<string, number> = {};

    loadClusterCounts(): void {
        this.clusters.forEach(cluster => {
            this.clusterService.getClusterHospitals(cluster.id).subscribe({
                next: hospitals => {
                    this.hospitalCounts[cluster.id] = hospitals.length;
                }
            });
        });
    }

    get activeClusterCount(): number {
        return this.clusters.filter(
            cluster => cluster.status === 'ACTIVE'
        ).length;
    }

    createCluster(): void {
        this.clusterService.createCluster(this.newCluster).subscribe({
            next: () => {
                this.successMessage = 'Cluster created successfully!';
                this.newCluster = {
                    clusterName: '',
                    clusterCode: '',
                    location: '',
                    status: 'ACTIVE'
                };
                this.showClusterForm = false;
                this.loadClusters();
            },
            error: err => {
                this.errorMessage = err.error?.message || 'Unable to create cluster.';
            }
        });
    }

    openHospitalForm(): void {
        if (!this.selectedCluster) return;

        this.newHospital.clusterId = this.selectedCluster.id;
        this.newHospital.location = this.selectedCluster.location;
        this.showHospitalForm = true;
    }

    createHospital(): void {

        this.hospitalService.createHospital(this.newHospital).subscribe({

            next: () => {
                this.successMessage = 'Hospital added successfully!';

                this.newHospital = {
                    hospitalName: '',
                    hospitalCode: '',
                    location: '',
                    status: 'ACTIVE',
                    clusterId: this.selectedCluster?.id ?? ''
                };

                this.showHospitalForm = false;

                if (this.selectedCluster) {
                    this.selectCluster(this.selectedCluster);
                }

                this.loadClusterCounts();
            },

            error: err => {
                this.errorMessage =
                    err.error?.message || 'Unable to add hospital.';
            }

        });
    }
    refresh(): void {
        this.successMessage = '';
        this.errorMessage = '';
        this.loadClusters();
    }
}