import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CallDeleteComponent } from './call-delete.component';

describe('CallDeleteComponent', () => {
  let component: CallDeleteComponent;
  let fixture: ComponentFixture<CallDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CallDeleteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CallDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
