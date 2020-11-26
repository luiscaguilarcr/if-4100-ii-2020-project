import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CallUpdateComponent } from './call-update.component';

describe('CallUpdateComponent', () => {
  let component: CallUpdateComponent;
  let fixture: ComponentFixture<CallUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CallUpdateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CallUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
