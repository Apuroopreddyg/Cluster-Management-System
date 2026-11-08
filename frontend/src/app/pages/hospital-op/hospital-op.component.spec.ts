import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalOpComponent } from './hospital-op.component';

describe('HospitalOpComponent', () => {
  let component: HospitalOpComponent;
  let fixture: ComponentFixture<HospitalOpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HospitalOpComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HospitalOpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
