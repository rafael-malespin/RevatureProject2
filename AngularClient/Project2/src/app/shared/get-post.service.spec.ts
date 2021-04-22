import { TestBed } from '@angular/core/testing';

import { GetPostService } from './get-post.service';

describe('GetPostService', () => {
  let service: GetPostService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetPostService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
