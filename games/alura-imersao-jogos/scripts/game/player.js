const PLAYER_MINIMUM_X_OFFSET = 20;

const PLAYER_WIDTH = 110;
const PLAYER_HEIGHT = 140;
const PLAYER_X_HITBOX = 100;

const PLAYER_SPRITE_WIDTH = 220;
const PLAYER_SPRITE_HEIGHT = 270;

const PLAYER_SPRITE_IMAGE = 'images/character/running.png';
const PLAYER_SPRITE_QTY_STEPS = 16;
const PLAYER_SPRITE_QTY_PER_ROW = 4;

const PLAYER_FRAMES_PER_RENDER = 4;

class Player {
  constructor(playerSprite) {
    this.points = 0;

    this.playerSprite = playerSprite;
    this.currentAnimationStep = 0;
    this.spriteXOffset = 0;
    this.spriteYOffset = 0;

    this.x = PLAYER_MINIMUM_X_OFFSET;
    this.initialY = height - PLAYER_HEIGHT - SCENARIO_Y_OFFSET;
    this.y = this.initialY;

    this.width = PLAYER_WIDTH;
    this.height = PLAYER_HEIGHT;

    this.gravity = 3;
    this.verticalSpeed = 0;
    this.isJumping = false;
    this.isDoubleJumping = false;
  }

  animate() {
    this.move();
    this.render();
  }

  move() {
    if (frameCount % PLAYER_FRAMES_PER_RENDER != 0) {
      return;
    }

    this.currentAnimationStep++;

    let spriteColIndex;
    let spriteRowIndex;

    if (this.currentAnimationStep >= PLAYER_SPRITE_QTY_STEPS) {
      this.currentAnimationStep = 0;
    }

    spriteColIndex = (this.currentAnimationStep % PLAYER_SPRITE_QTY_PER_ROW);
    spriteRowIndex = Math.floor(this.currentAnimationStep / PLAYER_SPRITE_QTY_PER_ROW);

    this.spriteXOffset = spriteColIndex * PLAYER_SPRITE_WIDTH;
    this.spriteYOffset = spriteRowIndex * PLAYER_SPRITE_HEIGHT;

    // console.log(this.currentAnimationStep, spriteColIndex, spriteRowIndex,
    //            this.characterSpriteXOffset, this.characterSpriteYOffset);
  }

  render() {
    image(this.playerSprite,
      this.x, this.y,
      PLAYER_WIDTH, PLAYER_HEIGHT,
      this.spriteXOffset, this.spriteYOffset,
      PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
  }

  jump() {
    if (this.isJumping && this.isDoubleJumping) {
      return;
    }
    this.verticalSpeed = -50;
    if (this.isJumping) {
      this.isDoubleJumping = true;
      actionDoubleJumpSound.play(undefined, undefined, 0.5);
    } else {
      this.isJumping = true;
      actionJumpSound.play(undefined, undefined, 0.5);
    }
  }

  applyGravity() {
    this.y = this.y + this.verticalSpeed;
    this.verticalSpeed = this.verticalSpeed + this.gravity;

    if (this.y > this.initialY) {
      this.y = this.initialY;
      this.isJumping = false;
      this.isDoubleJumping = false;
    }
  }

  collideWith(object) {
    noFill();
    rect(this.x, this.y, this.width, this.height);
    rect(object.x, object.y, object.width, object.height);

    const collided = collideRectRect(
      this.x, this.y, this.width * HITBOX_PRECISION, this.height * HITBOX_PRECISION,
      object.x, object.y, object.width * HITBOX_PRECISION, object.height * HITBOX_PRECISION
    );
    if (collided) {
      console.error('M O R R E U');
      gameOverMusic.play(undefined, undefined, 0.5);
      noLoop();
    } else {
      if (!object.contabilized && this.x > (object.x + object.width)) {
        object.contabilized = true;
        eventEnemyDodge.play(undefined, undefined, 0.6);
        this.points += 1;
      }
    }
  }

  intersects(object, otherObject) {
    var d = dist(object.x, object.y, otherObject.x, otherObject.y);
    if (d < PLAYER_X_HITBOX) {
      console.error('M O R R E U!');
    }
  }
}