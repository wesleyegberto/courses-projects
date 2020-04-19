import { NO_ERRORS_SCHEMA, DebugElement } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';

import { HeroComponent } from './hero.component';

describe('HeroComponent (shallow tests)', () => {
  let fixture: ComponentFixture<HeroComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HeroComponent],
      // using NO_ERRORS_SCHEMA ignore unknown elements (not recommended when test the template)
      schemas: [NO_ERRORS_SCHEMA]
    });
    fixture = TestBed.createComponent(HeroComponent);
  });

  const createHero = () => {
    return { id: 1337, name: 'Duke', strength: 3 };
  };

  it('should have the correct hero', () => {
    fixture.componentInstance.hero = createHero();

    expect(fixture.componentInstance.hero.name).toBe('Duke');
  });

  it('should render the hero name inside an anchor tag', () => {
    fixture.componentInstance.hero = createHero();
    // run change detection to update the models
    fixture.detectChanges();

    // `nativeElement` points to the JS DOM, its depends on runtime
    // maybe the test in running on a non-browser platform that doesn't provide all DOM API
    const heroEl: HTMLElement = fixture.nativeElement;
    expect(heroEl.querySelector('a').textContent).toContain('Duke');

    // `debugElment` is wrapper around the native elements to work safely accross platforms
    const debugElement: DebugElement = fixture.debugElement;
    expect(debugElement.query(By.css('a')).nativeElement.textContent)
      .toContain('Duke');
  });
});
