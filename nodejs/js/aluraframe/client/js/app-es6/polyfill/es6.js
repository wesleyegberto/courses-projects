if (!Array.prototype.includes) { // <= Edge 13
  console.log('[Polyfill] Array.includes.');
  Array.prototype.includes = function (elemento) {
    return this.indexOf(elemento) != -1;
  };
}