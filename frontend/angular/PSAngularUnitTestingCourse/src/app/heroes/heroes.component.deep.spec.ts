import { By } from '@angular/platform-browser';
import { Directive, Input, HostListener } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs/internal/observable/of';

import { HeroesComponent } from './heroes.component';
import { HeroComponent } from '../hero/hero.component';
import { HeroService } from '../hero.service';

/**
 * Directive to simulate a routerLink, when clicked it will
 * save the path to be inspected later.
 */
@Directive({
  // tslint:disable-next-line: directive-selector
  selector: '[routerLink]',
  // host: { '(click)': 'onClick()' }
})
class RouterLinkStubDirective {
  @Input('routerLink') linkParams: string;

  navigateTo: any = null;

  @HostListener('click')
  onClick() {
    this.navigateTo = this.linkParams;
  }
}

describe('HeroComponent (deep tests)', () => {
  let fixture: ComponentFixture<HeroesComponent>;
  let mockHeroService;

  let HEROES: Array<any> = [];

  beforeEach(() => {
    HEROES = [
      { id: 1337, name: 'Duke', strength: 10 },
      { id: 42, name: 'Tom', strength: 12 }
    ];

    mockHeroService = jasmine.createSpyObj(['getHeroes', 'addHero', 'deleteHero']);

    TestBed.configureTestingModule({
      declarations: [
        HeroesComponent,
        HeroComponent,
        RouterLinkStubDirective
      ],
      providers: [
        // here we use provide to inject our mock when a component needs a HeroService
        { provide: HeroService, useValue: mockHeroService }
      ]
    });
    fixture = TestBed.createComponent(HeroesComponent);
  });

  it('should render each hero as a HeroComponent', () => {
    mockHeroService.getHeroes.and.returnValue(of(HEROES));
    // runs ngOnInit
    fixture.detectChanges();

    const heroComponentsDEs = fixture.debugElement.queryAll(By.directive(HeroComponent));
    expect(heroComponentsDEs.length).toBe(2);

    for (let i = 0; i < heroComponentsDEs.length; i++) {
      expect(heroComponentsDEs[i].componentInstance.hero).toEqual(HEROES[i]);
    }
  });

  it('should call heroService.delete when the HeroComponent\'s delete button is clicked', () => {
    mockHeroService.getHeroes.and.returnValue(of(HEROES));
    spyOn(fixture.componentInstance, 'delete');

    fixture.detectChanges();

    const heroComponents = fixture.debugElement.queryAll(By.directive(HeroComponent));

    // finds the button and trigger its event passing the $event objet used in the callback
    heroComponents[0].query(By.css('button'))
      .triggerEventHandler('click', { stopPropagation: () => {}});

    // triggering the events from the child
    (<HeroComponent>heroComponents[0].componentInstance).delete.emit(undefined);
    // triggering the event using only its name (won't be affected by refactoring and we need to change this when the template is changed)
    heroComponents[0].triggerEventHandler('delete', null);

    expect(fixture.componentInstance.delete).toHaveBeenCalledWith(HEROES[0]);
  });

  it('should add a new hero when the add button is clicked', () => {
    mockHeroService.getHeroes.and.returnValue(of(HEROES));
    fixture.detectChanges();
    const name = 'Uzumaki Naruto';
    mockHeroService.addHero.and.returnValue(of({ id: 203, name, strength: 100 }));

    const inputElement = fixture.debugElement.query(By.css('input')).nativeElement;
    const addButton = fixture.debugElement.queryAll(By.css('button'))[0];

    inputElement.value = name;
    addButton.triggerEventHandler('click', null);
    // we need to call detection to trigger the updates
    fixture.detectChanges();

    const heroesLiDEs = fixture.debugElement.queryAll(By.css('li'));
    expect(heroesLiDEs.length).toBe(3);
    expect(heroesLiDEs[2].nativeElement.textContent).toContain(name);
  });

  it('should have the correct route for the first hero', () => {
    mockHeroService.getHeroes.and.returnValue(of(HEROES));
    fixture.detectChanges();
    const heroComponents = fixture.debugElement.queryAll(By.directive(HeroComponent));

    // captures the stub instance
    const routerLink = heroComponents[0]
      .query(By.directive(RouterLinkStubDirective))
      .injector.get(RouterLinkStubDirective);

    heroComponents[0].query(By.css('a')).triggerEventHandler('click', null);

    // check if the path is correct
    expect(routerLink.navigateTo).toBe('/detail/1337');
  });
});
