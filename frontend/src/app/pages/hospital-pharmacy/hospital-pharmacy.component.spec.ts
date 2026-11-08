import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalPharmacyComponent } from './hospital-pharmacy.component';

describe('HospitalPharmacyComponent', () => {
  let component: HospitalPharmacyComponent;
  let fixture: ComponentFixture<HospitalPharmacyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HospitalPharmacyComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HospitalPharmacyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
