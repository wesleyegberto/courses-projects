const SCENARIO_FRAMES_PER_MOVE = 2;

class Scenario {
  constructor(backgroundImage, speed) {
    this.backgroundImage = backgroundImage;
    this.speed = speed;

    this.x1 = 0;
    this.x2 = width;
  }

  move() {
    if (frameCount % SCENARIO_FRAMES_PER_MOVE != 0) {
      return;
    }

    this.x1 = this.x1 - this.speed;
    this.x2 = this.x2 - this.speed;
    if (this.x1 < -width) {
      this.x1 = width;
    }
    if (this.x2 < -width) {
      this.x2 = width;
    }
  }

  render() {
    image(this.backgroundImage, this.x1, 0, width, height);
    image(this.backgroundImage, this.x2, 0, width, height);
  }

  animate() {
    this.move();
    this.render();
  }
}