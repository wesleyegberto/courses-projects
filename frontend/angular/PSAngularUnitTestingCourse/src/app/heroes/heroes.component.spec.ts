import { of } from 'rxjs/internal/observable/of';

import { HeroesComponent } from './heroes.component';

describe('HeroesComponent', () => {
  let HEROES: Array<any>;
  let mockHeroService: any;

  let component: HeroesComponent;

  beforeEach(() => {
    HEROES = [
      { id: 1, name: 'First Hero', strength: 9 },
      { id: 2, name: 'Second Hero', strength: 12 },
      { id: 3, name: 'Third Hero', strength: 34 }
    ];

    // mock the dependency from our component
    mockHeroService = jasmine.createSpyObj(['getHeroes', 'addHero', 'deleteHero']);

    component = new HeroesComponent(mockHeroService);
  });

  describe('delete', () => {
    it('should remove the indicated hero from the heroes list', () => {
      component.heroes = HEROES;
      mockHeroService.deleteHero.and.returnValue(of(true));
      const heroToRemove = HEROES[1];

      component.delete(heroToRemove);

      expect(component.heroes.length).toBe(2);
      expect(component.heroes.indexOf(heroToRemove)).toBe(-1);
    });
  });
});
