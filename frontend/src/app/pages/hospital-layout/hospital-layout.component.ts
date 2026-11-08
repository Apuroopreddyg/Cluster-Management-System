import { Component, inject } from '@angular/core';
import { RouterOutlet, RouterLink, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-hospital-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './hospital-layout.component.html',
  styleUrl: './hospital-layout.component.css'
})
export class HospitalLayoutComponent {

  private route = inject(ActivatedRoute);

  hospitalId = this.route.snapshot.paramMap.get('hospitalId') ?? '';

  getHospitalRoute(page: string): string[] {
    return ['/hospitals', this.hospitalId, page];
  }
}