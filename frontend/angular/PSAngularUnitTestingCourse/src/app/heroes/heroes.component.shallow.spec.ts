import { By } from '@angular/platform-browser';
import { Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeroesComponent } from './heroes.component';
import { HeroService } from '../hero.service';
import { of } from 'rxjs/internal/observable/of';

describe('HeroComponent (shallow tests)', () => {
  let fixture: ComponentFixture<HeroesComponent>;
  let mockHeroService;

  let HEROES: Array<any> = [];

  beforeEach(() => {
    HEROES = [
      { id: 1337, name: 'Duke', strength: 10 },
      { id: 42, name: 'Tom', strength: 12 }
    ];

    mockHeroService = jasmine.createSpyObj(['getHeroes', 'addHero', 'deleteHero']);

    @Component({
      selector: 'app-hero',
      template: '<div></div>'
    })
    class MockHeroComponent {
      @Input() hero: any;
    }

    TestBed.configureTestingModule({
      declarations: [
        HeroesComponent,
        MockHeroComponent
      ],
      providers: [
        // here we use provide to inject our mock when a component needs a HeroService
        { provide: HeroService, useValue: mockHeroService }
      ],
      // we don't need this because we're providing a fake component
      // schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(HeroesComponent);
  });

  it('should set heroes correctly from the service', () => {
    mockHeroService.getHeroes.and.returnValue(of(HEROES));
    fixture.detectChanges();

    expect(fixture.componentInstance.heroes.length).toBe(HEROES.length);
  });

  it('should create one li for each hero', () => {
    mockHeroService.getHeroes.and.returnValue(of(HEROES));
    fixture.detectChanges();

    const listLiDe = fixture.debugElement.queryAll(By.css('li'));
    expect(listLiDe.length).toBe(2);
  });
});
