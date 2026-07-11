import { TestBed } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { appConfig } from './app.config';

describe('appConfig', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: appConfig.providers
    });
  });

  it('provides HttpClient for the standalone app', () => {
    expect(TestBed.inject(HttpClient)).toBeTruthy();
  });
});
