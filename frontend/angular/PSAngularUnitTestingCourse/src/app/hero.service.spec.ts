import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { HeroService } from './hero.service';
import { MessageService } from './message.service';
import { Hero } from './hero';

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

  // group the getHeroes tests together
  describe('getHeroes', () => {
    it('should call get with the correct URL', () => {
      heroSvc.getHeroes().subscribe();

      // it expects one request to the given URL
      httpTestingController.expectOne('/api/heroes');

      // asserts that only the given URL was called (other calls will fail)
      httpTestingController.verify();
    });

    it('should update the name with Super prefix', () => {
      heroSvc.getHeroes()
        // we can assert the result in the subscriber
        .subscribe(heroes => {
          expect(heroes).toBeTruthy();
          expect(heroes.length).toBe(1);
          const hero = heroes[0];
          expect(hero.name).toBe('Super Odair');
          expect(hero.strength).toBe(42);
        });

      // it expects one request to the given URL
      const req = httpTestingController.expectOne('/api/heroes');
      req.flush([ { id: 42, name: 'Odair', strength: 42 } ]);

      // asserts that only the given URL was called (other calls will fail)
      httpTestingController.verify();
    });
  });

  describe('addHero', () => {
    it('should save a hero', () => {
      heroSvc.addHero({ name: 'Mr. Anderson', strength: 42 } as Hero)
        .subscribe(hero => {
          expect(hero).toBeTruthy();
          expect(hero.id).toBeGreaterThan(0);
          expect(hero.name).toBe('Neo');
          expect(hero.strength).toBe(42);
        });

      const req = httpTestingController.expectOne('/api/heroes');
      // verify the request body to assert if everything we passed was sent correctly
      expect(req.request.body.name).toBe('Mr. Anderson');
      expect(req.request.body.strength).toBe(42);

      req.flush({ id: 1, name: 'Neo', strength: 42 });
      httpTestingController.verify();
    });
  });

  describe('getHero', () => {
    it('should call get with the correct URL', () => {
      heroSvc.getHero(42).subscribe(hero => {
          expect(hero).toBeTruthy();
          expect(hero.name).toBe('Adam');
          expect(hero.strength).toBe(42);
        });

      // it expects one request to the given URL
      const req = httpTestingController.expectOne('/api/heroes/42');
      // returns the value so the observable is returned
      req.flush({ id: 42, name: 'Adam', strength: 42 });
      // asserts that only the given URL was called (other calls will fail)
      httpTestingController.verify();
    });
  });
});
