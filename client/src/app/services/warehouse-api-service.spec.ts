import { TestBed } from '@angular/core/testing';

import { WarehouseApiService } from './warehouse-api-service';

describe('WarehouseApiServiceService', () => {
  let service: WarehouseApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WarehouseApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
