describe("my unit", () => {
  let sut; // system under test

  beforeEach(() => {
    sut = {};
  });

  it("should be true if true", () => {
    // Montagem do cen√°rio
    sut.a = false;
    // A√ß√£o
    sut.a = true;
    // Asser√ß√£o
    expect(sut.a).toBe(true);
  });
});
