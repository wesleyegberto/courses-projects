class Sprite {
  constructor(width, height, spriteImage, qtyStepsInAnimation, spriteQtyPerRow, spriteStartIndex = 0) {
    this.width = width;
    this.height = height;

    this.spriteImage = spriteImage;

    this.qtyStepsInAnimation = qtyStepsInAnimation;
    this.spriteQtyPerRow = spriteQtyPerRow;
    let spriteQtyRow = Math.ceil(qtyStepsInAnimation / spriteQtyPerRow);

    this.spriteWidth = this.spriteImage.width / this.spriteQtyPerRow;
    this.spriteHeight = this.spriteImage.height / spriteQtyRow;

    this.currentAnimationStep = spriteStartIndex;
    this.currentSpriteXOffset = 0;
    this.currentSpriteYOffset = 0;

    // console.debug(`Sprite {steps=${this.qtyStepsInAnimation}, perRow=${this.spriteQtyPerRow}, rows=${this.spriteQtyRow}}`);
  }

  move() {
    this.currentAnimationStep++;

    let spriteColIndex;
    let spriteRowIndex;

    if (this.currentAnimationStep >= this.qtyStepsInAnimation) {
      this.currentAnimationStep = 0;
    }

    spriteColIndex = this.currentAnimationStep % this.spriteQtyPerRow;
    spriteRowIndex = Math.floor(this.currentAnimationStep / this.spriteQtyPerRow);

    this.currentSpriteXOffset = spriteColIndex * this.spriteWidth;
    this.currentSpriteYOffset = spriteRowIndex * this.spriteHeight;

    // console.log(this.currentAnimationStep, spriteColIndex, spriteRowIndex,
    //            this.currentSpriteXOffset, this.currentSpriteYOffset);
  }

  renderAt(x, y) {
    image(
      this.spriteImage,
      x, y,
      this.width, this.height,
      this.currentSpriteXOffset, this.currentSpriteYOffset,
      this.spriteWidth, this.spriteHeight
    );
  }

  animateAt(x, y) {
    this.move();
    this.renderAt(x, y);
  }
}
