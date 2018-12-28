import { FirstservicePage } from './app.po';

describe('firstservice App', function() {
  let page: FirstservicePage;

  beforeEach(() => {
    page = new FirstservicePage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
