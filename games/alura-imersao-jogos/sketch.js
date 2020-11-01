let SCENARIO_SPEED = 10;
let lastUpdate = 0;

const SCENARIO_Y_OFFSET = 10;

const HITBOX_PRECISION = 0.7;

let gameMusic;
let gameOverMusic;
let eventEnemyDodge;
let actionJumpSound;
let actionDoubleJumpSound;

let scenarioImage;
let scenario;

let playerCharacterSprite;
let player;

let enemyWaterDropImage;
let enemyWaterDrop;

function preload() {
  gameMusic = loadSound('sounds/game_music.mp3');
  gameOverMusic = loadSound('sounds/smb_die.wav');
  eventEnemyDodge = loadSound('sounds/smb_coin.wav');
  actionJumpSound = loadSound('sounds/smb_jump-small.wav');
  actionDoubleJumpSound = loadSound('sounds/smb_jump-super.wav');

  scenarioImage = loadImage('images/scenario/jungle.png');
  playerCharacterSprite = loadImage(PLAYER_SPRITE_IMAGE);
  enemyWaterDropImage = loadImage(ENEMY_WATER_DROP_SPRITE_IMAGE);
}

function setup() {
  frameRate(60);

  createCanvas(800, 600);
  // gameMusic.play();

  scenario = new Scenario(scenarioImage, SCENARIO_SPEED);

  player = new Player(playerCharacterSprite);
  enemyWaterDrop = new EnemyWaterDrop(enemyWaterDropImage);
}

function draw() {
  scenario.animate();

  player.applyGravity();

  player.animate();
  enemyWaterDrop.animate();

  player.collideWith(enemyWaterDrop);

  drawScore();
  updateDifficult();
}

function mouseClicked() {
  player.jump();
}

function keyPressed() {
  // Arrow Up or Space
  if (keyCode == UP_ARROW || keyCode == 32) {
    player.jump();
  }
}

function drawScore() {
  fill(150);
  rect(width - 250, 10, 240, 80);
  fill(50);
  textSize(32);
  text("Witcher Hunt", (width - 240), 50);
  textSize(22);
  text("Points: " + player.points, (width - 240), 80);
}

function updateDifficult() {
  if (lastUpdate != player.points) {
    SCENARIO_SPEED = SCENARIO_SPEED + player.points;
    lastUpdate = player.points;
  }
}