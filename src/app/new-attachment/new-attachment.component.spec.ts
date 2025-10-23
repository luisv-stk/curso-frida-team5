import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewAttachmentComponent } from './new-attachment.component';

describe('NewAttachmentComponent', () => {
  let component: NewAttachmentComponent;
  let fixture: ComponentFixture<NewAttachmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewAttachmentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewAttachmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
