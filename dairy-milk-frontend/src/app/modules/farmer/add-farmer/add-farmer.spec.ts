import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { AddFarmer } from './add-farmer';

describe('AddFarmer', () => {
  let component: AddFarmer;
  let fixture: ComponentFixture<AddFarmer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddFarmer],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: () => null
              }
            }
          }
        }
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AddFarmer);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
