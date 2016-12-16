import { DatabindPage } from './app.po';

describe('databind App', function() {
  let page: DatabindPage;

  beforeEach(() => {
    page = new DatabindPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
