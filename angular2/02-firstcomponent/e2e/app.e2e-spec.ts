import { FirstcomponentPage } from './app.po';

describe('firstcomponent App', function() {
  let page: FirstcomponentPage;

  beforeEach(() => {
    page = new FirstcomponentPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
