import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleContainerComponent } from './single-container.component';

describe('SingleContainerComponent', () => {
  let component: SingleContainerComponent;
  let fixture: ComponentFixture<SingleContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SingleContainerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SingleContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
