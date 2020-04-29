package com.nathan.game.entity.items.weapons;

import com.nathan.game.entity.items.Item;
import com.nathan.game.graphics.Sprite;

public class Staff extends Item {

    private String name;

    public Staff(int x, int y, String name, int damgMult) {
        super(x,y, "res/textures/icons/weapons/weakstafficon.png");

        sprite = Sprite.weak_staff;

        this.name = name;
        dmgMult = damgMult;

        setDescription("increases dmg dealt");
        unique = true;
    }

    public Staff(String name, int dmgMult) {
        unique = true;
        sprite = Sprite.weak_staff;

        this.name = name;
        this.dmgMult = dmgMult;

        setDescription("increases dmg dealt");
    }

    public void setDmgMult(int dmgMult) {
        this.dmgMult = dmgMult;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasDmgMult() {
        return true;
    }
}
