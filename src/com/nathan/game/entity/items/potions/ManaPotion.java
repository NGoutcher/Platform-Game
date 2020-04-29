package com.nathan.game.entity.items.potions;

import com.nathan.game.entity.items.Item;
import com.nathan.game.graphics.Screen;
import com.nathan.game.graphics.Sprite;

public class ManaPotion extends Item {

    public double healAmount;
    public double manaAmount;

    public ManaPotion(int x, int y) {
        super(x, y, "res/textures/icons/weapons/weakstafficon.png");
        sprite = Sprite.small_mana_potion;

        name = "Mana Potion";
        setDescription("adds 20 MP when used.");
    }

    public ManaPotion() {
        name = "Mana Potion";
        setDescription("adds 20 HP when used.");
    }

    public void use() {
        owner.setManaRegen(manaAmount);
    }

    public void render(Screen screen) {
        screen.renderItem((int) (x - 16), (int) (y - 16), sprite);
    }
}
