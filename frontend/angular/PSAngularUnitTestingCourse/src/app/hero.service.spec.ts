import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { HeroService } from './hero.service';
import { MessageService } from './message.service';

describe('HeroService', () => {
  let httpTestingController: HttpTestingController;
  let mockMessageService;

  let heroSvc: HeroService;

  beforeEach(() => {
    mockMessageService = jasmine.createSpyObj(['add']);

    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule ],
      providers: [
        HeroService,
        { provide: MessageService, useValue: mockMessageService }
      ]
    });

    // gets the controller from container
    httpTestingController = TestBed.get(HttpTestingController);
    heroSvc = TestBed.get(HeroService);
  });

  // example how to use `inject()` method
  it('should be able to create a service', inject([HeroService], (heroService: HeroService) => {
    expect(heroService).toBeDefined();
  }));

  describe('getHero', () => {
    it('should call get with the correct URL', () => {
      heroSvc.getHero(4).subscribe();

      // it expects one request to the given URL
      const req = httpTestingController.expectOne('api/heroes/4');
      // returns the value so the observable is returned
      req.flush({ id: 42, name: 'Adam', strength: 42 });
      // asserts that only the given URL was called (other calls will fail)
      httpTestingController.verify();
    });
  });
});
