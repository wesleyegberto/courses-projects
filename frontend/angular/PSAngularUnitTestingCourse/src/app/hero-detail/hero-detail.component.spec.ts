import { FormsModule } from '@angular/forms';
import { ComponentFixture, TestBed, fakeAsync, tick, flush, async } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { HeroDetailComponent } from './hero-detail.component';
import { HeroService } from '../hero.service';
import { of } from 'rxjs/internal/observable/of';

describe('HeroDetailComponent', () => {
  let mockActivatedRoute, mockHeroService, mockLocation;

  let fixture: ComponentFixture<HeroDetailComponent>;

  beforeEach(() => {
    mockActivatedRoute = {
      snapshot: { paramMap: { get: () => '42' } }
    };

    mockHeroService = jasmine.createSpyObj(['getHero', 'updateHero']);
    mockLocation = jasmine.createSpyObj(['back']);

    TestBed.configureTestingModule({
      imports: [FormsModule],
      declarations: [HeroDetailComponent],
      providers: [
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
        { provide: HeroService, useValue: mockHeroService },
        { provide: Location, useValue: mockLocation }
      ]
    });
    fixture = TestBed.createComponent(HeroDetailComponent);
  });

  it('should render hero name in a h2 tag', () => {
    mockHeroService.getHero.and.returnValue(of({ id: 42, name: 'Adam', strength: 100 }));

    fixture.detectChanges();

    expect(fixture.nativeElement.querySelector('h2').textContent)
      .toContain('ADAM');
  });

  // we can receive `done` to communicate when the test finishes
  // (but is slow - will hang the runner until the test ends)
  it('should save later (blocking until timeout)', (done) => {
    mockHeroService.getHero.and.returnValue(of({ id: 42, name: 'Adam', strength: 100 }));
    mockHeroService.updateHero.and.returnValue(of({}));
    fixture.detectChanges();

    fixture.componentInstance.saveWithTimeout();

    setTimeout(() => {
      expect(mockHeroService.updateHero).toHaveBeenCalled();
      done();
    }, 300);
  });

  // test async code using `fakeAsync` utility where we can manipulate the Zone.js' clock
  it('should save later (fast forward)', fakeAsync(() => {
    mockHeroService.getHero.and.returnValue(of({ id: 42, name: 'Adam', strength: 100 }));
    mockHeroService.updateHero.and.returnValue(of({}));
    fixture.detectChanges();

    fixture.componentInstance.saveWithTimeout();
    // fast forward the clock (can be done because Angular runs inside
    // Zone.js where we can manipulate the clock)
    tick(300); // only used when we know the time it takes

    // flush force the Zone.js to look for the first pending callback
    // and fast forward by the time needed
    flush(); // better wat

    expect(mockHeroService.updateHero).toHaveBeenCalled();
  }));

  // test async code using `async` utility
  it('should save later', async(() => {
    mockHeroService.getHero.and.returnValue(of({ id: 42, name: 'Adam', strength: 100 }));
    mockHeroService.updateHero.and.returnValue(of({}));
    fixture.detectChanges();

    fixture.componentInstance.saveWithPromise();

    // will wait until all pending promises resolves thenselves (Zone.js knows if there is any pending promise)
    fixture.whenStable()
      .then(() => expect(mockHeroService.updateHero).toHaveBeenCalled());
  }));
});
