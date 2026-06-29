import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCollection } from './view-collection';

describe('ViewCollection', () => {
  let component: ViewCollection;
  let fixture: ComponentFixture<ViewCollection>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewCollection],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewCollection);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
