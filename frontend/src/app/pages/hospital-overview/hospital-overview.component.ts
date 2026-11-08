import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { HospitalService } from '../../core/services/hospital.service';
import { HospitalOverview } from '../../models/hospital-overview.model';

@Component({
  selector: 'app-hospital-overview',
  standalone: true,
  templateUrl: './hospital-overview.component.html',
  styleUrl: './hospital-overview.component.css'
})
export class HospitalOverviewComponent implements OnInit {

  private route = inject(ActivatedRoute);
  private hospitalService = inject(HospitalService);

  overview: HospitalOverview | null = null;

  loading = true;
  errorMessage = '';

  ngOnInit(): void {

    const hospitalId =
      this.route.parent?.snapshot.paramMap.get('hospitalId');

    if (!hospitalId) {
      this.errorMessage = 'Hospital ID not found';
      this.loading = false;
      return;
    }

    this.hospitalService.getOverview(hospitalId).subscribe({

      next: (data) => {
        this.overview = data;
        this.loading = false;
      },

      error: () => {
        this.errorMessage = 'Unable to load hospital overview';
        this.loading = false;
      }

    });
  }
}