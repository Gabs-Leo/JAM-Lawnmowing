package com.gabs.rpggame.entities.enemies;

import com.gabs.rpggame.Main;
import com.gabs.rpggame.world.Floor;
import com.gabs.rpggame.world.Position;

import java.util.ArrayList;
import java.util.List;

public class EnemySpawner {
    private List<Position> spawnPoints = new ArrayList<>();
    private Enemy entity;
    private int wave = 0;

    public EnemySpawner(){}
    public EnemySpawner(Enemy entity){
        this.entity = entity;
    }

    public void spawn(EnemyType enemyType, int amount){
        int index = 0;
        for(int i = 0; i < amount; i++){
            if (spawnPoints.size() <= index) {
                index = 0;
            } else {
                Enemy enemy = new Enemy(enemyType)
                        .setX(spawnPoints.get(index).getX())
                        .setY(spawnPoints.get(index).getY());
                Main.enemies.add(enemy);
                index++;
            }
        }
    }

    public void spawn(int DEFAULT, int RUNNER, int TANK){
        spawn(EnemyType.DEFAULT, DEFAULT);
        spawn(EnemyType.RUNNER, RUNNER);
        spawn(EnemyType.TANK, TANK);
    }

    public void spawnNextWave(){
        wave++;
        int defaultMultiplier = 3;
        int runnerMultiplier = 2;
        int tankMultiplier = 1;
        spawn(
                wave*defaultMultiplier,
                wave*runnerMultiplier,
                wave*tankMultiplier
        );
        Main.entities.forEach(i -> {
            if(i instanceof Floor){
                ((Floor) i).setMowed(false);
            }
        });
    }

    public List<Position> getSpawnPoints() {
        return spawnPoints;
    }

    public Enemy getEntity() {
        return entity;
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }
}
