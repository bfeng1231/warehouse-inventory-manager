import { TestBed } from '@angular/core/testing';

import { ContainerApiService } from './container-api.service';

describe('ContainerApiService', () => {
  let service: ContainerApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContainerApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
