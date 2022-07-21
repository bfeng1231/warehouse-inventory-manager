import { TestBed } from '@angular/core/testing';

import { WarehouseInfoService } from './warehouse-info.service';

describe('WarehouseInfoService', () => {
  let service: WarehouseInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WarehouseInfoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
