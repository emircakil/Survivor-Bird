package com.emirhancakil.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	public AdsController adsController;

	public SurvivorBird (AdsController adsController){

		this.adsController = adsController;
	}
	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture birdup;
	Texture birddown;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;
	Texture frontTree;
	Texture backTree;
	Texture frontMountain;
	Texture backMountain;
	Texture apricot;
	Texture deadbird;
	Texture deadScreen;
	Texture fireworks1;
	Texture fireworks2;

	Sound jumpSound;
	Sound deadSound;
	Sound apricotSound;

    Preferences preferences;


	float birdX = 0;
	float birdY = 0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 1f;
	float distance =0;
	float enemyVelocity = 7;
	int frontTreeVelocity = 6;
	int backTreeVelocity = 4;
	int frontMountainVelocity = 2;
	int backMountainVelocity =1;
	int score = 0;
	int backgroundCount =3;
	int apricotRandomX;
	int apricotRandomY;
	int soundCtrl;
	int keep =0;
	int adCounter =0;

	boolean deadmode;
	boolean record = false;

	BitmapFont font;
	BitmapFont gameOver;
	BitmapFont ownScoreFont;
	BitmapFont maxScore;

	Circle birdCircle;
	Rectangle[] enemyCircle;
	Rectangle [] enemyCircle2;
	Rectangle [] enemyCircle3;
	Circle apricotCircle;

	ShapeRenderer shapeRenderer;

	int rndX;
	int rndY;

	int numberOfEnemy = 6;
	float enemyX [] = new float [numberOfEnemy];
	int enemyOfSett [] = new int[numberOfEnemy];
	int enemyOfSett2 [] = new int[numberOfEnemy];
	int enemyOfSett3 [] = new int[numberOfEnemy];
	int enemyOfSettX [] = new int[numberOfEnemy];
	int enemyOfSettX2 [] = new int[numberOfEnemy];
	int frontTreeX [] = new int[backgroundCount];
	int backTreeX [] = new int [backgroundCount];
	int frontMountainX []= new int [backgroundCount];
	int backMountainX [] = new int[backgroundCount];
	int enemyOfSettX3 [] = new int[numberOfEnemy];

	Random random;

	@Override
	public void create () {

		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		birdup = new Texture("birdup.png");
		birddown = new Texture("birddown.png");
		deadbird = new Texture("deadbird.png");
		enemy1 = new Texture("enemyypng.png");
		enemy2 = new Texture("enemyypng.png");
		enemy3 = new Texture("enemyypng.png");
		backMountain = new Texture("backmountain.png");
		frontMountain = new Texture("frontmountain.png");
		backTree = new Texture("backtree.png");
		frontTree = new Texture("fronttree.png");
		apricot = new Texture("apricot3.png");
		deadScreen = new Texture("deadscreen.png");
		fireworks1 = new Texture("fireworks1.png");
		fireworks2 = new Texture("fireworks2.png");

		jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
		deadSound = Gdx.audio.newSound(Gdx.files.internal("dead.mp3"));
		apricotSound = Gdx.audio.newSound(Gdx.files.internal("apricot.wav"));

		soundCtrl = 0;

		preferences = Gdx.app.getPreferences("maxScore");
		preferences.putInteger("maxScore", 0);

		birdX = Gdx.graphics.getWidth()/8;
		birdY = Gdx.graphics.getHeight()/2;
		distance = Gdx.graphics.getWidth()/5;
		birdCircle = new Circle();
		enemyCircle = new Rectangle[numberOfEnemy];
		enemyCircle2 = new Rectangle[numberOfEnemy];
		enemyCircle3 = new Rectangle[numberOfEnemy];

		shapeRenderer = new ShapeRenderer();

		gameOver = new BitmapFont();
		gameOver.setColor(Color.WHITE);
		gameOver.getData().setScale(8);

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		ownScoreFont = new BitmapFont();
		ownScoreFont.setColor(Color.WHITE);
		ownScoreFont.getData().setScale(3);

		maxScore = new BitmapFont();
		maxScore.setColor(Color.WHITE);
		maxScore.getData().setScale(6);

		backMountainX [0] = 0;
		backMountainX [1] = Gdx.graphics.getWidth();
		backMountainX [2] = 2* backMountainX[1];

		frontMountainX[0] = 0;
		frontMountainX[1] = Gdx.graphics.getWidth();
		frontMountainX[2] = 2* frontMountainX[1];

		backTreeX[0] = 0;
		backTreeX[1] = Gdx.graphics.getWidth();
		backTreeX[2] = 2*backTreeX[1];

		frontTreeX[0] = 0; //(Gdx.graphics.getWidth() - frontTree.getWidth()/2 + 3 * distance +400);
		frontTreeX[1] = Gdx.graphics.getWidth();
		frontTreeX[2] = 2*frontTreeX[1];

		apricotRandomX = 3* Gdx.graphics.getWidth();
		deadmode = false;

		for ( int i =0; i < numberOfEnemy; i++){

			enemyX [i]= Gdx.graphics.getWidth() - enemy1.getWidth()/2 + i * distance +400;


			random = new Random();

			apricotRandomY = random.nextInt(Gdx.graphics.getHeight()-200);
			enemyOfSett[i] = (random.nextInt(200));
			enemyOfSett2[i] = random.nextInt(200);
			enemyOfSett3[i] = random.nextInt(200);

			enemyOfSettX[i] = random.nextInt(300)-150;
			enemyOfSettX2[i] = random.nextInt(300)-150;
			enemyOfSettX3[i] = random.nextInt(300)-150;

			enemyCircle[i] = new Rectangle();
			enemyCircle2[i] = new Rectangle();
			enemyCircle3[i] = new Rectangle();


		}

		apricotCircle = new Circle();
		adCounter =0;
	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0	, 0 , Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (Gdx.input.justTouched() && deadmode == false){

			gameState = 1;

			velocity = -17;
		}
		if (gameState == 1){

			if (Gdx.input.justTouched() && deadmode == false) {

				long id = jumpSound.play(0.5f);
				jumpSound.setPitch(id , 2);
				//jumpSound.setLooping(id, false);

				score++;
			}

			record = false;

			for (int i = 0; i < backgroundCount; i++) {

				if (backMountainX[i] + Gdx.graphics.getWidth() < 0) {

					backMountainX[i] += 2 * distance * 5;


				} else {
					backMountainX[i] -= backMountainVelocity;
					batch.draw(backMountain, backMountainX[i], Gdx.graphics.getHeight() / 2000, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				}
			}
			for (int i = 0 ; i < backgroundCount; i++) {
				if (frontMountainX[i] + Gdx.graphics.getWidth() < 0) {

					frontMountainX[i] += 2 * distance * 5;

				}else {
					frontMountainX[i] -= frontMountainVelocity;
					batch.draw(frontMountain, frontMountainX[i], Gdx.graphics.getHeight() / 2000, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				}

			}
			for ( int i = 0 ;i  < backgroundCount; i++){
				if (backTreeX[i] + Gdx.graphics.getWidth() < 0){

					backTreeX[i] += 2 * distance*5;

				}else {
					backTreeX[i] -= backTreeVelocity;
					batch.draw(backTree, backTreeX[i], Gdx.graphics.getHeight() / 2000, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				}
			}
			for (int i = 0;  i < backgroundCount; i++) {

				if (frontTreeX[i] + Gdx.graphics.getWidth() < 0) {

					frontTreeX[i] += 2 * distance * 5;

				}else {
					frontTreeX[i] -= frontTreeVelocity;
					batch.draw(frontTree, frontTreeX[i], Gdx.graphics.getHeight() / 2000, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				}


			}
			for (int i =0; i< numberOfEnemy; i++) {

				if ( enemyX[i] < -200 ){

					enemyX[i] += numberOfEnemy * distance;

					enemyOfSett[i] = random.nextInt(Gdx.graphics.getHeight()/4);
					enemyOfSett2[i] = random.nextInt(Gdx.graphics.getHeight()/4);
					enemyOfSett3[i] = random.nextInt(Gdx.graphics.getHeight()/4);

					enemyOfSettX[i] = random.nextInt(Gdx.graphics.getHeight()/6) ;
					enemyOfSettX2[i] = random.nextInt(Gdx.graphics.getHeight()/6);
					enemyOfSettX3[i] = random.nextInt(Gdx.graphics.getHeight()/6);



				}else {

					enemyX[i] = enemyX[i] - enemyVelocity;
				}
				batch.draw(enemy1, enemyX[i] + enemyOfSettX[i], Gdx.graphics.getHeight() - Gdx.graphics.getHeight() * 5/54 - enemyOfSett[i], Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/8);
				batch.draw(enemy2, enemyX[i] + enemyOfSettX2[i], Gdx.graphics.getHeight()/2 - Gdx.graphics.getHeight() * 5/27 + enemyOfSett2[i], Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/8);
				batch.draw(enemy3, enemyX[i] + enemyOfSettX3[i], Gdx.graphics.getHeight() * 1/108 + enemyOfSett3[i], Gdx.graphics.getWidth() /15, Gdx.graphics.getHeight()/8);

				enemyCircle[i] = new Rectangle(enemyX[i] + enemyOfSettX[i] + Gdx.graphics.getWidth() / 120, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() * 11 / 210 - enemyOfSett[i], Gdx.graphics.getWidth() / 23, Gdx.graphics.getHeight() / 23);
				enemyCircle2[i] = new Rectangle(enemyX[i] + enemyOfSettX2[i] + Gdx.graphics.getWidth()/120, Gdx.graphics.getHeight()/2 - Gdx.graphics.getHeight() * 31/216 + enemyOfSett2[i] , Gdx.graphics.getWidth()/23, Gdx.graphics.getHeight()/23 );
				enemyCircle3[i] = new Rectangle(enemyX[i] + enemyOfSettX3[i] + Gdx.graphics.getWidth()/120, Gdx.graphics.getHeight()/16 - Gdx.graphics.getHeight() * 1/108 + enemyOfSett3[i], Gdx.graphics.getWidth()/23, Gdx.graphics.getHeight()/23);
			}

			if (apricotRandomX < -200){
				apricotRandomY = random.nextInt(Gdx.graphics.getHeight()-200);
				apricotRandomX = 3*Gdx.graphics.getWidth()+Gdx.graphics.getWidth()/20;
			}else {
				apricotRandomX -= enemyVelocity;
			}
			batch.draw(apricot, apricotRandomX, apricotRandomY, Gdx.graphics.getWidth()/27, Gdx.graphics.getHeight()/12);
			apricotCircle = new Circle(apricotRandomX + Gdx.graphics.getWidth()/49, apricotRandomY+ Gdx.graphics.getHeight()/25, Gdx.graphics.getHeight()/28);

			if ( birdY < 0 ){

				if (deadmode == false) {
					long id = deadSound.play(1.0f);
					deadSound.setPitch(id, 2);
					Gdx.input.vibrate(1);

				}
				gameState =2;
			}else if (birdY > Gdx.graphics.getHeight()-100){


				velocity =0;
				velocity --;
				birdY += velocity;
				if ( Gdx.input.justTouched()){

					score --;
				}
			} else {
				velocity += gravity;
				birdY -= velocity;
			}

		}else if (gameState == 0) {
			batch.draw(backMountain, backMountainX[0], Gdx.graphics.getHeight()/2000, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			batch.draw(frontMountain,frontMountainX[0], Gdx.graphics.getHeight()/2000, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			batch.draw(backTree, backTreeX[0], Gdx.graphics.getHeight()/2000, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			batch.draw(frontTree, backTreeX[0], Gdx.graphics.getHeight()/2000, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

			if (Gdx.input.justTouched()) {

				gameState = 1;
			}
		} else if ( gameState == 2){


			if (keep > preferences.getInteger("maxScore")){

				preferences.remove("maxScore");
				preferences.flush();
				preferences.putInteger("maxScore" , keep);
				preferences.flush();

				record = true;
			}

		if (score != 0) {
			 keep = score;
			 adCounter++;

			 if (adCounter == 3){
			adsController.showAd();
			adCounter =0;
			 }
		}
			soundCtrl =1;
			score =0;

			if(Gdx.input.justTouched()){
				gameState = 1;
			}

			deadmode = false;

			apricotRandomY = random.nextInt(Gdx.graphics.getHeight()-200);

			apricotRandomX = 3+ Gdx.graphics.getWidth();
			batch.draw(backMountain, backMountainX[0], Gdx.graphics.getHeight()/2000, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			batch.draw(frontMountain,frontMountainX[0], Gdx.graphics.getHeight()/2000, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			batch.draw(backTree, backTreeX[0], Gdx.graphics.getHeight()/2000, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			batch.draw(frontTree, backTreeX[0], Gdx.graphics.getHeight()/2000, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

			birdY = Gdx.graphics.getHeight()/2;

			if (record == true) {
				maxScore.draw(batch, "Congratulations! New Maximum Score", Gdx.graphics.getWidth() / 6 + Gdx.graphics.getWidth() / 60, Gdx.graphics.getHeight() / 9);
				batch.draw(fireworks1, Gdx.graphics.getWidth() / 30 - Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 10, Gdx.graphics.getWidth() / 3 + Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() * 3 / 4);
				batch.draw(fireworks2, Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 30 - (Gdx.graphics.getWidth() / 3), Gdx.graphics.getHeight() / 6, Gdx.graphics.getWidth() / 3 + Gdx.graphics.getWidth() / 35, Gdx.graphics.getHeight() * 4 / 6);
			}

			gameOver.draw(batch, "GAME OVER" , Gdx.graphics.getWidth()/3 + Gdx.graphics.getWidth()/60, Gdx.graphics.getHeight() -Gdx.graphics.getHeight() /3);
			font.draw(batch, "Your Score = " + keep, Gdx.graphics.getWidth() / 3 + Gdx.graphics.getWidth()/14, Gdx.graphics.getHeight() / 3);
			ownScoreFont.draw(batch,"Your Own Record = " + preferences.getInteger("maxScore") , Gdx.graphics.getWidth()/35 , Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/15 );

			for ( int i =0; i < numberOfEnemy; i++){

				enemyX [i]= Gdx.graphics.getWidth() - enemy1.getWidth()/2 + i * distance +400;
				frontMountainX[0] = 0;
				frontMountainX[1] = Gdx.graphics.getWidth();
				frontMountainX[2] = 2* frontMountainX[1];

				backMountainX [0] = 0;
				backMountainX [1] = Gdx.graphics.getWidth();
				backMountainX [2] = 2* backMountainX[1];

				frontTreeX[0] = 0; //(Gdx.graphics.getWidth() - frontTree.getWidth()/2 + 3 * distance +400);
				frontTreeX[1] = Gdx.graphics.getWidth();
				frontTreeX[2] = 2*frontTreeX[1];

				backTreeX[0] = 0;
				backTreeX[1] = Gdx.graphics.getWidth();
				backTreeX[2] = 2*backTreeX[1];

				random = new Random();

				enemyOfSett[i] = (random.nextInt(200));
				enemyOfSett2[i] = random.nextInt(200);
				enemyOfSett3[i] = random.nextInt(200);

				enemyOfSettX[i] = random.nextInt(100);
				enemyOfSettX2[i] = random.nextInt(100);
				enemyOfSettX3[i] = random.nextInt(100);

				enemyCircle[i] = new Rectangle();
				enemyCircle2[i] = new Rectangle();
				enemyCircle3[i] = new Rectangle();
				velocity =0;
			}
		}

		font.draw(batch, String.valueOf(score) , Gdx.graphics.getWidth()-200, Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/15);

		if ( velocity < 0 && deadmode == false){

			batch.draw(birdup, birdX, birdY, Gdx.graphics.getWidth()/20 ,Gdx.graphics.getHeight()/10 );
			birdCircle.set(birdX+ Gdx.graphics.getWidth()/40, birdY+ Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/(22*2));

		}

		else if (velocity > 0 && deadmode == false){

			batch.draw(birddown, birdX, birdY, Gdx.graphics.getWidth()/20 ,Gdx.graphics.getHeight()/10 );
			birdCircle.set(birdX+ Gdx.graphics.getWidth()/40, birdY+ Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/(22*2));

		}else if (deadmode == true){


			batch.draw(deadbird, birdX, birdY, Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/10);
			birdCircle.set(birdX+ Gdx.graphics.getWidth()/40, birdY+ Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/(22*2));

		} else {

			batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/10);
			birdCircle.set(birdX+ Gdx.graphics.getWidth()/40, birdY+ Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/(22*2));

		}

		for (int i = 0;  i < numberOfEnemy; i++){

			if (Intersector.overlaps(birdCircle, enemyCircle[i]) || Intersector.overlaps(birdCircle, enemyCircle2[i]) || Intersector.overlaps(birdCircle, enemyCircle3[i]) ){
				deadmode = true;


				if(soundCtrl ==1) {
					long id = deadSound.play(1.0f);
					deadSound.setPitch(id, 2);
					//deadSound.setLooping(id, false);
				}
				soundCtrl++;

				if( birdY == 0){

					soundCtrl =1;
					gameState = 2;

				}
			}

			if (Intersector.overlaps(apricotCircle, enemyCircle[i]) || Intersector.overlaps(apricotCircle, enemyCircle2[i])  || Intersector.overlaps(apricotCircle, enemyCircle3[i])){

				apricotRandomX = 3* Gdx.graphics.getWidth();
			}

		}
		if (Intersector.overlaps(birdCircle, apricotCircle) && deadmode == false){

			long id = apricotSound.play(3.0f);
			apricotSound.setPitch(id, 2);
			//apricotSound.setLooping(id, false);
			score+= 10;
			apricotRandomX = 3* Gdx.graphics.getWidth();
		}

		if ( deadmode == true ){

			batch.draw(deadScreen, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			Gdx.input.vibrate(1);

		}
		batch.end();
	}

	@Override
	public void dispose () {

		batch.dispose();
		font.dispose();
		jumpSound.dispose();
		apricotSound.dispose();
		deadSound.dispose();
	}
}