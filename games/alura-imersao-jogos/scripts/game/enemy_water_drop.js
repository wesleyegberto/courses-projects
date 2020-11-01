const ENEMY_WATER_DROP_WIDTH = 90;
const ENEMY_WATER_DROP_HEIGHT = 67;

const ENEMY_WATER_DROP_SPRITE_IMAGE = 'images/enemies/water_drop.png';
const ENEMY_WATER_DROP_QTY_STEPS_ANIMATION = 28;
const ENEMY_WATER_DROP_QTY_STEPS_PER_ROW = 4;

const ENEMY_WATER_DROP_FRAMES_PER_RENDER = 3;

class EnemyWaterDrop extends Enemy {
  constructor(spriteImage) {
    super(width, height, ENEMY_WATER_DROP_WIDTH, ENEMY_WATER_DROP_HEIGHT);

    this.enemySprite = new Sprite(
      this.width, this.height,
      enemyWaterDropImage,
      ENEMY_WATER_DROP_QTY_STEPS_ANIMATION,
      ENEMY_WATER_DROP_QTY_STEPS_PER_ROW
    );

		this.x = width;
    this.y = height - ENEMY_WATER_DROP_HEIGHT - SCENARIO_Y_OFFSET;
  }

  animate() {
    if (frameCount % ENEMY_WATER_DROP_FRAMES_PER_RENDER == 0) {
      if (this.x <= -(ENEMY_WATER_DROP_WIDTH)) {
        this.x = width;
        this.contabilized = false;
      } else {
        this.x -= SCENARIO_SPEED;
      }
    }

    this.enemySprite.animateAt(this.x, this.y);
  }
}
