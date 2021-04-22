import { TestBed } from '@angular/core/testing';

import { GetLikeService } from './get-like.service';

describe('GetLikeService', () => {
  let service: GetLikeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetLikeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
